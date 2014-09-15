package com.droidkit.file.selectors.items.folders;

import android.os.Environment;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class VideoFolderItem extends FolderItem {
    public VideoFolderItem() {
        super(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
    }

    public VideoFolderItem(File file){
        super(file);
    }

    @Override
    public int getImage() {
        return android.R.drawable.presence_video_online;
    }
}
