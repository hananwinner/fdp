package com.fractureof.demos.location.wizard.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.fractureof.demos.location.R;
import com.fractureof.demos.location.dialogs.DatePickerFragment;
import com.fractureof.demos.location.dialogs.TimePickerFragment;
import com.fractureof.demos.location.util.DateTimeFormat;
import com.fractureof.demos.location.wizard.model.DatesTimePage;

import java.text.DateFormat;
import java.util.Calendar;

public class DateTimeFragment
        extends Fragment
        implements
        DatePickerFragment.DatesDatePickerListener
        , TimePickerFragment.DatesTimePickerListener  {

    private static final String ARG_KEY = "key";
    private String mKey;
    private DatesTimePage mPage;
    private PageFragmentCallbacks mCallbacks;

    private ImageButton mTonightButton;
    private ImageButton mCustomButton;
    private ImageButton mTommorowButton;

    private TextView mTonightText;
    private TextView mTomorrowNightText;
    private TextView mCustomTimeText;

    private TableRow mTonightTableRow;
    private TableRow mTomorrowTableRow;
    private TableRow mCustomTimeTableRow;

    private int mStackLevel = 0;
    private FragmentTransaction mDialogsFragmentTransaction;
    public static final int SET_DATES_DATE_DIALOG = 1;
    public static final int SET_DATES_TIME_DIALOG = 2;
    public static final String DIALOG_ARG_ACTIVATORROWID = "activatorRowId";

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
            mPage = (DatesTimePage) mCallbacks.onGetPage(mKey);
        }
        if (savedInstanceState != null) {
            mStackLevel = savedInstanceState.getInt("level");
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("level", mStackLevel);
    }

    void showDialog(int type, int activatorRowId) {
        Bundle args = new Bundle();
        args.putInt(DateTimeFragment.DIALOG_ARG_ACTIVATORROWID,activatorRowId);

        switch(type) {
            case SET_DATES_DATE_DIALOG: {
                DialogFragment dialogFrag = new DatePickerFragment();
                dialogFrag.setArguments(args);
                dialogFrag.setTargetFragment(this, SET_DATES_DATE_DIALOG);
                dialogFrag.show(getActivity().getSupportFragmentManager().beginTransaction(), "SET_DATES_DATE_DIALOG");
                break;
            }
            case SET_DATES_TIME_DIALOG: {
                DialogFragment dialogFrag = new TimePickerFragment();
                dialogFrag.setArguments(args);
                dialogFrag.setTargetFragment(this, SET_DATES_TIME_DIALOG);
                dialogFrag.show(getActivity().getSupportFragmentManager().beginTransaction(), "SET_DATES_TIME_DIALOG");
                break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_date_time, container, false);

        mTonightButton= (ImageButton) rootView.findViewById(R.id.dates_time_tonight_button);
        mTonightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int y = calendar.get(Calendar.YEAR);
                int m = calendar.get(Calendar.MONTH);
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                mYearToCommit = y;
                mMonthOfYearToCommit = m;
                mDayOfMonthToCommit = d;
                showDialog(SET_DATES_TIME_DIALOG,R.id.dates_time_tonight_table_row);
            }
        });
        mTommorowButton = (ImageButton) rootView.findViewById(R.id.dates_time_tomorrow_night_button);
        mTommorowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH,1);
                int y = calendar.get(Calendar.YEAR);
                int m = calendar.get(Calendar.MONTH);
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                mYearToCommit = y;
                mMonthOfYearToCommit = m;
                mDayOfMonthToCommit = d;
                showDialog(SET_DATES_TIME_DIALOG,R.id.dates_time_tomorrow_table_row);
            }
        });
        mCustomButton = (ImageButton) rootView.findViewById(R.id.test_button_date_time_fragment);
        mCustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(SET_DATES_DATE_DIALOG,R.id.dates_time_custom_table_row);
            }
        });
        mTonightText = (TextView) rootView.findViewById(R.id.date_time_tonight_text);
        mTomorrowNightText = (TextView) rootView.findViewById(R.id.date_time_tommorow_text);
        mCustomTimeText= (TextView) rootView.findViewById(R.id.date_time_custom_text);

        mTonightTableRow = (TableRow) rootView.findViewById(R.id.dates_time_tonight_table_row);
        mTomorrowTableRow = (TableRow) rootView.findViewById(R.id.dates_time_tomorrow_table_row);
        mCustomTimeTableRow = (TableRow) rootView.findViewById(R.id.dates_time_custom_table_row);

        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());
        setFromPage();
        return rootView;
    }

    private void setFromPage() {
        if (mPage.isCompleted()) {
            Calendar calendar = mPage.getDatesTime();
            int daysDiff = - (DateTimeFormat.calcDaysDiff(calendar));
            int activatorRowNum = mTonightTableRow.getId();
            if (daysDiff == 1) {
                activatorRowNum = mTomorrowTableRow.getId();
            } else if (daysDiff > 1) {
                activatorRowNum = mCustomTimeTableRow.getId();
            }
            String simpleDateDesc = DateTimeFormat.make_simple_date_desc(calendar);
            String hourStr= DateTimeFormat.getFormattedHour( calendar );
            String label = String.format("%s\n%s", simpleDateDesc,hourStr);
            setDate(activatorRowNum, label);
        }
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

    private int mYearToCommit = -1;
    private int mMonthOfYearToCommit = -1;
    private int mDayOfMonthToCommit = -1;

    @Override
    public void onDatesDatePicked(int activatorRowId,int year, int monthOfYear, int dayOfMonth) {
        mYearToCommit = year;
        mMonthOfYearToCommit = monthOfYear;
        mDayOfMonthToCommit = dayOfMonth;
        showDialog(SET_DATES_TIME_DIALOG, activatorRowId);
    }

    @Override
    public void onDatesTimePicked(int activatorRowId,int hour, int minute) {
        mPage.getData().putInt(DatesTimePage.DATESTIME_YEAR_DATA_KEY, mYearToCommit );
        mPage.getData().putInt(DatesTimePage.DATESTIME_MONTH_DATA_KEY, mMonthOfYearToCommit);
        mPage.getData().putInt(DatesTimePage.DATESTIME_DAY_OF_MONTH_DATA_KEY, mDayOfMonthToCommit );
        resetTemporaryDateValues();
        mPage.getData().putInt(DatesTimePage.DATESTIME_HOUR_DATA_KEY, hour);
        mPage.getData().putInt(DatesTimePage.DATESTIME_MINUTE_DATA_KEY, minute );
        mPage.notifyDataChanged();

        resetRows();
        Calendar calendar = mPage.getDatesTime();
        String simpleDateDesc = DateTimeFormat.make_simple_date_desc(calendar);
        String hourStr= DateTimeFormat.getFormattedHour( calendar );
        String label = String.format("%s\n%s", simpleDateDesc,hourStr);

        setDate(activatorRowId, label);

        Toast.makeText(this.getContext(), "It's a Date !",Toast.LENGTH_LONG).show();
    }

    private void setDate(int activatorRowId, String label) {
        int selectedColor = this.getResources().getColor(R.color.dates_time_highlight_row
               /* , this.getContext().getTheme()*/);
        if (mTonightTableRow.getId() == (activatorRowId)) {
            mTonightTableRow.setBackgroundColor(selectedColor);
            mTonightText.setText(label);
        }
        if (mTomorrowTableRow.getId() == (activatorRowId)) {
            mTomorrowTableRow.setBackgroundColor(selectedColor);
            mTomorrowNightText.setText(label);
        }
        if (mCustomTimeTableRow.getId() == (activatorRowId)) {
            mCustomTimeTableRow.setBackgroundColor(selectedColor);
            mCustomTimeText.setText(label);
        }
    }

    private void resetTemporaryDateValues() {
        mYearToCommit = -1;
        mMonthOfYearToCommit = -1;
        mDayOfMonthToCommit = -1;
    }
    private void resetRows() {
        mTomorrowTableRow.setBackgroundColor(Color.TRANSPARENT);
        mTonightTableRow.setBackgroundColor(Color.TRANSPARENT);
        mCustomTimeTableRow.setBackgroundColor(Color.TRANSPARENT);
        mTonightText.setText(R.string.dates_time_tonight);
        mTomorrowNightText.setText(R.string.dates_time_tomorrow_night);
        mCustomTimeText.setText(R.string.dates_time_set);
    }
}
