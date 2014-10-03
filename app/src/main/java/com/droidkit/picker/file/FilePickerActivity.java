package com.droidkit.picker.file;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;


import com.droidkit.file.R;
import com.droidkit.picker.SuperPickerActivity;
import com.droidkit.picker.items.BackItem;
import com.droidkit.picker.items.ExplorerItem;
import com.droidkit.picker.util.DatabaseConnector;

import java.util.ArrayList;

public class FilePickerActivity extends SuperPickerActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //searchEnable();
        findViewById(R.id.controllers).setVisibility(View.GONE);
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(true);
        // getActionBar().setIcon(null);
    }

    @Override
    protected Fragment getWelcomeFragment() {
        return new ExplorerFragment();
    }

    @Override
    protected void save() {

        DatabaseConnector.save(this, selectedItems);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {

        ExplorerItem item = (ExplorerItem) parent.getItemAtPosition(position);

        if(item instanceof BackItem){
            onBackPressed();
            return;
        }

        if (item.isDirectory()) {
            String path = item.getPath();
            Bundle bundle = new Bundle();
            bundle.putString("path", path);

            Fragment fragment = new ExplorerFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    // todo animate out
                    //     .setCustomAnimations(R.animator.fragment_explorer_enter, R.animator.fragment_explorer_exit,
                    //           R.animator.fragment_explorer_return, R.animator.fragment_explorer_out)
                    .replace(R.id.container, fragment)
                    .addToBackStack(path)
                    .commit();
        } else {

            selectItem(item,itemView);
            returnResult();
        }
    }

}