package com.droidkit.file.selectors.util;

import android.os.Environment;

import com.droidkit.file.selectors.items.ExplorerItem;
import com.droidkit.file.selectors.items.files.ArchiveFileItem;
import com.droidkit.file.selectors.items.files.DocFileItem;
import com.droidkit.file.selectors.items.files.ExecutableFileItem;
import com.droidkit.file.selectors.items.files.MusicFileItem;
import com.droidkit.file.selectors.items.files.PictureFileItem;
import com.droidkit.file.selectors.items.files.VideoFileItem;
import com.droidkit.file.selectors.items.folders.CameraFolderItem;
import com.droidkit.file.selectors.items.folders.DocFolderItem;
import com.droidkit.file.selectors.items.folders.DownloadFolderItem;
import com.droidkit.file.selectors.items.folders.FolderItem;
import com.droidkit.file.selectors.items.folders.MusicFolderItem;
import com.droidkit.file.selectors.items.folders.PictureFolderItem;
import com.droidkit.file.selectors.items.folders.VideoFolderItem;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class Converter {

    public static ExplorerItem getFileItem(File file, boolean selected, String fileType) {

        switch (FormatChecker.getType(fileType)) {
            case FormatChecker.FORMAT_MUSIC:
                return new MusicFileItem(
                        file,
                        selected,
                        fileType
                );
            case FormatChecker.FORMAT_PICTURE:
                return new PictureFileItem(
                        file,
                        selected,
                        fileType
                );
            case FormatChecker.FORMAT_DOC:
                return new DocFileItem(
                        file,
                        selected,
                        fileType
                );
            case FormatChecker.FORMAT_ARCHIVE:
                return new ArchiveFileItem(
                        file,
                        selected,
                        fileType
                );
            case FormatChecker.FORMAT_EXECUTABLE:
                return new ExecutableFileItem(
                        file,
                        selected,
                        fileType
                );
            case FormatChecker.FORMAT_VIDEO:
                return new VideoFileItem(
                        file,
                        selected,
                        fileType
                );
            default:
                return new ExplorerItem(
                        file,
                        selected,
                        fileType);
        }

    }

    public static FolderItem getFolderItem(File file) {
        String filePath = file.getPath();
        if (filePath.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath())) {
            return new MusicFolderItem();
        }

        if (filePath.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath())) {
            return new PictureFolderItem();
        }


        if (filePath.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath())) {
            return new DownloadFolderItem();
        }
        /*
        if (filePath.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath())) {
            return new DocFolderItem();
        }
        */
        if (filePath.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath())) {
            return new VideoFolderItem();
        }

        if (filePath.contains(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath())) {
            return new CameraFolderItem();
        }

        // todo: check content inside

        return new FolderItem(file);
    }
}
