package com.ict_life.merchantapp.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    private static final String PREF_NAME = "ictlife";
    int PRIVATE_MODE = 0;
    private static final String USER_TOKEN = "user_token";
    private static final String USER_ID = "user_id";
    private static final String COUNTRY_CODE = "country_code";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String BUSINESS_NAME = "business_name";
    private static final String truth = "truth";
    private static final String MERCHANTID = "merchant_id";
    private static final String SERVICES = "services";
    private static final String BUSINESS_TYPE = "business_type";
    private static final String ADDRESS = "address";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";



    public PrefManager(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getToken() {
        return pref.getString(USER_TOKEN, null);
    }

    public void setToken(String token) {
        editor.putString(USER_TOKEN, token);
        editor.commit();
    }

    public String getUserId() {
        return pref.getString(USER_ID, null);
    }

    public void setUserId(String userId) {
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public String getCountryCode() {
        return pref.getString(COUNTRY_CODE, null);
    }

    public String getPhoneNumber() {
        return pref.getString(PHONE_NUMBER, null);
    }

    public void setCountryCode(String countryCode) {
        editor.putString(COUNTRY_CODE, countryCode);
        editor.commit();
    }

    public void setPhoneNumber(String phoneNumber) {
        editor.putString(PHONE_NUMBER, phoneNumber);
        editor.commit();
    }

    public String getFirstName() {
        return pref.getString(FIRST_NAME, null);
    }

    public void setFirstName(String first_name) {
        editor.putString(FIRST_NAME, first_name);
        editor.commit();
    }

    public String getLastName() {
        return pref.getString(LAST_NAME, null);
    }

    public void setLastName(String last_name) {
        editor.putString(LAST_NAME, last_name);
        editor.commit();
    }

    public String getBusinessName() {
        return pref.getString(BUSINESS_NAME, null);
    }

    public void setBusinessName(String businessName) {
        editor.putString(BUSINESS_NAME, businessName);
        editor.commit();
    }

    public boolean getTruth() {
        return pref.getBoolean(truth, false);
    }

    public void setTruth(boolean b) {
        editor.putBoolean(truth, b);
        editor.commit();
    }

    public void setMerchantId(String merchant_id) {
        editor.putString(MERCHANTID, merchant_id);
        editor.commit();
    }

    public String getMerchantid() {
        return pref.getString(MERCHANTID, null);
    }

    public void setServices(String[] services) {

        //Set the values
        Gson gson = new Gson();
        List<String> textList = new ArrayList<>();
        textList.addAll(Arrays.asList(services));
        String jsonText = gson.toJson(textList);
        editor.putString(SERVICES, jsonText);
        editor.apply();
    }

    public String[] getServices() {
        Gson gson = new Gson();
        String jsonText = pref.getString(SERVICES, null);

        return gson.fromJson(jsonText, String[].class);
    }

    public void setAddress(String address){
        editor.putString(ADDRESS, address);
        editor.commit();
    }

    public String getAddress(){
        return pref.getString(ADDRESS, null);
    }


    public void setBusinessType(String businessType){
        editor.putString(BUSINESS_TYPE, businessType);
        editor.commit();
    }

    public String getBusinessType(){
        return pref.getString(BUSINESS_TYPE, null);
    }


    public void setLongitude(String longitude){
        editor.putString(LONGITUDE, longitude);
        editor.commit();
    }

    public String getLongitude(){
        return pref.getString(LONGITUDE, null);
    }

    public void setLatitude(String latitude){
        editor.putString(LATITUDE, latitude);
        editor.commit();
    }

    public String getLatitude(){
        return pref.getString(LATITUDE, null);
    }

}
