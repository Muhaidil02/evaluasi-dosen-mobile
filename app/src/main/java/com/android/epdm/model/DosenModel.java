package com.android.epdm.model;

public class DosenModel {
    public String id, namaDosen, emailDosen, passwordDosen, alamatDosen, jenisKelamin, lamaBekerja, statusKepegawaian, jabatanFungsional;

    public DosenModel() {}

    public DosenModel(String namaDosen, String emailDosen, String passwordDosen, String alamatDosen, String jenisKelamin, String lamaBekerja, String statusKepegawaian, String jabatanFungsional) {
        this.namaDosen = namaDosen;
        this.emailDosen = emailDosen;
        this.passwordDosen = passwordDosen;
        this.alamatDosen = alamatDosen;
        this.jenisKelamin = jenisKelamin;
        this.lamaBekerja = lamaBekerja;
        this.statusKepegawaian = statusKepegawaian;
        this.jabatanFungsional = jabatanFungsional;
    }

    public DosenModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaDosen() {
        return namaDosen;
    }

    public void setNamaDosen(String namaDosen) {
        this.namaDosen = namaDosen;
    }

    public String getEmailDosen() {
        return emailDosen;
    }

    public void setEmailDosen(String emailDosen) {
        this.emailDosen = emailDosen;
    }

    public String getPasswordDosen() {
        return passwordDosen;
    }

    public void setPasswordDosen(String passwordDosen) {
        this.passwordDosen = passwordDosen;
    }

    public String getAlamatDosen() {
        return alamatDosen;
    }

    public void setAlamatDosen(String alamatDosen) {
        this.alamatDosen = alamatDosen;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getLamaBekerja() {
        return lamaBekerja;
    }

    public void setLamaBekerja(String lamaBekerja) {
        this.lamaBekerja = lamaBekerja;
    }

    public String getStatusKepegawaian() {
        return statusKepegawaian;
    }

    public void setStatusKepegawaian(String statusKepegawaian) {
        this.statusKepegawaian = statusKepegawaian;
    }

    public String getJabatanFungsional() {
        return jabatanFungsional;
    }

    public void setJabatanFungsional(String jabatanFungsional) {
        this.jabatanFungsional = jabatanFungsional;
    }
}
