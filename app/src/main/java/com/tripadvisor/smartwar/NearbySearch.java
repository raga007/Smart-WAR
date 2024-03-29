package com.tripadvisor.smartwar;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;
import com.tripadvisor.smartwar.constants.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Jens on 7/28/14.
 */
public class NearbySearch {

    public static ArrayList<Restaurant> search(double lat, double lng, double dist, int stayedPutThreshold) {
        ArrayList<Restaurant> results = null;
        try {
            AsyncTask<Double, Void, ArrayList<Restaurant>> task = new SearchTask(stayedPutThreshold);
            task.execute(lat, lng, dist);
            results = task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private static class SearchTask extends AsyncTask<Double, Void, ArrayList<Restaurant>> {

        private int stayedPutThreshold;

        public SearchTask(int stayedPutThreshold){
            super();
            this.stayedPutThreshold = stayedPutThreshold;
        }
        // params should be lat, lng, radius (in this order)
        // any parameter after the third one is ignored
        protected ArrayList<Restaurant> doInBackground(Double... params) {
            int count = params.length;
            if (count < 3)
                return null;

            final Double lat = params[0], lng = params[1], dist = params[2];

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("api.tripadvisor.com")
                    .appendPath("api")
                    .appendPath("partner")
                    .appendPath("1.0")
                    .appendPath("map")
                    .appendPath(lat.toString() + "," + lng.toString())
                    .appendQueryParameter("distance", "1")
                    .appendQueryParameter("lunit", "km")
                    .appendQueryParameter("key", Constants.API_KEY);
            String https_url = builder.build().toString();
            Log.e("NearbySearch", https_url);

            ArrayList<Restaurant> results = new ArrayList<Restaurant>();

            try {
                URL url = new URL(https_url);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));

                StringBuffer jsonResponse = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    jsonResponse.append(inputLine);

                JSONArray data = new JSONObject(jsonResponse.toString())
                        .getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jObj = data.getJSONObject(i);
                    if (jObj.getJSONObject("category").getString("key").equals("restaurant")) {
                        Restaurant r = new Restaurant(Integer.parseInt(jObj.getString("location_id")),
                                jObj.getString("name"),
                                Double.parseDouble(jObj.getString("latitude")),
                                Double.parseDouble(jObj.getString("longitude")));
                        r.setRestaurantInfo();
                        results.add(r);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.e("Before filtering:","");
            RestaurantManager.printList(results);

            Log.e("-------------------","--------------------");
            // filter restaurants by distance
            for (int i = results.size() - 1; i >= 0; i--) {
                Location loc = results.get(i).getLocation();
                double distanceCalculated = UserLocationHelper.getInstance().distance(lat, lng, loc.getLatitude(), loc.getLongitude());
                Log.e(results.get(i).getName(),": " + distanceCalculated);
                if (distanceCalculated > Constants.SEARCH_RADIUS) {
                    results.remove(i);
                }
            }
            Log.e("-------------------","--------------------");

            Log.e("After filtering:","");
            RestaurantManager.printList(results);


            sortRestaurants(results, lat, lng);

            if (results.size() > 0) {
                Restaurant rest = results.get(0);
                if (SmartWarSettings.isUseNearbySuggestions()) {
                    for (int i = 1; i < results.size() && i <= Constants.NUM_NEARBY_RESTAURANTS; i++) {
                        rest.addNearbyRestaurant(results.get(i));
                    }
                }

                if (SmartWarSettings.isUseSmartTime()) {
                    addQItemIfProperDuration(rest, stayedPutThreshold);
                }
                else {
                    RestaurantManager.getInstance().addQItem(rest);
                }
            }

            return results;
        }

        private void sortRestaurants(ArrayList<Restaurant> list, final Double lat, final Double lng) {
            Collections.sort(list, new Comparator<Restaurant>() {
                @Override
                public int compare(Restaurant r, Restaurant r2) {
                    Double dist1 = UserLocationHelper.getInstance().distance(
                            r.getLocation().getLatitude(),
                            r.getLocation().getLongitude(),
                            lat, lng);
                    Double dist2 = UserLocationHelper.getInstance().distance(
                            r2.getLocation().getLatitude(),
                            r2.getLocation().getLongitude(),
                            lat, lng);
                    return dist1.compareTo(dist2);
                }
            });
        }

        private void addQItemIfProperDuration(Restaurant restaurant, int stayedPutThreshold){
            String type = restaurant.getType();
            if (stayedPutThreshold == 1) {
                RestaurantManager.getInstance().addQItem(restaurant);
            }
            else if (stayedPutThreshold == 2){
                if (type.equals(Constants.CAFE) || type.equals(Constants.BAKERY) || type.equals(Constants.FAST_FOOD)){
                    RestaurantManager.getInstance().addQItem(restaurant);
                }
            }
            else {
                RestaurantManager.getInstance().addQItem(restaurant);
            }
        }


    }

    public static void manualCheckIn(Context context){
        int index = UserLocationHelper.userLocationData.size() - 1 ;
        if (index < 0){
            return;
        }
        UserLocation lastLocation = UserLocationHelper.userLocationData.get(index);
        Constants.FORCED_LOCATION_UPDATE = true;
        LocationLibrary.forceLocationUpdate(context);
        Log.e("Doing a manual checkin","did!");
    }


}
