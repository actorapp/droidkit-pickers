package com.droidkit.picker.items;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidkit.file.R;

import java.io.File;

/**
* Created by kiolt_000 on 14/09/2014.
*/
public abstract class ExplorerItem {
    private String fileType = null;
    protected final File file;
    private boolean selected = false;
    private boolean enabled = true;
    private int imageId = 0;// todo  R.drawable.file_unknown;

    public ExplorerItem(File file) {
        this.file = file;
    }

    public ExplorerItem(File file, boolean selected) {
        this(file);
        this.selected = selected;
    }

    public ExplorerItem(File file, boolean selected, String fileType) {
        this(file, selected);
        this.fileType = fileType;
    }

    public ExplorerItem(String path) {
        this(new File(path), false);
    }

    public ExplorerItem(File file, boolean selected, String fileType, int imageId, boolean enabled) {
        this.file = file;
        this.selected = selected;
        this.fileType = fileType;
        this.imageId = imageId;
        this.enabled = enabled;
    }


    public String getTitle() {
        return file.getName();
    }

    public abstract String getSubtitle();

    public int getImage() {
        return imageId;
    }

    public String getPath() {
        return file.getPath();
    }

    public boolean isDirectory() {
        return file.isDirectory();
    }

    public File getFile() {
        return file;
    }

    public String getFileType() {
        return fileType;
    }

    public boolean isEnabled() {
        return enabled;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void bindImage(View itemView) {
        //todo: actors
        if(imageId!=0) {
            itemView.findViewById(R.id.image_fake).setVisibility(View.INVISIBLE);
            ImageView holder = (ImageView) itemView.findViewById(R.id.image);
            holder.setVisibility(View.VISIBLE);
            holder.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.setImageResource(imageId);
        }else{
            itemView.findViewById(R.id.image).setVisibility(View.INVISIBLE);
            TextView holder = (TextView) itemView.findViewById(R.id.image_fake);
            holder.setVisibility(View.VISIBLE);
            holder.setText(fileType);
        }
    }

    public void bindData(View itemView){
        TextView titleView  = (TextView) itemView.findViewById(R.id.title);
        TextView subTitleView = (TextView) itemView.findViewById(R.id.subtitle);
        titleView.setText(getTitle());
        subTitleView.setText(getSubtitle());
        View selectedView = itemView.findViewById(R.id.selected);
        if(selectedView!=null)
            selectedView.setSelected(selected);

    }
}