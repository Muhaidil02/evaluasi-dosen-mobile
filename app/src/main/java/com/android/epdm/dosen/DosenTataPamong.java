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
import com.android.epdm.adapter.DosenTataPamongAdapter;
import com.android.epdm.model.DosenLayananPenelitianModel;
import com.android.epdm.model.DosenTataPamongModel;
import com.android.epdm.model.HasilKuisionerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DosenTataPamong extends AppCompatActivity {

    DosenTataPamongAdapter dosenTataPamongAdapter;
    List<DosenTataPamongModel> dosenTataPamongModelList;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    Button btnSimpan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen_tata_pamong);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.pop_dosenTatapamong);

        btnSimpan = findViewById(R.id.btnSimpan);

        LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        dosenTataPamongModelList = new ArrayList<>();
        dosenTataPamongAdapter =new DosenTataPamongAdapter(getApplicationContext(), dosenTataPamongModelList);
        recyclerView.setAdapter(dosenTataPamongAdapter);

        db.collection("DosenTataPamong").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                DosenTataPamongModel dosenTataPamongModel =documentSnapshot.toObject(DosenTataPamongModel.class);
                                dosenTataPamongModelList.add(dosenTataPamongModel);
                                dosenTataPamongAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(DosenTataPamong.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        btnSimpan.setOnClickListener(view -> simpanHasilKuisioner());
    }

    private void simpanHasilKuisioner() {
        List<HasilKuisionerModel> hasilKuisionerList = new ArrayList<>();
        for (DosenTataPamongModel model : dosenTataPamongModelList) {
            int selectedOption = model.getPilihanTerpilih(); // Pastikan Anda mendapatkan opsi yang dipilih dari model
            HasilKuisionerModel hasilKuisioner = new HasilKuisionerModel(model.getPertanyaanId(), model.getPertanyaan(), selectedOption);
            hasilKuisionerList.add(hasilKuisioner);
        }

        for (HasilKuisionerModel hasil : hasilKuisionerList) {
            db.collection("ResponsTataPamong")
                    .add(hasil)
                    .addOnSuccessListener(documentReference -> {
                        // Berhasil menyimpan data
                        Toast.makeText(DosenTataPamong.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Gagal menyimpan data
                        Toast.makeText(DosenTataPamong.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
