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

import com.android.epdm.model.MahasiswaModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignMahasiswa extends AppCompatActivity {
    EditText nim, full_name,email,password,confirm_password;
    TextView register, sudahPunyaAkun;
    FirebaseAuth auth;
    ProgressBar progressBar;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_mahasiswa);
        nim = findViewById(R.id.nim);
        full_name= findViewById(R.id.full_name);
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
        confirm_password= findViewById(R.id.confirm_password);
        register= findViewById(R.id.register);
        sudahPunyaAkun= findViewById(R.id.login);

        progressBar= findViewById(R.id.progresBar);
        progressBar.setVisibility(View.INVISIBLE);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        register.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String strNim = nim.getText().toString().trim();
        String strFullName = full_name.getText().toString().trim();
        String strEmail = email.getText().toString().trim();
        String strPassword = password.getText().toString().trim();
        String strConfirmPassword = confirm_password.getText().toString().trim();

        if (TextUtils.isEmpty(strNim)){
            nim.setError("Masukkan Nim Terlebih Dahulu");
            return;
        }
        if (TextUtils.isEmpty(strFullName)){
            full_name.setError("Masukkan Nama Lengkap Terlebih Dahulu");
            return;
        }
        if (TextUtils.isEmpty(strEmail)){
            email.setError("Masukkan Email Terlebih Dahulu");
            return;
        }
        if (TextUtils.isEmpty(strPassword)){
            password.setError("Masukkan Password Terlebih Dahulu");
            return;
        }
        if (TextUtils.isEmpty(strConfirmPassword)){
            confirm_password.setError("Masukkan Konfirmasi Password Terlebih Dahulu");
            return;
        }
        if (!strPassword.equals(strConfirmPassword)){
            password.setError("Password Harus Sama");
            return;
        }
        if (strPassword.length()<6){
            password.setError("Password harus lebih dari 6 Karakter");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        register.setVisibility(View.INVISIBLE);

        auth.createUserWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    MahasiswaModel model = new MahasiswaModel(strNim,strFullName,strEmail,strPassword,strConfirmPassword);
                                    String id = task.getResult().getUser().getUid();
                                    database.getReference().child("Mahasiswa").child(id).setValue(model);
                                    Toast.makeText(SignMahasiswa.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignMahasiswa.this, LoginMahasiswa.class));
                                    finish();
                                }
                            },1500);
                        }else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    register.setVisibility(View.VISIBLE);
                                    Toast.makeText(SignMahasiswa.this, "Registrasi Gagal", Toast.LENGTH_SHORT).show();
                                }
                            },1500);
                        }
                    }
                });
    }
}