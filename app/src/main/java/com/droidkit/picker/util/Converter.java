package com.droidkit.picker.util;

import android.os.Environment;

import com.droidkit.file.R;
import com.droidkit.picker.items.FileItem;
import com.droidkit.picker.items.FolderItem;
import com.droidkit.picker.items.PictureItem;

import java.io.File;

public class Converter {

    public static FileItem getFileItem(File file, boolean selected) {

        if(!file.exists()){
            return null;
        }

        String fileName = file.getName();
        String fileType = "";
        String[] fileNameSplit = fileName.split("\\.");

        if (fileNameSplit.length > 1) {
            fileType = fileNameSplit[fileNameSplit.length - 1];
        } else {
            return new FileItem(file, selected,"?");
        }

        int imageId = 0;// R.drawable.file;
        switch (FormatChecker.getType(fileType)) {
            case FormatChecker.FORMAT_MUSIC:
                // todo imageId = R.drawable.file_music;
                break;
            case FormatChecker.FORMAT_PICTURE:
                return new PictureItem(file, selected);
            // todo imageId = R.drawable.file_picture;
            // break;
            case FormatChecker.FORMAT_DOC:
                // todo imageId = R.drawable.file_doc;
                break;
            case FormatChecker.FORMAT_ARCHIVE:
                // todo imageId = R.drawable.file_archive;
                break;
            case FormatChecker.FORMAT_EXECUTABLE:
                // todo imageId = R.drawable.file_exe;
                break;
            case FormatChecker.FORMAT_VIDEO:
                // todo imageId = R.drawable.file_video;
                break;
        }
        return new FileItem(file, selected, fileType, imageId);
    }

    public static FolderItem getFolderItem(File file) {

        int imageId = R.drawable.picker_folder;

        if (file.list() == null) {
            if (file.getName().toCharArray()[0] == '.') {
                // imageId = R.drawable.folder_locked;
                return null;
            } else
                return new FolderItem(file, R.drawable.picker_system_folder, true);
        }else
        if(file.list().length==0){
            return new FolderItem(file, R.drawable.picker_empty_folder, true);
        }

        String folderPath = file.getPath();
        if(folderPath.equals(Environment.getExternalStorageDirectory().getPath())){
            // return new ExternalStorageItem("External memory");
            // todo imageId = R.drawable.folder_external;
        }else
        if (folderPath.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath())) {
            // todo    imageId = R.drawable.folder_music;
        } else if (folderPath.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath())) {
            // todo   imageId = R.drawable.folder_picture;
        } else if (folderPath.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath())) {
            // todo imageId = R.drawable.folder_downloads;
        } else //if (Build.VERSION.SDK_INT >= 19 && folderPath.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath())) {
            // todo imageId = R.drawable.folder_docs;
        //} else
            if (folderPath.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath())) {
            // todo imageId = R.drawable.folder_movies;
        } else if (folderPath.contains(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath())) {
            // todo imageId = R.drawable.folder_camera;
        } else {

            folderPath = folderPath.toLowerCase();
            if (folderPath.contains("music")) {
                // todo imageId = R.drawable.folder_music;
            } else if (folderPath.contains("picture") || folderPath.contains("image") || folderPath.contains("photo")) {
                // todo imageId = R.drawable.folder_picture;
            } else if (folderPath.contains("download")) {
                // todo imageId = R.drawable.folder_downloads;
            } else if (folderPath.contains("doc")) {
                // todo imageId = R.drawable.folder_docs;
            } else if (folderPath.contains("movie")) {
                // todo imageId = R.drawable.folder_movies;
            }


        }

        return new FolderItem(file, imageId);
    }
}
