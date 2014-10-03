package com.droidkit.picker.file.search;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by kiolt_000 on 17/09/2014.
 */
public abstract class SearchTask extends AsyncTask<Void,File,Integer> {
    private final File root;
    private final String query;
    private Integer foundCount = 0;
    private Handler handler;

    public SearchTask(File searchRoot, String searchQuery) {
        this.root = searchRoot;
        this.query = searchQuery.toLowerCase();
        handler = new Handler();
    }


    @Override
    protected final Integer doInBackground(Void... voids) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onPreStart();
            }
        });

        try {
            Thread.sleep(350);
        } catch (InterruptedException e) {
            e.printStackTrace();
            if (isCancelled()) {
                return null;
            }
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                onSearchStarted();
            }
        });
        Log.i("Searching", "Scanning started. Root path: " + root);
        if (!root.getPath().equals("")) {
            scanFolder(root);
        } else {
            scanFolder(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
            scanFolder(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
            if (Build.VERSION.SDK_INT >= 19)
                scanFolder(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
            scanFolder(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        }
        if (isCancelled()) {
            return null;
        } else
            Log.i("Searching", "Scanning ended. " + foundCount + " items found");
        return foundCount;
    }

    private void scanFolder(File folder) {
        if (folder.getPath().contains("/sys")) {
            return;
        }

        if (folder.listFiles() == null || folder.getName().toCharArray()[0] == '.') {
            return;
        }
        if (folder.getName().toLowerCase().contains(query)) {
            onProgressUpdate(folder);
            foundCount++;
        }

        for (final File file : folder.listFiles()) {
            if (isCancelled()) {
                return;
            }
            if (file.isDirectory()) {
                scanFolder(file);
            } else {
                if (file.getName().toLowerCase().contains(query)) {
                    onProgressUpdate(file);
                    foundCount++;
                }
            }
        }
        Log.i("Searching", "Scanned: " + folder.getPath());
    }


    @Override
    protected void onProgressUpdate(final File... values) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                onItemFound(values[0]);
            }
        });

    }

    @Override
    protected final void onPostExecute(Integer result) {
        if (result == null) {
            Log.i("Searching", "Scanning canceled.");
        } else
            onSearchEnded(result);
    }

    public abstract void onPreStart();

    public abstract void onSearchStarted();

    public abstract void onItemFound(File file);

    public abstract void onSearchEnded(int foundCount);
}