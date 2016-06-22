package com.fractureof.demos.location.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.fractureof.demos.location.PartnerNameEntryFragment;
import com.fractureof.demos.location.wizard.ui.DateTimeFragment;

import java.util.ArrayList;

/**
 * Created by tyler on 19/06/2016.
 */
public class DateTimePage extends Page {
    public static final String DATETIME_DATA_KEY = "datetime";
    public static final String DATESTIME_HOUR_DATA_KEY = "DATESTIME_HOUR";
    public static final String DATESTIME_MINUTE_DATA_KEY = "DATESTIME_MINUTE";
    public static final String DATESTIME_YEAR_DATA_KEY = "DATESTIME_YEAR";
    public static final String DATESTIME_MONTH_DATA_KEY = "DATESTIME_MONTH";
    public static final String DATESTIME_DAY_OF_MONTH_DATA_KEY = "DATESTIME_DAY_OF_MONTH";
    public DateTimePage(ModelCallbacks callbacks, String title)
    {
        super(callbacks, title);
    }
    @Override
    public Fragment createFragment() {
        return DateTimeFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {

        DateTimeFormatter.

        dest.add(new ReviewItem("Date time", mData.getString(DATETIME_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(DATETIME_DATA_KEY));
    }
}
