package com.tripadvisor.smartwar;

import android.location.Location;
import android.util.Log;

import com.pixplicity.easyprefs.library.Prefs;
import com.tripadvisor.smartwar.constants.Constants;

import java.util.ArrayList;
import java.util.Collections;

public class RestaurantManager {

    private static ArrayList<QItem> theQ;
    private static ArrayList<Review> completedReviews;
    private static RestaurantManager restaurantManager;

    public static final String THE_Q_KEY = "restaurant queue";
    public static final String COMPLETED_REVIEWS_KEY = "restaurant review list";


    private RestaurantManager(){
        if (theQ == null) {
            theQ = new ArrayList<QItem>();
        }
    }

    public static RestaurantManager getInstance(){
        if (restaurantManager == null){
            restaurantManager = new RestaurantManager();
        }
        return restaurantManager;
    }

    public void addQItem(Restaurant restaurant){
        Log.e("addQItem", "adding item");
        removeDuplicates(restaurant);
        theQ.add(new QItem(restaurant, System.currentTimeMillis()));
    }

    public void removeDuplicates(Restaurant rest){
        Log.e("removeDuplicates", "removing duplicates");
        for (int i = theQ.size() - 1; i >= 0; i--) {
            if (theQ.get(i).restaurant.getLocationId().equals(rest.getLocationId())) {
                Log.e("removeDuplicates", "removed " + theQ.get(i).restaurant.toString());
                theQ.remove(i);
            }
        }
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

    public ArrayList<Review> getCompletedReviews(){
        return completedReviews;
    }

    public void setCompletedReviews(ArrayList<Review> reviews){
        this.completedReviews = reviews;
    }

    public ArrayList<QItem> getTheQ(){
        if (SmartWarSettings.isUseExpiration())
            this.removeExpiredQItems();
        return theQ;
    }

    private void removeExpiredQItems() {
        for (int i = theQ.size() - 1; i >= 0; i--) {
            if (theQ.get(i).age() > Constants.EXPIRATION_TIME) {
                theQ.remove(i);
            }
        }
    }

    public void setTheQ(ArrayList<QItem> theQ){
        this.theQ = theQ;
    }

}