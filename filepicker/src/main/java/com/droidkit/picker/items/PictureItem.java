package com.droidkit.picker.items;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
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

    public PictureItem(File file, boolean selected, String fileType) {
        super(file, selected, fileType);
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
    public String getSubtitle(Context context) {
        return null;
    }

    @Override
    public void bindImage(final View itemView) {
        final ImageView holder = (ImageView) itemView.findViewById(R.id.image);
        try {


            // todo: actors
        } catch (Exception exp) {

        }

        super.bindImage(itemView);
    }

    public boolean isVideo() {
        return false;
    }
}