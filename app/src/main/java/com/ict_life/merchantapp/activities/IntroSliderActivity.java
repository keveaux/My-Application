package com.ict_life.merchantapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ict_life.merchantapp.R;

public class IntroSliderActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnLetsGo;
    private RelativeLayout next_layout;
    CardView next_btn,previous_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro_slider);


        viewPager =  findViewById(R.id.pager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnSkip =  findViewById(R.id.btn_skip);
        btnLetsGo =  findViewById(R.id.btn_next);
        next_layout=findViewById(R.id.next_layout);
        previous_btn=findViewById(R.id.previous_btn);
        next_btn=findViewById(R.id.next_cv);
        btnLetsGo.setVisibility(View.GONE);
        previous_btn.setVisibility(View.GONE);


        ZoomOutTransformation zoomOutTransformation = new ZoomOutTransformation();

        viewPager.setPageTransformer(true,zoomOutTransformation);

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.step_one,
                R.layout.step_two,
                R.layout.step_three};

        // adding bottom dots
        addBottomDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(v -> launchHomeScreen());

        btnLetsGo.setOnClickListener(v -> {
            // checking for last page
            // if last page home screen will be launched
            int current = getItem(+1);
            if (current < layouts.length) {
                // move to next screen
                viewPager.setCurrentItem(current);
            } else {
                launchHomeScreen();
            }
        });

        next_btn.setOnClickListener(v -> {

            int current = getItem(+1);
            if (current < layouts.length) {
                // move to next screen
                viewPager.setCurrentItem(current);
            }

        });


        previous_btn.setOnClickListener(v -> {
            int current = getItem(-1);
            if (current < layouts.length) {
                // move to next screen
                viewPager.setCurrentItem(current);
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

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

    private void launchHomeScreen() {
        //   prefManager.setFirstTimeLaunch(false);

        startActivity(new Intent(IntroSliderActivity.this, LoginActivity.class));
        finish();

    }

    //	viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            if(position==0){
                previous_btn.setVisibility(View.GONE);
            }else {
                previous_btn.setVisibility(View.VISIBLE);
            }


            if(position!=2){
                btnLetsGo.setVisibility(View.GONE);
                btnSkip.setVisibility(View.INVISIBLE);
                next_layout.setVisibility(View.VISIBLE);

            }else {
                next_layout.setVisibility(View.GONE);
                btnSkip.setVisibility(View.GONE);
                btnLetsGo.setVisibility(View.VISIBLE);
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

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

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

    public class ZoomOutTransformation implements ViewPager.PageTransformer {

        private static final float MIN_SCALE = 0.65f;
        private static final float MIN_ALPHA = 0.3f;

        @Override
        public void transformPage(View page, float position) {

            if (position <-1){  // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.setAlpha(0);

            }
            else if (position <=1){ // [-1,1]

                page.setScaleX(Math.max(MIN_SCALE,1-Math.abs(position)));
                page.setScaleY(Math.max(MIN_SCALE,1-Math.abs(position)));
                page.setAlpha(Math.max(MIN_ALPHA,1-Math.abs(position)));

            }
            else {  // (1,+Infinity]
                // This page is way off-screen to the right.
                page.setAlpha(0);

            }


        }
    }

}
