package com.droidkit.picker.picture;

import android.app.Fragment;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.droidkit.file.R;
import com.droidkit.picker.SuperPickerActivity;
import com.droidkit.picker.items.ExplorerItem;
import com.droidkit.picker.util.DatabaseConnector;

public class PicturePickerActivity extends SuperPickerActivity {



    @Override
    protected Fragment getWelcomeFragment() {
        return new PicturePickerFragment();
    }

    @Override
    protected void save() {

       // DatabaseConnector.save(this, selectedItems);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {

        ExplorerItem item = (ExplorerItem) parent.getItemAtPosition(position);

        if (item.isDirectory()) {
            String path = item.getPath();
            Bundle bundle = new Bundle();
            bundle.putString("path", path);
            bundle.putString("path_name", item.getTitle());

            Fragment fragment = new PicturePickerFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.animator.fragment_explorer_enter, R.animator.fragment_explorer_exit,
                            R.animator.fragment_explorer_return, R.animator.fragment_explorer_out)
                    .replace(R.id.container, fragment)
                    .addToBackStack(path)
                    .commit();
        } else {

            String path = item.getPath();
            Bundle bundle = new Bundle();
            bundle.putString("path", path);

            Fragment fragment = new PictureFullFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.animator.fragment_explorer_enter, R.animator.fragment_explorer_exit,
                            R.animator.fragment_explorer_return, R.animator.fragment_explorer_out)
                    .replace(R.id.container, fragment)
                    .addToBackStack(path)
                    .commit();

        }
    }
}
