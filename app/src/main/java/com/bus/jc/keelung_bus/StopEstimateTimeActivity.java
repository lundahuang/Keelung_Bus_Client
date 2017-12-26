package com.bus.jc.keelung_bus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class StopEstimateTimeActivity extends AppCompatActivity {

    Bus_Module bus = new Bus_Module();
    JSONObject json;
    String StopStr;

    private ListView listEstimateTime;
    private ArrayAdapter<String> estimateTimeAsapter;

    private ArrayList<String> getListData(){
        final ArrayList<String> estimateTimeList = new ArrayList<>();
        StopStr = getIntent().getStringExtra("searchbus");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    json = bus.busStopEstimateTime(StopStr,true);
                    JSONArray jsonArray = json.getJSONArray("stopInfo");
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

        ArrayList<String> list = getListData();
        estimateTimeAsapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listEstimateTime  = (ListView) findViewById(R.id.listBusdata);
        listEstimateTime.setAdapter(estimateTimeAsapter);
    }
}
