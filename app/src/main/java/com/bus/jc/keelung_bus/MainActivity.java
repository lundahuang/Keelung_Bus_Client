package com.bus.jc.keelung_bus;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Bus_Module bus = new Bus_Module();
    JSONObject json;

    private Button busbtn;
    private Button stopbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("基隆公車Only");

        busbtn = (Button) findViewById(R.id.busbtn);
        busbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MainActivity.this, SearchBusActivity.class);
                startActivityForResult(i,0);
            }
        });

        stopbtn = (Button) findViewById(R.id.stopbtn);
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MainActivity.this, SearchStopActivity.class);
                startActivityForResult(i,0);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    json = bus.searchBus("海大");
                    System.out.println(json);
                    json = bus.busStopEstimateTime("情人湖濱海大道紀念碑",true);
                    System.out.println(json);
                    json = bus.busRouteEstimateTime("3052",true);
                    System.out.println(json);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
