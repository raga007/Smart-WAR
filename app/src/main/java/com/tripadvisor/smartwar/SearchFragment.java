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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.search_fragment, container, false);
        debugTextView = (EditText) contentView.findViewById(R.id.description);

        UserLocationHelper.getInstance().addUserLocation(2,3,4);
        UserLocationHelper.getInstance().addUserLocation(2,3,5);
        UserLocationHelper.getInstance().addUserLocation(2,3,6);
        UserLocationHelper.getInstance().addUserLocation(2, 3, 7);

        StringBuffer debugData = new StringBuffer();
        debugData.append(UserLocationHelper.getInstance().userLocationData.toString() + "\n");
        debugData.append("Distance test :" + UserLocationHelper.getInstance().distance(2,3,2,3,'K') + "\n");
        debugData.append("User range duration : " + UserLocationHelper.getInstance().getUserInRangeDuration());

        debugTextView.setText(debugData.toString());
        return contentView;
    }


}
