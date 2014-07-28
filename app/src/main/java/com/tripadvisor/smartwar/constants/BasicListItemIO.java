package com.tripadvisor.smartwar.constants;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;


import java.util.Comparator;

public class BasicListItemIO {
    public long mId;
    public String mTitle;
    public Drawable mImage;
    public Drawable mImageRight;
    public String mTextRight;
    public Object mObject;
    private OnClickListener mClickListener;
    public String mEventLabel;
    public int mCount = -1;
    private String mTrackingAction;

    private BasicListItemIO(long id,
            String title,
            Drawable image,
            Drawable imageRight,
            Object object,
            OnClickListener clickListener,
            String trackingAction,
            int count) {
        mId = id;
        mTitle = title;
        mImage = image;
        mImageRight = imageRight;
        mObject = object;
        mClickListener = clickListener;
        mCount = count;
        mTrackingAction = trackingAction;
    }

    public static Comparator<BasicListItemIO> getTitleComparator() {
        return new Comparator<BasicListItemIO>() {
            @Override
            public int compare(BasicListItemIO item1, BasicListItemIO item2) {
                if(item1 == null || item1.mTitle == null) {
                    return (item2 == null || item2.mTitle == null) ? 0 : -1;
                }
                return item1.mTitle.compareTo(item2.mTitle);
            }
        };
    }

    public String getTrackingAction() {
        return mTrackingAction;
    }

    public void setOnClickEvent(View view) {
        if (view != null && mClickListener != null) {
            view.setOnClickListener(mClickListener);
        }
    }

    public static class Builder {
        private long mId;
        private String mTitle;
        private Drawable mImage;
        private Drawable mImageRight;
        private Object mObject;
        private String mTrackingAction;
        private OnClickListener mClickListener;
        private int mCount = -1;

        public Builder(String title, Drawable image) {
            mTitle = title;
            mImage = image;
        }

        public Builder(String title, Object object, Drawable image){
            mTitle = title;
            mObject = object;
            mImage = image;
        }

        public Builder id(long value) {
            mId = value;
            return this;
        }

        public Builder imageRight(Drawable imageRight) {
            mImageRight = imageRight;
            return this;
        }

        public Builder object(Object val) {
            mObject = val;
            return this;
        }

        public Builder trackingAction(String trackingAction) {
            mTrackingAction = trackingAction;
            return this;
        }

        public Builder onClick(OnClickListener val) {
            mClickListener = val;
            return this;
        }

        public Builder count(int count) {
            mCount = count;
            return this;
        }

        public BasicListItemIO build() {
            return new BasicListItemIO(mId,
                    mTitle,
                    mImage,
                    mImageRight,
                    mObject,
                    mClickListener,
                    mTrackingAction,
                    mCount);
        }
    }
}
