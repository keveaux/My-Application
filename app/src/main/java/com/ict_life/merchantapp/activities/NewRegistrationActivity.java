package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.prefs.PrefManager;
import com.ict_life.merchantapp.retrofit.APIService;
import com.ict_life.merchantapp.retrofit.APIUtils;
import com.ict_life.merchantapp.retrofit.entities.Business;
import com.ict_life.merchantapp.retrofit.entities.PhoneNumber;
import com.ict_life.merchantapp.retrofit.entities.UpdateUserDetails;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRegistrationActivity extends AppCompatActivity {

    private PrefManager prefManager;
    APIService apiService = null;
    String id,token;
    UpdateUserDetails updateUserDetails;
    String first_name,last_name,business_name,business_username="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_registration);

        Button finish_registration_btn=findViewById(R.id.finish_registration_btn);
        LinearLayout name_layout=findViewById(R.id.name_layout);
        EditText et_first_name=findViewById(R.id.et_first_name);
        EditText et_last_name=findViewById(R.id.et_last_name);
        EditText business_username_et=findViewById(R.id.business_username_et);
        EditText business_name_et=findViewById(R.id.business_name_et);


//        Bundle bundle=getIntent().getExtras();
//
//        String from=bundle.getString("from");
//
//        if(from.equals("business")){
//            name_layout.setVisibility(View.GONE);
//        }

        prefManager = new PrefManager(this);
        apiService = APIUtils.getAPIService();


         id=prefManager.getUserId();
         token=prefManager.getToken();

        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();

        Toast.makeText(this, ""+token, Toast.LENGTH_SHORT).show();

        PhoneNumber phoneNumber=new PhoneNumber(prefManager.getCountryCode(),prefManager.getPhoneNumber());



        finish_registration_btn.setOnClickListener(v->{

             first_name=et_first_name.getText().toString();
             last_name=et_last_name.getText().toString();
             business_name=business_name_et.getText().toString();
             business_username=business_username_et.getText().toString();

            if(first_name.isEmpty()){
                et_first_name.setError("Please enter details");
            }else if(last_name.isEmpty()){
                et_last_name.setError("Please enter details");
            }else if(business_name.isEmpty()){
                business_name_et.setError("Please enter details");
            }else if(business_username.isEmpty()){
                business_username_et.setError("Please enter details");
            }else {
                saveToPref();

                updateUserDetails =new UpdateUserDetails(et_first_name.getText().toString(),et_last_name.getText().toString(),phoneNumber,""+prefManager.getCountryCode()+prefManager.getPhoneNumber());

                UpdateUser(updateUserDetails);
            }


        });

    }

    private void saveToPref(){
        prefManager.setFirstName(first_name);
        prefManager.setLastName(last_name);
        prefManager.setBusinessName(business_name);
    }

    private void UpdateUser(UpdateUserDetails updateUserDetails){
        apiService.updateUser(token, updateUserDetails,id).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        Toast.makeText(NewRegistrationActivity.this, "" + jObjError.getString("error_message"), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    JsonElement responseUser = response.body();
                    Toast.makeText(NewRegistrationActivity.this, ""+responseUser.toString(), Toast.LENGTH_SHORT).show();

                    Business business=new Business(business_name,business_username);
                    CreateBusiness(business);

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());

                    } catch (Exception e) {

                    }

                }
                }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    private void CreateBusiness (Business business){
        apiService.createMerchant(token,business).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        Toast.makeText(NewRegistrationActivity.this, "" + jObjError.getString("error_message"), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    JsonElement responseUser = response.body();
                    Toast.makeText(NewRegistrationActivity.this, ""+responseUser.toString(), Toast.LENGTH_SHORT).show();

                    openValidateActivity();

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());

                    } catch (Exception e) {

                    }

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    private void openValidateActivity() {
        Intent intent=new Intent(NewRegistrationActivity.this,ValidateMerchantAccount.class);
        startActivity(intent);
    }
}
