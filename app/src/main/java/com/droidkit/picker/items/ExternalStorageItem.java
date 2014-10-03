package com.droidkit.picker.items;

import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.droidkit.file.R;

import java.io.File;

/**
 * Created by kiolt_000 on 22/09/2014.
 */
public class ExternalStorageItem extends ExplorerItem {
    private final String name;

    public ExternalStorageItem(String name, int imageId) {
        super(Environment.getExternalStorageDirectory(),false, "", imageId,true);
        this.name = name;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubtitle() {
        return null;
    }


    @Override
    public void bindData(View itemView) {
        super.bindData(itemView);

        TextView subTitleView = (TextView) itemView.findViewById(R.id.subtitle);

        subTitleView.setVisibility(View.GONE);
    }
}
