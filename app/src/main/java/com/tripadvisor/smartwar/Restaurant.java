package com.tripadvisor.smartwar;

import android.location.Location;

public class Restaurant {

    public Location location;
    public int location_id;
    String name;

    public Restaurant(int id, String name, double lat, double lng) {
        this.name = name;
        this.location_id = id;
        location = new Location("restaurant");
        location.setLatitude(lat);
        location.setLongitude(lng);
    }

    public Location getLocation() {
        return location;
    }

    public String toString(){
        return name+" location: "+location.toString();
    }

}