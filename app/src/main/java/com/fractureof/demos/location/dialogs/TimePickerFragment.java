package com.fractureof.demos.location.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.fractureof.demos.location.wizard.ui.DateTimeFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    int mActivatorRowId;
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ((DatesTimePickerListener) getTargetFragment()).onDatesTimePicked(mActivatorRowId, hourOfDay,minute);
        //this.dismiss();
    }

    public interface DatesTimePickerListener {
        void onDatesTimePicked(int activatorRowId, int hour, int minute);
    }

    public TimePickerFragment() {
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
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(
                getActivity(), //parent activity
                this,
                hour, minute,
                DateFormat.is24HourFormat(getActivity())
        );
    }
}
