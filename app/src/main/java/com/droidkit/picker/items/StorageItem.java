package com.droidkit.picker.items;

import android.app.Activity;

public class StorageItem extends FolderItem {
    public StorageItem() {
        super("/");
    }

    @Override
    public String getTitle() {
        return "Phone storage";
    }
}
