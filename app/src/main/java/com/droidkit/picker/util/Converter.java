package com.droidkit.picker.util;

import android.os.Environment;

import com.droidkit.file.R;
import com.droidkit.picker.items.ExplorerItem;
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
            return new FileItem(file, selected,"?", R.drawable.picker_unknown);
        }

        int imageId = 0;// R.drawable.file;
        switch (FormatChecker.getType(fileType)) {
            case FormatChecker.FORMAT_PICTURE:
                return new PictureItem(file, selected, fileType);
            case FormatChecker.FORMAT_MUSIC:
                imageId = R.drawable.picker_music;
                break;
            case FormatChecker.FORMAT_DOC:
                imageId = R.drawable.picker_doc;
                break;
            case FormatChecker.FORMAT_PDF:
                imageId = R.drawable.picker_pdf;
                break;
            case FormatChecker.FORMAT_RAR:
                imageId = R.drawable.picker_rar;
                break;
            case FormatChecker.FORMAT_APK:
                imageId = R.drawable.picker_apk;
                break;
            case FormatChecker.FORMAT_VIDEO:
                imageId = R.drawable.picker_video;
                break;
            case FormatChecker.FORMAT_CSV:
                imageId = R.drawable.picker_csv;
                break;
            case FormatChecker.FORMAT_HTM:
                imageId = R.drawable.picker_htm;
                break;
            case FormatChecker.FORMAT_HTML:
                imageId = R.drawable.picker_html;
                break;
            case FormatChecker.FORMAT_PPT:
                imageId = R.drawable.picker_ppt;
                break;
            case FormatChecker.FORMAT_XLS:
                imageId = R.drawable.picker_xls;
                break;
            case FormatChecker.FORMAT_ZIP:
                imageId = R.drawable.picker_zip;
                break;
        }
        return new FileItem(file, selected, fileType, imageId);
    }

    public static FolderItem getFolderItem(File file) {

        int imageId = R.drawable.picker_folder;

        if (file.list() == null || file.getName().toCharArray()[0] == '.') {
            return null;
            /*if (file.getName().toCharArray()[0] == '.') {
                // imageId = R.drawable.folder_locked;
                return null;
            } else
                return new FolderItem(file, R.drawable.picker_system_folder, true);*/
        }else
        if(file.list().length==0){
            return new FolderItem(file, R.drawable.picker_folder, true);// todo: picker_folder_empty
        }

        String folderPath = file.getPath();
        if(folderPath.equals(Environment.getExternalStorageDirectory().getPath())){
            // return new ExternalStorageItem("External memory");
            // todo imageId = R.drawable.folder_external;
        }else
        if (folderPath.contains(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath())) {
            imageId = R.drawable.picker_folder_music;
        } else if (folderPath.contains(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath())) {
            imageId = R.drawable.picker_folder_pictures;
        } else if (folderPath.contains(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath())) {
            imageId = R.drawable.picker_folder_download;
        } else //if (Build.VERSION.SDK_INT >= 19 && folderPath.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath())) {
            // todo imageId = R.drawable.folder_docs;
        //} else
            if (folderPath.contains(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath())) {
            imageId = R.drawable.picker_folder_video;
        } else if (folderPath.contains(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath())) {
            imageId = R.drawable.picker_folder_camera;
        } else {

            folderPath = folderPath.toLowerCase();
            if (folderPath.contains("music") ) {
                imageId = R.drawable.picker_folder_music;
            } else if (folderPath.contains("picture") || folderPath.contains("image") || folderPath.contains("photo")) {
                imageId = R.drawable.picker_folder_pictures;
            } else if (folderPath.contains("download") ) {
                imageId = R.drawable.picker_folder_download;
            } else if (folderPath.contains("doc")) {
                // todo imageId = R.drawable.folder_docs;
            } else if (folderPath.contains("movie") || folderPath.contains("video")) {
                imageId = R.drawable.picker_folder_video;
            }


        }

        return new FolderItem(file, imageId);
    }

    public static ExplorerItem getItem(File file, boolean selected) {
        return file.isDirectory()? getFolderItem(file):getFileItem(file,selected);
    }
}
