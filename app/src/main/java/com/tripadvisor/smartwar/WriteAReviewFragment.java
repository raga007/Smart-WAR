package com.tripadvisor.smartwar;

import com.actionbarsherlock.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import android.widget.RatingBar.OnRatingBarChangeListener;
import com.actionbarsherlock.app.SherlockFragment;
import android.view.View.OnClickListener;
import com.tripadvisor.smartwar.constants.BasicListItemIO;
import com.tripadvisor.smartwar.constants.RatingType;
import com.tripadvisor.smartwar.constants.VisitType;
import com.tripadvisor.smartwar.tools.BasicListItemAdapter;
import java.text.DateFormat;
import java.util.Date;

import java.util.ArrayList;


public class WriteAReviewFragment extends SherlockFragment {

    private View contentView;
    private RatingBar mRatingBar;
    private LinearLayout mRatingLayout = null;
    private BasicListItemAdapter mVisitTypePickerAdapter = null;
    private TextView mVisitTypeDisplay;
    private TextView mRateMessage;
    private LinearLayout mVisitTypeLayout;
    private TextView mTimeVisited;
    private TextView mDateVisited;
    private LinearLayout mDateVisitedLayout;
    private Button mSubmitButton;

    private static final int SELECTED_VISIT_TYPE = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.write_a_review_fragment, container, false);
        initViews();
        Bundle bundle = this.getArguments();
        String restaurantName = bundle.getString("restaurantName");
        long visitTime = bundle.getLong("visitTime");
        getSherlockActivity().getSupportActionBar().setTitle(restaurantName);
        Date visitDate = new Date(visitTime);
        mDateVisited.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(visitDate));
        mTimeVisited.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(visitDate));
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment fragment = new MainMenuFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).commit();
            }
        });
        return contentView;
    }




    private void initViews(){
        initDateVisitedView();
        initTypeOfVisitView();
        initRatingViews();
    }


    private void initDateVisitedView(){
        mSubmitButton = (Button) contentView.findViewById(R.id.submitButton);
        mTimeVisited = (TextView) contentView.findViewById(R.id.timeVisited);
        mDateVisited = (TextView) contentView.findViewById(R.id.dateVisited);
        mDateVisitedLayout = (LinearLayout) contentView.findViewById(R.id.dateVisitedLayout);
    }


    private void initTypeOfVisitView() {
        mVisitTypeDisplay = (TextView) contentView.findViewById(R.id.visitType);
        mVisitTypeLayout = (LinearLayout) contentView.findViewById(R.id.visitTypeLayout);
        if (mVisitTypeLayout.isEnabled()) {
            mVisitTypeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showVisitTypeSelectionListAlert();
                }
            });
        }
    }

    private void initRatingViews() {
        mRatingLayout = (LinearLayout) contentView.findViewById(R.id.ratingLayout);
        mRatingBar = (RatingBar) contentView.findViewById(R.id.ratingBar);
        mRateMessage = (TextView) contentView.findViewById(R.id.rateMessage);

        mRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                TextView ratingMessage = (TextView) contentView.findViewById(R.id.rateMessage);
                mRateMessage.setTextColor(getResources().getColor(R.color.light_green_text_view));
                if (rating > 0) {
                    ratingMessage.setText(RatingType.findByValue((int) rating).stringId);
                } else {
                    ratingMessage.setText("Please select a rating for your review");
                }
            }
        });

    }

    private void showVisitTypeSelectionListAlert() {

        ArrayList<BasicListItemIO> basicListItems = new ArrayList<BasicListItemIO>();
        if (mVisitTypePickerAdapter == null) {
            VisitType[] visitTypeArray;
                visitTypeArray = VisitType.FOR_HOTELS_RESTAURANTS;
            for (VisitType visitType : visitTypeArray) {
                String visitTypeName = getString(visitType.stringId);
                BasicListItemIO basicListItemIO = new BasicListItemIO.Builder(visitTypeName, null).object(visitType).build();
                basicListItems.add(basicListItemIO);
            }

            mVisitTypePickerAdapter = new BasicListItemAdapter(getActivity(), R.layout.basic_list_item, basicListItems);
        }

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Type of visit")
                .setSingleChoiceItems(mVisitTypePickerAdapter, SELECTED_VISIT_TYPE, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BasicListItemIO visiteType = mVisitTypePickerAdapter.getItem(which);
                        mVisitTypeDisplay.setText(visiteType.mTitle);
                        mVisitTypePickerAdapter.selectedItem(visiteType);
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        dialog.dismiss();

                    }
                }).create();

        alertDialog.show();
    }
}
