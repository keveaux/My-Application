package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ict_life.merchantapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        RelativeLayout layout_app_name=findViewById(R.id.layout_app_name);
        TextView tv_made_in_africa=findViewById(R.id.tv_made_in_africa);

        layout_app_name.setTranslationY(1000);
        layout_app_name.setAlpha(0);

        tv_made_in_africa.setTranslationX(-400);
        tv_made_in_africa.setAlpha(0);

        layout_app_name.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(200).start();
        tv_made_in_africa.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        Intent intent=new Intent(getApplicationContext(),IntroSliderActivity.class);
                        startActivity(intent);
                    }
                },
                2500
        );

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus) {
            return;
        }

        animate();

        super.onWindowFocusChanged(hasFocus);
    }

    private void animate() {
    }
}
