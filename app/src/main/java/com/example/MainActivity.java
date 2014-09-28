package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.droidkit.file.R;
import com.droidkit.picker.file.FilePickerActivity;
import com.droidkit.picker.map.MapPickerActivity;
import com.droidkit.picker.picture.PicturePickerActivity;
import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executor;


public class MainActivity extends Activity  {

    private static final int ACTIVITY_REQUEST_FILES = 1;
    private static final int ACTIVITY_REQUEST_PICTURES = 2;
    private static final int ACTIVITY_REQUEST_MAP = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button selectFiles = (Button) findViewById(R.id.select_files);
        selectFiles.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, FilePickerActivity.class), ACTIVITY_REQUEST_FILES);
            }
        });
        Button selectImages = (Button) findViewById(R.id.select_images);
        selectImages.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, PicturePickerActivity.class), ACTIVITY_REQUEST_PICTURES);
            }
        });
        Button pickMap = (Button) findViewById(R.id.pick_location);
        pickMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(MainActivity.this, MapPickerActivity.class), ACTIVITY_REQUEST_MAP);
            }
        });
        File cacheDir = StorageUtils.getCacheDirectory(getBaseContext());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getBaseContext())
                .threadPriority(Thread.MAX_PRIORITY)
                .build();
        ImageLoader.getInstance().init(config);

    }




    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        TextView statusView = (TextView) findViewById(R.id.status);

        switch (requestCode) {
            case ACTIVITY_REQUEST_FILES:
            case ACTIVITY_REQUEST_PICTURES: {
                if (resultCode == RESULT_OK) {
                    statusView.setText(resultData.getStringExtra("result"));
                    String fileString = "";
                    ArrayList<String> files = resultData.getStringArrayListExtra("picked");
                    if (!files.isEmpty())
                        for (String file : files) {
                            fileString += file + "\n";
                        }
                    else {
                        fileString = "No file selected";
                    }
                    statusView.setText(fileString);

                }
                if (resultCode == RESULT_CANCELED) {
                    statusView.setText("canceled");
                }
            }
            break;
            case ACTIVITY_REQUEST_MAP:
                if (resultCode == RESULT_OK) {
                    LatLng geoData = new LatLng(
                            resultData.getDoubleExtra("latitude",0),
                            resultData.getDoubleExtra("longitude",0)
                    );
                    statusView.setText(geoData.toString());
                } else {
                    statusView.setText("canceled");
                }
                break;
        }
    }
}
