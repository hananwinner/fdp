package com.fractureof.demos.location.dialogs;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.fractureof.demos.location.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    public interface DatesDateSetListener {
        void onDatesDatePicked(int year, int monthOfYear, int dayOfMonth);
    }

    public DatePickerFragment() {
        // Required empty public constructor
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

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        DatesDateSetListener listener = (DatesDateSetListener) getParentFragment();
        listener.onDatesDatePicked(year, monthOfYear,dayOfMonth);
    }
}
