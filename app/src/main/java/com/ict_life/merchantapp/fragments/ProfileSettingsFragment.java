package com.ict_life.merchantapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.ict_life.merchantapp.R;
import com.ict_life.merchantapp.activities.SettingsActivity;
import com.ict_life.merchantapp.adapters.ProfileViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ProfileSettingsFragment extends Fragment {

    View view;
    ViewPager viewPager_profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_coming_soon, container, false);

        TabLayout tabLayout=view.findViewById(R.id.tabLayout);
        viewPager_profile=view.findViewById(R.id.viewPager_profile);
        ImageView settings_iv=view.findViewById(R.id.settings_iv);


        final ProfileViewPagerAdapter adapter = new ProfileViewPagerAdapter(getContext(),getFragmentManager(),
                tabLayout.getTabCount());

        tabLayout.addTab(tabLayout.newTab().setText("My Businesses"));
        tabLayout.addTab(tabLayout.newTab().setText("Notifications"));

        setupViewPager(viewPager_profile);

        tabLayout.setupWithViewPager(viewPager_profile);

        settings_iv.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
        });


        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ProfileSettingsFragment.ViewPagerAdapter adapter = new ProfileSettingsFragment.ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new MyBusinessesFragment(), "My Businesses");
        adapter.addFrag(new NotificationsFragment(), "Notifications");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
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


}
