package com.fractureof.demos.location.wizard.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fractureof.demos.location.R;
import com.fractureof.demos.location.wizard.model.DateTimePage;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DateTimeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DateTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateTimeFragment extends Fragment {

    private static final String ARG_KEY = "key";

    private String mKey;
    private DateTimePage mPage;
    private PageFragmentCallbacks mCallbacks;

    private Button mTestButton;

    public DateTimeFragment() {
        // Required empty public constructor
    }

    public static Fragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY,key);
        DateTimeFragment fragment = new DateTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mKey = args.getString(ARG_KEY);
            mPage = (DateTimePage) mCallbacks.onGetPage(mKey);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_date_time, container, false);
        mTestButton = (Button) rootView.findViewById(R.id.test_button_date_time_fragment);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPage.getData().putString( DateTimePage.DATETIME_DATA_KEY, "21/06/2016 21:00:00");
                mPage.notifyDataChanged();
            }
        });
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());



        return rootView;

    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PageFragmentCallbacks) {
            mCallbacks = (PageFragmentCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PageFragmentCallbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

}
