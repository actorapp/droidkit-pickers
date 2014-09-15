package com.droidkit.file.selectors.items.folders;

import com.droidkit.file.selectors.items.ExplorerItem;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class LockedFolderItem extends ExplorerItem {
    public LockedFolderItem(File file) {
        super(file,false);
    }

    @Override
    public int getImage() {
        return android.R.drawable.ic_lock_lock;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getSubtitle() {
        return super.getSubtitle();
    }
}
