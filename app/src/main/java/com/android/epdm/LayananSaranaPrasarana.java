package com.android.epdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.epdm.adapter.LayananSaranaPrasaranaAdapter;
import com.android.epdm.model.HasilKuisionerModel;
import com.android.epdm.model.LayananSaranaPrasaranaModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LayananSaranaPrasarana extends AppCompatActivity {

    LayananSaranaPrasaranaAdapter layananSaranaPrasaranaAdapter;
    List<LayananSaranaPrasaranaModel> layananSaranaPrasaranaModelList;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    Button btnsimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_sarana_prasarana);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.pop_layananAdministrasi);
        btnsimpan = findViewById(R.id.btnSimpan);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        layananSaranaPrasaranaModelList = new ArrayList<>();
        layananSaranaPrasaranaAdapter = new LayananSaranaPrasaranaAdapter(getApplicationContext(), layananSaranaPrasaranaModelList);
        recyclerView.setAdapter(layananSaranaPrasaranaAdapter);

        db.collection("LayananSaranaPrasarana").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                LayananSaranaPrasaranaModel layananSaranaPrasaranaModel = documentSnapshot.toObject(LayananSaranaPrasaranaModel.class);
                                layananSaranaPrasaranaModelList.add(layananSaranaPrasaranaModel);
                                layananSaranaPrasaranaAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(LayananSaranaPrasarana.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        btnsimpan.setOnClickListener(view -> simpanHasilKuisioner());
    }

    private void simpanHasilKuisioner() {
        List<HasilKuisionerModel> hasilKuisionerList = new ArrayList<>();
        for (LayananSaranaPrasaranaModel model : layananSaranaPrasaranaModelList) {
            int selectedOption = model.getPilihanTerpilih(); // Pastikan Anda mendapatkan opsi yang dipilih dari model
            HasilKuisionerModel hasilKuisioner = new HasilKuisionerModel(model.getPertanyaanId(), model.getPertanyaan(), selectedOption);
            hasilKuisionerList.add(hasilKuisioner);
        }

        for (HasilKuisionerModel hasil : hasilKuisionerList) {
            db.collection("ResponsesSaranaPrasarana")
                    .add(hasil)
                    .addOnSuccessListener(documentReference -> {
                        // Berhasil menyimpan data
                        Toast.makeText(LayananSaranaPrasarana.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Gagal menyimpan data
                        Toast.makeText(LayananSaranaPrasarana.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}