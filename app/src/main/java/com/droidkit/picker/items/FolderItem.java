package com.droidkit.picker.items;

import android.view.View;
import android.widget.TextView;

import com.droidkit.file.R;

import java.io.File;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class FolderItem extends ExplorerItem {

    public FolderItem(File file) {
        super(file,false, null,R.drawable.picker_folder, true);

    }

    public FolderItem(String path) {
        super(new File(path),false, null,R.drawable.picker_folder, true);
    }



    public FolderItem(File file, int imageId) {

        super(file, false, null, imageId, true);
    }

    public FolderItem(File file, int imageId, boolean locked) {
        super(file, false, null, imageId, !locked);
    }

    @Override
    public String getSubtitle() {
        return null;
    }



    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public void bindData(View itemView) {
        super.bindData(itemView);

        TextView subTitleView = (TextView) itemView.findViewById(R.id.subtitle);

        subTitleView.setVisibility(View.GONE);
    }
}