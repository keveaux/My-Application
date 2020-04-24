package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ict_life.merchantapp.R;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import kotlin.jvm.internal.PropertyReference0Impl;

public class LoginActivity extends AppCompatActivity {

    String et_pno_value="";
    String country_code_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView iv_sign_up_logo = findViewById(R.id.iv_sign_up_logo);
        Button sign_in_button = findViewById(R.id.sign_in_button);
        TextView tv_terms_and_conditions = findViewById(R.id.tv_terms_and_conditions);
        EditText et_pno = findViewById(R.id.et_pno);
        CountryCodePicker countryCodePicker=findViewById(R.id.ccp);

//        Glide.with(this)
//                .load(R.drawable.logo)
//                .into(iv_sign_up_logo);


        Spannable wordtoSpan = new SpannableString(getResources().getString(R.string.terms_and_conditions));

        wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 38, 58, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_terms_and_conditions.setText(wordtoSpan);

        et_pno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                et_pno_value = et_pno.getText().toString();

                if (et_pno_value.startsWith("0") && et_pno_value.length() > 10) {

                    et_pno.setError("Must not exceed 10 digits");

                } else if (!et_pno_value.startsWith("0") && et_pno_value.length() > 9) {

                    et_pno.setError("Must not exceed 9 digits");

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sign_in_button.setOnClickListener(v -> {

             country_code_str=countryCodePicker.getSelectedCountryCode();


            if(et_pno_value.equals("")){
                et_pno.setError("Please enter your phone number");
            }else {

                if(et_pno_value.startsWith("0")){
                    et_pno_value=et_pno_value.substring(1);
                }else if(et_pno_value.length()<9){
                    et_pno.setError("Enter correct value");
                }else {

                    if(!checkIfAlreadyhavePermission()){
                        requestForSMSPermission();
                    }else {
                        openOTPVerificationActivity();
                    }

                }


            }

        });

    }

    private void openOTPVerificationActivity(){
        Intent intent = new Intent(getApplicationContext(), OTPVerificationActivity.class);
        intent.putExtra("pno", et_pno_value);
        intent.putExtra("country_code",country_code_str);
        startActivity(intent);
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;
        } else {
            return false;
        }
    }

    private void requestForSMSPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 101) {
            openOTPVerificationActivity();
        }

    }


}
