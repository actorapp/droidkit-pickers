package com.droidkit.picker.util;

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

    public static String getConvertedTime(long date){
       return SimpleDateFormat.getInstance().format(new Date(date * 1000L));// todo today at 2:30pm
    }
}
