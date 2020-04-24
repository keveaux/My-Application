package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.Trial;
import com.ict_life.merchantapp.prefs.PrefManager;
import com.ict_life.merchantapp.retrofit.entities.Phone;
import com.ict_life.merchantapp.retrofit.entities.PhoneNumber;
import com.ict_life.merchantapp.retrofit.APIService;
import com.ict_life.merchantapp.retrofit.APIUtils;
import com.ict_life.merchantapp.retrofit.entities.User;
import com.ict_life.merchantapp.services.SmsProcessService;
import com.ict_life.merchantapp.widgets.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerificationActivity extends AppCompatActivity {


    APIService apiService = null;
    Intent smsServiceIntent;
    EditText et_otp;
    private static OTPVerificationActivity instance;
    PhoneNumber phoneNumber;
    Phone phone;
    private PrefManager prefManager;
    ProgressBar progress_bar;
    LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        Button verify_otp_btn = findViewById(R.id.verify_otp_btn);
        TextView verify_pno_tv = findViewById(R.id.verify_pno_tv);
        et_otp = findViewById(R.id.et_otp);
        progress_bar = findViewById(R.id.progress_bar);


        Bundle bundle = getIntent().getExtras();
        String pno = bundle.getString("pno");
        String country_code = bundle.getString("country_code");


        prefManager = new PrefManager(this);
        prefManager.setCountryCode(country_code);
        prefManager.setPhoneNumber(pno);


        String phone_no = String.format("+%s%s", country_code, pno);

        verify_pno_tv.setText("Verify " + phone_no);

        apiService = APIUtils.getAPIService();

        sendPhoneNumber(country_code, pno);

        instance = this;


        smsServiceIntent = new Intent(this, SmsProcessService.class);

        verify_otp_btn.setOnClickListener(v -> {


            if (et_otp.getText().toString().isEmpty()) {
                et_otp.setError("please wait for verification code");
            } else {
                User user = new User("first", "last", "12345678", "12345678", phoneNumber, phone_no.substring(1), et_otp.getText().toString());
                loginUser(user);
                loadingDialog=new LoadingDialog();
                loadingDialog.showDialog(this);

            }

        });



    }

    public static OTPVerificationActivity getInstance() {
        return instance;
    }


    public void setOTP(String otp) {
        et_otp.setText(otp);
        progress_bar.setVisibility(View.GONE);

        Toast.makeText(this, "otp" + otp, Toast.LENGTH_SHORT).show();
    }


    private void sendPhoneNumber(String country_code, String pno) {

        phoneNumber = new PhoneNumber(country_code, pno);
        phone = new Phone(phoneNumber);

        apiService.regUser(phone).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OTPVerificationActivity.this, "" + response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(OTPVerificationActivity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loginUser(User user) {

        Toast.makeText(this, "one", Toast.LENGTH_SHORT).show();

        apiService.loginUser("user", user).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        Toast.makeText(OTPVerificationActivity.this, "" + jObjError.getString("error_message"), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    JsonElement responseUser = response.body();

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        String id = jsonObject.optString("id");
                        Toast.makeText(OTPVerificationActivity.this, "" + id, Toast.LENGTH_SHORT).show();
                        prefManager.setUserId(id);


                    } catch (Exception e) {

                    }


                    String token = response.headers().get("X-Ictlife-Token");

                    prefManager.setToken(token);

                    Toast.makeText(OTPVerificationActivity.this, ""+token, Toast.LENGTH_SHORT).show();

                    getMerchantInformation(user);


                    if (responseUser != null) {

                    } else {
                        String err = String.format("Response is %s", String.valueOf(response.code()));
                        Toast.makeText(OTPVerificationActivity.this, err, Toast.LENGTH_SHORT).show();
                        loadingDialog.dismissDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(OTPVerificationActivity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getMerchantInformation(User user) {


        apiService.getMerchantInfo(prefManager.getToken(), prefManager.getUserId()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        Toast.makeText(OTPVerificationActivity.this, "" + jObjError.getString("error_message"), Toast.LENGTH_SHORT).show();

                        loadingDialog.dismissDialog();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    JsonElement responseUser = response.body();

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        JSONArray groupObject = jsonObject.getJSONArray("user_merchants");

                        Toast.makeText(OTPVerificationActivity.this, "" + jsonObject, Toast.LENGTH_SHORT).show();

                        if (groupObject.length() == 0) {

                            openRegistrationActivity(user);
                            loadingDialog.dismissDialog();

                        } else {
                            JSONObject jsonObject1 = groupObject.getJSONObject(0);

                            String merchant_id = jsonObject1.optString("id");
                            prefManager.setMerchantId(merchant_id);

                            getMerchantFullDetails(prefManager.getMerchantid());

                        }


                    } catch (Exception e) {
                        Toast.makeText(OTPVerificationActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                    }


                    if (responseUser != null) {

                    } else {
                        String err = String.format("Response is %s", String.valueOf(response.code()));
                        Toast.makeText(OTPVerificationActivity.this, err, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(OTPVerificationActivity.this, ""+t.toString(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();

            }
        });
    }

    private void openRegistrationActivity(User user) {
        Intent intent = new Intent(OTPVerificationActivity.this, NewRegistrationActivity.class);
        intent.putExtra("from", "otp");
        intent.putExtra("country_code", user.getPhone_number().getCountry_code());
        intent.putExtra("pno", user.getPhone_number().getNumber());
        startActivity(intent);
    }

    private void getMerchantFullDetails(String id) {

        Toast.makeText(this, "four", Toast.LENGTH_SHORT).show();


        apiService.getFullMerchantInfo(prefManager.getToken(), id).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        Toast.makeText(OTPVerificationActivity.this, "" + jObjError.getString("error_message"), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    JsonElement responseUser = response.body();

                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        Toast.makeText(OTPVerificationActivity.this, ""+jsonObject.toString(), Toast.LENGTH_SHORT).show();


                        takeMerchantToMainScreen(jsonObject);



                    } catch (Exception e) {

                    }


                    if (responseUser != null) {

                    } else {
                        String err = String.format("Response is %s", String.valueOf(response.code()));
                        Toast.makeText(OTPVerificationActivity.this, "server error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(OTPVerificationActivity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();

            }
        });
    }

    private void takeMerchantToMainScreen(JSONObject jsonObject) {

        Toast.makeText(getApplicationContext(), "toast", Toast.LENGTH_SHORT).show();

        try {
            String merchant_profile = jsonObject.getJSONObject("merchant").optString("merchant_profile");
            String cover_photos = jsonObject.getJSONObject("merchant").optString("cover_photos");
            String profile_photo = jsonObject.getJSONObject("merchant").optString("profile_photo");



            if (TextUtils.isEmpty(merchant_profile)&&TextUtils.isEmpty(cover_photos)&&TextUtils.isEmpty(profile_photo)) {
                Intent intent=new Intent(getApplicationContext(),SetUpProfileStepsActivity.class);
                intent.putExtra("position",0);
                startActivity(intent);
            }else {
//                saveServiceTags(jsonObject);
//                saveMerchantProfileInfo(jsonObject);
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }

            loadingDialog.dismissDialog();
        }catch (Exception e){

        }


    }


    public void saveServiceTags(JSONObject jsonObject){

        try {
            JSONArray servicesJsonArray =jsonObject.getJSONArray("services");

            String[] arr = new String[servicesJsonArray.length()];


            for(int i = 0; i < servicesJsonArray.length(); i++)
            {
                JSONObject objects = servicesJsonArray.getJSONObject(i);

                Iterator key = objects.keys();

                while (key.hasNext()) {

                    arr[i]= String.valueOf(objects.get("name"));


                }
            }

            prefManager.setServices(arr);

            Toast.makeText(this, ""+ Arrays.toString(prefManager.getServices()), Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }


    }

    private void saveMerchantProfileInfo(JSONObject jsonObject){
        try {
            JSONArray servicesJsonArray = jsonObject.getJSONArray("merchant_profile");

            String address_line = "",business_type="",longitude="",latitude="";


            for(int i = 0; i < servicesJsonArray.length(); i++)
            {
                JSONObject objects = servicesJsonArray.getJSONObject(i);
                Iterator key = objects.keys();


                while (key.hasNext()) {

                    address_line= String.valueOf(objects.get("address_line_1"));
                    business_type= String.valueOf(objects.get("business_type"));
                    longitude= String.valueOf(objects.get("longitude"));
                    latitude= String.valueOf(objects.get("latitude"));

                }
            }

            prefManager.setAddress(address_line);
            prefManager.setBusinessType(business_type);
            prefManager.setLatitude(latitude);
            prefManager.setLongitude(longitude);

            Toast.makeText(this, ""+prefManager.getLongitude(), Toast.LENGTH_SHORT).show();

        }catch (Exception e){

        }
    }


    @Override
    protected void onResume() {

        super.onResume();

        startService(smsServiceIntent);
    }


}
