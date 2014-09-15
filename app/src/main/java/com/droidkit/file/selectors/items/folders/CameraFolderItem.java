package com.droidkit.file.selectors.items.folders;

import android.os.Environment;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class CameraFolderItem extends FolderItem {
    public CameraFolderItem() {
        super(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
    }

}
