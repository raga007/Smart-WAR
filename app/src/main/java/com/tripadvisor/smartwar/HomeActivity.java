package com.tripadvisor.smartwar;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.gson.reflect.TypeToken;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;
import com.pixplicity.easyprefs.library.Prefs;
import com.tripadvisor.smartwar.constants.Constants;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class HomeActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Fragment fragment = new WriteAReviewFragment();
//        ArrayList<Restaurant> results = NearbySearch.search(40.761808, -73.981798, 0.2);
//        RestaurantManager.printList(results);
        LocationLibrary.forceLocationUpdate(getBaseContext());
        getSupportFragmentManager().beginTransaction().add(R.id.content_fragment, fragment).commit();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }


    /**
     * Create a compatible helper that will manipulate the action bar if
     * available.
     */
    private ActionBarHelper createActionBarHelper() {
        return new ActionBarHelper();
    }


    private class ActionBarHelper {
        private final ActionBar mActionBar;
        private CharSequence mDrawerTitle;
        private CharSequence mTitle;

        private ActionBarHelper() {
            mActionBar = getSupportActionBar();
        }

        public void init() {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mTitle = mDrawerTitle = getTitle();
        }

        /**
         * When the drawer is closed we restore the action bar state reflecting
         * the specific contents in view.
         */
        public void onDrawerClosed() {
            mActionBar.setTitle(mTitle);
        }

        /**
         * When the drawer is open we set the action bar to a generic title. The
         * action bar should only contain data relevant at the top level of the
         * nav hierarchy represented by the drawer, as the rest of your content
         * will be dimmed down and non-interactive.
         */
        public void onDrawerOpened() {
            mActionBar.setTitle(mDrawerTitle);
        }

        public void setTitle(CharSequence title) {
            mTitle = title;
        }
    }

    @Override
    public void onPause(){
        super.onPause();

        RestaurantManager restaurantManager = RestaurantManager.getInstance();

        //store REVIEWS
        String newReviewList = Constants.gsonObject.toJson(restaurantManager.getCompletedReviews());
        Prefs.putString(restaurantManager.COMPLETED_REVIEWS_KEY, newReviewList);

        //store THE QUEUE
        String newQList = Constants.gsonObject.toJson(restaurantManager.getTheQ());
        Prefs.putString(restaurantManager.THE_Q_KEY,newQList);

        UserLocationHelper userLocationHelper = UserLocationHelper.getInstance();

        //store USER LOCATION DATA
        String newUserLocationData = Constants.gsonObject.toJson(userLocationHelper.userLocationData);
        Prefs.putString(userLocationHelper.USER_LOCATION_DATA_KEY, newUserLocationData);

    }

    @Override
    public void onResume(){
        super.onResume();

        RestaurantManager restaurantManager = RestaurantManager.getInstance();

        //retrieve THE QUEUE
        String toConvert = Prefs.getString(restaurantManager.THE_Q_KEY, null);
        if (toConvert != null){
            ArrayList<QItem> theNewQ = Constants.gsonObject.fromJson(toConvert, new TypeToken<ArrayList<QItem>>(){}.getType());
            restaurantManager.setTheQ(theNewQ);
        }
        toConvert = null;

        //retrieve REVIEWS
        toConvert = Prefs.getString(restaurantManager.COMPLETED_REVIEWS_KEY, null);
        if (toConvert != null){
            ArrayList<Review> theNewReviewList = Constants.gsonObject.fromJson(toConvert, new TypeToken<ArrayList<Review>>(){}.getType());
            restaurantManager.setCompletedReviews(theNewReviewList);
        }
        toConvert = null;

        //retrieve USER LOCATION DATA
        UserLocationHelper userLocationHelper = UserLocationHelper.getInstance();
        toConvert = Prefs.getString(UserLocationHelper.USER_LOCATION_DATA_KEY, null);
        if (toConvert != null) {
            ArrayList<UserLocation> newUserLocationData = Constants.gsonObject.fromJson(toConvert, new TypeToken<ArrayList<UserLocation>>(){}.getType());
            Log.e("JSon", newUserLocationData.toString());
            UserLocationHelper.userLocationData = newUserLocationData;
        }

    }
}
