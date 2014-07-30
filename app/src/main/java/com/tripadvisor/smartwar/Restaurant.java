package com.tripadvisor.smartwar;

import android.location.Location;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Restaurant {

    private static final String[] IMAGE_TYPES = {"medium", "small", "thumbnail", "original", "large"};

    public Location location;
    public Integer location_id;
    String name;
    String image;

    public Restaurant(Integer id, String name, Double lat, Double lng) {
        this.name = name;
        this.location_id = id;
        location = new Location("restaurant");
        location.setLatitude(lat);
        location.setLongitude(lng);
    }

    public Location getLocation() {
        return this.location;
    }
    public Integer getLocationId() { return this.location_id; }
    public String getImage() { return this.image; }

    public String toString(){
        return (name + " location: " + location.toString() + " location_id: " + this.location_id.toString() + " image: " + this.image);
    }

    public void setImage() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.tripadvisor.com")
                .appendPath("api")
                .appendPath("partner")
                .appendPath("1.0")
                .appendPath("location")
                .appendPath(this.getLocationId().toString())
                .appendPath("restaurants")
                .appendQueryParameter("key", NearbySearch.API_KEY);
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
                Log.e("Restaurant:setImage", "Failed to find restaurant " + this.toString());
            } else {
                JSONObject restaurantObj = data.getJSONObject(0);
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
                    Log.e("Restaurant:setImage", "Failed to find image for restaurant " + this.toString());
                } else {
                    this.image = image;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}