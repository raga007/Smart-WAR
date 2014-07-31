package com.tripadvisor.smartwar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;
import com.github.johnpersano.supertoasts.SuperCardToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;

public class MainMenuFragment extends SherlockFragment {

    private View contentView;
    private Button writeAReviewButton;
    private Button checkIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.main_menu_fragment, container, false);
        getSherlockActivity().getSupportActionBar().setTitle(R.string.app_name);
        writeAReviewButton = (Button) contentView.findViewById(R.id.btn_review);
        checkIn = (Button) contentView.findViewById(R.id.btn_checkin);
        writeAReviewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment fragment = new SearchFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).commit();
            }
        });
        checkIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NearbySearch.manualCheckIn();
                SuperToast.create(getSherlockActivity(), "You request has been recorded, we will add" +
                                " the restaurant to your visited restaurant lists.", SuperToast.Duration.LONG,
                        Style.getStyle(Style.GREEN, SuperToast.Animations.FLYIN)).show();
            }
        });

        return contentView;
    }

}

