package com.ict_life.merchantapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.adapters.ImagesAdapter;
import com.ict_life.merchantapp.fragments.EditKindOfBusinessBottomSheetDialog;
import com.ict_life.merchantapp.fragments.EditServiceBottomSheetDialogFragment;
import com.ict_life.merchantapp.interfaces.KindOfBusinessInterface;
import com.ict_life.merchantapp.interfaces.ServiceProductTagsInterface;
import com.ict_life.merchantapp.multipleimagepickerlibrary.MultiImageSelector;
import com.ict_life.merchantapp.widgets.ScrollingLinearLayoutManager;
import com.shivtechs.maplocationpicker.LocationPickerActivity;
import com.shivtechs.maplocationpicker.MapUtility;
import com.takusemba.spotlight.OnSpotlightListener;
import com.takusemba.spotlight.OnTargetListener;
import com.takusemba.spotlight.Spotlight;
import com.takusemba.spotlight.Target;
import com.takusemba.spotlight.shape.Circle;
import com.takusemba.spotlight.shape.RoundedRectangle;

import java.util.ArrayList;
import java.util.Objects;

public class SetUpProfileStepsActivity extends AppCompatActivity implements OnMapReadyCallback, ServiceProductTagsInterface, KindOfBusinessInterface {

    private static final int REQUEST_PROFILE_IMAGE = 200;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 123;
    private static final int ADDRESS_PICKER_REQUEST = 400;
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private int[] layouts;
    CardView next_btn, previous_btn;
    ImageView profile_pic_iv, profile_pic_cover_layout_iv, add_business_details_prof_pic;
    View view;
    RecyclerView cover_photos_recycler_view, business_details_recycler_view;
    TextView address_tv, product_service_tags,tv_kind_of_business;
    private double lat = 20;
    private double lon = 60;
    private GoogleMap map;
    private MapView mapView;
    Bundle saved_state;
    CardView add_location_cv;
    ArrayList<String> img = new ArrayList<>();
    ArrayList<String> cover_images = new ArrayList<>();
    private ServiceProductTagsInterface serviceCallback;
    private KindOfBusinessInterface businessCallback;
    private ScrollView scroll_view;
    Spotlight spotlight;
    View view_spotlight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_profile_steps);

        saved_state = savedInstanceState;

        viewPager = findViewById(R.id.pager);
        dotsLayout = findViewById(R.id.layoutDots);
        next_btn = findViewById(R.id.next_cv);
        previous_btn = findViewById(R.id.previous_btn);
        Button skip_btn = findViewById(R.id.skip_btn);

        FrameLayout frameLayout=new FrameLayout(this);
        view_spotlight=getLayoutInflater().inflate(R.layout.target_layout,frameLayout);
        view_spotlight.setVisibility(View.GONE);


        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int position = bundle.getInt("position");

        viewPager.setPageTransformer(true, new PageTransformer());

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.upload_profile_photo_layout,
                R.layout.add_cover_photo_layout,
                R.layout.add_business_details_layout};


        // adding bottom dots
        addBottomDots(position);


        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setCurrentItem(position);
        viewPager.setOffscreenPageLimit(2);

        next_btn.setOnClickListener(v -> {

            int current = getItem(+1);
            if (current < layouts.length) {
                // move to next screen
                viewPager.setCurrentItem(current);
            }

            if (current == 3) {
                successPopUp();
            }

            if(view_spotlight.getVisibility() == View.VISIBLE){
                spotlight.finish();
            }
        });

        if (position == 0) {
            previous_btn.setVisibility(View.GONE);
        }

        previous_btn.setOnClickListener(v -> {
            int current = getItem(-1);
            if (current < layouts.length) {
                // move to next screen
                viewPager.setCurrentItem(current);
            }
        });

        skip_btn.setOnClickListener(v -> {
            int current = getItem(+1);
            if (current < layouts.length) {
                // move to next screen
                viewPager.setCurrentItem(current);
            }

            if (current == 3) {
                openMainActivity();
            }
        });

        MapUtility.apiKey = getResources().getString(R.string.maps_api_key);

        serviceCallback = this;
        businessCallback=this;



    }

    private void showSpotlight(){

        view_spotlight.setVisibility(View.VISIBLE);

        Target target=new Target.Builder()
                .setAnchor(findViewById(R.id.next_cv))
                .setOverlay(view_spotlight)
                .setOnTargetListener(new OnTargetListener() {
                    @Override
                    public void onStarted() {

                    }

                    @Override
                    public void onEnded() {

                    }
                }).build();

         spotlight= new Spotlight.Builder(this)
                .setTargets(target)
                .setBackgroundColor(R.color.spotlightBackground)
                .setDuration(1000L)
//                .setAnimation(DecelerateInterpolator(2f))
                .setOnSpotlightListener(new OnSpotlightListener() {
                    @Override
                    public void onStarted() {

                    }

                    @Override
                    public void onEnded() {

                    }
                })
          .build();

        spotlight.start();

        view_spotlight.findViewById(R.id.close_btn).setOnClickListener(v->spotlight.finish());

    }

    private void checkPermissions() {
        ArrayList<String> arrPerm = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(SetUpProfileStepsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            arrPerm.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            arrPerm.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


        if (!arrPerm.isEmpty()) {
            String[] permissions = new String[arrPerm.size()];
            permissions = arrPerm.toArray(permissions);
            ActivityCompat.requestPermissions(this, permissions, 100);
        } else {
            openGallery(1, REQUEST_PROFILE_IMAGE);
        }
    }

    private void openGallery(int count, int request_code) {
        MultiImageSelector mMultiImageSelector;

        //initialize multi image selector
        mMultiImageSelector = MultiImageSelector.create();

        mMultiImageSelector.showCamera(true);
        mMultiImageSelector.count(count);
        mMultiImageSelector.multi();
        startActivityForResult(mMultiImageSelector.createIntent(this), request_code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            checkPermissions();
        }

        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            checkLocationPermissions();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PROFILE_IMAGE) {

            try {
                setProfileImage(data);
                showSpotlight();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        if (requestCode == 300) {
            try {
                setBackgroundPicsImages(data);

            } catch (Exception e) {

            }

        }


        if (requestCode == ADDRESS_PICKER_REQUEST) {


            try {
                if (data != null && data.getStringExtra(MapUtility.ADDRESS) != null) {
                    setUpMapView(data);
                    scroll_view.postDelayed(() ->scroll_view.fullScroll(View.FOCUS_DOWN) ,1000);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }

    private void setProfileImage(Intent data) {
        img = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);

        Glide.with(this).load(img.get(0)).into(profile_pic_iv);
        Glide.with(this).load(img.get(0)).into(profile_pic_cover_layout_iv);
        Glide.with(this).load(img.get(0)).into(add_business_details_prof_pic);
    }

    private void setBackgroundPicsImages(Intent data) {
        cover_images = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
        setUpCoverPhotosRecyclerView(cover_images, cover_photos_recycler_view);
        setUpCoverPhotosRecyclerView(cover_images, business_details_recycler_view);
    }

    private void setUpMapView(Intent data) {
        String address = data.getStringExtra(MapUtility.ADDRESS);
        double selectedLatitude = data.getDoubleExtra(MapUtility.LATITUDE, 0.0);
        double selectedLongitude = data.getDoubleExtra(MapUtility.LONGITUDE, 0.0);

        address_tv.setText("" + address);


        lat = selectedLatitude;
        lon = selectedLongitude;

        mapView.setVisibility(View.VISIBLE);
        add_location_cv.setVisibility(View.GONE);


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 12.0f));

    }


    private void setUpCoverPhotosRecyclerView(ArrayList<String> cover_images, RecyclerView recyclerView) {

        recyclerView.setAdapter(new ImagesAdapter(getApplicationContext(), cover_images));
        recyclerView.setLayoutManager(new ScrollingLinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        if (cover_images.size() > 1) {

            final int speedScroll = 2000;
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                int count = 0;
                boolean flag = true;

                @Override
                public void run() {
                    if (count < recyclerView.getAdapter().getItemCount()) {
                        if (count == recyclerView.getAdapter().getItemCount() - 1) {
                            flag = false;
                        } else if (count == 0) {
                            flag = true;
                        }
                        if (flag) count++;
                        else count--;

                        recyclerView.smoothScrollToPosition(count);
                        handler.postDelayed(this, speedScroll);
                    }
                }
            };

            handler.postDelayed(runnable, speedScroll);
        }
    }

    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[layouts.length];

        int colorsActive = getResources().getColor(R.color.colorPrimary);
        int colorsInactive = getResources().getColor(R.color.light_grey);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    //	viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            if (position == 0) {
                previous_btn.setVisibility(View.GONE);
            } else {
                previous_btn.setVisibility(View.VISIBLE);
            }

            addBottomDots(position);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    public void SetServicesProductTags(ArrayList<String> tagsList) {
        if (!tagsList.isEmpty()) {
            product_service_tags.setText(tagsList.get(0));
            if (tagsList.size() > 1) {
                for (int i = 1; i < tagsList.size(); i++) {
                    product_service_tags.append("," + tagsList.get(i));
                }
            }
        }
    }

    @Override
    public void setKindOfBusiness(String kind_of_business) {
        tv_kind_of_business.setText(kind_of_business);
    }


    class PageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(@NonNull View page, float position) {

        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {

        MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            assert layoutInflater != null;
            view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);


            switch (position) {
                case 0:
                    RelativeLayout profile_layout = view.findViewById(R.id.profile_layout);
                    profile_pic_iv = view.findViewById(R.id.profile_pic_iv);


                    profile_layout.setOnClickListener(v -> checkPermissions());


                    break;
                case 1:
                    RelativeLayout topLayout_cover_photo = view.findViewById(R.id.topLayout_cover_photo);
                    profile_pic_cover_layout_iv = view.findViewById(R.id.profile_pic_cover_layout_iv);
                    cover_photos_recycler_view = view.findViewById(R.id.cover_photos_recycler_view);
                    ImageView upload_iv = view.findViewById(R.id.upload_iv);

                    upload_iv.setOnClickListener(v -> openGallery(5, 300));

                    topLayout_cover_photo.setOnClickListener(v -> openGallery(5, 300));
                    break;

                case 2:
                    initializeBusinessDetailsViews();
                    mapView.onCreate(saved_state);
                    mapView.getMapAsync(SetUpProfileStepsActivity.this);


                    break;

            }

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void initializeBusinessDetailsViews() {
        add_location_cv = view.findViewById(R.id.add_location_cv);
        CardView add_service_btn = view.findViewById(R.id.add_service_btn);
        CardView add_kind_of_biz_cv = view.findViewById(R.id.add_kind_of_biz_cv);
        business_details_recycler_view = view.findViewById(R.id.business_details_recycler_view);
        add_business_details_prof_pic = view.findViewById(R.id.add_business_details_prof_pic);
        address_tv = view.findViewById(R.id.address_tv);
        mapView = view.findViewById(R.id.map_view);
        product_service_tags = view.findViewById(R.id.tv_services);
        tv_kind_of_business=view.findViewById(R.id.tv_kind_of_business);
        scroll_view=view.findViewById(R.id.scroll_view);

        add_location_cv.setOnClickListener(v -> {
            OpenPlacePickerLibrary();
        });

        mapView.setOnClickListener(v -> {
            OpenPlacePickerLibrary();
        });

        add_service_btn.setOnClickListener(v -> {
            showEditServiceBottomSheet();
        });

        add_kind_of_biz_cv.setOnClickListener(v -> {
            showEditKindOfBusinessBottomSheet();
        });
    }

    private void showEditServiceBottomSheet() {
        EditServiceBottomSheetDialogFragment fragment =
                new EditServiceBottomSheetDialogFragment(serviceCallback);
        fragment.show(getSupportFragmentManager(), EditServiceBottomSheetDialogFragment.TAG);

    }

    private void showEditKindOfBusinessBottomSheet() {
        EditKindOfBusinessBottomSheetDialog fragment =
                new EditKindOfBusinessBottomSheetDialog(businessCallback);
        fragment.show(Objects.requireNonNull(getSupportFragmentManager()),
                EditKindOfBusinessBottomSheetDialog.TAG);

    }


    private void checkLocationPermissions() {
        ArrayList<String> arrPerm = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            arrPerm.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            arrPerm.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }


        if (!arrPerm.isEmpty()) {
            String[] permissions = new String[arrPerm.size()];
            permissions = arrPerm.toArray(permissions);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_ID_MULTIPLE_PERMISSIONS);
        } else {
            mapView.getMapAsync(this);
        }
    }

    private void OpenPlacePickerLibrary() {
        Intent i = new Intent(this, LocationPickerActivity.class);
        startActivityForResult(i, ADDRESS_PICKER_REQUEST);

    }

    private boolean checkPermission() {
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mapView != null) {
            map = googleMap;
            map.getUiSettings().setMyLocationButtonEnabled(false);
            if(checkPermission())
                map.setMyLocationEnabled(true);

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 2.0f));

            googleMap.setOnMapClickListener(latLng -> OpenPlacePickerLibrary());
        }


    }

    @Override
    public void onResume() {
        if (mapView != null) {
            mapView.onResume();
        }
        super.onResume();
    }


    @Override
    public void onPause() {
        if (mapView != null) {
            mapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mapView != null) {
            mapView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        if (mapView != null) {
            mapView.onLowMemory();
        }
        super.onLowMemory();
    }

    private void successPopUp() {

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(SetUpProfileStepsActivity.this);
        View layoutView = (SetUpProfileStepsActivity.this).getLayoutInflater().inflate(R.layout.success_dialog_layout, null);
        dialogBuilder.setView(layoutView);
        dialogBuilder.setCancelable(false);


        Button ok_btn = layoutView.findViewById(R.id.ok_btn);
        TextView text_tv = layoutView.findViewById(R.id.text_tv);

        text_tv.setText("Your business profile is 100% complete");

        ok_btn.setOnClickListener(v -> {
            openMainActivity();
        });


        android.app.AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();


    }

    private void openMainActivity(){
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }


}
