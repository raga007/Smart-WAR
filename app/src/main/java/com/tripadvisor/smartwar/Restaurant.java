package com.tripadvisor.smartwar;

import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.tripadvisor.smartwar.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class Restaurant {

    private static final String[] IMAGE_TYPES = {"medium", "small", "thumbnail", "original", "large"};
    private final String DEFAULT_IMAGE = "http://www.pivnicesaloon.eu/knife-fork-plate.png";

    public Location location;
    public Integer location_id;
    private String name;
    private String image;
    private String type;

    ArrayList<Restaurant> nearbyRestaurants;

    public Restaurant(Integer id, String name, Double lat, Double lng) {
        this.name = name;
        this.location_id = id;
        location = new Location("restaurant");
        location.setLatitude(lat);
        location.setLongitude(lng);
        this.nearbyRestaurants = new ArrayList<Restaurant>();
        this.image = DEFAULT_IMAGE;
    }

    public Location getLocation() {
        return this.location;
    }
    public Integer getLocationId() { return this.location_id; }
    public String getImageURL() { return this.image; }
    public String getType() { return this.type; }

    public String toString(){
        return (name + " location: " + location.toString() + " location_id: " + this.location_id.toString() + " image: " + this.image + " type: " + this.type);
    }

    public void addNearbyRestaurant(Restaurant r) {
        this.nearbyRestaurants.add(r);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // sets restaurant image and type
    public void setRestaurantInfo() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.tripadvisor.com")
                .appendPath("api")
                .appendPath("partner")
                .appendPath("1.0")
                .appendPath("location")
                .appendPath(this.getLocationId().toString())
                .appendPath("restaurants")
                .appendQueryParameter("key", Constants.API_KEY);
        String https_url = builder.build().toString();

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

            if (data.length() == 0) {
                Log.e("Restaurant:setRestaurantInfo", "Failed to find restaurant " + this.toString());
            } else {
                JSONObject restaurantObj = data.getJSONObject(0);
                String type = restaurantObj.getJSONObject("subcategory").getString("key");
                this.type = type;

                JSONObject images = restaurantObj.getJSONObject("photo").getJSONObject("images");
                String image = null;
                for (int i = 0; i < IMAGE_TYPES.length; i++) {
                    try {
                        image = images.getJSONObject(IMAGE_TYPES[i]).getString("url");
                    } catch (JSONException e) {
                        continue;
                    }
                }
                if (image == null) {
                    Log.e("Restaurant:setRestaurantInfo", "Failed to find image for restaurant " + this.toString());
                } else {
                    this.image = image;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static Restaurant getDummyRestaurant(){
        Restaurant rest = new Restaurant(2,"McDonalds",34.43,97.76);
        rest.image = "http://media-cdn.tripadvisor.com/media/photo-l/05/2b/ba/6f/deuxave.jpg";
        return rest;
    }

}