package com.droidkit.picker.picture;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.droidkit.file.R;
import com.droidkit.picker.SuperPickerActivity;
import com.droidkit.picker.items.ExplorerItem;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by kiolt_000 on 24/09/2014.
 */
public class PictureViewerAdapter extends FragmentPagerAdapter {
    private final ArrayList<ExplorerItem> items;
    private SuperPickerActivity pickerActivity;

    public PictureViewerAdapter(SuperPickerActivity pickerActivity, ArrayList<ExplorerItem> items){
        super(pickerActivity.getFragmentManager());
        this.pickerActivity = pickerActivity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position+1 + " of " + getCount();
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new PictureViewerItemFragment();
        Bundle bundle = new Bundle();
                bundle.putString("path", items.get(i).getPath());
        fragment.setArguments(bundle);
        return fragment;
    }



}
