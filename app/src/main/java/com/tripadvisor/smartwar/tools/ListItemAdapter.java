package com.tripadvisor.smartwar.tools;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;


public class ListItemAdapter<T> extends ArrayAdapter<T> {

	private static final int REFRESH_PROXIMITY_TIME = 5000; //in ms
	private static final float REFRESH_PROXIMITY_ACCURACY = 100.0f; //in meters
	
	protected LayoutInflater mLayoutInflater;
	protected Location mCurrentLocation;
	
	
	public ListItemAdapter(Context context, int textViewResourceId, List<T> objects) {
		super(context, textViewResourceId, objects);

		mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void onNewLocation(Location location) {
		long time = 0;
		
		if (mCurrentLocation != null)
			time = location.getTime() - mCurrentLocation.getTime();
		if (time == 0 || time > REFRESH_PROXIMITY_TIME && location.hasAccuracy() 
				&& location.getAccuracy() < REFRESH_PROXIMITY_ACCURACY) {
			mCurrentLocation = location;
			notifyDataSetChanged();
		}
	}
	
}
