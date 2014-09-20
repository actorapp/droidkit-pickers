package com.droidkit.picker.items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.droidkit.file.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by kiolt_000 on 16/09/2014.
 */
public class PictureFolderItem extends FolderItem {
    private final String bucketName;
    private final int bucketId;
    ArrayList<String> imgUris = new ArrayList<String>();
    private int imgCounter = 0;

    public PictureFolderItem(int bucketId, String bucketName) {
        super("");
        this.bucketId = bucketId;
        this.bucketName = bucketName;
    }

    @Override
    public String getTitle() {
        return bucketName;
    }

    @Override
    public String getSubtitle() {
        return imgCounter + " pictures";
    }

    @Override
    public String getPath() {
        return ""+ bucketId;
    }

    @Override
    public void bindImage(View itemView) {

        final ImageView holder = (ImageView) itemView.findViewById(R.id.image);
        if (holder != null) {
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
                        return BitmapFactory.decodeFile(imgUris.get(0), options);
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
        }
         // super.bindImage(itemView);
    }

    public void putImage(String imgUri) {
        if(imgUris.size()<1)
            imgUris.add(imgUri);
        imgCounter++;
    }
}
