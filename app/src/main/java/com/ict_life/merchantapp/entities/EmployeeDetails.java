package com.ict_life.merchantapp.entities;

public class EmployeeDetails {
    String name,phone_number;

    public EmployeeDetails(String name, String phone_number) {
        this.name = name;
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

}
