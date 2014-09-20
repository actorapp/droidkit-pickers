package com.droidkit.picker.util;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class FormatChecker {
    public static final int FORMAT_UNKNOWN = 0;
    public static final int FORMAT_MUSIC = -1;
    public static final int FORMAT_PICTURE = -2;
    public static final int FORMAT_DOC = -3;
    public static final int FORMAT_ARCHIVE = -4;
    public static final int FORMAT_VIDEO = -5;
    public static final int FORMAT_EXECUTABLE = -6;

    //todo implement regex
    public static boolean checkMusic(String fileType) {
        return
                fileType.equals("mp3") ||
                        fileType.equals("ogg") ||
                        fileType.equals("flac") ||
                        fileType.equals("alac") ||
                        fileType.equals("m3u");
    }

    public static boolean checkDoc(String fileType) {
        return
                fileType.equals("doc") ||
                        fileType.equals("pdf") ||
                        fileType.equals("txt") ||
                        fileType.equals("xlc") ||
                        fileType.equals("docx");
    }

    public static boolean checkPicture(String fileType) {
        return
                fileType.equals("jpg") ||
                        fileType.equals("png") ||
                        fileType.equals("psd");
    }

    public static boolean checkExecutable(String fileType) {
        return
                fileType.equals("exe") ||
                        fileType.equals("apk");
    }

    public static boolean checkArchive(String fileType) {
        return
                fileType.equals("rar") ||
                        fileType.equals("zip") ||
                        fileType.equals("7z");
    }


    public static int getType(String fileType) {
        if (checkMusic(fileType)) {
            return FORMAT_MUSIC;
        }
        if (checkDoc(fileType)) {
            return FORMAT_DOC;
        }
        if (checkPicture(fileType)) {
            return FORMAT_PICTURE;
        }
        if (checkExecutable(fileType)) {
            return FORMAT_EXECUTABLE;
        }
        if (checkArchive(fileType)) {
            return FORMAT_ARCHIVE;
        }
        return FORMAT_UNKNOWN;
    }
}