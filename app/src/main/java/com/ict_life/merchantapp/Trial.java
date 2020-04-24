package com.ict_life.merchantapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ict_life.merchantapp.activities.MainActivity;
import com.ict_life.merchantapp.activities.MapActivity;
import com.ict_life.merchantapp.activities.OTPVerificationActivity;
import com.ict_life.merchantapp.activities.SetUpProfileStepsActivity;
import com.ict_life.merchantapp.adapters.ImagesAdapter;
import com.ict_life.merchantapp.multipleimagepickerlibrary.MultiImageSelector;
import com.ict_life.merchantapp.prefs.PrefManager;
import com.ict_life.merchantapp.retrofit.APIService;
import com.ict_life.merchantapp.retrofit.APIUtils;
import com.ict_life.merchantapp.widgets.LoadingDialog;
import com.shivtechs.maplocationpicker.LocationPickerActivity;
import com.shivtechs.maplocationpicker.MapUtility;
import com.soundcloud.android.crop.Crop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Trial extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerViewImages;
    private GridLayoutManager gridLayoutManager;

    private ArrayList<String> mSelectedImagesList = new ArrayList<>();
    private final int MAX_IMAGE_SELECTION_LIMIT=10;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 401;
    private final int REQUEST_IMAGE=301;

    private MultiImageSelector mMultiImageSelector;
    private ImagesAdapter mImagesAdapter;
    ShareDialog shareDialog;
    private PrefManager prefManager;



    private static final String TAG = "Trial";
    private static final int ERROR_DIALOG_REQUEST=9901;
    private static final int ADDRESS_PICKER_REQUEST=200;
    LottieAnimationView image;
    APIService apiService = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);

        image=findViewById(R.id.image);

        prefManager = new PrefManager(this);
        apiService = APIUtils.getAPIService();


//        image.


//        if (isServicesOK()){
//            init();
//        }

//        MapUtility.apiKey = getResources().getString(R.string.maps_api_key);
//
//        Intent i =new Intent(Trial.this, LocationPickerActivity.class); startActivityForResult(i, ADDRESS_PICKER_REQUEST);


        getMerchantFullDetails(prefManager.getMerchantid());

       LoadingDialog loadingDialog=new LoadingDialog();
       loadingDialog.showDialog(this);

