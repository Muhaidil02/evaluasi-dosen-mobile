package com.android.epdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MahasiswaActivity extends AppCompatActivity {

    CardView btnsarana, btnkemahasiswaan, btnpembelajaran, btnadministrasi, btncivitasakademik
            ,btncivitassarana, btnKeuangan, btnAdmin;
    TextView ucapanHalo;
    DatabaseReference reference;
    FirebaseUser user;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        btnsarana = findViewById(R.id.btnsarana);
        ucapanHalo = findViewById(R.id.ucapanHaloMahasiswa);


        btnpembelajaran = findViewById(R.id.btnPembelajaran);
        btnadministrasi = findViewById(R.id.btnadministrasi);
        btnKeuangan= findViewById(R.id.btnKeuangan);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Mahasiswa");
        userId = user.getUid();

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String username =snapshot.child("username").getValue(String.class);
                    ucapanHalo.setText("Halo "+username +"!");
                }else {
                    Toast.makeText(MahasiswaActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnsarana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LayananSaranaPrasarana.class);
                startActivity(i);
            }
        });


        btnpembelajaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LayananProsesPembelajaran.class);
                startActivity(i);
            }
        });



        btnadministrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LayananAdministrasiAkademik.class);
                startActivity(i);
            }
        });
        btnKeuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LayananKeuangan.class);
                startActivity(i);
            }
        });



    }
}
