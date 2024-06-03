package com.android.epdm.adapter;

import android.app.AlertDialog;
import android.content.Context;
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
import com.android.epdm.model.DosenModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DosenAdapter extends RecyclerView.Adapter<DosenAdapter.DosenViewHolder> {

    private final List<DosenModel> dosenList;
    Context context;

    public DosenAdapter(List<DosenModel> dosenList, Context context) {
        this.dosenList = dosenList;
        this.context = context;
    }

    @NonNull
    @Override
    public DosenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dosen, parent, false);
        return new DosenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DosenViewHolder holder, int position) {
        DosenModel dosen = dosenList.get(position);
        holder.name.setText(dosen.getNamaDosen());
        holder.email.setText(dosen.getEmailDosen());
        holder.statusPegawai.setText(dosen.getStatusKepegawaian());
        holder.alamat.setText(dosen.getAlamatDosen());
        holder.jabatan.setText(dosen.getJabatanFungsional());
        holder.lamaBekerja.setText(dosen.getLamaBekerja());
        holder.password.setText(dosen.getPasswordDosen());
        holder.jenisKelamin.setText(dosen.getJenisKelamin());

        holder.btnEdit.setOnClickListener(v -> showEdit(dosen));

        holder.btnHapus.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Hapus Dosen")
                    .setMessage("Apakah Anda yakin ingin menghapus dosen ini?")
                    .setPositiveButton("Ya", (dialog, which) -> deleteDosen(dosen.getId()))
                    .setNegativeButton("Tidak", null)
                    .show();
        });
    }

    private void showEdit(DosenModel dosen) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.activity_edit_akun_dosen, null);
        dialogBuilder.setView(dialogView);

        EditText editName = dialogView.findViewById(R.id.namaEditDosen);
        EditText editEmail = dialogView.findViewById(R.id.emailEditDosen);
        EditText editStatusPegawai = dialogView.findViewById(R.id.statusKepegawaianEditDosen);
        EditText jabatanFungsional = dialogView.findViewById(R.id.jabatanFungsionalEditDosen);
        EditText password = dialogView.findViewById(R.id.passwordEditDosen);
        TextView saveButton = dialogView.findViewById(R.id.btnSimpanEdit);

        editName.setText(dosen.getNamaDosen());
        editEmail.setText(dosen.getEmailDosen());
        editStatusPegawai.setText(dosen.getStatusKepegawaian());

        jabatanFungsional.setText(dosen.getJabatanFungsional());
        password.setText(dosen.getPasswordDosen());

        AlertDialog alertDialog = dialogBuilder.create();

        saveButton.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String email = editEmail.getText().toString();
            String statusPegawai = editStatusPegawai.getText().toString();

            String jabatanText = jabatanFungsional.getText().toString();
            String passwordText = password.getText().toString();

            if (!name.isEmpty() && !email.isEmpty() && !statusPegawai.isEmpty() && !jabatanText.isEmpty() && !passwordText.isEmpty()) {
                updateDosen(dosen.getId(), name, email, statusPegawai,  jabatanText, passwordText);
                alertDialog.dismiss();
            } else {
                Toast.makeText(context, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.show();
    }

    private void updateDosen(String id, String name, String email, String statusPegawai,  String jabatan, String password) {
        DatabaseReference dosenRef = FirebaseDatabase.getInstance().getReference("Dosen").child(id);
        dosenRef.child("namaDosen").setValue(name);
        dosenRef.child("emailDosen").setValue(email);
        dosenRef.child("passwordDosen").setValue(password);

        dosenRef.child("jabatanFungsional").setValue(jabatan);
        dosenRef.child("statusKepegawaian").setValue(statusPegawai).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Dosen berhasil diperbarui", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Gagal memperbarui dosen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteDosen(String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Dosen").child(id);
        reference.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Berhasil menghapus akun", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Gagal menghapus akun", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dosenList.size();
    }

    static class DosenViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, statusPegawai, alamat, jabatan, lamaBekerja, password, jenisKelamin;
        Button btnEdit, btnHapus;

        DosenViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namaDosen);
            email = itemView.findViewById(R.id.emailDosen);
            statusPegawai = itemView.findViewById(R.id.statusKepegawaianDosen);
            alamat = itemView.findViewById(R.id.alamatDosen);
            jabatan = itemView.findViewById(R.id.jabatanFungsionalDosen);
            lamaBekerja = itemView.findViewById(R.id.lamaBekerjaDosen);
            password = itemView.findViewById(R.id.password);
            jenisKelamin = itemView.findViewById(R.id.jenisKelaminDosen);
            btnHapus = itemView.findViewById(R.id.btnHapus);
            btnEdit = itemView.findViewById(R.id.btnEditDosen);
        }
    }
}
