package com.droidkit.picker.items;

import com.droidkit.picker.items.ExplorerItem;

import java.io.File;
import java.util.Calendar;

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
        long size = (int) file.length();
        if(size>1000000000){
            return (size / (1000000000))+"."+((size%1000000000)/100000000) + " MB ";
        }
        if(size>1000000){
            return (size / (1000000))+"."+((size%1000000)/100000) + " MB ";
        }

        return (size / (1000)) + " KB " ;
    }


}