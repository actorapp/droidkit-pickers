package com.droidkit.picker.map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidkit.file.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class PlacesAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<MapItem> resultList;

    public PlacesAdapter(Context context, ArrayList<MapItem> items) {
        resultList = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public MapItem getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View itemView;
        if(convertView!=null){
            itemView = convertView;
        }else{
            itemView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_place,null);
        }

        TextView titleView = (TextView) itemView.findViewById(R.id.title);
        TextView subtitleView = (TextView) itemView.findViewById(R.id.subtitle);
        ImageView iconView = (ImageView) itemView.findViewById(R.id.icon);

        MapItem item = getItem(position);
        // iconView.setImageResource();
        // todo: bind image

        titleView.setText(item.name);
        subtitleView.setText(item.vicinity);



        return itemView;
    }


}