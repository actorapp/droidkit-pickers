package com.droidkit.picker.picture;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.droidkit.file.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by kiolt_000 on 24/09/2014.
 */
public class PictureViewerItemFragment extends Fragment {
    private PicturePickerActivity pickerActivity;
    private View rootView;
    private String path;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_picture_viewer_item, null);

        path = getArguments().getString("path");

        final ImageView holder = (ImageView) rootView.findViewById(R.id.image);

        ImageLoader.getInstance().displayImage("file://"+path, holder);


        final View selectedView = rootView.findViewById(R.id.selected);
        selectedView.setSelected(pickerActivity.isSelected(path));

        selectedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean selected = pickerActivity.selectItem(path);
                selectedView.setSelected(selected);
            }
        });

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.pickerActivity = (PicturePickerActivity) activity;
    }
}
