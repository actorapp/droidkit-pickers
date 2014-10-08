package com.droidkit.picker.file.search;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by kiolt_000 on 07/10/2014.
 */
public abstract class SearchTask extends AsyncTask<Void,Void,ArrayList<File>> {

    private final File root;
    private final String strongRegex;
    private final ArrayList<File> foundItems;
    private final ArrayList<File> index;
    private final String weakRegex;
    private Handler handler;

    public SearchTask(File searchRoot, String searchQuery, ArrayList<File> index) {
        this.root = searchRoot;
        String tempRegex = searchQuery.toLowerCase();
        String[] splitedTempRegex = tempRegex.split("\\s+");
        ArrayList<String> filtered = new ArrayList<String>();
        for (String s : splitedTempRegex) {
            if (s != null && !s.equals("")) {
                filtered.add(s);
            }
        }
        tempRegex = filtered.toString()
                .replaceAll("\\[", "(").replaceAll("]", ")")
                .replaceAll(",", "|")
                .replaceAll("\\s+", "")
                .replaceAll("\\.","\\\\.");
        this.strongRegex = "(((.*)(\\s+))|(^))"+tempRegex + ".*";
        this.weakRegex = ".*"+tempRegex+".*";
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
            compare(file);
        }
        if (isCancelled()) {
            return null;
        } else
            Log.i("Searching", "Scanning ended. " + foundItems.size() + " items found");
        return foundItems;
    }

    private void compare(File file) {
        if (root == null || file.getPath().contains(root.getPath())) {

            String name = file.getName().toLowerCase();
            if (//name.matches(strongRegex) ||
                    name.matches(weakRegex)) {
                foundItems.add(file);
            }
        }
    }

    @Override
    protected final void onPostExecute(ArrayList<File> files) {
        onSearchEnded(files);
    }

    protected abstract void onSearchEnded(ArrayList<File> files);
}