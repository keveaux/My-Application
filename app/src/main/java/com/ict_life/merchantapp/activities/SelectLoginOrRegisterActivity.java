package com.ict_life.merchantapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ict_life.merchantapp.R;


public class SelectLoginOrRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_or_register);

        TextView tv_logo=findViewById(R.id.tv_logo);
        TextView tv_motto=findViewById(R.id.tv_motto);
        Button btn_log_in=findViewById(R.id.btn_log_in);
        Button btn_sign_up=findViewById(R.id.btn_sign_up);

        tv_logo.setTranslationX(400);
        tv_logo.setAlpha(0);

        tv_motto.setTranslationX(400);
        tv_motto.setAlpha(0);

        btn_log_in.setTranslationX(400);
        btn_log_in.setAlpha(0);

        btn_sign_up.setTranslationX(400);
        btn_sign_up.setAlpha(0);


        tv_logo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        tv_motto.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        btn_sign_up.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        btn_log_in.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();


        btn_log_in.setOnClickListener(v->{
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        });

        btn_sign_up.setOnClickListener(v->{
            Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(intent);
        });


    }
}
