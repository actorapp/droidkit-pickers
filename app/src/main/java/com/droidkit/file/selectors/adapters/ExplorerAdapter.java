package com.droidkit.file.selectors.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidkit.file.R;
import com.droidkit.file.selectors.items.ExplorerItem;
import com.droidkit.file.selectors.items.HistoryItem;

import java.util.ArrayList;

/**
* Created by kiolt_000 on 14/09/2014.
*/
public class ExplorerAdapter extends BaseAdapter {

    private final ArrayList<ExplorerItem> items;
    protected final Context context;

    public ExplorerAdapter(Context context, ArrayList<ExplorerItem> items) {
        this.context = context;
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

        View itemView;
        if (convertView == null)
            itemView = View.inflate(context, R.layout.item_folder, null);
        else
            itemView = convertView;

        ExplorerItem item =  getItem(position);


        TextView titleView = (TextView) itemView.findViewById(R.id.title);
        TextView subTitleView = (TextView) itemView.findViewById(R.id.subtitle);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.image);
        View selectedView = itemView.findViewById(R.id.selected);


        titleView.setText(item.getTitle());
        if (item.isDirectory()) {
            subTitleView.setVisibility(View.GONE);
        } else {
            subTitleView.setText(item.getSubtitle());

            subTitleView.setVisibility(View.VISIBLE);
        }
        imageView.setImageResource(item.getImage());

        if (item.isSelected()) {
            selectedView.setVisibility(View.VISIBLE);
        } else
            selectedView.setVisibility(View.GONE);
        itemView.setTag("item");
        return itemView;
    }
}
