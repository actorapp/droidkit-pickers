package com.droidkit.picker.items;

import android.view.View;
import android.widget.TextView;

import com.droidkit.file.R;

import java.io.File;

/**
 * Created by kiolt_000 on 01/10/2014.
 */
public class BackItem extends ExplorerItem {

    public BackItem() {
        super(new File(""),false, "..",R.drawable.picker_folder,true);
    }

    @Override
    public String getTitle() {
        return "..";
    }


    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void bindData(View itemView) {

        TextView titleView  = (TextView) itemView.findViewById(R.id.title);
        TextView subTitleView = (TextView) itemView.findViewById(R.id.subtitle);

        subTitleView.setVisibility(View.GONE);

        titleView.setText(getTitle());
    }
}
