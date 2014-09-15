package com.droidkit.file.selectors.items.folders;

import android.os.Environment;

import com.droidkit.file.R;
import com.droidkit.file.selectors.items.ExplorerItem;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class MusicFolderItem extends FolderItem {
    public MusicFolderItem() {
        super(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
    }
    public MusicFolderItem(File file){
        super(file);
    }

    @Override
    public int getImage() {
        return android.R.drawable.ic_media_play;
    }
}
