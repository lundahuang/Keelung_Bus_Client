package com.bus.jc.keelung_bus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Bus_Module bus = new Bus_Module();
    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
