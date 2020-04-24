package com.ict_life.merchantapp.retrofit;

import com.google.gson.JsonElement;
import com.ict_life.merchantapp.retrofit.entities.Business;
import com.ict_life.merchantapp.retrofit.entities.BusinessLocation;
import com.ict_life.merchantapp.retrofit.entities.Phone;
import com.ict_life.merchantapp.retrofit.entities.Services;
import com.ict_life.merchantapp.retrofit.entities.UpdateUserDetails;
import com.ict_life.merchantapp.retrofit.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    //send phone number to get sms verification code
    @POST("/api/v1/phone_verification")
    Call<JsonElement> regUser(@Body Phone phone);

    //send verification code to get token
    @POST("/api/v1/users")
    Call<JsonElement> loginUser(@Header("X-ICTLIFE-APPLICATION")String ictlife_header_value, @Body User user);

    @PUT("/api/v1/users/{id}")
    Call<JsonElement> updateUser(@Header("X-ICTLIFE-TOKEN")String ictlife_header_value, @Body UpdateUserDetails updateUserDetails, @Path("id") String id);

    @POST("/api/v1/merchants")
    Call<JsonElement> createMerchant(@Header("X-ICTLIFE-TOKEN")String ictlife_header_value, @Body Business createBusiness);

    @GET("/api/v1/users/{id}/merchants")
    Call<JsonElement> getMerchantInfo(@Header("X-ICTLIFE-TOKEN") String token,@Path("id") String id);

    @GET("/api/v1/merchants/{id}/full_profile")
    Call<JsonElement> getFullMerchantInfo(@Header("X-ICTLIFE-TOKEN") String token,@Path("id") String id);

    @POST("/api/v1/merchant_services")
    Call<JsonElement> createMerchantServices(@Header("X-ICTLIFE-TOKEN")String token, @Body Services services);

    @POST("api/v1/profiles")
    Call<JsonElement> updateLocationDetails(@Header("X-ICTLIFE-TOKEN") String token, @Body BusinessLocation location);

}
