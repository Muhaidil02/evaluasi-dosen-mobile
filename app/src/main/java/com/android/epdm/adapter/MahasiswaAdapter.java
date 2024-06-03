package com.android.epdm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.epdm.R;
import com.android.epdm.model.MahasiswaModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder> {

    private final List<MahasiswaModel> mahasiswaList;
    Context context;

    public MahasiswaAdapter(List<MahasiswaModel> mahasiswaList, Context context) {
        this.mahasiswaList = mahasiswaList;
        this.context = context;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa, parent, false);
        return new MahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position) {
        MahasiswaModel mahasiswa = mahasiswaList.get(position);
        holder.name.setText(mahasiswa.getUsername());
        holder.nim.setText(mahasiswa.getNim());
        holder.email.setText(mahasiswa.getEmail());
        holder.password.setText(mahasiswa.getPassword());
        holder.confirm_password.setText(mahasiswa.getConfirm_password());

        holder.btnEdit.setOnClickListener(v -> showEdit(mahasiswa));

        holder.btnHapus.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Hapus Akun Mahasiswa")
                    .setMessage("Apakah anda yakin ingin menghapus mahasiswa ini?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteMahasiswa(mahasiswa.getId());
                        }
                    })
                    .setNegativeButton("Tidak", null)
                    .show();
        });

    }

    private void showEdit(MahasiswaModel mahasiswa) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.activity_edit_akun_mahasiswa, null);
        dialogBuilder.setView(dialogView);

        EditText editName = dialogView.findViewById(R.id.namaEditMahasiswa);
        EditText editEmail = dialogView.findViewById(R.id.emailEditMahasiswa);
        EditText nim= dialogView.findViewById(R.id.nimEditMahasiswa);
        EditText password = dialogView.findViewById(R.id.passwordEditMahasiswa);
        TextView saveButton = dialogView.findViewById(R.id.btnSimpanEditMhs);

        editName.setText(mahasiswa.getUsername());
        editEmail.setText(mahasiswa.getEmail());
        nim.setText(mahasiswa.getNim());
        password.setText(mahasiswa.getPassword());

        AlertDialog alertDialog = dialogBuilder.create();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaMhs = editName.getText().toString();

                String emailMhs = editEmail.getText().toString();
                String nimMhs = nim.getText().toString();
                String passwordMhs = password.getText().toString();

                if (!namaMhs.isEmpty() && !emailMhs.isEmpty() && !nimMhs.isEmpty() && !passwordMhs.isEmpty()){
                    updateMahasiswa(mahasiswa.getId(), namaMhs,emailMhs, nimMhs,passwordMhs);
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(context, "Semua Kolom harus diisi", Toast.LENGTH_SHORT).show();
                }

            }
        });
        alertDialog.show();
    }

    private void updateMahasiswa(String id, String namaMhs, String emailMhs, String nimMhs, String passwordMhs) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Mahasiswa").child(id);
        reference.child("username").setValue(namaMhs);
        reference.child("email").setValue(emailMhs);
        reference.child("nim").setValue(nimMhs);
        reference.child("password").setValue(passwordMhs).addOnCompleteListener(task -> {

            if (task.isSuccessful()){
                Toast.makeText(context, "Berhasil Update Akun", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Gagal Update", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void deleteMahasiswa(String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Mahasiswa")
                .child(id);
        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context, "Berhasil Menghapus Akun", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Gagal Menghapus Akun", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    static class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        TextView name, nim, email,password, confirm_password;
        Button btnEdit, btnHapus;
        MahasiswaViewHolder(@NonNull View itemView) {


            super(itemView);
            name = itemView.findViewById(R.id.namaMahasiswa);
            nim = itemView.findViewById(R.id.nimMahasiswa);
            email = itemView.findViewById(R.id.emailMahasiswa);
            password = itemView.findViewById(R.id.passwordMahasiswa);
            confirm_password= itemView.findViewById(R.id.confirmPassword);
            btnEdit = itemView.findViewById(R.id.btnEdit) ;
            btnHapus= itemView.findViewById(R.id.btnHapus);
;        }
    }
}