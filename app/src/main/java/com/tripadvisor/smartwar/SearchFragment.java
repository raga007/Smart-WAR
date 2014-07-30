package com.tripadvisor.smartwar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.actionbarsherlock.app.SherlockFragment;

import java.util.ArrayList;


public class SearchFragment extends SherlockFragment {

    private View contentView;
    public static String debugData = new String();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.search_fragment, container, false);
        return contentView;
    }

    public class MainItemAdapter extends BaseAdapter {
        private Activity activity;
        private LayoutInflater inflater;
        private ArrayList<QItem> data;

        public MainItemAdapter(Activity a, ArrayList<QItem> data) {
            activity = a;
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
            View vi=convertView;
            if(convertView==null) {
                vi = inflater.inflate(R.layout.restaurant_row_main, null);
            }

            return vi;
        }
    }


}
