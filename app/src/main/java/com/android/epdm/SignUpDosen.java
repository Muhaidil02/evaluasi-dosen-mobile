package com.android.epdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.epdm.model.DosenModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpDosen extends AppCompatActivity {
    EditText namaDosen, emailDosen, passwordDosen, alamatDosen, jenisKelamin, lamaBekerja, statusKepegawaian, jabatanFungsional;
    TextView registerDosen;
    FirebaseAuth auth;
    ProgressBar progressBar;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_dosen);
        namaDosen = findViewById(R.id.namaDosen);
        emailDosen = findViewById(R.id.emailDosen);
        passwordDosen = findViewById(R.id.passwordDosen);
        alamatDosen = findViewById(R.id.alamatDosen);
        jenisKelamin = findViewById(R.id.jenisKelaminDosen);
        lamaBekerja = findViewById(R.id.lamaBekerjaDosen);
        statusKepegawaian = findViewById(R.id.statusKepegawaianDosen);
        jabatanFungsional = findViewById(R.id.jabatanFungsionalDosen);
        registerDosen = findViewById(R.id.register);

        progressBar = findViewById(R.id.progresBar);
        progressBar.setVisibility(View.INVISIBLE);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        registerDosen.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String strNamaDosen = namaDosen.getText().toString().trim();
        String strEmailDosen = emailDosen.getText().toString().trim();
        String strPasswordDosen = passwordDosen.getText().toString().trim();
        String strAlamatDosen = alamatDosen.getText().toString().trim();
        String strJenisKelamin = jenisKelamin.getText().toString().trim();
        String strLamaBekerja = lamaBekerja.getText().toString().trim();
        String strStatusKepegawaian = statusKepegawaian.getText().toString().trim();
        String strJabatanFungsional = jabatanFungsional.getText().toString().trim();

        if (TextUtils.isEmpty(strNamaDosen)){
            namaDosen.setError("Masukkan Nama Dosen Terlebih Dahulu");
            return;
        }
        if (TextUtils.isEmpty(strEmailDosen)){
            emailDosen.setError("Masukkan Email Terlebih Dahulu");
            return;
        }
        if (TextUtils.isEmpty(strPasswordDosen)){
            passwordDosen.setError("Masukkan Password Terlebih Dahulu");
            return;
        }
        if (TextUtils.isEmpty(strAlamatDosen)){
            alamatDosen.setError("Masukkan Alamat Terlebih Dahulu");
            return;
        }
        if (TextUtils.isEmpty(strJenisKelamin)){
            jenisKelamin.setError("Masukkan Jenis Kelamin Terlebih Dahulu");
            return;
        }
        if (TextUtils.isEmpty(strLamaBekerja)){
            lamaBekerja.setError("Masukkan Lama Bekerja Terlebih Dahulu");
            return;
        }
        if (TextUtils.isEmpty(strStatusKepegawaian)){
            statusKepegawaian.setError("Masukkan Status Kepegawaian Terlebih Dahulu");
            return;
        }
        if (TextUtils.isEmpty(strJabatanFungsional)){
            jabatanFungsional.setError("Masukkan Jabatan Fungsional Terlebih Dahulu");
            return;
        }
        if (strPasswordDosen.length()<6){
            passwordDosen.setError("Password harus lebih dari 6 Karakter");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        registerDosen.setVisibility(View.INVISIBLE);

        auth.createUserWithEmailAndPassword(strEmailDosen, strPasswordDosen)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    DosenModel model = new DosenModel(strNamaDosen, strEmailDosen, strPasswordDosen, strAlamatDosen, strJenisKelamin, strLamaBekerja, strStatusKepegawaian, strJabatanFungsional);
                                    String id = task.getResult().getUser().getUid();
                                    DatabaseReference dosenReference = database.getReference().child("Dosen").child(id);
                                    dosenReference.setValue(model)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(SignUpDosen.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(SignUpDosen.this, LoginDosen.class));
                                                        finish();
                                                    } else {
                                                        progressBar.setVisibility(View.GONE);
                                                        registerDosen.setVisibility(View.VISIBLE);
                                                        Toast.makeText(SignUpDosen.this, "Registrasi Gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            },1500);
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    registerDosen.setVisibility(View.VISIBLE);
                                    Toast.makeText(SignUpDosen.this, "Registrasi Gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },1500);
                        }
                    }
                });
    }
}