package com.fractureof.demos.location.wizard.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fractureof.demos.location.R;
import com.fractureof.demos.location.dialogs.DatePickerFragment;
import com.fractureof.demos.location.dialogs.TimePickerFragment;
import com.fractureof.demos.location.wizard.model.DateTimePage;

public class DateTimeFragment extends Fragment
        implements TimePickerFragment.DatesTimePickerListener
        ,DatePickerFragment.DatesDateSetListener {

    private static final String ARG_KEY = "key";

    private String mKey;
    private DateTimePage mPage;
    private PageFragmentCallbacks mCallbacks;

    private ImageButton mTestButton;

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
        mTestButton = (ImageButton) rootView.findViewById(R.id.test_button_date_time_fragment);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(
                        getActivity().getSupportFragmentManager(),
                        "DatesDatePicker"
                );
            }
        });



        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());
        return rootView;
    }

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

    @Override
    public void onDatesTimePicked(int hour, int minute) {
        mPage.getData().putInt(DateTimePage.DATESTIME_YEAR_DATA_KEY, hour );
        mPage.getData().putInt(DateTimePage.DATESTIME_MINUTE_DATA_KEY, minute );
    }

    @Override
    public void onDatesDatePicked(int year, int monthOfYear, int dayOfMonth) {
        mPage.getData().putInt(DateTimePage.DATESTIME_YEAR_DATA_KEY, year );
        mPage.getData().putInt(DateTimePage.DATESTIME_MONTH_DATA_KEY, monthOfYear );
        mPage.getData().putInt(DateTimePage.DATESTIME_DAY_OF_MONTH_DATA_KEY, dayOfMonth );

        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(
                getActivity().getSupportFragmentManager(),
                "DatesTimePicker"
        );

    }


}
