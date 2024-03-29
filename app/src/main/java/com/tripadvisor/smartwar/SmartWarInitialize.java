package com.tripadvisor.smartwar;

import android.app.Application;
import android.location.Location;

import com.google.android.gms.internal.in;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pixplicity.easyprefs.library.Prefs;
import com.tripadvisor.smartwar.constants.Constants;

import net.danlew.android.joda.ResourceZoneInfoProvider;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class SmartWarInitialize extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault("fonts/RobotoSlab-Regular.ttf", R.attr.fontPath);
        LocationLibrary.showDebugOutput(false);
        LocationLibrary.initialiseLibrary(getBaseContext(), Constants.GET_LOCATION_RATE, 1000, "com.tripadvisor.smartwar");
        Prefs.initPrefs(this);

        //Initialize the Joda Time Library
        ResourceZoneInfoProvider.init(this);

        //Initialize the image loader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
