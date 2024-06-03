package com.android.epdm.model;

public class AdminModel {
    String emailAdmin;
    String passwordAdmin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AdminModel(String id) {
        this.id = id;
    }

    String id;

    public AdminModel() {
    }

    public AdminModel(String emailAdmin, String passwordAdmin) {
        this.emailAdmin = emailAdmin;
        this.passwordAdmin = passwordAdmin;
    }

    public String getEmailAdmin() {
        return emailAdmin;
    }

    public void setEmailAdmin(String emailAdmin) {
        this.emailAdmin = emailAdmin;
    }

    public String getPasswordAdmin() {
        return passwordAdmin;
    }

    public void setPasswordAdmin(String passwordAdmin) {
        this.passwordAdmin = passwordAdmin;
    }
}
