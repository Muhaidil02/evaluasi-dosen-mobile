package com.android.epdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.epdm.adapter.LayananAdministrasiAdapter;
import com.android.epdm.adapter.LayananKemahasiswaanAdapter;
import com.android.epdm.model.LayananAdministrasiModel;
import com.android.epdm.model.LayananKemahasiswaanModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LayananKemahasiswaan extends AppCompatActivity {


    LayananKemahasiswaanAdapter layananKemahasiswaanAdapter;
    List<LayananKemahasiswaanModel> layananKemahasiswaanModelList;
    RecyclerView recyclerView;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_kemahasiswaan);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.pop_layananKemahasiswaan);

        LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        layananKemahasiswaanModelList = new ArrayList<>();
        layananKemahasiswaanAdapter =new LayananKemahasiswaanAdapter(getApplicationContext(), layananKemahasiswaanModelList);
        recyclerView.setAdapter(layananKemahasiswaanAdapter);

        db.collection("LayananKemahasiswaan").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                LayananKemahasiswaanModel layananKemahasiswaanModel =documentSnapshot.toObject(LayananKemahasiswaanModel.class);
                                layananKemahasiswaanModelList.add(layananKemahasiswaanModel);
                                layananKemahasiswaanAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(LayananKemahasiswaan.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}