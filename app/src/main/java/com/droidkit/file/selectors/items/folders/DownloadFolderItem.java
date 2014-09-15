package com.droidkit.file.selectors.items.folders;

import android.os.Environment;

import com.droidkit.file.selectors.items.ExplorerItem;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class DownloadFolderItem extends FolderItem {
    public DownloadFolderItem() {
        super(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
    }

    public DownloadFolderItem(File file){
        super(file);
    }

    @Override
    public int getImage() {
        return android.R.drawable.stat_sys_download;
    }
}
