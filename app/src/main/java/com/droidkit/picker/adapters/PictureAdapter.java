package com.droidkit.picker.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidkit.file.R;
import com.droidkit.picker.SuperPickerActivity;
import com.droidkit.picker.items.ExplorerItem;
import com.droidkit.picker.items.PictureFolderItem;

import java.util.ArrayList;

/**
 * Created by kiolt_000 on 15/09/2014.
 */
public class PictureAdapter extends BaseAdapter {

    private final ArrayList<ExplorerItem> items;
    protected final SuperPickerActivity pickerActivity;

    public PictureAdapter(SuperPickerActivity activity, ArrayList<ExplorerItem> items) {
        this.pickerActivity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isEnabled(int position) {
        return getItem(position).isEnabled();
    }

    @Override
    public ExplorerItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ExplorerItem item = getItem(position);
        final View itemView;


        if(item instanceof PictureFolderItem){
            if (convertView != null && convertView.getTag().equals("picture_folder"))
                itemView = convertView;
            else
                itemView = View.inflate(pickerActivity, R.layout.item_picture_folder, null);

            itemView.setTag("picture_folder");

        }else{
            if (convertView != null && convertView.getTag().equals("picture"))
                itemView = convertView;
            else
                itemView = View.inflate(pickerActivity, R.layout.item_picture, null);

            itemView.setTag("picture");
        }


        TextView titleView = (TextView) itemView.findViewById(R.id.title);
        TextView subTitleView = (TextView) itemView.findViewById(R.id.subtitle);
        View selectedView = itemView.findViewById(R.id.selected);




        titleView.setText(item.getTitle());
        subTitleView.setText(item.getSubtitle());

        item.bindImage(itemView);
        item.bindData(itemView);

        if(selectedView!=null) {
            selectedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickerActivity.selectItem(item, itemView);
                }
            });
        }
        return itemView;
    }
}