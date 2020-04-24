package com.ict_life.merchantapp.retrofit.entities;

public class User {

    private String first_name;
    private String last_name;
    private String password;
    private String password_confirmation;
    private PhoneNumber phone_number;
    private String username;
    private String verification_code;

    public User(String first_name, String last_name, String password, String password_confirmation, PhoneNumber phone_number, String username, String verification_code) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.password_confirmation = password_confirmation;
        this.phone_number = phone_number;
        this.username = username;
        this.verification_code = verification_code;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public PhoneNumber getPhone_number() {
        return phone_number;
    }

    public String getUsername() {
        return username;
    }

    public String getVerification_code() {
        return verification_code;
    }
}
