package com.tripadvisor.smartwar.constants;

import com.google.gson.Gson;

/**
 * Created by jsklar on 7/30/14.
 */
public class Constants {

    public static final Gson gsonObject = new Gson();

    //Types of restaurants
    public static final String CAFE = "cafe";
    public static final String BAKERY = "bakery";
    public static final String DELI = "deli";
    public static final String FAST_FOOD = "fast_food";
    public static final String SIT_DOWN = "sit_down";

    public static final Integer NUM_NEARBY_RESTAURANTS = 3;
    public static final double SEARCH_RADIUS = 0.1;
    public static final String API_KEY = "785cb9e5-067b-478f-9c79-ad59bde7ed25";

    public static final long EXPIRATION_TIME = 7*24*60*1000;

    public static final boolean IS_TEST = true;

}
