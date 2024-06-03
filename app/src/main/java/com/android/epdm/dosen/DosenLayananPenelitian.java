package com.android.epdm.dosen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.epdm.R;
import com.android.epdm.adapter.DosenLayananPenelitianAdapter;
import com.android.epdm.model.DosenLayananPenelitianModel;
import com.android.epdm.model.HasilKuisionerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DosenLayananPenelitian extends AppCompatActivity {

    DosenLayananPenelitianAdapter dosenLayananPenelitianAdapter;
    List<DosenLayananPenelitianModel> dosenLayananPenelitianModelList;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    Button btnsimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen_layanan_penelitian);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.pop_LayananPenelitian);
        btnsimpan = findViewById(R.id.btnSimpan);

        LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        dosenLayananPenelitianModelList = new ArrayList<>();
        dosenLayananPenelitianAdapter =new DosenLayananPenelitianAdapter(getApplicationContext(), dosenLayananPenelitianModelList);
        recyclerView.setAdapter(dosenLayananPenelitianAdapter);

        db.collection("DosenLayananPenelitian").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                DosenLayananPenelitianModel dosenLayananPenelitianModel =documentSnapshot.toObject(DosenLayananPenelitianModel.class);
                                dosenLayananPenelitianModelList.add(dosenLayananPenelitianModel);
                                dosenLayananPenelitianAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(DosenLayananPenelitian.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        btnsimpan.setOnClickListener(view -> simpanHasilKuisioner());
    }

    private void simpanHasilKuisioner() {
        List<HasilKuisionerModel> hasilKuisionerList = new ArrayList<>();
        for (DosenLayananPenelitianModel model : dosenLayananPenelitianModelList) {
            int selectedOption = model.getPilihanTerpilih(); // Pastikan Anda mendapatkan opsi yang dipilih dari model
            HasilKuisionerModel hasilKuisioner = new HasilKuisionerModel(model.getPertanyaanId(), model.getPertanyaan(), selectedOption);
            hasilKuisionerList.add(hasilKuisioner);
        }

        for (HasilKuisionerModel hasil : hasilKuisionerList) {
            db.collection("Responses")
                    .add(hasil)
                    .addOnSuccessListener(documentReference -> {
                        // Berhasil menyimpan data
                        Toast.makeText(DosenLayananPenelitian.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Gagal menyimpan data
                        Toast.makeText(DosenLayananPenelitian.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
