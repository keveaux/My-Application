package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonElement;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.fragments.AsksFragment;
import com.ict_life.merchantapp.fragments.ProfileSettingsFragment;
import com.ict_life.merchantapp.fragments.HomeFragment;
import com.ict_life.merchantapp.prefs.PrefManager;
import com.ict_life.merchantapp.retrofit.APIService;
import com.ict_life.merchantapp.retrofit.APIUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView navigation;
    boolean doubleBackToExitPressedOnce = false;
    PrefManager prefManager;
    APIService apiService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout banner_layout = findViewById(R.id.banner_layout);
        ImageButton cancel_banner_btn = findViewById(R.id.cancel_banner_btn);
        Button finish_btn = findViewById(R.id.finish_btn);
        TextView name_tv=findViewById(R.id.name_tv);

        apiService = APIUtils.getAPIService();

        prefManager=new PrefManager(this);
        name_tv.setText("Hey "+prefManager.getFirstName()+"!");

        openFragment(new HomeFragment());

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        View activeLabel = navigation.findViewById(com.google.android.material.R.id.largeLabel);
        if (activeLabel instanceof TextView) {
            (activeLabel).setPadding(0, 0, 0, 0);
        }

        banner_layout.setTranslationY(-400);
        banner_layout.setAlpha(0);

        banner_layout.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(2000).start();

        cancel_banner_btn.setOnClickListener(v -> banner_layout.animate().translationY(-400).alpha(0).setDuration(800).setStartDelay(200).start());

        finish_btn.setOnClickListener(v -> {
            openSetUpProfilePage();
        });

//        openSetUpProfilePage();

//        getMerchantFullDetails(prefManager.getMerchantid());


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



    private void openSetUpProfilePage() {
        Intent intent = new Intent(getApplicationContext(), SetUpProfileStepsActivity.class);
        intent.putExtra("position", 0);
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                openFragment(new HomeFragment());

                return true;
            case R.id.navigation_asks:
                openFragment(new AsksFragment());

                return true;
            case R.id.navigation_coming_soon:
                openFragment(new ProfileSettingsFragment());

                return true;
        }
        return false;
    };

    public void openFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        fm.popBackStack();
        transaction.addToBackStack(null);
        transaction.add(R.id.main_frame_container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
