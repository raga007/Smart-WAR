package com.tripadvisor.smartwar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;

public class MainMenuFragment extends SherlockFragment {

    private View contentView;
    private Button writeAReviewButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.main_menu_fragment, container, false);
        getSherlockActivity().getSupportActionBar().setTitle(R.string.app_name);
        writeAReviewButton = (Button) contentView.findViewById(R.id.btn_logout);
        writeAReviewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment fragment = new SearchFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).commit();
            }
        });
        return contentView;
    }

}

