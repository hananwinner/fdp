package com.fractureof.demos.location;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyler on 08/06/2016.
 */
public class PartnerSearchAdapter extends RecyclerView.Adapter<PartnerSearchAdapter.ViewHolder>{



    public static class PartnerSearchModel {
        public String formattedName;
        public String datingDescription;
        public int partnerBackendId;
        public String imageUri;

        public PartnerSearchModel(String formattedName, String datingDescription, int partnerBackendId, String imageUri) {
            this.formattedName = formattedName;
            this.datingDescription = datingDescription;
            this.partnerBackendId = partnerBackendId;
            this.imageUri = imageUri;
        }
    }

    private final Context mContext;
    private ViewHolder.IMyViewHolderClicks listener;
    private List<PartnerSearchModel> mModels;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PartnerSearchAdapter(Context context,
                                //int sel,
                                List<PartnerSearchModel> models,
                                ViewHolder.IMyViewHolderClicks listener) {
        this.listener = listener;
        mModels = new ArrayList<>(models);
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PartnerSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partner_search_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder((ViewGroup) v, listener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mBoundItemIdx = position;
        final PartnerSearchModel model = mModels.get(position);
        holder.mTextView1.setText(model.formattedName);
        holder.mTextView2.setText(model.datingDescription);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public static interface IMyViewHolderClicks {
            public void onPartnerSelected(int idx);
        }

        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mImageView;
        IMyViewHolderClicks listener;
        public int mBoundItemIdx = 0;

        public ViewHolder(ViewGroup v, IMyViewHolderClicks listener) {
            super(v);
            this.listener = listener;
            mImageView = (ImageView) v.findViewById(R.id.partner_search_partner_avatar);
            mTextView1 = (TextView)v.findViewById(R.id.partner_fmt_name_label);
            mTextView2 = (TextView)v.findViewById(R.id.partner_app_desc);
            this.itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            listener.onPartnerSelected(mBoundItemIdx);
        }

    }
}
