package com.ict_life.merchantapp.retrofit.entities;

public class UpdateUserDetails {
    private String first_name;
    private String last_name;
    private PhoneNumber phone_number;
    private String username;

    public UpdateUserDetails(String first_name, String last_name, PhoneNumber phone_number, String username) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.username = username;
    }
}
