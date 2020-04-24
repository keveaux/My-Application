package com.ict_life.merchantapp.retrofit.entities;

public class BusinessLocation {
    String address_line_1;
    Double latitude;
    Double longitude;
    int merchant_id;

    public BusinessLocation( Double latitude, Double longitude,String address_line_1, int merchant_id) {
        this.address_line_1 = address_line_1;
        this.latitude = latitude;
        this.longitude = longitude;
        this.merchant_id=merchant_id;
    }
}
