package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.ict_life.merchantapp.R;

public class WaitingScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_screen);

        Button contact_us_btn=findViewById(R.id.contact_us_btn);

//        contact_us_btn.setOnClickListener(v->{
//            Intent intent=new Intent(WaitingScreenActivity.this,MainActivity.class);
//            startActivity(intent);
//        });
    }
}
