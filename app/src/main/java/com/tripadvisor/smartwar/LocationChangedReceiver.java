package com.tripadvisor.smartwar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibraryConstants;
import com.tripadvisor.smartwar.constants.Constants;
import com.tripadvisor.smartwar.constants.UserLocationHelper;

import java.util.ArrayList;

public class LocationChangedReceiver extends BroadcastReceiver {
    public LocationChangedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        LocationInfo receivedLocationInfo = null;
        if (extras != null) {
            receivedLocationInfo = (LocationInfo) intent.getExtras().get(LocationLibraryConstants.LOCATION_BROADCAST_EXTRA_LOCATIONINFO);
        }
        StringBuffer debugInfo = new StringBuffer();
        debugInfo.append("onReceive: " + receivedLocationInfo.toString() + "\n");

        UserLocationHelper userLocationHelper = UserLocationHelper.getInstance();
        userLocationHelper.addUserLocation(receivedLocationInfo);
        long stayedPutThreshold = userLocationHelper.hasUserStayedPutLongEnough();
        if (stayedPutThreshold != 0) {
            ArrayList<Restaurant> results = NearbySearch.search(receivedLocationInfo.lastLat, receivedLocationInfo.lastLong, Constants.SEARCH_RADIUS, stayedPutThreshold);
            for (Restaurant r : results) {
                debugInfo.append(r.toString() + "\n");
            }
        }
        debugInfo.append("---------------------------------------" + "\n");
        debugInfo.append("listoflocations:" + UserLocationHelper.getInstance().userLocationData.toString() + "\n");
        debugInfo.append("timestayedhere:" + UserLocationHelper.getInstance().getUserInRangeDuration() + "" + "\n");
        debugInfo.append("location:" + "lat: " + receivedLocationInfo.lastLat + " lon : " + receivedLocationInfo.lastLong + "\n");
        debugInfo.append("longenough?" + " Not stayed long enough" + "\n");
        debugInfo.append("---------------------------------------" + "\n");
        Log.e("debug : ", debugInfo.toString());
    }
}
