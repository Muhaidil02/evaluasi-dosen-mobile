package com.android.epdm.model;

public class HasilKuisionerModel {
    private String userId;
    private String pertanyaan;
    private int pilihanTerpilih;

    public HasilKuisionerModel() {
    }

    public HasilKuisionerModel(String userId, String pertanyaan, int pilihanTerpilih) {
        this.userId = userId;
        this.pertanyaan = pertanyaan;
        this.pilihanTerpilih = pilihanTerpilih;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public int getPilihanTerpilih() {
        return pilihanTerpilih;
    }

    public void setPilihanTerpilih(int pilihanTerpilih) {
        this.pilihanTerpilih = pilihanTerpilih;
    }
}