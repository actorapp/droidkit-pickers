package com.droidkit.file.selectors.items;

import android.os.Environment;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class ExternalStorageItem extends ExplorerItem {
    public ExternalStorageItem() {
        super(Environment.getExternalStorageDirectory(), false);
    }

    @Override
    public String getTitle() {
        return "sdcard";
    }

    @Override
    public int getImage() {
        return android.R.drawable.stat_notify_sdcard;
    }
}
