package com.ict_life.merchantapp.entities;

public class MyBusinessesDetailsModel {
    String business_name,business_rating,business_reviews;
    boolean selected_business;
    int image_drawable;

    public MyBusinessesDetailsModel(String business_name, String business_rating, String business_reviews, boolean selected_business,int image_drawable) {
        this.business_name = business_name;
        this.business_rating = business_rating;
        this.business_reviews = business_reviews;
        this.selected_business = selected_business;
        this.image_drawable = image_drawable;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public String getBusiness_rating() {
        return business_rating;
    }

    public String getBusiness_reviews() {
        return business_reviews;
    }

    public boolean isSelected_business() {
        return selected_business;
    }

    public int getImage_drawable() {
        return image_drawable;
    }
}
