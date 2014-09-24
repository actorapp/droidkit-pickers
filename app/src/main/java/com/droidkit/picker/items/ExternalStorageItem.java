package com.droidkit.picker.items;

import android.os.Environment;

import java.io.File;

/**
 * Created by kiolt_000 on 22/09/2014.
 */
public class ExternalStorageItem extends FolderItem {
    private final String name;

    public ExternalStorageItem(String name){
        super(Environment.getExternalStorageDirectory());
        this.name = name;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubtitle() {
        return null;
    }
}
