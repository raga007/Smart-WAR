package com.tripadvisor.smartwar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibraryConstants;

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
        Toast.makeText(context,"onReceive: "+receivedLocationInfo.toString(),Toast.LENGTH_LONG);
        NearbySearch.search(receivedLocationInfo.lastLat, receivedLocationInfo.lastLong, NearbySearch.RADIUS);
    }
}
