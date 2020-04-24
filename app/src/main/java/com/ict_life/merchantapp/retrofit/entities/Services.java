package com.ict_life.merchantapp.retrofit.entities;

import java.util.ArrayList;

public class Services {
    String [] names;
    int merchant_id;

    public Services(String[] names,int merchant_id) {
        this.names = names;
        this.merchant_id=merchant_id;
    }
}
