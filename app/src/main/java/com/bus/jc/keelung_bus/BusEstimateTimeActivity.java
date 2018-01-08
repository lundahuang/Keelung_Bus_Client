package com.bus.jc.keelung_bus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BusEstimateTimeActivity extends AppCompatActivity {
    private Bus_Module bus = new Bus_Module();
    private String Id;
    private String nameZh;
    private String departureZh;
    private String destinationZh;
    private ListView listviewGo;
    private ListView listviewBack;
    private JSONObject json;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_estimate_time);
        Intent i = getIntent();
        Id = i.getStringExtra("Id");
        nameZh = i.getStringExtra("nameZh");
        departureZh = i.getStringExtra("departureZh");
        destinationZh = i.getStringExtra("destinationZh");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(nameZh + " (每5秒更新)");

        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        TabHost.TabSpec spec;
        spec = host.newTabSpec("Tab1");
        spec.setIndicator("往" + destinationZh);
        spec.setContent(R.id.Go);
        host.addTab(spec);

        spec = host.newTabSpec("Tab2");
        spec.setIndicator("往" + departureZh);
        spec.setContent(R.id.Back);
        host.addTab(spec);

        listviewGo = (ListView) findViewById(R.id.listBusdataGo);
        listviewBack = (ListView) findViewById(R.id.listBusdataBack);



        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                downloadDataFromServer(false);
                downloadDataFromServer(true);
            }
        };

        timer.schedule(task,0,5000);
    }

    private void downloadDataFromServer(boolean goBack) {
        final boolean goBack_f = goBack;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    json = bus.busRouteEstimateTime(Id,goBack_f);
                    System.out.println(json);
                    final ArrayList<String> str = new ArrayList<String>();
                    JSONArray array = ((JSONArray)json.get("routeInfo"));
                    if(array.length() == 0 )
                        str.add("沒有這個方向的資料！");
                    for(int i = 0 ; i < array.length() ; i++) {
                        JSONObject data = array.getJSONObject(i);
                        str.add(data.getString("nameZh") + " - " + data.getString("EstimateTime"));
                    }

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            ArrayAdapter adapter = new ArrayAdapter(BusEstimateTimeActivity.this,android.R.layout.simple_list_item_1,str);
                            if(goBack_f)
                                listviewBack.setAdapter(adapter);
                            else
                                listviewGo.setAdapter(adapter);
                        }
                    });
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
