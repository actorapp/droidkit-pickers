package com.droidkit.picker.items;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.droidkit.file.R;
import com.droidkit.picker.util.TrimmedTextView;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by kiolt_000 on 14/09/2014.
 */
public class FolderItem extends ExplorerItem {

    private String name;

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

    public FolderItem(File file, int imageId, String name) {
        super(file,false,"",imageId,true);
        this.name = name;
    }

    @Override
    public String getSubtitle() {
        return null;
    }

    @Override
    public String getTitle() {
        if(name!=null){
            return name;
        }
        return super.getTitle();
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

        TextView titleView = (TextView) itemView.findViewById(R.id.title);
        titleView.setEllipsize(null);
        long startTime = System.currentTimeMillis();
        SpannableStringBuilder text = new SpannableStringBuilder();
        text.append((getTitle()));
        //text.append(countFiles());
        titleView.setText(text);
        Log.d("Picker", "Time to create item: "+ (System.currentTimeMillis() - startTime));
    }

    CharSequence countFiles() {

        String[] list = getFile().list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {

                if(!file.exists() || file.list()==null)
                    return false;
                if(file.isDirectory() && (file.getName().charAt(0)=='.')){
                    return false;
                }
                return true;
            }
        });
        if(list!=null) {
            int count = list.length;
            String notSpanned = " (" + count + ")";
            /*SpannableString spannableString = new SpannableString(notSpanned);
            spannableString.setSpan( new ForegroundColorSpan(0xff888888), 0, spannableString.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/
            return notSpanned;
        }
        return "";
    }

    CharSequence ellipsizeTitle(String text) {
        SpannableString s = new SpannableString(text);
        s.setSpan(TrimmedTextView.EllipsizeRange.ELLIPSIS_AT_END, 0, s.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return s;
    }

}