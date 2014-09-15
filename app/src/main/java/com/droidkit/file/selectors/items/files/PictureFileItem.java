package com.droidkit.file.selectors.items.files;

import com.droidkit.file.selectors.items.ExplorerItem;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class PictureFileItem extends ExplorerItem {
    public PictureFileItem(File file, boolean selected, String fileType) {
        super(file,selected,fileType);
    }

    @Override
    public int getImage() {
        return android.R.drawable.ic_menu_gallery;
    }
}
