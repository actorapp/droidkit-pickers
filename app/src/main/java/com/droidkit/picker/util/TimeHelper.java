package com.droidkit.picker.util;

/**
 * Created by kiolt_000 on 15/09/2014.
 */
public class TimeHelper {
    public static int getUnixNow() {
        return (int) (System.currentTimeMillis() / 1000);
    }
}
