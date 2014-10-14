package com.droidkit.filepicker.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.droidkit.pickers.Intents;
import com.droidkit.pickers.sample.R;

import java.util.ArrayList;


public class PickerSampleActivity extends Activity {

    private static final int ACTIVITY_REQUEST_FILES = 1;
    private static final int ACTIVITY_REQUEST_MAP = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_sample);

        findViewById(R.id.pickFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(Intents.pickFile(PickerSampleActivity.this), ACTIVITY_REQUEST_FILES);
            }
        });

        findViewById(R.id.pickLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(Intents.pickLocation(PickerSampleActivity.this), ACTIVITY_REQUEST_MAP);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ACTIVITY_REQUEST_FILES) {
                String res = "";
                ArrayList<String> files = data.getStringArrayListExtra("picked");
                if (!files.isEmpty())
                    for (int i = 0; i < files.size(); i++) {
                        res += "extra[picked][" + i + "]: " + files.get(i) + "\n";
                    }
                else {
                    res = "extra[picked].size() == 0";
                }
                ((TextView) findViewById(R.id.result)).setText(res);
            } else if (requestCode == ACTIVITY_REQUEST_MAP) {
                String res = "extra[latitude]: " + data.getDoubleExtra("latitude", 0) + "\n";
                res += "extra[longitude]: " + data.getDoubleExtra("longitude", 0) + "\n";
                res += "extra[street]: " + data.getStringExtra("street") + "\n";
                res += "extra[place]: " + data.getStringExtra("place");
                ((TextView) findViewById(R.id.result)).setText(res);
            }
        } else if (resultCode == RESULT_CANCELED) {
            ((TextView) findViewById(R.id.result)).setText("Selection cancelled");
        }
    }
}
