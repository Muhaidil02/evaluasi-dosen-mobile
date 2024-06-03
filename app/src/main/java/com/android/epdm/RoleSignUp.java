package com.android.epdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RoleSignUp extends AppCompatActivity {
    TextView mahasiswa, dosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_sign_up);

        mahasiswa = findViewById(R.id.mahasiswa);
        dosen = findViewById(R.id.dosen);

        mahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignMahasiswa.class);
                startActivity(i);
            }
        });
        dosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpDosen.class);
                startActivity(i);
            }
        });


    }
}