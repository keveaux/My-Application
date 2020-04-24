package com.ict_life.merchantapp.retrofit.entities;

public class PhoneNumber {
    String country_code;
    String number;
    public PhoneNumber(String country_code, String number) {
        this.country_code = country_code;
        this.number = number;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getNumber() {
        return number;
    }
}
