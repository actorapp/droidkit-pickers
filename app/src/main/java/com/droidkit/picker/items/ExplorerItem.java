package com.droidkit.picker.items;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidkit.file.R;

import java.io.File;

/**
* Created by kiolt_000 on 14/09/2014.
*/
public class ExplorerItem {
    private String fileType = null;
    protected final File file;
    private boolean selected = false;
    private boolean enabled = true;
    private int imageId = 0;

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

    public String getSubtitle(Context context){
        return null;
    }

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
        ImageView holder = (ImageView) itemView.findViewById(R.id.image);
        holder.setScaleType(ImageView.ScaleType.CENTER);
        if(imageId!=0) {
            itemView.findViewById(R.id.image_fake).setVisibility(View.INVISIBLE);
            holder.setVisibility(View.VISIBLE);
            holder.setImageResource(imageId);
        }else{
            holder.setImageResource(R.drawable.picker_file);
            TextView formatHolder = (TextView) itemView.findViewById(R.id.image_fake);
            if(formatHolder!=null) {
                formatHolder.setVisibility(View.VISIBLE);
                formatHolder.setText(fileType);
            }
        }
    }

    public void bindData(View itemView){
        TextView titleView  = (TextView) itemView.findViewById(R.id.title);
        TextView subTitleView = (TextView) itemView.findViewById(R.id.subtitle);

        titleView.setEllipsize(TextUtils.TruncateAt.END);
        subTitleView.setVisibility(View.VISIBLE);

        titleView.setText(getTitle());
        subTitleView.setText(getSubtitle(itemView.getContext()));
        View selectedView = itemView.findViewById(R.id.selected);
        if(selectedView!=null)
            selectedView.setSelected(selected);

    }
}