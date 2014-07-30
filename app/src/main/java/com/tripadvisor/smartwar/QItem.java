package com.tripadvisor.smartwar;

import android.location.*;
import android.text.format.Time;

import java.util.ArrayList;
import java.util.Date;

import java.util.Calendar;

public class QItem implements Comparable<QItem>{

    public Restaurant restaurant;
    public Time timeAddedToQ = new Time();

    public QItem(Restaurant r, long timeInMillis){
        this.restaurant = r;
        timeAddedToQ.set(timeInMillis);
    }

    public long age() {
       return (Calendar.getInstance().getTimeInMillis() - timeAddedToQ.toMillis(false));
    }

    public ArrayList<QItem> getNearbyQItems(){
        ArrayList<QItem> nearby = new ArrayList<QItem>();
        for(Restaurant r : restaurant.nearbyRestaurants){
            nearby.add(new QItem(r,timeAddedToQ.toMillis(true)));
        }
        return nearby;
    }

    @Override
    public int compareTo(QItem other){
        if (timeAddedToQ.before(other.timeAddedToQ)){
            return -1;
        }
        else {
            return 1;
        }
    }

    public String toString(){
        return restaurant.toString()+" time: "+timeAddedToQ;
    }

    public static QItem getDummyQItem(){
        Restaurant toBeAdded = Restaurant.getDummyRestaurant();
        for(int i=0; i<3; i++){
            toBeAdded.addNearbyRestaurant(Restaurant.getDummyRestaurant());
        }
        QItem ret = new QItem(toBeAdded,new Date().getTime());
        return ret;
    }

}