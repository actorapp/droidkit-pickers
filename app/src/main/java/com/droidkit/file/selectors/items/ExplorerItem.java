package com.droidkit.file.selectors.items;

import com.droidkit.file.R;

import java.io.File;

/**
* Created by kiolt_000 on 14/09/2014.
*/
public class ExplorerItem {
    protected String fileType;
    protected File file;
    protected boolean selected;


    public ExplorerItem(File file, boolean selected){
        this.file = file;
        this.selected = selected;
    }

    public ExplorerItem(String path) {
        this(new File(path),false);
    }

    public ExplorerItem(File file, boolean selected, String fileType) {
        this(file, selected);
        this.fileType = fileType;
    }

    public ExplorerItem(File file) {
        this.file = file;
    }


    public String getTitle(){
        return file.getName();
    }
    public String getSubtitle(){
            return String.valueOf(file.length()/1000)+"kb";
    }
    public int getImage() {
        return R.drawable.file;
    }

    public String getPath() {
        return file.getPath();
    }
    public boolean isDirectory(){
        return file.isDirectory();
    }
    public boolean isSelected(){
        return selected;
    }
    public void setSelected(boolean selected){
        this.selected = selected;
    }
    public File getFile() {
        return file;
    }

    public boolean isEnabled() {
        return true;
    }
}
