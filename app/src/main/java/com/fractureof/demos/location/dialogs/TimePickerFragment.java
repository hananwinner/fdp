package com.fractureof.demos.location.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    public interface DatesTimePickerListener {
        void onDatesTimePicked(int hour, int minute);
    }

    public TimePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        DatesTimePickerListener listener = (DatesTimePickerListener) getParentFragment();
        listener.onDatesTimePicked(hourOfDay,minute);
        this.dismiss();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(
                getActivity(), //parent activity
                this, hour, minute,
                DateFormat.is24HourFormat(getActivity())
        );
    }
}
