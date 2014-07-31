package com.tripadvisor.smartwar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibraryConstants;
import com.tripadvisor.smartwar.constants.Constants;

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
            receivedLocationInfo.refresh(context);
        }

        StringBuffer debugInfo = new StringBuffer();

        UserLocationHelper userLocationHelper = UserLocationHelper.getInstance();
        userLocationHelper.addUserLocation(receivedLocationInfo);
        int stayedPutThreshold = userLocationHelper.hasUserStayedPutLongEnough();
        if (stayedPutThreshold != 0) {
            ArrayList<Restaurant> results = NearbySearch.search(receivedLocationInfo.lastLat, receivedLocationInfo.lastLong, Constants.POLLING_RADIUS, stayedPutThreshold);
            if (Constants.IS_TEST) {
                for (Restaurant r : results) {
                    debugInfo.append(r.toString() + "\n");
                }
            }
        }

        if (Constants.IS_TEST) {
            debugInfo.append("onReceive: " + receivedLocationInfo.toString() + "\n");
            debugInfo.append("---------------------------------------" + "\n");
//            debugInfo.append("listoflocations:" + UserLocationHelper.getInstance().userLocationData.toString() + "\n");
            debugInfo.append("timestayedhere:" + UserLocationHelper.getInstance().getUserInRangeDuration() + "" + "\n");
            debugInfo.append("location:" + "lat: " + receivedLocationInfo.lastLat + " lon : " + receivedLocationInfo.lastLong + "\n");
            debugInfo.append("longenough:" + UserLocationHelper.getInstance().hasUserStayedPutLongEnough() + "\n");
            debugInfo.append("---------------------------------------" + "\n");
            Log.e("debug : ", debugInfo.toString());
        }

    }
}
