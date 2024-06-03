package com.android.epdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginDosen extends AppCompatActivity {
    EditText emailDosenLogin, passwordDosenLogin;
    TextView loginDosen;
    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dosen);
        emailDosenLogin = findViewById(R.id.email);
        passwordDosenLogin = findViewById(R.id.password);
        loginDosen = findViewById(R.id.login);

        auth = FirebaseAuth.getInstance();

        loginDosen.setOnClickListener(view -> loginUser());
    }

    private void loginUser() {
        String strEmailDosenLogin = emailDosenLogin.getText().toString().trim();
        String strPasswordDosenLogin = passwordDosenLogin.getText().toString().trim();

        if (TextUtils.isEmpty(strEmailDosenLogin)){
            emailDosenLogin.setError("Masukkan Email Terlebih Dahulu");
            return;
        }
        if (TextUtils.isEmpty(strPasswordDosenLogin)){
            passwordDosenLogin.setError("Masukkan Password Terlebih Dahulu");
            return;
        }


        auth.signInWithEmailAndPassword(strEmailDosenLogin, strPasswordDosenLogin)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String userId = auth.getCurrentUser().getUid();
                            DatabaseReference dosenReference = FirebaseDatabase.getInstance().getReference().child("Dosen").child(userId);

                            dosenReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        Toast.makeText(LoginDosen.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                        // Proceed to the next activity after login
                                         startActivity(new Intent(LoginDosen.this, DosenActivity.class));
                                         finish();
                                    } else {
                                        auth.signOut();
                                        Toast.makeText(LoginDosen.this, "Login Gagal: Akun tidak ditemukan sebagai Dosen", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(LoginDosen.this, "Login Gagal: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(LoginDosen.this, "Login Gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
