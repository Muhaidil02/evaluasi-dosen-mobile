package com.android.epdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.epdm.model.HasilKuisionerModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class HasilKuesionerAdmin extends AppCompatActivity {

    FirebaseFirestore db;
    TextView hasilPenelitianTextView;
    TextView hasilSaranaPrasaranaTextView, hasilLayananAdministrasi,hasilLayananKeuangan,hasilLayananPembelajaran
            ,hasilDosenCivitasAkademik,  hasilDOsenTataPamong, hasilDosenPengabdiLayanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db = FirebaseFirestore.getInstance();
        hasilPenelitianTextView = findViewById(R.id.resultTextView);
        hasilSaranaPrasaranaTextView = findViewById(R.id.hasilLayananSarana);
        hasilLayananAdministrasi = findViewById(R.id.hasilLayananAdministrasi);
        hasilLayananKeuangan = findViewById(R.id.hasilLayananKeuangan);
        hasilLayananPembelajaran = findViewById(R.id.hasilLayananPembelajaran);
        hasilDosenCivitasAkademik = findViewById(R.id.hasilDosenCivitasAkademik);

        hasilDOsenTataPamong = findViewById(R.id.hasilDosenTataPamong);
hasilDosenPengabdiLayanan = findViewById(R.id.hasilDosenPengabdiLayanan);

        loadHasilDariFirestore();
    }

    private void loadHasilDariFirestore() {
        db.collection("Responses").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String, Map<Integer, Integer>> hasilMap = new HashMap<>();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            HasilKuisionerModel hasilKuisioner = documentSnapshot.toObject(HasilKuisionerModel.class);
                            String pertanyaan = hasilKuisioner.getPertanyaan();
                            int pilihanTerpilih = hasilKuisioner.getPilihanTerpilih();

                            if (!hasilMap.containsKey(pertanyaan)) {
                                hasilMap.put(pertanyaan, new HashMap<>());
                            }
                            Map<Integer, Integer> pilihanMap = hasilMap.get(pertanyaan);
                            pilihanMap.put(pilihanTerpilih, pilihanMap.containsKey(pilihanTerpilih) ? pilihanMap.get(pilihanTerpilih) + 1 : 1);
                        }
                        tampilkanHasil(hasilMap);
                    } else {
                        Toast.makeText(HasilKuesionerAdmin.this, "Error loading results", Toast.LENGTH_SHORT).show();
                    }
                });

        // Load hasil dari layanan sarana prasarana
        db.collection("ResponsesSaranaPrasarana").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String, Map<Integer, Integer>> hasilMap = new HashMap<>();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            HasilKuisionerModel hasilKuisioner = documentSnapshot.toObject(HasilKuisionerModel.class);
                            String pertanyaan = hasilKuisioner.getPertanyaan();
                            int pilihanTerpilih = hasilKuisioner.getPilihanTerpilih();

                            if (!hasilMap.containsKey(pertanyaan)) {
                                hasilMap.put(pertanyaan, new HashMap<>());
                            }
                            Map<Integer, Integer> pilihanMap = hasilMap.get(pertanyaan);
                            pilihanMap.put(pilihanTerpilih, pilihanMap.containsKey(pilihanTerpilih) ? pilihanMap.get(pilihanTerpilih) + 1 : 1);
                        }
                        tampilkanHasilSaranaPrasarana(hasilMap);
                    } else {
                        Toast.makeText(HasilKuesionerAdmin.this, "Error loading results", Toast.LENGTH_SHORT).show();
                    }
                });


        // Load hasil dari layanan sarana prasarana
        db.collection("ResponsAkademik").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String, Map<Integer, Integer>> hasilMap = new HashMap<>();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            HasilKuisionerModel hasilKuisioner = documentSnapshot.toObject(HasilKuisionerModel.class);
                            String pertanyaan = hasilKuisioner.getPertanyaan();
                            int pilihanTerpilih = hasilKuisioner.getPilihanTerpilih();

                            if (!hasilMap.containsKey(pertanyaan)) {
                                hasilMap.put(pertanyaan, new HashMap<>());
                            }
                            Map<Integer, Integer> pilihanMap = hasilMap.get(pertanyaan);
                            pilihanMap.put(pilihanTerpilih, pilihanMap.containsKey(pilihanTerpilih) ? pilihanMap.get(pilihanTerpilih) + 1 : 1);
                        }
                        tampilkanHasilAkademik(hasilMap);
                    } else {
                        Toast.makeText(HasilKuesionerAdmin.this, "Error loading results", Toast.LENGTH_SHORT).show();
                    }
                });

        db.collection("ResponsKeuangan").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String, Map<Integer, Integer>> hasilMap = new HashMap<>();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            HasilKuisionerModel hasilKuisioner = documentSnapshot.toObject(HasilKuisionerModel.class);
                            String pertanyaan = hasilKuisioner.getPertanyaan();
                            int pilihanTerpilih = hasilKuisioner.getPilihanTerpilih();

                            if (!hasilMap.containsKey(pertanyaan)) {
                                hasilMap.put(pertanyaan, new HashMap<>());
                            }
                            Map<Integer, Integer> pilihanMap = hasilMap.get(pertanyaan);
                            pilihanMap.put(pilihanTerpilih, pilihanMap.containsKey(pilihanTerpilih) ? pilihanMap.get(pilihanTerpilih) + 1 : 1);
                        }
                        tampilkanHasilKeuangan(hasilMap);
                    } else {
                        Toast.makeText(HasilKuesionerAdmin.this, "Error loading results", Toast.LENGTH_SHORT).show();
                    }
                });

        db.collection("ResponKuesionerMahasiswa").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String, Map<Integer, Integer>> hasilMap = new HashMap<>();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            HasilKuisionerModel hasilKuisioner = documentSnapshot.toObject(HasilKuisionerModel.class);
                            String pertanyaan = hasilKuisioner.getPertanyaan();
                            int pilihanTerpilih = hasilKuisioner.getPilihanTerpilih();

                            if (!hasilMap.containsKey(pertanyaan)) {
                                hasilMap.put(pertanyaan, new HashMap<>());
                            }
                            Map<Integer, Integer> pilihanMap = hasilMap.get(pertanyaan);
                            pilihanMap.put(pilihanTerpilih, pilihanMap.containsKey(pilihanTerpilih) ? pilihanMap.get(pilihanTerpilih) + 1 : 1);
                        }
                        tampilkanKuesionerMhs(hasilMap);
                    } else {
                        Toast.makeText(HasilKuesionerAdmin.this, "Error loading results", Toast.LENGTH_SHORT).show();
                    }
                });

        db.collection("ResponsCivitasAkademik").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String, Map<Integer, Integer>> hasilMap = new HashMap<>();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            HasilKuisionerModel hasilKuisioner = documentSnapshot.toObject(HasilKuisionerModel.class);
                            String pertanyaan = hasilKuisioner.getPertanyaan();
                            int pilihanTerpilih = hasilKuisioner.getPilihanTerpilih();

                            if (!hasilMap.containsKey(pertanyaan)) {
                                hasilMap.put(pertanyaan, new HashMap<>());
                            }
                            Map<Integer, Integer> pilihanMap = hasilMap.get(pertanyaan);
                            pilihanMap.put(pilihanTerpilih, pilihanMap.containsKey(pilihanTerpilih) ? pilihanMap.get(pilihanTerpilih) + 1 : 1);
                        }
                        tampilkanCivitasAkademik(hasilMap);
                    } else {
                        Toast.makeText(HasilKuesionerAdmin.this, "Error loading results", Toast.LENGTH_SHORT).show();
                    }
                });

        db.collection("ResponsTataPamong").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String, Map<Integer, Integer>> hasilMap = new HashMap<>();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            HasilKuisionerModel hasilKuisioner = documentSnapshot.toObject(HasilKuisionerModel.class);
                            String pertanyaan = hasilKuisioner.getPertanyaan();
                            int pilihanTerpilih = hasilKuisioner.getPilihanTerpilih();

                            if (!hasilMap.containsKey(pertanyaan)) {
                                hasilMap.put(pertanyaan, new HashMap<>());
                            }
                            Map<Integer, Integer> pilihanMap = hasilMap.get(pertanyaan);
                            pilihanMap.put(pilihanTerpilih, pilihanMap.containsKey(pilihanTerpilih) ? pilihanMap.get(pilihanTerpilih) + 1 : 1);
                        }
                        tampilkanTataPamong(hasilMap);
                    } else {
                        Toast.makeText(HasilKuesionerAdmin.this, "Error loading results", Toast.LENGTH_SHORT).show();
                    }
                });

        db.collection("ResponsPengabdiLayanan").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String, Map<Integer, Integer>> hasilMap = new HashMap<>();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            HasilKuisionerModel hasilKuisioner = documentSnapshot.toObject(HasilKuisionerModel.class);
                            String pertanyaan = hasilKuisioner.getPertanyaan();
                            int pilihanTerpilih = hasilKuisioner.getPilihanTerpilih();

                            if (!hasilMap.containsKey(pertanyaan)) {
                                hasilMap.put(pertanyaan, new HashMap<>());
                            }
                            Map<Integer, Integer> pilihanMap = hasilMap.get(pertanyaan);
                            pilihanMap.put(pilihanTerpilih, pilihanMap.containsKey(pilihanTerpilih) ? pilihanMap.get(pilihanTerpilih) + 1 : 1);
                        }
                        tampilkanPengabdiLayanan(hasilMap);
                    } else {
                        Toast.makeText(HasilKuesionerAdmin.this, "Error loading results", Toast.LENGTH_SHORT).show();
                    }
                });




    }

    private void tampilkanHasil(Map<String, Map<Integer, Integer>> hasilMap) {
        StringBuilder hasilBuilder = new StringBuilder();
        for (Map.Entry<String, Map<Integer, Integer>> entry : hasilMap.entrySet()) {
            hasilBuilder.append("Pertanyaan: ").append(entry.getKey()).append("\n");
            for (Map.Entry<Integer, Integer> pilihanEntry : entry.getValue().entrySet()) {
                hasilBuilder.append("Pilihan ").append(pilihanEntry.getKey()).append(": ").append(pilihanEntry.getValue()).append("\n");
            }
            hasilBuilder.append("\n");
        }
        hasilPenelitianTextView.setText(hasilBuilder.toString());
    }

    private void tampilkanHasilSaranaPrasarana(Map<String, Map<Integer, Integer>> hasilMap) {
        StringBuilder hasilBuilder = new StringBuilder();
        for (Map.Entry<String, Map<Integer, Integer>> entry : hasilMap.entrySet()) {
            hasilBuilder.append("Pertanyaan: ").append(entry.getKey()).append("\n");
            for (Map.Entry<Integer, Integer> pilihanEntry : entry.getValue().entrySet()) {
                hasilBuilder.append("Pilihan ").append(pilihanEntry.getKey()).append(": ").append(pilihanEntry.getValue()).append("\n");
            }
            hasilBuilder.append("\n");
        }
        hasilSaranaPrasaranaTextView.setText(hasilBuilder.toString());
    }

    private void tampilkanHasilAkademik(Map<String, Map<Integer, Integer>> hasilMap) {
        StringBuilder hasilBuilder = new StringBuilder();
        for (Map.Entry<String, Map<Integer, Integer>> entry : hasilMap.entrySet()) {
            hasilBuilder.append("Pertanyaan: ").append(entry.getKey()).append("\n");
            for (Map.Entry<Integer, Integer> pilihanEntry : entry.getValue().entrySet()) {
                hasilBuilder.append("Pilihan ").append(pilihanEntry.getKey()).append(": ").append(pilihanEntry.getValue()).append("\n");
            }
            hasilBuilder.append("\n");
        }
        hasilLayananAdministrasi.setText(hasilBuilder.toString());
    }
    private void tampilkanHasilKeuangan(Map<String, Map<Integer, Integer>> hasilMap) {
        StringBuilder hasilBuilder = new StringBuilder();
        for (Map.Entry<String, Map<Integer, Integer>> entry : hasilMap.entrySet()) {
            hasilBuilder.append("Pertanyaan: ").append(entry.getKey()).append("\n");
            for (Map.Entry<Integer, Integer> pilihanEntry : entry.getValue().entrySet()) {
                hasilBuilder.append("Pilihan ").append(pilihanEntry.getKey()).append(": ").append(pilihanEntry.getValue()).append("\n");
            }
            hasilBuilder.append("\n");
        }
        hasilLayananKeuangan.setText(hasilBuilder.toString());
    }

    private void tampilkanKuesionerMhs(Map<String, Map<Integer, Integer>> hasilMap) {
        StringBuilder hasilBuilder = new StringBuilder();
        for (Map.Entry<String, Map<Integer, Integer>> entry : hasilMap.entrySet()) {
            hasilBuilder.append("Pertanyaan: ").append(entry.getKey()).append("\n");
            for (Map.Entry<Integer, Integer> pilihanEntry : entry.getValue().entrySet()) {
                hasilBuilder.append("Pilihan ").append(pilihanEntry.getKey()).append(": ").append(pilihanEntry.getValue()).append("\n");
            }
            hasilBuilder.append("\n");
        }
        hasilLayananPembelajaran.setText(hasilBuilder.toString());
    }

    private void tampilkanCivitasAkademik(Map<String, Map<Integer, Integer>> hasilMap) {
        StringBuilder hasilBuilder = new StringBuilder();
        for (Map.Entry<String, Map<Integer, Integer>> entry : hasilMap.entrySet()) {
            hasilBuilder.append("Pertanyaan: ").append(entry.getKey()).append("\n");
            for (Map.Entry<Integer, Integer> pilihanEntry : entry.getValue().entrySet()) {
                hasilBuilder.append("Pilihan ").append(pilihanEntry.getKey()).append(": ").append(pilihanEntry.getValue()).append("\n");
            }
            hasilBuilder.append("\n");
        }
        hasilDosenCivitasAkademik.setText(hasilBuilder.toString());
    }

    private void tampilkanTataPamong(Map<String, Map<Integer, Integer>> hasilMap) {
        StringBuilder hasilBuilder = new StringBuilder();
        for (Map.Entry<String, Map<Integer, Integer>> entry : hasilMap.entrySet()) {
            hasilBuilder.append("Pertanyaan: ").append(entry.getKey()).append("\n");
            for (Map.Entry<Integer, Integer> pilihanEntry : entry.getValue().entrySet()) {
                hasilBuilder.append("Pilihan ").append(pilihanEntry.getKey()).append(": ").append(pilihanEntry.getValue()).append("\n");
            }
            hasilBuilder.append("\n");
        }
        hasilDOsenTataPamong.setText(hasilBuilder.toString());
    }

    private void tampilkanPengabdiLayanan(Map<String, Map<Integer, Integer>> hasilMap) {
        StringBuilder hasilBuilder = new StringBuilder();
        for (Map.Entry<String, Map<Integer, Integer>> entry : hasilMap.entrySet()) {
            hasilBuilder.append("Pertanyaan: ").append(entry.getKey()).append("\n");
            for (Map.Entry<Integer, Integer> pilihanEntry : entry.getValue().entrySet()) {
                hasilBuilder.append("Pilihan ").append(pilihanEntry.getKey()).append(": ").append(pilihanEntry.getValue()).append("\n");
            }
            hasilBuilder.append("\n");
        }
        hasilDosenPengabdiLayanan.setText(hasilBuilder.toString());
    }
}
