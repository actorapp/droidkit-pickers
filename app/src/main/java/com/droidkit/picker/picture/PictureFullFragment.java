package com.droidkit.picker.picture;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.droidkit.file.R;
import com.droidkit.picker.SuperPickerActivity;

import java.io.File;

/**
 * Created by kiolt_000 on 15/09/2014.
 */
public class PictureFullFragment extends Fragment {

    private View rootView;
    private File file;
    private SuperPickerActivity pickerActivity;

    // todo more interactive
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_picture_full, null);
        final ImageView holder = (ImageView) rootView.findViewById(R.id.image);
        final String path = getArguments().getString("path");
        file = new File(path);

        // todo: actors
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... voids) {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inDither = true;
                    options.outHeight = 100;
                    options.outWidth = 100;
                    return BitmapFactory.decodeFile(path, options);
                }catch (Exception exp) {

                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    holder.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    holder.setImageBitmap(bitmap);
                }
            }
        }.execute();
        getActivity().getActionBar().setTitle(file.getName());

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
        this.pickerActivity = (SuperPickerActivity) activity;
    }
}
