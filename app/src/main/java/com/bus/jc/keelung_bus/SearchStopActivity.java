package com.bus.jc.keelung_bus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SearchStopActivity extends AppCompatActivity {


    Bus_Module bus = new Bus_Module();
    JSONObject json;

    private EditText stopput;
    private Button searchbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bus);

        getSupportActionBar();

        stopput = (EditText) findViewById(R.id.busput);


        searchbtn = (Button) findViewById(R.id.searchbtn);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("searchbus", stopput.getText().toString());
                i.setClass(SearchStopActivity.this, StopEstimateTimeActivity.class);
                startActivityForResult(i,0);
            }
        });
    }
}
