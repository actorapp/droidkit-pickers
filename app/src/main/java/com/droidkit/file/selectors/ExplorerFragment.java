package com.droidkit.file.selectors;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.droidkit.file.R;
import com.droidkit.file.selectors.adapters.ExplorerAdapter;
import com.droidkit.file.selectors.adapters.WelcomeExplorerAdapter;
import com.droidkit.file.selectors.items.BadExternalStorageItem;
import com.droidkit.file.selectors.items.files.ArchiveFileItem;
import com.droidkit.file.selectors.items.files.DocFileItem;
import com.droidkit.file.selectors.items.ExplorerItem;
import com.droidkit.file.selectors.items.ExternalStorageItem;
import com.droidkit.file.selectors.items.files.ExecutableFileItem;
import com.droidkit.file.selectors.items.files.PictureFileItem;
import com.droidkit.file.selectors.items.folders.DownloadFolderItem;
import com.droidkit.file.selectors.items.folders.FolderItem;
import com.droidkit.file.selectors.items.HistoryItem;
import com.droidkit.file.selectors.items.folders.LockedFolderItem;
import com.droidkit.file.selectors.items.files.MusicFileItem;
import com.droidkit.file.selectors.items.folders.MusicFolderItem;
import com.droidkit.file.selectors.items.folders.PictureFolderItem;
import com.droidkit.file.selectors.items.StorageItem;
import com.droidkit.file.selectors.util.Converter;
import com.droidkit.file.selectors.util.DatabaseConnector;
import com.droidkit.file.selectors.util.FileOrderComparator;
import com.droidkit.file.selectors.util.FormatChecker;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExplorerFragment extends Fragment {

    protected static final String LOG_TAG = "FILE SELECTOR";
    protected View rootView;
    protected String path;
    private FileSelectorActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FileSelectorActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //if (savedInstanceState == null)
        {
            rootView = inflater.inflate(R.layout.fragment_file_selector, container, false);
            ListView list = (ListView) rootView.findViewById(R.id.list);
            Bundle bundle = getArguments();

            ArrayList<ExplorerItem> items = new ArrayList<ExplorerItem>();
            ExplorerAdapter adapter;
            if (bundle != null) {
                path = bundle.getString("path");

                Log.d(LOG_TAG, "Path: " + path);
                File f = new File(path);
                File[] fileList = f.listFiles();

                if (fileList == null) {
                    TextView statusView = (TextView) rootView.findViewById(R.id.status);
                    statusView.setVisibility(View.VISIBLE);
                    File external = Environment.getExternalStorageDirectory();
                    if (path.equals(external.getPath()))
                        statusView.setText(R.string.sdcard_empty);
                    else
                        statusView.setText(R.string.denied);

                    setTitle();
                    return rootView;
                } else {
                    if (fileList.length == 0) {

                        TextView statusView = (TextView) rootView.findViewById(R.id.status);
                        statusView.setVisibility(View.VISIBLE);
                        statusView.setText(R.string.empty);
                        return rootView;
                    }
                }

                Log.d(LOG_TAG, "Size: " + fileList.length);
                for (File file : fileList) {
                    ExplorerItem item = null;
                    if (file.isDirectory()) {
                        if (file.listFiles() == null) {
                            item = new LockedFolderItem(file);
                        } else {
                            item = getFolderItem(file);
                        }
                    } else {
                        item = getFileItem(file);
                    }
                    items.add(item);
                }

                Collections.sort(items, new FileOrderComparator());
                adapter = new ExplorerAdapter(getActivity(), items);

            } else {



                items.add(new StorageItem());
                Log.w("logtag", Environment.getExternalStorageState());
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED)) {
                    items.add(new BadExternalStorageItem());
                } else {
                    items.add(new ExternalStorageItem());
                }
                items.add(new HistoryItem());

                items.addAll(loadHistory());

                adapter = new WelcomeExplorerAdapter(getActivity(), items);

                path = "Select files";
            }

            list.setAdapter(adapter);
            list.setOnItemClickListener((FileSelectorActivity) getActivity());
            setTitle();
        }

        return rootView;
    }

    private ExplorerItem getFolderItem(File file) {
        String filePath = file.getPath();
        return Converter.getFolderItem(file);
    }

    private ExplorerItem getFileItem(File file) {
        ExplorerItem item = null;

        String fileName = file.getName();
        String fileType = "";
        String[] fileNameSplit = fileName.split("\\.");

        if (fileNameSplit.length > 1) {
            fileType = fileNameSplit[fileNameSplit.length - 1];
        } else {
            return new ExplorerItem(file, activity.isSelected(file.getPath()), fileType);
        }
        return Converter.getFileItem(file, activity.isSelected(file.getPath()), fileType);
    }

    private ArrayList<ExplorerItem> loadHistory() {
        ArrayList<ExplorerItem> history = new ArrayList<ExplorerItem>();

        ArrayList<String> pathesFromDB = DatabaseConnector.getHistory(activity);
        for(String pathFromDB: pathesFromDB){
            history.add(getFileItem(new File(pathFromDB)));
        }

        return history;
    }


    private void setTitle() {
        getActivity().getActionBar().setTitle(path);
    }

}