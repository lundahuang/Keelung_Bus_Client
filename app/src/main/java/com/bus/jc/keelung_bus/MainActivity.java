package com.bus.jc.keelung_bus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    EditText searchTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("基隆公車搜尋");

        searchTextField = (EditText)findViewById(R.id.editText);

        Button busbtn = (Button) findViewById(R.id.searchButton);
        busbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("KeyWords",searchTextField.getText().toString());
                i.setClass(MainActivity.this, SearchListActivity.class);
                startActivityForResult(i,0);
            }
        });

    }
}
