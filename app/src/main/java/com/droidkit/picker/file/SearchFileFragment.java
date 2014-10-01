package com.droidkit.picker.file;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.droidkit.file.R;
import com.droidkit.picker.SuperPickerActivity;
import com.droidkit.picker.file.search.SearchTask;
import com.droidkit.picker.items.FileItem;
import com.droidkit.picker.util.Converter;
import com.droidkit.picker.util.SearchViewHacker;

import java.io.File;
import java.util.ArrayList;


public class SearchFileFragment extends Fragment implements AbsListView.OnScrollListener {
    private View rootView;
    private String lastTitle;
    private ArrayList<FileItem> items = new ArrayList<FileItem>();
    SearchTask task;
    private ListView listView;
    private ExplorerAdapter adapter;
    private String root;
    private TextView status;
    private View searchingProgressBar;
    private SuperPickerActivity pickerActivity;
    private SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_file_search, container, false);
        root = getArguments().getString("root");

//        ViewGroup searchContainer = (ViewGroup) rootView.findViewById(R.id.search_container);
        View contentContainer = rootView.findViewById(R.id.content_container);

        status = (TextView) rootView.findViewById(R.id.status);
        searchingProgressBar = rootView.findViewById(R.id.progressBar);
        listView = (ListView) contentContainer.findViewById(R.id.list);
        listView.setOnScrollListener(this);

        adapter = new ExplorerAdapter(pickerActivity, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(pickerActivity);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(300);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, -150, 0);
        translateAnimation.setDuration(450);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

//        searchContainer.startAnimation(translateAnimation);
        contentContainer.startAnimation(alphaAnimation);

//        for (int i = 0; i < searchContainer.getChildCount(); i++) {
//            View searchItemView = searchContainer.getChildAt(i);
//            searchItemView.startAnimation(alphaAnimation);
//        }

        getActivity().getActionBar().setTitle(R.string.picker_files_search_activity_title);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_search:

                searchView.setIconified(!searchView.isIconified());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.picker_search, menu);
        MenuItem searchMenuItem = menu.getItem(0);

        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setIconified(false);
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate != null) {
            // searchPlate.setBackgroundColor(Color.DKGRAY);
            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText != null) {
                searchText.setTextColor(Color.WHITE);
                searchText.setHintTextColor(Color.WHITE);
            }
        }

        SearchViewHacker.disableCloseButton(searchView);
        SearchViewHacker.disableMagIcon(searchView);
        SearchViewHacker.setIcon(searchView, R.drawable.bar_search);
        SearchViewHacker.setText(searchView, getResources().getColor(R.color.picker_file_searchbox_focused_color));
        SearchViewHacker.setEditText(searchView, R.drawable.search_text_box);
        SearchViewHacker.setHint(searchView, getString(R.string.picker_files_search_query_text), 0, getResources().getColor(R.color.picker_file_searchbox_hint_color), null);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                hideKeyBoard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                if (query.equals("")) {
                    return true;
                }

                if (task != null) {
                    task.cancel(true);
                    task = null;
                }
                task = new SearchTask(new File(root), query) {

                    @Override
                    public void onPreStart() {

                        status.setVisibility(View.GONE);
                        searchingProgressBar.setVisibility(View.VISIBLE);
                        listView.setAlpha(0.5f);
                        listView.setOnItemClickListener(null);
                    }

                    @Override
                    public void onSearchStarted() {
                        items.clear();
                        listView.setAlpha(1);
                        listView.setOnItemClickListener(pickerActivity);
                        adapter.notifyDataSetChanged();
                    }


                    @Override
                    public void onItemFound(File file) {

                        status.setVisibility(View.GONE);
                        searchingProgressBar.setVisibility(View.GONE);
                        items.add(Converter.getFileItem(file, pickerActivity.isSelected(file)));
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onSearchEnded(int foundCount) {
                        searchingProgressBar.setVisibility(View.GONE);
                        if (foundCount == 0) {
                            status.setVisibility(View.VISIBLE);
                            status.setText(R.string.picker_empty);
                        } else {

                        }

                    }
                };
                task.execute();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                    hideKeyBoard();
                    getActivity().onBackPressed();
                return false;
            }
        });
        searchView.requestFocusFromTouch();
        InputMethodManager inputMethodManager = (InputMethodManager) pickerActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void onPause() {
        super.onPause();

        hideKeyBoard();
        pickerActivity.getActionBar().setDisplayShowHomeEnabled(false);
        pickerActivity.getActionBar().setDisplayUseLogoEnabled(false);
        pickerActivity.getActionBar().setIcon(null);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        // pickerActivity.searchDisable();
        pickerActivity.setFragment(this);
        pickerActivity.invalidateOptionsMenu();
        pickerActivity.getActionBar().setDisplayShowHomeEnabled(true);
        pickerActivity.getActionBar().setDisplayUseLogoEnabled(true);
        pickerActivity.getActionBar().setIcon(R.drawable.bar_search);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.pickerActivity = (SuperPickerActivity) activity;
    }

    void hideKeyBoard(){
        searchView.clearFocus();
        pickerActivity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        View focusedView = pickerActivity.getCurrentFocus();
        if(focusedView!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) pickerActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        switch (i){
            case SCROLL_STATE_FLING:
                break;
            case SCROLL_STATE_IDLE:
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                hideKeyBoard();
                break;
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {

    }
}