package com.tripadvisor.smartwar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.widget.AdapterView.OnItemClickListener;
import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;

import java.util.ArrayList;


public class SearchFragment extends SherlockFragment {

    private static int NEARBY_RESTAURANT_IMAGE_DIMENSION = 70;

    private View contentView;
    private ListView listOfRestaurants;
    private MainItemAdapter mainAdapter;
    private ArrayList<QItem> dataInView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.search_fragment, container, false);
        ArrayList<QItem> items = new ArrayList<QItem>();
        for(int i=0;i<10;i++){
            items.add(QItem.getDummyQItem());
        }
        dataInView = items;
        listOfRestaurants = (ListView) contentView.findViewById(R.id.listOfRestaurants);
        mainAdapter = new MainItemAdapter(getActivity(),items);
        listOfRestaurants.setAdapter(new SlideExpandableListAdapter(
                mainAdapter,
                R.id.btn_nearby,
                R.id.expandable
        ));

        listOfRestaurants.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                QItem itemInView = dataInView.get(position);
                Bundle data = new Bundle();
                data.putString("restaurantName",itemInView.restaurant.getName());
                data.putLong("visitTime", itemInView.timeAddedToQ.toMillis(true));
                Fragment fragment = new WriteAReviewFragment();
                fragment.setArguments(data);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).commit();
            }
        });

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
            ListView nearbyListView = null;
            ExpandItemAdapter expandAdapter = null;
            final QItem item = data.get(position);
            if(convertView == null) {
                vi = inflater.inflate(R.layout.restaurant_row_main, null);
                innerRow = (LinearLayout) vi.findViewById(R.id.restaurantInfo);
                restaurantName = (TextView) innerRow.findViewById(R.id.restaurantName);
                timeVisited = (RelativeTimeTextView) innerRow.findViewById(R.id.timeVisited);
                restaurantImage = (ImageView) innerRow.findViewById(R.id.restaurantImage);
                nearbyButton = (Button) innerRow.findViewById(R.id.btn_nearby);
                nearbyListView = (ListView) vi.findViewById(R.id.listOfNearbyRestaurants);
            }else{
                innerRow = (LinearLayout) convertView.findViewById(R.id.restaurantInfo);
                restaurantName = (TextView) innerRow.findViewById(R.id.restaurantName);
                timeVisited = (RelativeTimeTextView) innerRow.findViewById(R.id.timeVisited);
                restaurantImage = (ImageView) innerRow.findViewById(R.id.restaurantImage);
                nearbyButton = (Button) innerRow.findViewById(R.id.btn_nearby);
                nearbyListView = (ListView) convertView.findViewById(R.id.listOfNearbyRestaurants);
            }

            nearbyListView.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    item.getNearbyQItems().size()*NEARBY_RESTAURANT_IMAGE_DIMENSION, getResources().getDisplayMetrics());
            expandAdapter = new ExpandItemAdapter(getActivity(),item.getNearbyQItems());
            nearbyListView.setAdapter(expandAdapter);
            nearbyListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    QItem itemInView = item.getNearbyQItems().get(position);
                    Bundle data = new Bundle();
                    data.putString("restaurantName",itemInView.restaurant.getName());
                    data.putLong("visitTime", itemInView.timeAddedToQ.toMillis(true));
                    Fragment fragment = new WriteAReviewFragment();
                    fragment.setArguments(data);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).commit();
                }
            });

            restaurantName.setText(item.restaurant.getName());
            timeVisited.setReferenceTime(item.timeAddedToQ.toMillis(true));
            ImageLoader.getInstance().displayImage(item.restaurant.getImageURL(),restaurantImage);
            return vi;
        }
    }



    public class ExpandItemAdapter extends BaseAdapter {
        private Activity activity;
        private LayoutInflater inflater;
        private ArrayList<QItem> data;

        public ExpandItemAdapter(Activity a, ArrayList<QItem> items) {
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
            TextView restaurantName = null;
            RelativeTimeTextView timeVisited = null;
            ImageView restaurantImage = null;
            QItem item = data.get(position);
            if(convertView == null) {
                vi = inflater.inflate(R.layout.restaurant_row_nearby, null);
                restaurantName = (TextView) vi.findViewById(R.id.restaurantName);
                timeVisited = (RelativeTimeTextView) vi.findViewById(R.id.timeVisited);
                restaurantImage = (ImageView) vi.findViewById(R.id.restaurantImage);
            }else{
                restaurantName = (TextView) convertView.findViewById(R.id.restaurantName);
                timeVisited = (RelativeTimeTextView) convertView.findViewById(R.id.timeVisited);
                restaurantImage = (ImageView) convertView.findViewById(R.id.restaurantImage);
            }
            restaurantName.setText(item.restaurant.getName());
            timeVisited.setReferenceTime(item.timeAddedToQ.toMillis(true));
            ImageLoader.getInstance().displayImage(item.restaurant.getImageURL(),restaurantImage);
            return vi;
        }
    }
}
