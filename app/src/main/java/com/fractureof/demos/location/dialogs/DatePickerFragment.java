package com.fractureof.demos.location.dialogs;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.fractureof.demos.location.wizard.ui.DateTimeFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    int mActivatorRowId;
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        ((DatesDatePickerListener) getTargetFragment()).onDatesDatePicked(mActivatorRowId,year,monthOfYear,dayOfMonth);
    }

    public interface DatesDatePickerListener {
        void onDatesDatePicked(int activatorRowId,int year, int monthOfYear, int dayOfMonth);
    }

    public DatePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mActivatorRowId = args.getInt(DateTimeFragment.DIALOG_ARG_ACTIVATORROWID,-1);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(DateTimeFragment.DIALOG_ARG_ACTIVATORROWID, mActivatorRowId);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int monthOfYear = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(
                getActivity(),
                this,
                year,
                monthOfYear,
                dayOfMonth);
    }

}
