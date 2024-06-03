package com.android.epdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PilihanAdmin extends AppCompatActivity {

TextView lihatAkun, hasilKuesioner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan_admin);
        lihatAkun = findViewById(R.id.lihatAkun);
        hasilKuesioner = findViewById(R.id.hasilKuisoner);

        lihatAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PilihanAdmin.this, AkunAdmin.class));
            }
        });

        hasilKuesioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PilihanAdmin.this, HasilKuesionerAdmin.class));
            }
        });
    }
}