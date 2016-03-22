package com.fractureof.demos.location;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import com.fractureof.demos.location.backend.codebox.PojoHangoutResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanan on 08/03/2016.
 */
public class HangoutsAdapter extends RecyclerView.Adapter<HangoutsAdapter.ViewHolder> {
    private JSONArray mDataset;
    private int sel = 0;

    private ViewHolder.IMyViewHolderClicks listener;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView1;
        public TextView mTextView2;
        public int mBoundItemIdx = 0;
        IMyViewHolderClicks listener;
        public ViewHolder(ViewGroup v, IMyViewHolderClicks listener) {
            super(v);
            this.listener = listener;
            mTextView1 = (TextView)v.findViewById(android.R.id.text1);
            mTextView2 = (TextView)v.findViewById(android.R.id.text2);
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onHangoutSelected(mBoundItemIdx);
        }

        public static interface IMyViewHolderClicks {
            public void onHangoutSelected(int idx);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HangoutsAdapter(JSONArray myDataset, int sel, ViewHolder.IMyViewHolderClicks listener) {
        mDataset = myDataset;
        this.sel = sel;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HangoutsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder((ViewGroup) v, listener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mBoundItemIdx = position;
        try {
            JSONObject jsonObject = mDataset.getJSONObject(position);
            holder.mTextView1.setText(jsonObject.getString("name"));
            holder.mTextView2.setText(jsonObject.getString("address"));
        } catch (JSONException e) {}
        if ( sel == holder.mBoundItemIdx) {
            holder.mTextView1.setTextColor(Color.parseColor("#9124B2"));
            holder.mTextView2.setTextColor(Color.parseColor("#9124B2"));
        } else {
            holder.mTextView1.setTextColor(Color.BLACK);
            holder.mTextView2.setTextColor(Color.BLACK);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length();
    }
}