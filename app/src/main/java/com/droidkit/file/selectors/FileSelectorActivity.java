package com.droidkit.file.selectors;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;


import com.droidkit.file.R;
import com.droidkit.file.selectors.items.ExplorerItem;
import com.droidkit.file.selectors.util.DatabaseConnector;

import java.util.ArrayList;

public class FileSelectorActivity extends Activity implements AdapterView.OnItemClickListener {

    ArrayList<String> selectedItems = new ArrayList<String>();

    private MenuItem applyMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_selector);
        if (savedInstanceState == null)
        {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ExplorerFragment())
                    .commit();
        }
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.file_selector, menu);
        applyMenuItem = menu.getItem(0);
        applyMenuItem.setActionView(R.layout.action_bar_apply);
        applyMenuItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(applyMenuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.select:
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "Hello");

                returnIntent.putExtra("files",selectedItems);
                setResult(RESULT_OK, returnIntent);
                DatabaseConnector.save(this, selectedItems);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ExplorerItem item = (ExplorerItem) parent.getItemAtPosition(position);


        if (item.isDirectory()) {
            String path = item.getPath();
            Bundle bundle = new Bundle();
            bundle.putString("path", path);

            Fragment fragment = new ExplorerFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(path)
                    .commit();
        } else {
            item.setSelected(!selectedItems.contains(item.getPath()));
            if(item.isSelected()) {
                selectedItems.add(item.getPath());
                view.findViewById(R.id.selected).setVisibility(View.VISIBLE);
            }else{
                selectedItems.remove(item.getPath());
                view.findViewById(R.id.selected).setVisibility(View.INVISIBLE);
            }
            updateActionBar();
        }
    }

    private void updateActionBar() {


        TextView textView = (TextView) applyMenuItem.getActionView().findViewById(R.id.text);
        textView.setText(""+selectedItems.size());


    }

    public boolean isSelected(String path) {
        return selectedItems.contains(path);
    }


}
