package com.droidkit.picker.items;

import android.view.View;
import android.widget.TextView;

import com.droidkit.file.R;

import java.io.File;

public class StorageItem extends ExplorerItem {
    private final String name;

    public StorageItem(String name) {
        super(new File("/"), false, "", R.drawable.picker_memory, true);
        this.name = name;
    }

    @Override
    public String getTitle() {
        return name;
    }



    @Override
    public void bindData(View itemView) {
        super.bindData(itemView);

        TextView subTitleView = (TextView) itemView.findViewById(R.id.subtitle);

        subTitleView.setVisibility(View.GONE);
    }
}
