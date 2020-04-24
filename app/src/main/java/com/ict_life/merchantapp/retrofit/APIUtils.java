package com.ict_life.merchantapp.retrofit;



public class APIUtils {

    public APIUtils() {
    }

    public static final String BASE_URL = "https://staging-api.ictlife.com/";

    public static APIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }


}
