package com.tripadvisor.smartwar;

import android.app.Application;
import android.location.Location;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;
import com.pixplicity.easyprefs.library.Prefs;

import net.danlew.android.joda.ResourceZoneInfoProvider;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class SmartWarInitialize extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault("fonts/RobotoSlab-Regular.ttf", R.attr.fontPath);
        LocationLibrary.showDebugOutput(false);
        LocationLibrary.initialiseLibrary(getBaseContext(), 30000, 1000, "com.tripadvisor.smartwar");

        // Initialize the Prefs class
        Prefs.initPrefs(this);

        //Initialize the Joda Time Library
        ResourceZoneInfoProvider.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
