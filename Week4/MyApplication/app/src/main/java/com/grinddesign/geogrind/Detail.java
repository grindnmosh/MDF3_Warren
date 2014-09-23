package com.grinddesign.geogrind;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Detail extends Activity {

    TextView name;
    TextView dateMe;
    TextView latit;
    TextView longit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        name = (TextView) findViewById(R.id.imageName);
        dateMe = (TextView) findViewById(R.id.dateText);
        latit = (TextView) findViewById(R.id.latitude);
        longit = (TextView) findViewById(R.id.longitude);

        Intent intent = getIntent();
        //Uri image = intent.getStringExtra("uri");
        name.setText(intent.getStringExtra("name"));
        dateMe.setText(intent.getStringExtra("date"));
        latit.setText(intent.getStringExtra("lat"));
        longit.setText(intent.getStringExtra("long"));

    }



}
