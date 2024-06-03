package com.android.epdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.epdm.adapter.LayananKeuanganAdapter;
import com.android.epdm.adapter.LayananSaranaPrasaranaAdapter;
import com.android.epdm.dosen.DosenLayananPenelitian;
import com.android.epdm.model.DosenLayananPenelitianModel;
import com.android.epdm.model.HasilKuisionerModel;
import com.android.epdm.model.LayananKeuanganModel;
import com.android.epdm.model.LayananSaranaPrasaranaModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LayananKeuangan extends AppCompatActivity {

    LayananKeuanganAdapter layananKeuanganAdapter;
    List<LayananKeuanganModel> layananKeuanganModelList;
    RecyclerView recyclerView;
    Button btnSimpan;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_keuangan);

        btnSimpan = findViewById(R.id.btnSimpan);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.pop_layananKeuangan);

        LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        layananKeuanganModelList = new ArrayList<>();
        layananKeuanganAdapter =new LayananKeuanganAdapter(getApplicationContext(), layananKeuanganModelList);
        recyclerView.setAdapter(layananKeuanganAdapter);

        db.collection("LayananKeuangan").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                LayananKeuanganModel layananKeuanganModel =documentSnapshot.toObject(LayananKeuanganModel.class);
                                layananKeuanganModelList.add(layananKeuanganModel);
                                layananKeuanganAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(LayananKeuangan.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        btnSimpan.setOnClickListener(view -> simpanHasilKuisioner());
    }

    private void simpanHasilKuisioner() {
        List<HasilKuisionerModel> hasilKuisionerList = new ArrayList<>();
        for (LayananKeuanganModel model : layananKeuanganModelList) {
            int selectedOption = model.getPilihanTerpilih(); // Pastikan Anda mendapatkan opsi yang dipilih dari model
            HasilKuisionerModel hasilKuisioner = new HasilKuisionerModel(model.getPertanyaanId(), model.getPertanyaan(), selectedOption);
            hasilKuisionerList.add(hasilKuisioner);
        }

        for (HasilKuisionerModel hasil : hasilKuisionerList) {
            db.collection("ResponsKeuangan")
                    .add(hasil)
                    .addOnSuccessListener(documentReference -> {
                        // Berhasil menyimpan data
                        Toast.makeText(LayananKeuangan.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Gagal menyimpan data
                        Toast.makeText(LayananKeuangan.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
