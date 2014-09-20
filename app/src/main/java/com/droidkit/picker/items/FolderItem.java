package com.droidkit.picker.items;

import com.droidkit.file.R;
import com.droidkit.picker.items.ExplorerItem;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class FolderItem extends ExplorerItem {

    public FolderItem(File file) {
        super(file,false, null,R.drawable.folder, true);

    }

    public FolderItem(String path) {
        super(new File(path),false, null,R.drawable.folder, true);
    }

    public FolderItem(File file, boolean disabled) {
        super(file, false, null, !disabled ? R.drawable.folder : R.drawable.folder, disabled);// todo R.drawable.folder_locked, disabled);
    }

    public FolderItem(File file, int imageId) {

        super(file, false, null, imageId, true);
    }

    public FolderItem(File file, int imageId, boolean locked) {
        super(file, false, null, imageId, locked);
    }

    @Override
    public String getSubtitle() {
        return null;
    }



    @Override
    public boolean isDirectory() {
        return true;
    }
}