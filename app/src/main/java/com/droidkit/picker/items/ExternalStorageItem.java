package com.droidkit.picker.items;

import android.os.Environment;

import java.io.File;

/**
 * Created by kiolt_000 on 22/09/2014.
 */
public class ExternalStorageItem extends FolderItem {
    public ExternalStorageItem()
    {
        super(Environment.getExternalStorageDirectory());
    }

    @Override
    public String getTitle() {
        return "External memory";
    }

    @Override
    public String getSubtitle() {
        return null;
    }
}
