package com.droidkit.file.selectors.items.folders;

import com.droidkit.file.R;
import com.droidkit.file.selectors.items.ExplorerItem;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class FolderItem extends ExplorerItem {
    public FolderItem(File file) {
        super(file);
    }

    @Override
    public int getImage() {
        return R.drawable.folder;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }
}
