package com.fractureof.demos.location.util;

import android.support.annotation.NonNull;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by tyler on 22/06/2016.
 */
public class DateTimeFormat {

    public static Map<Integer,String> daysDiffMap;

    static {
        daysDiffMap = new HashMap<>();
        daysDiffMap.put(0,"this");
        daysDiffMap.put(-1, "Tomorrow");
        daysDiffMap.put(1, "Yesterday");
    }

    public static int calcDaysDiff(Calendar date) {
        long _date_diff = Calendar.getInstance().getTimeInMillis() - date.getTimeInMillis();
        int daysDiff = (int) TimeUnit.MILLISECONDS.toDays(_date_diff);
        return daysDiff;
    }

    public static String make_simple_date_desc(Calendar date) {
        int daysDiff = calcDaysDiff(date);
        int hourOfDay = date.get(Calendar.HOUR_OF_DAY);
        if (daysDiff == 0 && (hourOfDay > 20 || hourOfDay < 7)) {
            return "Tonight";
        }
        String day_str = dateStr(daysDiff, date);
        if ((daysDiff <=1) && (daysDiff>= -1)) {
            String day_period_str = get_day_period_str(date);
            String formatted_day_str = String.format("%s %s", day_str, day_period_str);
            return formatted_day_str;
        }
        return day_str;
    }

        public static String get_day_period_str(Calendar date) {
            int h = date.get(Calendar.HOUR_OF_DAY);
            if (h< 7 || h>20) {
                return "night";
            } else if (h<13) {
                return "morning";
            } else if (h<18) {
                return "afternoon";
            }
            return "evening";
        }

    @NonNull
    public static String getFormattedDatesTime(Calendar calendar) {
        StringBuffer strBufToAppendTo = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        StringBuffer strBuf = sdf.format(
                new Date(calendar.getTimeInMillis())
                , strBufToAppendTo,
                new FieldPosition(0));
        return strBuf.toString();
    }

    @NonNull
    public static String getFormattedHour(Calendar calendar) {
        StringBuffer strBufToAppendTo = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        StringBuffer strBuf = sdf.format(
                new Date(calendar.getTimeInMillis())
                , strBufToAppendTo,
                new FieldPosition(0));
        return strBuf.toString();
    }



    public static String dateStr(int daysDiff, Calendar date) {
        if (daysDiff < -7 || daysDiff > 7) {
            return getFormattedDatesTime(date);
        } else if (daysDiff < -1) {
            StringBuffer strBufToAppendTo = new StringBuffer("Next ");
            SimpleDateFormat sdf = new SimpleDateFormat("EEE");
            StringBuffer strBuf = sdf.format(
                    new Date(date.getTimeInMillis())
                    , strBufToAppendTo,
                    new FieldPosition(0));
            return strBufToAppendTo.toString();
        } else if (daysDiff > 1) {
            StringBuffer strBufToAppendTo = new StringBuffer("Last ");
            SimpleDateFormat sdf = new SimpleDateFormat("EEE");
            StringBuffer strBuf = sdf.format(
                    new Date(date.getTimeInMillis())
                    , strBufToAppendTo,
                    new FieldPosition(0));
            return strBufToAppendTo.toString();
        } else return daysDiffMap.get(daysDiff);
    }

}

