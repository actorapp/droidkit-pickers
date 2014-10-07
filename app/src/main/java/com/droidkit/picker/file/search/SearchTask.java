package com.droidkit.picker.file.search;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by kiolt_000 on 07/10/2014.
 */
public abstract class SearchTask extends AsyncTask<Void,Void,ArrayList<File>> {

    private final File root;
    private final String query;
    private final ArrayList<File> foundItems;
    private final ArrayList<File> index;
    private Handler handler;

    public SearchTask(File searchRoot, String searchQuery, ArrayList<File> index) {
        this.root = searchRoot;
        this.query = searchQuery.toLowerCase();
        this.index = index;
        handler = new Handler();
        foundItems = new ArrayList<File>();
    }


    @Override
    protected final ArrayList<File> doInBackground(Void... voids) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //onPreStart();
            }
        });


        handler.post(new Runnable() {
            @Override
            public void run() {
                //onSearchStarted();
            }
        });
        Log.i("Searching", "Scanning started. Root path: " + root);
        for (File file : index) {
            if(file.getName().toLowerCase().contains(query)){
                foundItems.add(file);
            }
        }
        if (isCancelled()) {
            return null;
        } else
            Log.i("Searching", "Scanning ended. " + foundItems.size() + " items found");
        return foundItems;
    }


    @Override
    protected final void onPostExecute(ArrayList<File> files) {
        onSearchEnded(files);
    }

    protected abstract void onSearchEnded(ArrayList<File> files);
}