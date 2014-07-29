package com.tripadvisor.smartwar;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class RestaurantManager {

    private static ArrayList<QItem> theQ;
    private static ArrayList<Review> completedReviews;

    public RestaurantManager(){
        theQ = new ArrayList<QItem>();
    }

    public void addQItem(Restaurant restaurant){
        theQ.add(new QItem(restaurant, System.currentTimeMillis()));
    }

    public QItem remove(int index){
        return theQ.remove(index);
    }

    public QItem getQItem(int index){
        return theQ.get(index);
    }

    public void addReviewItem(Review review){
        completedReviews.add(review);
    }

    public void sortQByTime(){
        Collections.sort(theQ);
    }

    public void sortReviewsByTime(){
        Collections.sort(completedReviews);
    }

    public void printTheQ(){
        for (QItem i : theQ){
            Log.e("Q item",i.toString());
        }
    }

    public static void printList(ArrayList<Restaurant> list){
        for (Restaurant r: list){
            Log.e("restaurant", r.toString());
        }
    }

    public void printCompletedReviews(){
        for (Review i : completedReviews){
            Log.e("review item",i.toString());
        }
    }

}