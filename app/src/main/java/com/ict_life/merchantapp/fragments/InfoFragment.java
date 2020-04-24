package com.ict_life.merchantapp.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonElement;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.interfaces.KindOfBusinessInterface;
import com.ict_life.merchantapp.interfaces.ServiceProductTagsInterface;
import com.ict_life.merchantapp.prefs.PrefManager;
import com.ict_life.merchantapp.retrofit.APIService;
import com.ict_life.merchantapp.retrofit.APIUtils;
import com.ict_life.merchantapp.retrofit.entities.BusinessLocation;
import com.shivtechs.maplocationpicker.LocationPickerActivity;
import com.shivtechs.maplocationpicker.MapUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InfoFragment extends Fragment implements EditBusinessNameBottomDialogFragment.setBusinessNameCallback,
        EditContactsInformationBottomSheetFragment.setContactInfoCallBack,ServiceProductTagsInterface, KindOfBusinessInterface, OnMapReadyCallback {


    private View view;
    private EditBusinessNameBottomDialogFragment.setBusinessNameCallback callback;
    private EditContactsInformationBottomSheetFragment.setContactInfoCallBack contactInfoCallBack;
    private ServiceProductTagsInterface serviceCallback;
    private TextView business_name_tv,address_tv,product_service_tags,kind_of_biz_tv;
    private TextView contact_info_tv;
    private static final int ADDRESS_PICKER_REQUEST=200;
    private MapView mapView;
    private GoogleMap map;
    private double lat,lon=0;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 401;
    private KindOfBusinessInterface kindOfBusinessInterface;
    private APIService apiService;
    private PrefManager prefManager;
    String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBsaWNhdGlvbl90eXBlIjoidXNlciIsImV4cCI6MTYxNjA1MzY5NiwicmVmcmVzaCI6MTU4NDUyMTI5Niwic2Vzc2lvbl9pZCI6ODg5LCJzdGF0dXMiOiJhY3RpdmUiLCJ1c2VyX2lkIjoxMX0.6AnjLGv44AlhqQl4ne2WJwwLqtXDHJ3b7FR9aJH1ueQ";





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_info, container, false);
        ImageButton edit_business_description=view.findViewById(R.id.edit_business_description);
        ImageButton edit_business_location=view.findViewById(R.id.edit_business_location);
        ImageButton edit_business_sevice=view.findViewById(R.id.edit_business_sevice);
        ImageButton edit_business_type=view.findViewById(R.id.edit_business_type);
        ImageButton edit_business_contact=view.findViewById(R.id.edit_business_contact);
        contact_info_tv=view.findViewById(R.id.contact_info_tv);
        address_tv=view.findViewById(R.id.address_tv);
        mapView=view.findViewById(R.id.map_view);
        product_service_tags=view.findViewById(R.id.product_service_tags);
        kind_of_biz_tv=view.findViewById(R.id.kind_of_biz_tv);


        business_name_tv=view.findViewById(R.id.business_name_tv);

        MapUtility.apiKey = getResources().getString(R.string.maps_api_key);


        mapView.onCreate(savedInstanceState);

        checkPermissions();


        edit_business_description.setOnClickListener(v-> showEditBusinessNameBottomSheet());

        edit_business_location.setOnClickListener(v-> OpenPlacePickerLibrary());

        edit_business_sevice.setOnClickListener(v-> showEditServiceBottomSheet());

        edit_business_type.setOnClickListener(v-> showEditKindOfBusinessBottomSheet());

        edit_business_contact.setOnClickListener(v-> showEditContactBottomSheet());

        callback=this;
        contactInfoCallBack=this;
        serviceCallback=this;
        kindOfBusinessInterface=this;

        prefManager=new PrefManager(getContext());
        apiService = APIUtils.getAPIService();



        return view;
    }

    private void OpenPlacePickerLibrary(){
        Intent i =new Intent(getContext(), LocationPickerActivity.class);
        startActivityForResult(i, ADDRESS_PICKER_REQUEST);
    }

    private void showEditBusinessNameBottomSheet() {
        EditBusinessNameBottomDialogFragment fragment =
                new EditBusinessNameBottomDialogFragment(callback);
        fragment.show(Objects.requireNonNull(getFragmentManager()),
                EditBusinessNameBottomDialogFragment.TAG);
    }

    private void showEditServiceBottomSheet() {
        EditServiceBottomSheetDialogFragment fragment =
                new EditServiceBottomSheetDialogFragment(serviceCallback);
        fragment.show(Objects.requireNonNull(getFragmentManager()),
                EditServiceBottomSheetDialogFragment.TAG);
    }


    private void showEditKindOfBusinessBottomSheet() {
        EditKindOfBusinessBottomSheetDialog fragment =
                new EditKindOfBusinessBottomSheetDialog(kindOfBusinessInterface);
        fragment.show(Objects.requireNonNull(getFragmentManager()),
                EditKindOfBusinessBottomSheetDialog.TAG);

    }

    private void showEditContactBottomSheet() {
        EditContactsInformationBottomSheetFragment fragment =
                new EditContactsInformationBottomSheetFragment(contactInfoCallBack);
        fragment.show(Objects.requireNonNull(getFragmentManager()),
                EditContactsInformationBottomSheetFragment.TAG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==ADDRESS_PICKER_REQUEST) {
            try {
                if (data !=null&& data.getStringExtra(MapUtility.ADDRESS) !=null) {
                    String address = data.getStringExtra(MapUtility.ADDRESS);
                    double selectedLatitude = data.getDoubleExtra(MapUtility.LATITUDE, 0.0);
                    double selectedLongitude = data.getDoubleExtra(MapUtility.LONGITUDE, 0.0);
                    address_tv.setText(""+address);

                    lat=selectedLatitude;
                    lon=selectedLongitude;


                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon),12.0f));

                    //send business location to server
                    BusinessLocation location=new BusinessLocation(lat,lon,address,12);
                    UpdateLocationDetails(location);


                    Toast.makeText(getContext(), ""+address+selectedLatitude+selectedLongitude, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void UpdateLocationDetails(BusinessLocation location){
        apiService.updateLocationDetails(token,location).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Toast.makeText(getActivity(), "res", Toast.LENGTH_SHORT).show();

                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        Toast.makeText(getActivity(), "" + jObjError.getString("error_message")+response.code(), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    JsonElement responseUser = response.body();
                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();

                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        Toast.makeText(getActivity(), "" + jsonObject.toString(), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                    if (responseUser != null) {

                    } else {
                        String err = String.format("Response is %s", String.valueOf(response.code()));
                        Toast.makeText(getActivity(), "server error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void setBusinessNameCallbackMethod(String result) {
        business_name_tv.setText(result);
    }


    @Override
    public void setContactInfoCallbackMethod(String s) {
        contact_info_tv.setText(s);
    }

    private void checkPermissions(){
        ArrayList<String> arrPerm = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            arrPerm.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            arrPerm.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }


        if(!arrPerm.isEmpty()) {
            String[] permissions = new String[arrPerm.size()];
            permissions = arrPerm.toArray(permissions);
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_ID_MULTIPLE_PERMISSIONS);
        }else {
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            checkPermissions();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon),2.0f));

    }

    @Override
    public void onResume() {
        if(mapView != null) {
            mapView.onResume();
        }
        super.onResume();
    }


    @Override
    public void onPause() {
        if(mapView != null) {
            mapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if(mapView != null) {
            mapView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        if(mapView != null) {
            mapView.onLowMemory();
        }
        super.onLowMemory();
    }

    @Override
    public void SetServicesProductTags(ArrayList<String> tagsList) {
        if(!tagsList.isEmpty()){
            product_service_tags.setText(tagsList.get(0));
            if(tagsList.size()>1){
                for (int i=1;i<tagsList.size();i++){
                    product_service_tags.append(","+tagsList.get(i));
                }
            }
        }
    }

    @Override
    public void setKindOfBusiness(String kind_of_business) {
        kind_of_biz_tv.setText(kind_of_business);
    }
}
