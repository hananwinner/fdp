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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DistanceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DistanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DistanceFragment extends Fragment {
    private static final String TEMP_MY_USER_ID="11";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MY_USER_ID = "1";
    private static final String ARG_PARTNER_USER_ID = "dummy";

    private String mMyUserId;
    private String mPartnerUserId;

    public DistanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DistanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DistanceFragment newInstance(String myUserId, String partnerUserId) {
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
        return result;

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        //if (mListener != null) {
        //    mListener.onFragmentInteraction(uri);
        //}
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
