package com.tripadvisor.smartwar;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Jens on 7/28/14.
 */
public class NearbySearch {
    
    public static final double RADIUS = 0.03;
    private static final String API_KEY = "785cb9e5-067b-478f-9c79-ad59bde7ed25";

    public static ArrayList<Restaurant> search(double lat, double lng, double dist) {
        ArrayList<Restaurant> results = null;
        try {
            AsyncTask<Double, Void, ArrayList<Restaurant>> task = new SearchTask();
            task.execute(lat, lng, dist);
            results = task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private static class SearchTask extends AsyncTask<Double, Void, ArrayList<Restaurant>> {

        // params should be lat, lng, radius (in this order)
        // any parameter after the third one is ignored
        protected ArrayList<Restaurant> doInBackground(Double... params) {
            int count = params.length;
            if (count < 3)
                return null;

            Double lat = params[0], lng = params[1], dist = params[2];

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("api.tripadvisor.com")
                    .appendPath("api")
                    .appendPath("partner")
                    .appendPath("1.0")
                    .appendPath("map")
                    .appendPath(lat.toString() + "," + lng.toString())
                    .appendQueryParameter("distance", dist.toString())
                    .appendQueryParameter("lunit", "km")
                    .appendQueryParameter("key", API_KEY);
            String https_url = builder.build().toString();

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
                        results.add(r);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return results;
        }
    }

}
