package com.droidkit.picker.picture;

import android.app.Fragment;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.droidkit.file.R;
import com.droidkit.picker.SuperPickerActivity;
import com.droidkit.picker.items.ExplorerItem;
import com.droidkit.picker.util.DatabaseConnector;

public class PicturePickerActivity extends SuperPickerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setIcon(R.drawable.bar_picker_icon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.camera:
                Toast.makeText(this, "Do we need it here?", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.picker_picture, menu);
        return true;
    }

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
                    // todo animate out
                    //.setCustomAnimations(R.animator.fragment_explorer_enter, R.animator.fragment_explorer_exit,
                      //      R.animator.fragment_explorer_return, R.animator.fragment_explorer_out)
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
                    // todo animate out
                   // .setCustomAnimations(R.animator.fragment_explorer_enter, R.animator.fragment_explorer_exit,
                    //        R.animator.fragment_explorer_return, R.animator.fragment_explorer_out)
                    .replace(R.id.container, fragment)
                    .addToBackStack(path)
                    .commit();

        }
    }
}
