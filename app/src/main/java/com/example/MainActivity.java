package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.droidkit.file.R;
import com.droidkit.file.selectors.FileSelectorActivity;

import java.util.ArrayList;


public class MainActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button selectFiles = (Button) findViewById(R.id.select);
        selectFiles.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, FileSelectorActivity.class), 1);
            }
        });
    }




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

           TextView statusView = (TextView) findViewById(R.id.status);
            if(resultCode == RESULT_OK){
                statusView.setText(data.getStringExtra("result"));
                String fileString = "";
                ArrayList<String> files = data.getStringArrayListExtra("files");
                if(!files.isEmpty())
                for (String file : files) {
                    fileString += file+"\n";
                }else{
                    fileString = "No file selected";
                }
                statusView.setText(fileString);

            }
            if (resultCode == RESULT_CANCELED) {
                statusView.setText("canceled");
            }
        }
    }
}
