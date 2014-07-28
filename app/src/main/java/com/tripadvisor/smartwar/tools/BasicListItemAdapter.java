package com.tripadvisor.smartwar.tools;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripadvisor.smartwar.R;
import com.tripadvisor.smartwar.constants.BasicListItemIO;


public class BasicListItemAdapter extends ListItemAdapter<BasicListItemIO> {

    protected int mTextViewResourceId;
    private boolean mIsDisabled = false;
    protected BasicListItemIO mSelectedItem = null;
    protected Context mContext;

    public BasicListItemAdapter(Context context, int textViewResourceId, List<BasicListItemIO> objects) {
        super(context, textViewResourceId, objects);

        mContext = context;
        mTextViewResourceId = textViewResourceId;
    }

    public BasicListItemAdapter(Context context, int textViewResourceId, List<BasicListItemIO> objects,
            boolean isDisabled) {
        super(context, textViewResourceId, objects);

        mContext = context;
        mTextViewResourceId = textViewResourceId;
        mIsDisabled = isDisabled;
    }

    public BasicListItemIO selectedItem() {
        return mSelectedItem;
    }

    public void selectedItem(BasicListItemIO selectedItem) {
        mSelectedItem = selectedItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            convertView = mLayoutInflater.inflate(mTextViewResourceId, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.title);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.imageRight = (ImageView) convertView.findViewById(R.id.imageRight);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BasicListItemIO item = getItem(position);

        holder.name.setText(Html.fromHtml(item.mTitle).toString());

        if (holder.image != null)
            holder.image.setVisibility(View.INVISIBLE);
        if (item.mImage != null) {
            holder.image.setBackgroundDrawable(item.mImage);
            holder.image.setVisibility(View.VISIBLE);
        }

        if (holder.imageRight != null)
            holder.imageRight.setVisibility(View.GONE);
        if (item.mImageRight != null) {
            holder.name.setTextColor(mContext.getResources().getColor(R.color.ta_green));
            holder.imageRight.setBackgroundDrawable(item.mImageRight);
            holder.imageRight.setVisibility(View.VISIBLE);
        } else {
            holder.name.setTextColor(mContext.getResources().getColor(R.color.dark_gray));
        }

        if (mSelectedItem != null && mSelectedItem.mTitle != null && mSelectedItem.mTitle.equals(item.mTitle)) {
            holder.name.setTextColor(mContext.getResources().getColor(R.color.ta_green));
        }

        return convertView;
    }

    protected static class ViewHolder {
        ImageView image;
        TextView name;
        ImageView imageRight;
    }

    @Override
    public boolean areAllItemsEnabled() {
        if (mIsDisabled)
            return false;
        else
            return super.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(int position) {
        if (mIsDisabled)
            return false;
        else
            return super.isEnabled(position);
    }

}
