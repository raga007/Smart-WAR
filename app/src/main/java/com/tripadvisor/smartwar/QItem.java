package com.tripadvisor.smartwar;

import android.location.*;
import android.text.format.Time;
import java.util.Date;

public class QItem implements Comparable<QItem>{

    public Restaurant restaurant;
    public Time timeAddedToQ = new Time();

    public QItem(Restaurant r, long timeInMillis){
        this.restaurant = r;
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
        return restaurant.toString()+" time: "+timeAddedToQ;
    }

    public static QItem getDummyQItem(){
        QItem ret = new QItem(Restaurant.getDummyRestaurant(),new Date().getTime());
        return ret;
    }

}