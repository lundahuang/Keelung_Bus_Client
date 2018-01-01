package com.bus.jc.keelung_bus;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SearchBusActivity extends AppCompatActivity {

    Bus_Module bus = new Bus_Module();
    JSONObject json;

    private EditText busput;
    private Button searchbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("路線搜尋");


        busput = (EditText) findViewById(R.id.busput);

        searchbtn = (Button) findViewById(R.id.searchbtn);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("searchbus", busput.getText().toString());
                i.setClass(SearchBusActivity.this, BusEstimateTimeActivity.class);
                startActivityForResult(i,0);
            }
        });
    }
}
