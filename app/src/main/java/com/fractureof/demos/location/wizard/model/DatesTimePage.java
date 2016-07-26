package com.fractureof.demos.location.wizard.model;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;

import com.fractureof.demos.location.backend.DatePlan;
import com.fractureof.demos.location.backend.FeWizPageDatestime;
import com.fractureof.demos.location.util.DateTimeFormat;
import com.fractureof.demos.location.wizard.ui.DateTimeFragment;
import com.syncano.library.data.SyncanoObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tyler on 19/06/2016.
 */
public class DatesTimePage extends Page {
    public static final String DATESTIME_HOUR_DATA_KEY = "DATESTIME_HOUR";
    public static final String DATESTIME_MINUTE_DATA_KEY = "DATESTIME_MINUTE";
    public static final String DATESTIME_YEAR_DATA_KEY = "DATESTIME_YEAR";
    public static final String DATESTIME_MONTH_DATA_KEY = "DATESTIME_MONTH";
    public static final String DATESTIME_DAY_OF_MONTH_DATA_KEY = "DATESTIME_DAY_OF_MONTH";
    public DatesTimePage(ModelCallbacks callbacks,
                         String title,
                         Context context,
                         LoaderManager loaderManager)
    {
        super(callbacks, title);
        initValues();
        mBackendObject = new FeWizPageDatestime();
        ((FeWizPageDatestime)mBackendObject).datePlan = datePlan;

    }

    private void initValues() {
        mData.putInt(DATESTIME_YEAR_DATA_KEY,-1);
        mData.putInt(DATESTIME_MONTH_DATA_KEY,-1);
        mData.putInt(DATESTIME_DAY_OF_MONTH_DATA_KEY,-1);
        mData.putInt(DATESTIME_HOUR_DATA_KEY,-1);
        mData.putInt(DATESTIME_MINUTE_DATA_KEY,-1);
    }
    @Override
    public Fragment createFragment() {
        return DateTimeFragment.create(getKey());
    }

    @Override
    public void notifyDataChanged() {
        super.notifyDataChanged();

    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        int y = mData.getInt(DATESTIME_YEAR_DATA_KEY);
        int m = mData.getInt(DATESTIME_MONTH_DATA_KEY);
        int d = mData.getInt(DATESTIME_DAY_OF_MONTH_DATA_KEY);
        int h = mData.getInt(DATESTIME_HOUR_DATA_KEY);
        int minute = mData.getInt(DATESTIME_MINUTE_DATA_KEY);
        if (!isDateTimeFieldsSet(y,m,d,h,minute)) {
            dest.add(new ReviewItem("Date time1",
                    "not set yet" ,
                    getKey(), -1));
        } else {
            String datesTime = DateTimeFormat.make_simple_date_desc(makeDatesTime(y,m,d,h,minute));

            dest.add(new ReviewItem("Date time1",
                    datesTime,
                    getKey(), -1));
        }
    }

    public Calendar getDatesTime() {
        int y = mData.getInt(DATESTIME_YEAR_DATA_KEY);
        int m = mData.getInt(DATESTIME_MONTH_DATA_KEY);
        int d = mData.getInt(DATESTIME_DAY_OF_MONTH_DATA_KEY);
        int h = mData.getInt(DATESTIME_HOUR_DATA_KEY);
        int minute = mData.getInt(DATESTIME_MINUTE_DATA_KEY);
        return makeDatesTime(y,m,d,h,minute);
    }

    private static Calendar makeDatesTime(int y, int m, int d, int h, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, y);
        calendar.set(Calendar.MONTH, m);
        calendar.set(Calendar.DAY_OF_MONTH, d);
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }

    @Override
    public boolean isCompleted() {
        int y = mData.getInt(DATESTIME_YEAR_DATA_KEY);
        int m = mData.getInt(DATESTIME_MONTH_DATA_KEY);
        int d = mData.getInt(DATESTIME_DAY_OF_MONTH_DATA_KEY);
        int h = mData.getInt(DATESTIME_HOUR_DATA_KEY);
        int minute = mData.getInt(DATESTIME_MINUTE_DATA_KEY);
        return isDateTimeFieldsSet(y,m,d,h,minute);
    }

    private boolean isDateTimeFieldsSet(int y, int m, int d, int h, int minute) {
        return
                isDateTimeFieldSet(y)
                &&
                isDateTimeFieldSet(m)
                &&
                isDateTimeFieldSet(d)
                &&
                isDateTimeFieldSet(h)
                &&
                isDateTimeFieldSet(minute);
    }
    private boolean isDateTimeFieldSet(int field) { return field >= 0;}
}
