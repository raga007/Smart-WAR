package com.tripadvisor.smartwar;


import android.util.Log;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.tripadvisor.smartwar.constants.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserLocationHelper {

    public static int LOCATION_CHECK_INTERVAL = 60*1000;

    //Custom visit thresholds
    public static int TEST_CONFIRMATION_THRESHOLD =2*60*1000;
    public static int SHORT_CONFIRMATION_THRESHOLD =10*60*1000;
    public static int LONG_CONFIRMATION_THRESHOLD =30*60*1000;

    public static double GEOFENCE_THRESHOLD = 0.03; //IN KM
    public static final String USER_LOCATION_DATA_KEY = "user location data";

    private static UserLocationHelper locationHelper;
    public static ArrayList<UserLocation> userLocationData;

    private Comparator locationListComparator = new Comparator <UserLocation>() {
        public int compare(UserLocation obj1, UserLocation obj2) {
            return new Long(obj2.getTimestamp()).compareTo(obj1.getTimestamp());
        }
    };

    public static UserLocationHelper getInstance(){
        if(locationHelper == null){
            locationHelper = new UserLocationHelper();
        }
        return locationHelper;
    }

    private UserLocationHelper(){
        userLocationData = new ArrayList<UserLocation>();
    }


    private void addUserLocation(float lat, float lng, long time){
        this.userLocationData.add(new UserLocation(lat, lng,time));
    }

    public void addUserLocation(LocationInfo info){
        this.userLocationData.add(new UserLocation(info.lastLat, info.lastLong, info.lastLocationUpdateTimestamp));
    }

    //returns 0, 1, 2, or 3
    //0 = NO
    //1 = Yes for test
    //2 = Yes for fast food
    //3 = Yes for all, (used for manual check in)
    public int hasUserStayedPutLongEnough(){
        long duration = getUserInRangeDuration();

        if (Constants.IS_TEST){
            if (duration > TEST_CONFIRMATION_THRESHOLD) {
                return 1;
            }
            return 0;
        }

        if (duration > LONG_CONFIRMATION_THRESHOLD){
            return 3;
        }
        else if (duration > SHORT_CONFIRMATION_THRESHOLD){
            return 2;
        }
        else {
            return 0;
        }
    }

    /**
     * Looks at the current state of the list and decides
     * how much time the user has stayed at a place.
     */
    public long getUserInRangeDuration(){

        Collections.sort(this.userLocationData,locationListComparator);

        UserLocation reference = this.userLocationData.get(0);
        UserLocation lastGoodLocation= this.userLocationData.get(0);

        for (UserLocation check: this.userLocationData){
            if (!isUserInRange(reference.getLatitude(),reference.getLongitude()
                    ,check.getLatitude(),check.getLongitude())){
                break;
            }
            lastGoodLocation = check;
        }

        return reference.getTimestamp() - lastGoodLocation.getTimestamp();
    }

    private boolean isUserInRange(float lat_a, float lng_a, float lat_b, float lng_b){
        double latOne = lat_a;
        double lonOne = lng_a;
        double latTwo = lat_b;
        double lonTwo = lng_b;
        return distance(latOne,lonOne,latTwo,lonTwo) < GEOFENCE_THRESHOLD;
    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}
