package com.droidkit.file.selectors.items.folders;

import android.os.Environment;

import com.droidkit.file.selectors.items.ExplorerItem;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class PictureFolderItem extends FolderItem {
    public PictureFolderItem() {
        super(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }
    public PictureFolderItem(File file){
        super(file);
    }

    @Override
    public int getImage() {
        return android.R.drawable.ic_menu_gallery;
    }
}
