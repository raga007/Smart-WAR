package com.tripadvisor.smartwar;

import android.app.Application;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;

import net.danlew.android.joda.ResourceZoneInfoProvider;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class SmartWarInitialize extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LocationLibrary.initialiseLibrary(getBaseContext(), "com.tripadvisor.smartwar");
        CalligraphyConfig.initDefault("fonts/Roboto-Thin.ttf", R.attr.fontPath);
        //Initialize the Joda Time Library
        ResourceZoneInfoProvider.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
