package com.droidkit.picker.items;

import android.app.Activity;

public class StorageItem extends FolderItem {
    public StorageItem(String name) {
        super("/");
    }

    @Override
    public String getTitle() {
        return "Phone storage";
    }
}
