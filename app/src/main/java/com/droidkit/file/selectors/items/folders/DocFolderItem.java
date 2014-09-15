package com.droidkit.file.selectors.items.folders;

import android.os.Environment;

import com.droidkit.file.selectors.items.ExplorerItem;
import com.droidkit.file.selectors.items.files.DocFileItem;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class DocFolderItem extends FolderItem {
    public DocFolderItem() {
        super(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
    }
    public DocFolderItem(File file){
        super(file);
    }


    @Override
    public int getImage() {
        return android.R.drawable.ic_menu_crop;
    }
}
