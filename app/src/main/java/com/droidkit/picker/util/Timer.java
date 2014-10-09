package com.droidkit.picker.util;

import android.util.Log;

/**
 * Created by kiolt_000 on 09/10/2014.
 */
public class Timer {
    private static long startTime;

    public static void start() {
        startTime = System.currentTimeMillis();
    }

    public static void stop(String message) {
        Log.d("Picker", "Timer: " + (System.currentTimeMillis() - startTime)+"ms, message: "+message);
        startTime = 0;
    }
}
