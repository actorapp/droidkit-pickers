package com.droidkit.picker.items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.droidkit.file.R;

import java.io.File;

/**
 * Created by kiolt_000 on 15/09/2014.
 */
public class PictureItem extends FileItem {

    public PictureItem(File file, boolean selected) {
        super(file, selected);
    }


    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public void bindData(View itemView) {
        super.bindData(itemView);
        View stroke = itemView.findViewById(R.id.selected_stroke);
        if (stroke != null) {
            stroke.setSelected(isSelected());
        }
    }

    @Override
    public void bindImage(final View itemView) {
        final ImageView holder = (ImageView) itemView.findViewById(R.id.image);
        try {
            holder.setImageResource(R.drawable.user_placeholder);
            new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... voids) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inDither = true;
                        options.outHeight = 1;
                        options.outWidth = 1;
                        options.inTargetDensity = 1;
                        options.inSampleSize = 10;
                        options.inScaled = true;
                        return BitmapFactory.decodeFile(getPath(), options);
                    }catch (Exception exp) {

                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if (bitmap != null) {
                        holder.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        holder.setImageBitmap(bitmap);
                    }
                }
            }.execute();

            // todo: actors
        } catch (Exception exp) {
            holder.setImageResource(android.R.drawable.stat_notify_error);
            // todo: image error
            holder.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    }
}