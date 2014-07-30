package com.tripadvisor.smartwar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tripadvisor.smartwar.constants.UserLocationHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchFragment extends SherlockFragment {

    private View contentView;
    private ListView listOfRestaurants;
    private MainItemAdapter mainAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.search_fragment, container, false);
        ArrayList<QItem> items = new ArrayList<QItem>();
        for(int i=0;i<10;i++){
            items.add(QItem.getDummyQItem());
        }
        listOfRestaurants = (ListView) contentView.findViewById(R.id.listOfRestaurants);
        mainAdapter = new MainItemAdapter(getActivity(),items);
        listOfRestaurants.setAdapter(mainAdapter);

        return contentView;
    }




    public class MainItemAdapter extends BaseAdapter {
        private Activity activity;
        private LayoutInflater inflater;
        private ArrayList<QItem> data;

        public MainItemAdapter(Activity a, ArrayList<QItem> items) {
            activity = a;
            data = items;
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            LinearLayout innerRow = null;
            TextView restaurantName = null;
            RelativeTimeTextView timeVisited = null;
            ImageView restaurantImage = null;
            Button nearbyButton = null;
            QItem item = data.get(position);
            if(convertView==null) {
                vi = inflater.inflate(R.layout.restaurant_row_main, null);
                innerRow = (LinearLayout) vi.findViewById(R.id.restaurantInfo);
                restaurantName = (TextView)innerRow.findViewById(R.id.restaurantName);
                timeVisited = (RelativeTimeTextView) innerRow.findViewById(R.id.timeVisited);
                restaurantImage = (ImageView)innerRow.findViewById(R.id.restaurantImage);
                nearbyButton = (Button) innerRow.findViewById(R.id.btn_nearby);
                restaurantName.setText(item.restaurant.getName());
                timeVisited.setReferenceTime(item.timeAddedToQ.toMillis(true));
                ImageLoader.getInstance().displayImage(item.restaurant.getImage(),restaurantImage);
            }
            return vi;
        }
    }


}
