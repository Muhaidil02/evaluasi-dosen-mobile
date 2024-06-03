package com.android.epdm.model;

import java.io.Serializable;

public class LayananKemahasiswaanModel implements Serializable {
    String pertanyaan;
    Integer pilihan1;
    Integer pilihan2;
    Integer pilihan3;
    Integer pilihan4;

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public Integer getPilihan1() {
        return pilihan1;
    }

    public void setPilihan1(Integer pilihan1) {
        this.pilihan1 = pilihan1;
    }

    public Integer getPilihan2() {
        return pilihan2;
    }

    public void setPilihan2(Integer pilihan2) {
        this.pilihan2 = pilihan2;
    }

    public Integer getPilihan3() {
        return pilihan3;
    }

    public void setPilihan3(Integer pilihan3) {
        this.pilihan3 = pilihan3;
    }

    public Integer getPilihan4() {
        return pilihan4;
    }

    public void setPilihan4(Integer pilihan4) {
        this.pilihan4 = pilihan4;
    }

    public LayananKemahasiswaanModel(String pertanyaan, Integer pilihan1, Integer pilihan2, Integer pilihan3, Integer pilihan4) {
        this.pertanyaan = pertanyaan;
        this.pilihan1 = pilihan1;
        this.pilihan2 = pilihan2;
        this.pilihan3 = pilihan3;
        this.pilihan4 = pilihan4;
    }

    public LayananKemahasiswaanModel() {
    }
}
