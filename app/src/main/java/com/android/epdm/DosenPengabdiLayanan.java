package com.android.epdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.epdm.adapter.DosenPengabdiLayananAdapter;
import com.android.epdm.model.DosenPengabdiLayananModel;
import com.android.epdm.model.HasilKuisionerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DosenPengabdiLayanan extends AppCompatActivity {

    DosenPengabdiLayananAdapter dosenPengabdiLayananAdapter;
    List<DosenPengabdiLayananModel> dosenPengabdiLayananModelList;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen_pengabdi_layanan);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.pop_dosenPengabdiLayanan);

        button = findViewById(R.id.btnSimpan);

        LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        dosenPengabdiLayananModelList = new ArrayList<>();
        dosenPengabdiLayananAdapter =new DosenPengabdiLayananAdapter(getApplicationContext(), dosenPengabdiLayananModelList);
        recyclerView.setAdapter(dosenPengabdiLayananAdapter);

        db.collection("DosenPengabdiLayanan").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                DosenPengabdiLayananModel dosenPengabdiLayananModel =documentSnapshot.toObject(DosenPengabdiLayananModel.class);
                                dosenPengabdiLayananModelList.add(dosenPengabdiLayananModel);
                                dosenPengabdiLayananAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(DosenPengabdiLayanan.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        button.setOnClickListener(view -> simpanHasilKuisioner());
    }

    private void simpanHasilKuisioner() {
        List<HasilKuisionerModel> hasilKuisionerList = new ArrayList<>();
        for (DosenPengabdiLayananModel model : dosenPengabdiLayananModelList) {
            int selectedOption = model.getPilihanTerpilih(); // Pastikan Anda mendapatkan opsi yang dipilih dari model
            HasilKuisionerModel hasilKuisioner = new HasilKuisionerModel(model.getPertanyaanId(), model.getPertanyaan(), selectedOption);
            hasilKuisionerList.add(hasilKuisioner);
        }

        for (HasilKuisionerModel hasil : hasilKuisionerList) {
            db.collection("ResponsPengabdiLayanan")
                    .add(hasil)
                    .addOnSuccessListener(documentReference -> {
                        // Berhasil menyimpan data
                        Toast.makeText(DosenPengabdiLayanan.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Gagal menyimpan data
                        Toast.makeText(DosenPengabdiLayanan.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
