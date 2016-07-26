package com.fractureof.demos.location;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fractureof.demos.location.backend.Pickup;
import com.fractureof.demos.location.backend.PickupInfoFactory;

public class DistanceFragment extends Fragment {
    private static final String TEMP_MY_USER_ID="11";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MY_USER_ID = "1";
    private static final String ARG_PARTNER_USER_ID = "dummy";

    private String mMyUserId;
    private String mPartnerUserId;

    public DistanceFragment() {
    }

    public static DistanceFragment newInstance(
            String myUserId, String partnerUserId
            , Pickup pickup
    ) {
        DistanceFragment fragment = new DistanceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MY_USER_ID, myUserId);
        args.putString(ARG_PARTNER_USER_ID, partnerUserId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMyUserId = getArguments().getString(ARG_MY_USER_ID);
            mMyUserId = TEMP_MY_USER_ID;
            mPartnerUserId = getArguments().getString(ARG_PARTNER_USER_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_distance, container, false);
        if ( SplashActivity.avatarBitmap != null ) {
            ImageView myAvatarImageView = (ImageView) result.findViewById(R.id.my_avatar);

            myAvatarImageView.setImageDrawable( new RoundedAvatarDrawable( SplashActivity.avatarBitmap) );

            ImageView partnerAvatarImageView = (ImageView) result.findViewById(R.id.partner_avatar);
            partnerAvatarImageView.setImageDrawable( new RoundedAvatarDrawable(
                Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(
                                getResources(), R.drawable.partner_avatar_f)
                        ,
                        160,
                        160,
                        false)
            ) );
        }
        DistanceDraw distanceDraw = (DistanceDraw) result.findViewById(R.id.distance_draw);
        distanceDraw.setPickupData(PickupInfoFactory.getPickupInfo());
        return result;

    }
}
