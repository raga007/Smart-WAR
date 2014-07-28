package com.tripadvisor.smartwar;

import android.location.*;
import android.text.format.Time;

public class QItem implements Comparable<QItem>{

    public Restaurant restaurant;
    public Location location;
    public Time timeAddedToQ = new Time();

    public QItem(Restaurant r, Location l, long timeInMillis){
        this.restaurant = r;
        this.location = l;
        timeAddedToQ.set(timeInMillis);
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
        //TEST, change later
        return restaurant.id+" <-- Qitem";
    }

}