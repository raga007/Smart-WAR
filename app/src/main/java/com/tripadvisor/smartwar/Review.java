package com.tripadvisor.smartwar;

import android.location.Location;
import android.text.format.Time;

import java.util.Date;

/**
 * Created by jsklar on 7/28/14.
 */
public class Review implements Comparable<Review>{

    public Location location;

    public int rating;
    public Restaurant restaurant;
    public Date dateVisited;
    public Time timeCompleted;
    public String visitType;
    public String title;
    public String reviewText;

    @Override
    public int compareTo(Review other){
        if (timeCompleted.before(other.timeCompleted)){
            return -1;
        }
        else {
            return 1;
        }
    }

    public String toString(){
        //TEST, change later
        return restaurant.id+" <-- review";
    }

}
