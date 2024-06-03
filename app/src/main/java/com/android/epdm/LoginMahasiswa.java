package com.android.epdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginMahasiswa extends AppCompatActivity {
    EditText nimLogin, passwordLogin;
    TextView loginButton;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mahasiswa);

        nimLogin = findViewById(R.id.Loginnimmhs);
        passwordLogin = findViewById(R.id.Loginpasswordmhs);
        loginButton = findViewById(R.id.btnLoginMhs);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Mahasiswa");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
                startActivity(new Intent(LoginMahasiswa.this, MahasiswaActivity.class));
            }
        });
        ;
    }

    private void loginUser() {
        String strNim = nimLogin.getText().toString().trim();
        String strPassword = passwordLogin.getText().toString().trim();

        if (TextUtils.isEmpty(strNim)) {
            nimLogin.setError("Masukkan NIM");
            return;
        }

        if (TextUtils.isEmpty(strPassword)) {
            passwordLogin.setError("Masukkan Password");
            return;
        }

        databaseReference.orderByChild("nim").equalTo(strNim).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        Mahasiswa mahasiswa = userSnapshot.getValue(Mahasiswa.class);
                        if (mahasiswa != null) {
                            String email = mahasiswa.email;
                            auth.signInWithEmailAndPassword(email, strPassword)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginMahasiswa.this, "Login berhasil!", Toast.LENGTH_SHORT).show();
                                            // Navigasi ke halaman utama
                                        } else {
                                            Toast.makeText(LoginMahasiswa.this, "Login gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                } else {
                    nimLogin.setError("NIM tidak ditemukan");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginMahasiswa.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class Mahasiswa {
    public String nim;
    public String fullName;
    public String email;

    public Mahasiswa() {
        // Default constructor required for calls to DataSnapshot.getValue(Mahasiswa.class)
    }

    public Mahasiswa(String nim, String fullName, String email) {
        this.nim = nim;
        this.fullName = fullName;
        this.email = email;
    }
}