package com.bus.jc.keelung_bus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class StopEstimateTimeActivity extends AppCompatActivity {
    private Bus_Module bus = new Bus_Module();
    private String nameZh;
    private ListView listviewGo;
    private ListView listviewBack;
    private JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_estimate_time);
        Intent i = getIntent();
        nameZh = i.getStringExtra("nameZh");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(nameZh);

        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        TabHost.TabSpec spec;
        spec = host.newTabSpec("Tab1");
        spec.setIndicator("去程");
        spec.setContent(R.id.Go);
        host.addTab(spec);

        spec = host.newTabSpec("Tab2");
        spec.setIndicator("回程");
        spec.setContent(R.id.Back);
        host.addTab(spec);

        listviewGo = (ListView) findViewById(R.id.listBusdataGo);
        listviewBack = (ListView) findViewById(R.id.listBusdataBack);

        downloadDataFromServer(true);
        downloadDataFromServer(false);
    }

    private void downloadDataFromServer(boolean goBack) {
        final boolean goBack_f = goBack;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    json = bus.busStopEstimateTime(nameZh,goBack_f);
                    System.out.println(json);
                    final ArrayList<String> str = new ArrayList<String>();
                    JSONArray array = ((JSONArray)json.get("stopInfo"));
                    if(array.length() == 0 )
                        str.add("沒有這個方向的資料！");
                    for(int i = 0 ; i < array.length() ; i++) {
                        JSONObject data = array.getJSONObject(i);
                        str.add(data.getString("nameZh") + " - " + data.getString("EstimateTime"));
                    }

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            ArrayAdapter adapter = new ArrayAdapter(StopEstimateTimeActivity.this,android.R.layout.simple_list_item_1,str);
                            if(goBack_f)
                                listviewGo.setAdapter(adapter);
                            else
                                listviewBack.setAdapter(adapter);
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
