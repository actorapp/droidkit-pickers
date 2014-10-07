package com.droidkit.picker.util;

import android.content.Context;

import com.droidkit.file.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kiolt_000 on 15/09/2014.
 */
public class TimeHelper {
    public static int getUnixNow() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static String getConvertedTime(long time, Context context) {


        Date currentDate = new Date(System.currentTimeMillis());
        Date convertableDate = new Date(time);

        if (currentDate.getYear() == convertableDate.getYear()) {
            if (currentDate.getMonth() == convertableDate.getMonth()) {
                if (currentDate.getDay() == convertableDate.getDay()) {
                    if (currentDate.getHours() == convertableDate.getHours()) {
                        if (currentDate.getHours() - 1 == convertableDate.getHours()) {
                            if (currentDate.getMinutes() == convertableDate.getMinutes()) {
                                return context.getString(R.string.picker_time_minute_ago);
                            }
                            int minutesAgo = currentDate.getMinutes() - convertableDate.getMinutes();
                            return context.getResources().getQuantityString(R.plurals.picker_time_minutes_ago, minutesAgo, minutesAgo);
                            // todo android-i18n-plurals implementation
                        }
                    } else {
                        if (currentDate.getHours() - 1 == convertableDate.getHours()) {
                            return context.getString(R.string.picker_time_hour_ago);
                        }
                    }
                    return context.getString(R.string.picker_time_today_at, SimpleDateFormat.getTimeInstance().format(convertableDate));
                } else {
                    if (currentDate.getDay() - 1 == convertableDate.getDay()) {
                        return context.getString(R.string.picker_time_yesterday_at, SimpleDateFormat.getTimeInstance().format(convertableDate));
                    }
                }
            }
        }
        String convertedDate = SimpleDateFormat.getDateInstance().format(convertableDate);
        return context.getString(R.string.picker_time_at, convertedDate, SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(convertableDate));
    }
}
