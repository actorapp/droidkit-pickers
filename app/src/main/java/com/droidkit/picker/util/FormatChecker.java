package com.droidkit.picker.util;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class FormatChecker {
    public static final int FORMAT_UNKNOWN = 0;
    public static final int FORMAT_MUSIC = -1;
    public static final int FORMAT_PICTURE = -2;
    public static final int FORMAT_DOC = -3;
    public static final int FORMAT_RAR = -4;
    public static final int FORMAT_VIDEO = -5;
    public static final int FORMAT_APK = -6;
    public static final int FORMAT_ZIP = -7;
    public static final int FORMAT_XLS = -8;
    public static final int FORMAT_PPT = -9;
    public static final int FORMAT_CSV = -10;
    public static final int FORMAT_HTM = -11;
    public static final int FORMAT_HTML = -12;
    public static final int FORMAT_PDF = -13;



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
                        fileType.equals("txt") ||
                        fileType.equals("xlc") ||
                        fileType.equals("docx");
    }
    public static boolean checkPdf(String fileType) {
        return
                fileType.equals("pdf");
    }

    public static boolean checkPicture(String fileType) {
        return
                fileType.equals("jpg") ||
                        fileType.equals("png") ||
                        fileType.equals("psd");
    }

    public static boolean checkApk(String fileType) {
        return
                fileType.equals("exe") ||
                        fileType.equals("apk");
    }

    public static boolean checkRar(String fileType) {
        return
                fileType.equals("rar");
    }
    public static boolean checkZip(String fileType) {
        return
                        fileType.equals("zip");
    }
    public static boolean checkXls(String fileType) {
        return
                fileType.contains("xls");
    }
    public static boolean checkPpt(String fileType) {
        return
                fileType.equals("ppt");
    }
    public static boolean checkCsv(String fileType) {
        return
                fileType.equals("ppt");
    }
    public static boolean checkHtm(String fileType) {
        return
                fileType.equals("htm");
    }
    public static boolean checkHtml(String fileType) {
        return
                fileType.equals("ppt");
    }

    public static int getType(String fileType) {
        if (checkMusic(fileType)) {
            return FORMAT_MUSIC;
        }
        if (checkDoc(fileType)) {
            return FORMAT_DOC;
        }

        if (checkPdf(fileType)) {
            return FORMAT_PDF;
        }
        if (checkPicture(fileType)) {
            return FORMAT_PICTURE;
        }
        if (checkApk(fileType)) {
            return FORMAT_APK;
        }
        if (checkRar(fileType)) {
            return FORMAT_RAR;
        }
        if (checkZip(fileType)) {
            return FORMAT_ZIP;
        }
        if (checkXls(fileType)) {
            return FORMAT_XLS;
        }
        if(checkPpt(fileType)){
            return FORMAT_PPT;
        }
        if(checkCsv(fileType)){
            return FORMAT_CSV;
        }
        if(checkHtm(fileType)){
            return FORMAT_HTM;
        }
        if(checkHtml(fileType)){
            return FORMAT_HTML;
        }
        return FORMAT_UNKNOWN;
    }
}