//        Uri uri = null;
//       new Crop(uri).output(uri).withMaxSize(3,3).start(this);

    }

    private void getMerchantFullDetails(String id) {
        apiService.getFullMerchantInfo(prefManager.getToken(), id).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        Toast.makeText(getApplicationContext(), "" + jObjError.getString("error_message"), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    JsonElement responseUser = response.body();

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
//                        Toast.makeText(getApplicationContext(), ""+jsonObject, Toast.LENGTH_SHORT).show();


                        saveinfo(jsonObject);
                        saveMerchantProfileInfo(jsonObject);
//                        takeMerchantToMainScreen(jsonObject);



                    } catch (Exception e) {
                        Toast.makeText(Trial.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                    if (responseUser != null) {

                    } else {
                        String err = String.format("Response is %s", String.valueOf(response.code()));
                        Toast.makeText(getApplicationContext(), "server error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveinfo(JSONObject jsonObject){
        JSONObject jsonData = jsonObject.optJSONObject("merchant");
        JSONArray servicesJsonArray = null;
        try {
            servicesJsonArray = jsonObject.getJSONArray("services");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] arr = new String[servicesJsonArray.length()];


        for(int i = 0; i < servicesJsonArray.length(); i++)
        {
            JSONObject objects = null;
            try {
                objects = servicesJsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Iterator key = objects.keys();
//                            arr[i] = servicesJsonArray.getString(i);
//                            Toast.makeText(Trial.this, ""+arr[i], Toast.LENGTH_SHORT).show();
//                            Toast.makeText(Trial.this, ""+objects.get(k), Toast.LENGTH_SHORT).show();
            while (key.hasNext()) {
                String k = key.next().toString();
//                                System.out.println("Key : " + k + ", value : "
//                                        + objects.getString(k));
//                                Toast.makeText(Trial.this, ""+objects.get(k), Toast.LENGTH_SHORT).show();
                try {
                    arr[i]= String.valueOf(objects.get("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        prefManager.setServices(arr);

        Toast.makeText(Trial.this, ""+ Arrays.toString(prefManager.getServices()), Toast.LENGTH_SHORT).show();

    }

    private void saveMerchantProfileInfo(JSONObject jsonObject){
        try {
            JSONArray servicesJsonArray = jsonObject.getJSONArray("merchant_profile");

            String address_line = "x",business_type="y",longitude="f",latitude="f";


            for(int i = 0; i < servicesJsonArray.length(); i++)
            {
                JSONObject objects = servicesJsonArray.getJSONObject(0);
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



        }catch (Exception e){
        }
    }

    private void takeMerchantToMainScreen(JSONObject jsonObject) {

        Toast.makeText(getApplicationContext(), "toast", Toast.LENGTH_SHORT).show();

        try {
            String merchant_profile = jsonObject.getJSONObject("merchant").optString("merchant_profile");
            String cover_photos = jsonObject.getJSONObject("merchant").optString("cover_photos");
            String profile_photo = jsonObject.getJSONObject("merchant").optString("profile_photo");



            if (TextUtils.isEmpty(merchant_profile)&&TextUtils.isEmpty(cover_photos)&&TextUtils.isEmpty(profile_photo)) {
                Intent intent=new Intent(getApplicationContext(), SetUpProfileStepsActivity.class);
                intent.putExtra("position",0);
                startActivity(intent);
            }else {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }catch (Exception e){

        }


    }


    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==ADDRESS_PICKER_REQUEST) {
            try {
                if (data !=null&& data.getStringExtra(MapUtility.ADDRESS) !=null) {
                    String address = data.getStringExtra(MapUtility.ADDRESS);
                    double selectedLatitude = data.getDoubleExtra(MapUtility.LATITUDE, 0.0);
                    double selectedLongitude = data.getDoubleExtra(MapUtility.LONGITUDE, 0.0);
//                    txtAddress.setText("Address: "+address);
//                    txtLatLong.setText("Lat:"+selectedLatitude+"  Long:"+selectedLongitude);

                    Toast.makeText(this, ""+address+selectedLatitude+selectedLongitude, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

//    private void init(){
//        Button map_btn=findViewById(R.id.map_btn);
//
//        map_btn.setOnClickListener(v->{
//            Intent intent=new Intent(Trial.this, MapActivity.class);
//            startActivity(intent);
//        });
//    }

    public boolean isServicesOK(){
        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Trial.this);

        if(available== ConnectionResult.SUCCESS){

            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(Trial.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this, "you cannot make map request", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

//
//    public void showBottomSheet() {
//        EditBusinessNameBottomDialogFragment addPhotoBottomDialogFragment =
//                EditBusinessNameBottomDialogFragment.newInstance();
//        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
//                EditBusinessNameBottomDialogFragment.TAG);
//    }

//    @Override public void onItemClick(String item) {
////        tvSelectedItem.setText("Selected action item is " + item);
//    }

//    @Override public void onItemClick(String item) {
//        tvSelectedItem.setText("Selected action item is " + item);
//    }

//    void init(){
//        callbackManager = CallbackManager.Factory.create();
//        shareDialog = new ShareDialog(this);
//        shareDialog.registerCallback(callbackManager, callback);
//    }

    private void share(){
        Bitmap image = BitmapFactory.decodeResource(getResources(),
                R.drawable.mzuri);

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        shareDialog.show(Trial.this,content);
    }

    private void checkPermissions(){
        ArrayList<String> arrPerm = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            arrPerm.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            arrPerm.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!arrPerm.isEmpty()) {
            String[] permissions = new String[arrPerm.size()];
            permissions = arrPerm.toArray(permissions);
            ActivityCompat.requestPermissions(Trial.this, permissions, REQUEST_ID_MULTIPLE_PERMISSIONS);
        }else {
            mMultiImageSelector.showCamera(true);
            mMultiImageSelector.count(MAX_IMAGE_SELECTION_LIMIT);
            mMultiImageSelector.multi();
            mMultiImageSelector.origin(mSelectedImagesList);
            mMultiImageSelector.start(Trial.this, REQUEST_IMAGE);
        }
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_IMAGE){
//            try {
//                mSelectedImagesList = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
//                mImagesAdapter = new ImagesAdapter(this,mSelectedImagesList);
//                recyclerViewImages.setAdapter(mImagesAdapter);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            floatingActionButton.performClick();
        }
    }

    public void clicck(View view) {
        image.playAnimation();
    }
}
