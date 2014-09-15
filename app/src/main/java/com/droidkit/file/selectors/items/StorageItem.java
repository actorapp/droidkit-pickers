package com.droidkit.file.selectors.items;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class StorageItem extends ExplorerItem {
    public StorageItem() {
        super("/");
    }

    @Override
    public String getTitle() {
        return "/";
    }

    @Override
    public int getImage() {
        return android.R.drawable.sym_contact_card;
    }
}
