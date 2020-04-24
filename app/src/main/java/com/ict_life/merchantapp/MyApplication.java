package com.ict_life.merchantapp;

import android.content.Context;

import androidx.core.content.FileProvider;
import androidx.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.ict_life.merchantapp.photoeditor.photoeditor.PhotoApp;

import io.fabric.sdk.android.Fabric;

public class MyApplication extends MultiDexApplication  {

    private static MyApplication myApplication;
    private static final String TAG = PhotoApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this,new Crashlytics());
        myApplication = this;


    }


    public Context getContext() {
        return myApplication.getContext();
    }


}


