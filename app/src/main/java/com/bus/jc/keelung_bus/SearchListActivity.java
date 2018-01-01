package com.bus.jc.keelung_bus;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SearchListActivity extends AppCompatActivity {
    private Bus_Module bus = new Bus_Module();
    private String keyWords;
    private ListView listview;
    private JSONObject json;

    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                JSONArray array = ((JSONArray)json.get("searchResult"));
                JSONObject data = array.getJSONObject(position);
                Intent i = new Intent();
                if(data.has("Id")) {
                    i.setClass(SearchListActivity.this, BusEstimateTimeActivity.class);
                }
                else {
                    i.setClass(SearchListActivity.this, StopEstimateTimeActivity.class);
                }

                startActivityForResult(i,0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Intent i = getIntent();
        listview = (ListView) findViewById(R.id.listView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        keyWords = i.getStringExtra("KeyWords");
        getSupportActionBar().setTitle("搜尋：" + keyWords);

        listview.setOnItemClickListener(onClickListView);

        downloadDataFromServer();
    }

    private void downloadDataFromServer() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    json = bus.searchBus(keyWords);
                    System.out.println(json);
                    final ArrayList<String> str = new ArrayList<String>();
                    JSONArray array = ((JSONArray)json.get("searchResult"));
                    for(int i = 0 ; i < array.length() ; i++) {
                        JSONObject data = array.getJSONObject(i);
                        if(data.has("Id")) {
                            str.add(data.getString("nameZh"));
                        }
                        else {
                            str.add(data.getString("nameZh") + " - 站牌");
                        }

                    }

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            ArrayAdapter adapter = new ArrayAdapter(SearchListActivity.this,android.R.layout.simple_list_item_1,str);
                            listview.setAdapter(adapter);
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
