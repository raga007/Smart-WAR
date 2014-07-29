package com.tripadvisor.smartwar.constants;


import android.util.FloatMath;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.tripadvisor.smartwar.UserLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserLocationHelper {

    public static int LOCATION_CHECK_INTERVAL = 60*1000;
    public static int USER_RESTAURANT_VISIT_CONFIRMATION_TRESHOLD=30*60*1000;
    public static int GEOFENCE_TRESHOLD = 20;

    private static UserLocationHelper locationHelper;
    public static ArrayList<UserLocation> userLocationData;
    private Comparator locationListComparator = new Comparator <UserLocation>() {
        public int compare(UserLocation obj1, UserLocation obj2) {
            return new Long(obj1.getTimestamp()).compareTo(obj2.getTimestamp());
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


    public void addUserLocation(float lat, float longi, long time){
        this.userLocationData.add(new UserLocation(lat, longi,time));
    }

    public void addUserLocation(LocationInfo info){
        this.userLocationData.add(new UserLocation(info.lastLat,info.lastLong,info.lastLocationUpdateTimestamp));
    }

    public boolean hasUserStayedPutLongEnough(){
        return getUserInRangeDuration() > USER_RESTAURANT_VISIT_CONFIRMATION_TRESHOLD;
    }

    /**
     * Looks at the current state of the list and decides
     * how much time the user has stayed at a place.
     */
    public long getUserInRangeDuration(){
        Collections.sort(this.userLocationData,locationListComparator);
        if(this.userLocationData.size() <= 1) return 0;
        UserLocation reference = this.userLocationData.get(0);
        UserLocation check = this.userLocationData.get(1);
        int index = 1;
        while(this.userLocationData.size() - 1 > index && isUserInRange(reference.getLatitude(),reference.getLongitude()
                ,check.getLatitude(),check.getLongitude())){
            index++;
            check = this.userLocationData.get(index);
        }
        index--;
        check = this.userLocationData.get(index);
        return reference.getTimestamp() - check.getTimestamp();
    }

    private boolean isUserInRange(float lat_a, float lng_a, float lat_b, float lng_b){
        double latOne = lat_a;
        double lonOne = lng_a;
        double latTwo = lat_b;
        double lonTwo = lng_b;
        return distance(latOne,lonOne,latTwo,lonTwo,'k') < GEOFENCE_TRESHOLD;
    }

    public double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
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
