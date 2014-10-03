package com.droidkit.picker.file;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.droidkit.file.R;
import com.droidkit.picker.SuperPickerActivity;
import com.droidkit.picker.items.BackItem;
import com.droidkit.picker.items.ExplorerItem;
import com.droidkit.picker.items.ExternalStorageItem;
import com.droidkit.picker.items.FileItem;
import com.droidkit.picker.items.FolderItem;
import com.droidkit.picker.items.StorageItem;
import com.droidkit.picker.util.Converter;
import com.droidkit.picker.util.DatabaseConnector;
import com.droidkit.picker.util.FileOrderComparator;

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
    private SuperPickerActivity pickerActivity;
    private ArrayList<ExplorerItem> items;
    private TextView statusView;
    private ListView list;
    private String title;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.pickerActivity = (SuperPickerActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //if (savedInstanceState == null)
        //todo: animate it here
        {
            rootView = inflater.inflate(R.layout.fragment_file_picker, container, false);
            list = (ListView) rootView.findViewById(R.id.list);
            Bundle bundle = getArguments();
            statusView = (TextView) rootView.findViewById(R.id.status);

            items = new ArrayList<ExplorerItem>();
            ExplorerAdapter adapter;
            if (bundle != null) {
                path = bundle.getString("path");



                Log.d(LOG_TAG, "Path: " + path);
                File currentPathFile = new File(path);
                File[] fileList = currentPathFile.listFiles();
                title = currentPathFile.getPath();
                if(title.contains(Environment.getExternalStorageDirectory().getPath())){
                    title = title.replace(Environment.getExternalStorageDirectory().getPath(),"");
                }
                if(title.length()>0 && title.toCharArray()[0] == '/'){
                    title = title.substring(1);
                }

                if (path.equals(Environment.getExternalStorageDirectory().getPath())) {
                    if(Environment.isExternalStorageEmulated()){
                        title = getString(R.string.picker_files_memory_phone);
                    }else
                        title = getString((R.string.picker_files_memory_external));// todo if its emulated?
                } else if (path.equals("/"))
                    title = getString(R.string.picker_files_memory_phone);

                if (fileList == null) {
                    statusView.setVisibility(View.VISIBLE);
                    File external = Environment.getExternalStorageDirectory();
                    if (path.equals(external.getPath()))
                        statusView.setText(R.string.picker_files_memory_external_error);
                    else
                        statusView.setText(R.string.picker_files_denied);

                    return rootView;
                } else {
                    if (fileList.length == 0) {

                        statusView.setVisibility(View.VISIBLE);
                        statusView.setText(R.string.picker_files_directory_empty);
                        return rootView;
                    }
                }

                Log.d(LOG_TAG, "Size: " + fileList.length);

                for (File file : fileList) {
                    putItem(file);
                }
                Collections.sort(items, new FileOrderComparator());

                insertBack();
                adapter = new ExplorerAdapter(getActivity(), items);

            } else {


//                items.add(new StorageItem(getActivity()));
                adapter = new WelcomeExplorerAdapter(getActivity(), items);

                String externalStorageState = Environment.getExternalStorageState();
                Log.w(LOG_TAG, externalStorageState);
                if (
                        externalStorageState.equals(Environment.MEDIA_REMOVED)
                                || externalStorageState.equals(Environment.MEDIA_BAD_REMOVAL)
                                || externalStorageState.equals(Environment.MEDIA_UNKNOWN)
                                || externalStorageState.equals(Environment.MEDIA_UNMOUNTED)
                                || externalStorageState.equals(Environment.MEDIA_UNMOUNTABLE)
                                || externalStorageState.equals(Environment.MEDIA_SHARED)
                                || externalStorageState.equals(Environment.MEDIA_NOFS)
                        ) {
                     items.add(new StorageItem(getString(R.string.picker_files_memory_phone)));
                } else {/*
                    File cameraFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    if(cameraFile.exists()) {
                       items.add(new FolderItem(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),R.drawable.picker_folder_camera,getString(R.string.picker_files_camera)));
                    }*/
                    putItem(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
                    putItem(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
                    putItem((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)));
                    putItem(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
                    putItem((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)));
                    if (Environment.isExternalStorageEmulated()) {

                        items.add(new ExternalStorageItem(getString(R.string.picker_files_memory_phone),R.drawable.picker_memory));
                    }else
                        items.add(new ExternalStorageItem(getString(R.string.picker_files_memory_external),R.drawable.picker_sdcard));


                    if (Build.VERSION.SDK_INT >= 19) {
                        // putItem(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
                        // even on sdk>19 documents folder does not work.
                    }
                }
                path = "";
                title = getString(R.string.picker_files_activity_title);

                ArrayList<ExplorerItem> historyItems = loadHistory();
                if (!historyItems.isEmpty()) {
                    items.addAll(historyItems);
                }

            }

            list.setAdapter(adapter);
            list.setOnItemClickListener((SuperPickerActivity) getActivity());
        }
        pickerActivity.updateCounter();

        return rootView;
    }

    private void insertBack() {
        items.add(0, new BackItem());
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.picker_file, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        switch (id){
            case R.id.search:
                    Bundle bundle = new Bundle();
                    bundle.putString("root", path );
                    SearchFileFragment searchFragment = new SearchFileFragment();
                    searchFragment.setArguments(bundle);
                    pickerActivity.getFragmentManager().beginTransaction()
                            //.setCustomAnimations(R.animator.fragment_explorer_welcome_enter, R.animator.fragment_explorer_welcome_exit)
                            .replace(R.id.container, searchFragment)
                            .addToBackStack("search")
                            .commit();
                    //pickerActivity.searchDisable();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void putItem(File file){

        ExplorerItem item;
        if (file.isDirectory()) {
            item = getFolderItem(file);

        } else {
            item = getFileItem(file);
        }
        if(item!=null)
            items.add(item);
    }
    private ExplorerItem getFolderItem(File file) {
        return Converter.getFolderItem(file);
    }

    private ExplorerItem getFileItem(File file) {
        return Converter.getFileItem(file, pickerActivity.isSelected(file.getPath()));
    }

    private ArrayList<ExplorerItem> loadHistory() {
        ArrayList<ExplorerItem> history = new ArrayList<ExplorerItem>();

        ArrayList<String> pathesFromDB = DatabaseConnector.getHistory(pickerActivity);
        for (String pathFromDB : pathesFromDB) {
            File historyFile = new File(pathFromDB);
            if(historyFile.exists())
                history.add(getFileItem(historyFile));
        }

        return history;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle();
        pickerActivity.setFragment(this);
        pickerActivity.invalidateOptionsMenu();
    }

    private void setTitle() {
        getActivity().getActionBar().setTitle(title);
    }

}