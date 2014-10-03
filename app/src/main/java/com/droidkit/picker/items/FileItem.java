package com.droidkit.picker.items;

import com.droidkit.picker.items.ExplorerItem;
import com.droidkit.picker.util.TimeHelper;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kiolt_000 on 15/09/2014.
 */
public class FileItem extends ExplorerItem {


    public FileItem(String path) {
        super(path);
    }

    public FileItem(File file) {
        super(file);
    }

    public FileItem(File file, boolean selected) {
        super(file, selected);
    }

    public FileItem(File file, boolean selected, String fileType) {
        super(file, selected, fileType);
    }


    public FileItem(File file, boolean selected, String fileType, int imageId) {
        super(file, selected, fileType, imageId, true);
    }

    @Override
    public String getSubtitle() {
        String convertedSize = null;
        long size = (int) file.length();
        if (size > 1024*1024*1024) {
            convertedSize = (size / (1024*1024*1024)) + "." + ((size % (1024*1024*1024)) / (100*1024*1024)) + " MB";
        }
        if (size > 1024*1024) {
            convertedSize = (size / (1024*1024)) + "." + ((size % (1024*1024)) / (100*1024)) + " MB";
        }
        if (convertedSize == null) {

            convertedSize = (size / (1024)) + " KB";
        }

        long date = file.lastModified();

        String convertedDate = TimeHelper.getConvertedTime(date);
        return convertedSize + ", " + convertedDate;
    }


}