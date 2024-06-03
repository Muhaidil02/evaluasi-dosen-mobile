package com.android.epdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.epdm.adapter.LayananAdministrasiAdapter;
import com.android.epdm.model.LayananAdministrasiModel;
import com.android.epdm.model.HasilKuisionerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LayananAdministrasiAkademik extends AppCompatActivity {

    LayananAdministrasiAdapter layananAdministrasiAdapter;
    List<LayananAdministrasiModel> layananAdministrasiModelList;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_sarana_prasarana);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.pop_layananAdministrasi);
        btnSimpan = findViewById(R.id.btnSimpan);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        layananAdministrasiModelList = new ArrayList<>();
        layananAdministrasiAdapter = new LayananAdministrasiAdapter(getApplicationContext(), layananAdministrasiModelList);
        recyclerView.setAdapter(layananAdministrasiAdapter);

        db.collection("LayananAdministrasiAkademik").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                LayananAdministrasiModel layananAdministrasiModel = documentSnapshot.toObject(LayananAdministrasiModel.class);
                                layananAdministrasiModelList.add(layananAdministrasiModel);
                                layananAdministrasiAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(LayananAdministrasiAkademik.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        btnSimpan.setOnClickListener(view -> simpanHasilKuisioner());
    }

    private void simpanHasilKuisioner() {
        List<HasilKuisionerModel> hasilKuisionerList = new ArrayList<>();
        for (LayananAdministrasiModel model : layananAdministrasiModelList) {
            int selectedOption = model.getPilihanTerpilih();
            HasilKuisionerModel hasilKuisioner = new HasilKuisionerModel(model.getPertanyaanId(), model.getPertanyaan(), selectedOption);
            hasilKuisionerList.add(hasilKuisioner);
        }

        for (HasilKuisionerModel hasil : hasilKuisionerList) {
            db.collection("ResponsAkademik")
                    .add(hasil)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(LayananAdministrasiAkademik.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(LayananAdministrasiAkademik.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
