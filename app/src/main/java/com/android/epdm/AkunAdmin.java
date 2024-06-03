package com.android.epdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.epdm.adapter.DosenAdapter;
import com.android.epdm.adapter.MahasiswaAdapter;
import com.android.epdm.model.DosenModel;
import com.android.epdm.model.MahasiswaModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AkunAdmin extends AppCompatActivity {
    RecyclerView recyclerViewDosen, recyclerViewMahasiswa;
    List<DosenModel> dosenList;
    List<MahasiswaModel> mahasiswaList;
    DosenAdapter dosenAdapter;
    MahasiswaAdapter  mahasiswaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_admin);

        recyclerViewDosen = findViewById(R.id.recyclerViewDosen);
        recyclerViewMahasiswa = findViewById(R.id.recyclerViewMahasiswa);

        recyclerViewDosen.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMahasiswa.setLayoutManager(new LinearLayoutManager(this));

        dosenList = new ArrayList<>();
        mahasiswaList = new ArrayList<>();

        dosenAdapter = new DosenAdapter(dosenList, this);
        mahasiswaAdapter = new MahasiswaAdapter(mahasiswaList, this);

        recyclerViewDosen.setAdapter(dosenAdapter);
        recyclerViewMahasiswa.setAdapter(mahasiswaAdapter);

        loadAkun();
    }

    private void loadAkun() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference("Dosen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dosenList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DosenModel dosen = dataSnapshot.getValue(DosenModel.class);
                    if (dosen != null) {
                        dosen.setId(dataSnapshot.getKey());
                        dosenList.add(dosen);
                    }
                }
                Log.d("AkunAdmin", "Dosen List: " + dosenList.size());
                dosenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("AkunAdmin", "Failed to load dosen accounts.", databaseError.toException());
                Toast.makeText(AkunAdmin.this, "Failed to load dosen accounts.", Toast.LENGTH_SHORT).show();
            }
        });

        database.getReference("Mahasiswa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mahasiswaList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MahasiswaModel mahasiswa = dataSnapshot.getValue(MahasiswaModel.class);
                    if (mahasiswa != null) {
                        mahasiswa.setId(dataSnapshot.getKey());
                        mahasiswaList.add(mahasiswa);
                    }
                }
                Log.d("AkunAdmin", "Mahasiswa List: " + mahasiswaList.size());
                mahasiswaAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("AkunAdmin", "Failed to load mahasiswa accounts.", databaseError.toException());
                Toast.makeText(AkunAdmin.this, "Failed to load mahasiswa accounts.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}