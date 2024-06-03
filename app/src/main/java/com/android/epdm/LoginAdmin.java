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

public class LoginAdmin extends AppCompatActivity {
    EditText emailAdmin, passwordAdmin;
    TextView btnLoginAdmin;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        emailAdmin = findViewById(R.id.emailAdmin);
        passwordAdmin = findViewById(R.id.passwordAdmin);
        btnLoginAdmin = findViewById(R.id.btnLoginAdmin);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin");

        btnLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser();
                startActivity(new Intent(LoginAdmin.this, PilihanAdmin.class));
            }
        });
    }

    private void loginuser() {
        String strEmail = emailAdmin.getText().toString().trim();
        String strPassword = passwordAdmin.getText().toString().trim();

        if (TextUtils.isEmpty(strEmail)) {
            emailAdmin.setError("Masukkan NIM");
            return;
        }

        if (TextUtils.isEmpty(strPassword)) {
            passwordAdmin.setError("Masukkan Password");
            return;
        }
            databaseReference.orderByChild("emailAdmin").equalTo(strEmail)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    AdminIni admin = dataSnapshot.getValue(AdminIni.class);
                                    if (admin != null){
                                        String email = admin.emailAdmin;
                                        auth.signInWithEmailAndPassword(email, strPassword)
                                                .addOnCompleteListener(task ->{
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(LoginAdmin.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        Toast.makeText(LoginAdmin.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                                                    };
                                                });
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



        }

    }

class  AdminIni{
    public AdminIni() {
    }

    public  String emailAdmin;

    public AdminIni(String emailAdmin, String passwordAdmin) {
        this.emailAdmin = emailAdmin;
        this.passwordAdmin = passwordAdmin;
    }

    public String passwordAdmin;

}