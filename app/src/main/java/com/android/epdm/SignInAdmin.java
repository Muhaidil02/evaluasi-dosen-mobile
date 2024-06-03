package com.android.epdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.epdm.model.AdminModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignInAdmin extends AppCompatActivity {
    EditText emailAdmin, passwordAdmin;
    TextView btnDaftarAdmin;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_admin);
        emailAdmin = findViewById(R.id.emailAdmin);
        passwordAdmin = findViewById(R.id.passwordAdmin);

        btnDaftarAdmin = findViewById(R.id.btnDaftarAdmin);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btnDaftarAdmin.setOnClickListener(view -> registerAdmin());

    }

    private void registerAdmin() {
        String strEmail = emailAdmin.getText().toString().trim();
        String strPassword = passwordAdmin.getText().toString().trim();

      auth.createUserWithEmailAndPassword(strEmail, strPassword)
              .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      AdminModel model = new AdminModel(strEmail, strPassword);
                      String id = task.getResult().getUser().getUid();
                      database.getReference().child("Admin").child(id).setValue(model);
                      Toast.makeText(SignInAdmin.this, "Berhasil" +task.getException(), Toast.LENGTH_SHORT).show();
                      finish();
                  }
              }) ;

    }
}