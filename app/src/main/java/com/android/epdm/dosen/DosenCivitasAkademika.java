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
import com.android.epdm.adapter.DosenCivitasAkademikaAdapter;
import com.android.epdm.adapter.KuisonerMhsAdapter;
import com.android.epdm.model.DosenCivitasAkademikaModel;
import com.android.epdm.model.DosenLayananPenelitianModel;
import com.android.epdm.model.HasilKuisionerModel;
import com.android.epdm.model.KuisonerMhsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DosenCivitasAkademika extends AppCompatActivity {


    DosenCivitasAkademikaAdapter dosenCivitasAkademikaAdapter;
    List<DosenCivitasAkademikaModel> dosenCivitasAkademikaModelList;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    Button btnSimpan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen_civitas_akademika);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.pop_dosencivitasAkademika);

        btnSimpan = findViewById(R.id.btnSimpan);

        LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        dosenCivitasAkademikaModelList = new ArrayList<>();
        dosenCivitasAkademikaAdapter =new DosenCivitasAkademikaAdapter(getApplicationContext(), dosenCivitasAkademikaModelList);
        recyclerView.setAdapter(dosenCivitasAkademikaAdapter);

        db.collection("DosenCivitasAkademika").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                DosenCivitasAkademikaModel dosenCivitasAkademikaModel =documentSnapshot.toObject(DosenCivitasAkademikaModel.class);
                                dosenCivitasAkademikaModelList.add(dosenCivitasAkademikaModel);
                                dosenCivitasAkademikaAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(DosenCivitasAkademika.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        btnSimpan.setOnClickListener(view -> simpanHasilKuisioner());
    }

    private void simpanHasilKuisioner() {
        List<HasilKuisionerModel> hasilKuisionerList = new ArrayList<>();
        for (DosenCivitasAkademikaModel model : dosenCivitasAkademikaModelList) {
            int selectedOption = model.getPilihanTerpilih(); // Pastikan Anda mendapatkan opsi yang dipilih dari model
            HasilKuisionerModel hasilKuisioner = new HasilKuisionerModel(model.getPertanyaanId(), model.getPertanyaan(), selectedOption);
            hasilKuisionerList.add(hasilKuisioner);
        }

        for (HasilKuisionerModel hasil : hasilKuisionerList) {
            db.collection("ResponsCivitasAkademik")
                    .add(hasil)
                    .addOnSuccessListener(documentReference -> {
                        // Berhasil menyimpan data
                        Toast.makeText(DosenCivitasAkademika.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Gagal menyimpan data
                        Toast.makeText(DosenCivitasAkademika.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
