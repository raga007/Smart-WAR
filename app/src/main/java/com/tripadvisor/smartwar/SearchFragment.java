package com.tripadvisor.smartwar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.tripadvisor.smartwar.constants.UserLocationHelper;


public class SearchFragment extends SherlockFragment {

    private View contentView;
    private EditText debugTextView;
    public static String debugData = new String();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.search_fragment, container, false);
        debugTextView = (EditText) contentView.findViewById(R.id.description);

        /*
        UserLocationHelper helper = UserLocationHelper.getInstance();
        helper.addUserLocation(2,3,4);
        helper.addUserLocation(2,3,5);
        helper.addUserLocation(2,3,6);
        helper.addUserLocation(2,3,7);

        debugData.append(helper.userLocationData.toString() + "\n");
        debugData.append("Distance test :" + helper.distance(2,3,2,3,'K') + "\n");
        debugData.append("User range duration : " + helper.getUserInRangeDuration());
        */

        debugTextView.setText(debugData.toString());
        return contentView;
    }

    public void updateDebugView(){
        debugTextView.setText(debugData.toString());
    }


}
