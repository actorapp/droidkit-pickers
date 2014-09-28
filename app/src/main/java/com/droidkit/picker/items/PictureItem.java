package com.droidkit.picker.items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.droidkit.file.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.ImageSizeUtils;

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
        if(isVideo()){
        View videoHolder = itemView.findViewById(R.id.video_holder);
        if(videoHolder!=null){
            videoHolder.setVisibility(View.VISIBLE);
        }
        }
    }

    @Override
    public void bindImage(final View itemView) {
        final ImageView holder = (ImageView) itemView.findViewById(R.id.image);
        try {
            int height = itemView.getMeasuredHeight();
            height = itemView.getHeight();
            height = itemView.getLayoutParams().height;
            ImageSize size = new ImageSize(height, height);

            ImageLoader.getInstance().loadImage("file://"+getPath(),size,new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    holder.setImageBitmap(bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
            // todo: actors
        } catch (Exception exp) {
            holder.setImageResource(android.R.drawable.stat_notify_error);
            // todo: image error
            holder.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    }

    public boolean isVideo() {
        return false;
    }
}