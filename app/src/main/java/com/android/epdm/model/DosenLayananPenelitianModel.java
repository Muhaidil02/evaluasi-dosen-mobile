package com.android.epdm.model;

public class DosenLayananPenelitianModel {
    private String pertanyaanId;
    private String pertanyaan;
    private String pilihan1;
    private String pilihan2;
    private String pilihan3;
    private String pilihan4;
    private int pilihanTerpilih;

    // Default constructor
    public DosenLayananPenelitianModel() {
    }

    // Constructor with parameters
    public DosenLayananPenelitianModel(String pertanyaanId, String pertanyaan, String pilihan1, String pilihan2, String pilihan3, String pilihan4, int pilihanTerpilih) {
        this.pertanyaanId = pertanyaanId;
        this.pertanyaan = pertanyaan;
        this.pilihan1 = pilihan1;
        this.pilihan2 = pilihan2;
        this.pilihan3 = pilihan3;
        this.pilihan4 = pilihan4;
        this.pilihanTerpilih = pilihanTerpilih;
    }

    // Constructor with pertanyaanId and pilihanTerpilih
    public DosenLayananPenelitianModel(String pertanyaanId, int pilihanTerpilih) {
        this.pertanyaanId = pertanyaanId;
        this.pilihanTerpilih = pilihanTerpilih;
    }

    // Getters and Setters
    public String getPertanyaanId() {
        return pertanyaanId;
    }

    public void setPertanyaanId(String pertanyaanId) {
        this.pertanyaanId = pertanyaanId;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getPilihan1() {
        return pilihan1;
    }

    public void setPilihan1(String pilihan1) {
        this.pilihan1 = pilihan1;
    }

    public String getPilihan2() {
        return pilihan2;
    }

    public void setPilihan2(String pilihan2) {
        this.pilihan2 = pilihan2;
    }

    public String getPilihan3() {
        return pilihan3;
    }

    public void setPilihan3(String pilihan3) {
        this.pilihan3 = pilihan3;
    }

    public String getPilihan4() {
        return pilihan4;
    }

    public void setPilihan4(String pilihan4) {
        this.pilihan4 = pilihan4;
    }

    public int getPilihanTerpilih() {
        return pilihanTerpilih;
    }

    public void setPilihanTerpilih(int pilihanTerpilih) {
        this.pilihanTerpilih = pilihanTerpilih;
    }
}
