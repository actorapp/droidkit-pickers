package com.droidkit.file.selectors.items;

import android.os.Environment;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class BadExternalStorageItem extends ExplorerItem {
    public BadExternalStorageItem() {
        super(Environment.getExternalStorageDirectory());
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public int getImage() {
        return android.R.drawable.ic_lock_lock;
    }
}
