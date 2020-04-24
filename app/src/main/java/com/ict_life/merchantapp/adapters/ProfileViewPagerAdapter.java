package com.ict_life.merchantapp.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ict_life.merchantapp.fragments.MyBusinessesFragment;
import com.ict_life.merchantapp.fragments.NotificationsFragment;

public class ProfileViewPagerAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;

    public ProfileViewPagerAdapter (Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MyBusinessesFragment myBusinessesFragment=new MyBusinessesFragment();
                return myBusinessesFragment;
            case 1:
                NotificationsFragment notificationsFragment=new NotificationsFragment();
                return notificationsFragment;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
