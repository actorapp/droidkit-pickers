package com.droidkit.picker.picture;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droidkit.file.R;
import com.droidkit.picker.items.ExplorerItem;

import java.io.File;
import java.util.ArrayList;


public class PictureViewerFragment extends PicturePickerFragment {

    private File file;
    private ViewPager pager;
    private PictureViewerAdapter adapter;

    // todo more interactive
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_picture_viewer, null);
        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getActivity().getActionBar().setTitle(adapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        items = new ArrayList<ExplorerItem>();
        //todo: animate it here
        // todo: actors

        Bundle bundle = getArguments();


        path = bundle.getString("path");
        String selectedItem = bundle.getString("selectedItem");
        loadDirectory();
        adapter = new PictureViewerAdapter(pickerActivity, items);
        pager.setAdapter(adapter);

        for (int i = 0; i < items.size(); i++) {
            ExplorerItem item = items.get(i);
            if (item.getPath().equals(selectedItem)) {
                pager.setCurrentItem(i, false);
                getActivity().getActionBar().setTitle(adapter.getPageTitle(i));
                break;
            }
        }


        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.pickerActivity = (PicturePickerActivity) activity;
    }
}
