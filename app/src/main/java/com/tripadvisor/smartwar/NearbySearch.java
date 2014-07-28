package com.tripadvisor.smartwar;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Jens on 7/28/14.
 */
public class NearbySearch {

    private static final String API_KEY = "785cb9e5-067b-478f-9c79-ad59bde7ed25";

    public static ArrayList<Restaurant> search(Double lat, Double lng, Double dist) {
        // make API call to google places
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.tripadvisor.com")
                .appendPath("api")
                .appendPath("partner")
                .appendPath("1.0")
                .appendPath("map")
                .appendPath(lat.toString() + "," + lng.toString())
                .appendQueryParameter("distance", dist.toString())
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
            while ((inputLine = in.readLine()) != null) {
                jsonResponse.append(inputLine);
            }

            JSONObject jsonObj = new JSONObject(jsonResponse.toString());
            JSONArray data = jsonObj.getJSONArray("data");
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }


}
