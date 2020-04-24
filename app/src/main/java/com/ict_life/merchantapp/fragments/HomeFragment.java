package com.ict_life.merchantapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonElement;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.activities.MainActivity;
import com.ict_life.merchantapp.activities.SetUpProfileStepsActivity;
import com.ict_life.merchantapp.prefs.PrefManager;
import com.ict_life.merchantapp.retrofit.APIService;
import com.ict_life.merchantapp.retrofit.APIUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tabOne,tabTwo,tabThree,services_list_tv,phone_no_details_tv;
    private RatingBar rating_bar;
    TextView count_tv;
    PrefManager prefManager;
    APIService apiService = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);

        apiService = APIUtils.getAPIService();

        prefManager=new PrefManager(getContext());

        tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabThree = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);

        Button btn_show_reviews=view.findViewById(R.id.btn_show_reviews);
        LinearLayout all_details_layout=view.findViewById(R.id.all_details_layout);
        LinearLayout rating_preview_layout=view.findViewById(R.id.rating_preview_layout);
        count_tv=view.findViewById(R.id.count_txt);
        services_list_tv=view.findViewById(R.id.services_list_tv);
        phone_no_details_tv=view.findViewById(R.id.phone_no_details_tv);

        RelativeLayout layoutTop = view.findViewById(R.id.layoutTop);
        RelativeLayout profile_layout=view.findViewById(R.id.profile_layout);
        ImageButton add_logo_btn=view.findViewById(R.id.add_logo_btn);
        TextView business_name_initial=view.findViewById(R.id.business_name_initial);
        TextView business_name_tv=view.findViewById(R.id.business_name_tv);
        PrefManager prefManager=new PrefManager(getContext());

        business_name_tv.setText(prefManager.getBusinessName());
        business_name_initial.setText(prefManager.getBusinessName().substring(0,1));

        layoutTop.setOnClickListener(v->{
            openProfileStepActivity(1);
        });

        profile_layout.setOnClickListener(v->{
            openProfileStepActivity(0);
        });

        add_logo_btn.setOnClickListener(v->{
            openProfileStepActivity(0);
        });


        rating_bar = view.findViewById(R.id.rating_bar);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            try {
                Drawable progressDrawable = rating_bar.getProgressDrawable();
                if (progressDrawable != null) {
                    DrawableCompat.setTint(progressDrawable, ContextCompat.getColor(getContext(), R.color.colorPrimary));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        viewPager =  view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(2);


        tabLayout =  view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setSecondItemSelected();

        changeTabImageColor();

        btn_show_reviews.setOnClickListener(v->{
            all_details_layout.setVisibility(View.GONE);
            rating_preview_layout.setVisibility(View.VISIBLE);


            new CountDownTimer(10000, 1000) {

                public void onTick(long millisUntilFinished) {

                    count_tv.setText("" + millisUntilFinished / 1000);
                    //here you can have your logic to set text to edittext
                }

                public void onFinish() {
                    rating_preview_layout.setVisibility(View.GONE);
                    all_details_layout.setVisibility(View.VISIBLE);
                }

            }.start();

        });

        getMerchantFullDetails();
        phone_no_details_tv.setText(prefManager.getPhoneNumber());


        return view;
    }

    private void openProfileStepActivity(int position){
        Intent i =new Intent(getContext(), SetUpProfileStepsActivity.class);
        i.putExtra("position",position);
        startActivity(i);

    }

    private void changeTabImageColor() {
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                Toast.makeText(getContext(), ""+tab.getPosition(), Toast.LENGTH_SHORT).show();

                switch (tab.getPosition()){
                    case 0:
                        setupTabIcons();
                        break;
                    case 1:
                        setSecondItemSelected();
                        break;
                    case 2:
                        setThirdItemSelected();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }
        });
    }


    private void setSecondItemSelected() {
        tabOne.setText(R.string.reviews_);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.reviews_icon_unselected, 0, 0);
        tabOne.setTextColor(getResources().getColor(R.color.grey));
        tabLayout.getTabAt(0).setCustomView(tabOne);

        tabTwo.setText("Posts");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.gallery_icon, 0, 0);
        tabTwo.setTextColor(getResources().getColor(R.color.colorAccent));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        tabThree.setText("Edit Info");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.info_icon_unselected, 0, 0);
        tabThree.setTextColor(getResources().getColor(R.color.grey));
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }


    private void setThirdItemSelected() {
        tabOne.setText("Reviews");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.reviews_icon_unselected, 0, 0);
        tabOne.setTextColor(getResources().getColor(R.color.grey));
        tabLayout.getTabAt(0).setCustomView(tabOne);

        tabTwo.setText("Posts");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.gallery_icon_unselected, 0, 0);
        tabTwo.setTextColor(getResources().getColor(R.color.grey));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        tabThree.setText("Edit Info");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.info_icon, 0, 0);
        tabThree.setTextColor(getResources().getColor(R.color.colorAccent));
        tabLayout.getTabAt(2).setCustomView(tabThree);

        ((MainActivity) Objects.requireNonNull(getActivity())).navigation.setVisibility(View.VISIBLE);

    }

    private void setupTabIcons() {

        tabOne.setText("Reviews");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.reviews_icon, 0, 0);
        tabOne.setTextColor(getResources().getColor(R.color.colorAccent));
        tabLayout.getTabAt(0).setCustomView(tabOne);

        tabTwo.setText("Posts");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.gallery_icon_unselected, 0, 0);
        tabTwo.setTextColor(getResources().getColor(R.color.grey));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        tabThree.setText("Edit Info");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.info_icon_unselected, 0, 0);
        tabThree.setTextColor(getResources().getColor(R.color.grey));
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new ReviewsFragment(), "Reviews");
        adapter.addFrag(new PostsFragment(), "Posts");
        adapter.addFrag(new InfoFragment(), "Info");
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void getMerchantFullDetails() {

        Toast.makeText(getActivity(), "four", Toast.LENGTH_SHORT).show();

        apiService.getFullMerchantInfo(prefManager.getToken(), prefManager.getMerchantid()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        Toast.makeText(getActivity(), "" + jObjError.getString("error_message"), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    JsonElement responseUser = response.body();

                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        Toast.makeText(getActivity(), ""+jsonObject.toString(), Toast.LENGTH_SHORT).show();

                        JSONArray servicesJsonArray =jsonObject.getJSONArray("services");

                        String[] arr = new String[servicesJsonArray.length()];


                        for(int i = 0; i < servicesJsonArray.length(); i++)
                        {
                            JSONObject objects = servicesJsonArray.getJSONObject(i);

                            arr[i] = String.valueOf(objects.get("name"));


                        }

                        prefManager.setServices(arr);

                        Toast.makeText(getActivity(), ""+ Arrays.toString(prefManager.getServices()), Toast.LENGTH_SHORT).show();

                        services_list_tv.setText(arr[0]);

                        if(arr.length>1){
                            for (int i=1;i<arr.length;i++){
                                services_list_tv.append(","+arr[i]);
                            }
                        }


                    } catch (Exception e) {

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
                Toast.makeText(getActivity(), "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
