package com.droidkit.picker.items;

/**
 * Created by kiolt_000 on 15/09/2014.
 */
public class StorageItem extends FolderItem {
    public StorageItem() {
        super("/");
    }

    @Override
    public String getTitle() {
        return "/";
    }
}
