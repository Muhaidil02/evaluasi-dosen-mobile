package com.android.epdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.epdm.dosen.DosenCivitasAkademika;
import com.android.epdm.dosen.DosenLayananPenelitian;
import com.android.epdm.dosen.DosenTataPamong;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DosenActivity extends AppCompatActivity {

    CardView btntatapamong, btncivitasakademik, btnPengabdiLayanan, btnLayananpenelitian;

    DatabaseReference reference;
    FirebaseUser user;
    String userId;
    TextView ucapanHalo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen);

        ucapanHalo = findViewById(R.id.ucapanHaloDosen);
        btntatapamong = findViewById(R.id.btnTataPamong);
        btncivitasakademik = findViewById(R.id.btncivitasakademika);


        btnLayananpenelitian= findViewById(R.id.btnlayananpenelitian);
        btnPengabdiLayanan= findViewById(R.id.btnpengabdilayanan);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Dosen");
        userId = user.getUid();

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String username = snapshot.child("namaDosen").getValue(String.class);
                    ucapanHalo.setText("Halo "+username+"!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DosenActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        btntatapamong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DosenTataPamong.class);
                startActivity(i);
            }
        });

        btncivitasakademik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DosenCivitasAkademika.class);
                startActivity(i);

            btnLayananpenelitian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), DosenLayananPenelitian.class);
                        startActivity(i);
                    }
                });

       btnPengabdiLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DosenPengabdiLayanan.class);
                startActivity(i);
            }
        });

            }
        });
    }
}