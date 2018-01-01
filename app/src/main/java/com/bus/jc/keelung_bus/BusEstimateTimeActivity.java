package com.bus.jc.keelung_bus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class BusEstimateTimeActivity extends AppCompatActivity {

    Bus_Module bus = new Bus_Module();
    JSONObject json;
    String busStr;

    private ListView listEstimateTime;
    private ArrayAdapter<String> estimateTimeAsapter;

    private ArrayList<String> getListData(){
        final ArrayList<String> estimateTimeList = new ArrayList<>();
        busStr = getIntent().getStringExtra("searchbus");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    json = bus.busRouteEstimateTime(busStr,true);
                    JSONArray jsonArray = json.getJSONArray("routeInfo");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String info = jsonArray.getJSONObject(i).getString("nameZh") + jsonArray.getJSONObject(i).getString("EstimateTime");
                        estimateTimeList.add(info);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        return estimateTimeList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_estimate_time);

        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        TabHost.TabSpec spec;
        spec = host.newTabSpec("Tab1");
        spec.setIndicator("往新豐街");
        spec.setContent(R.id.Go);
        host.addTab(spec);

        spec = host.newTabSpec("Tab2");
        spec.setIndicator("往總站");
        spec.setContent(R.id.Back);
        host.addTab(spec);

        ArrayList<String> list = getListData();
        estimateTimeAsapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listEstimateTime  = (ListView) findViewById(R.id.listBusdata);
        listEstimateTime.setAdapter(estimateTimeAsapter);
    }
}
