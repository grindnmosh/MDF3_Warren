package com.grinddesign.trakme;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DetailActivity extends Activity {

    static int pos;
    TextView itemName;
    TextView carr;
    TextView trk;
    TextView est;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemName = (TextView) findViewById(R.id.textView);
        carr = (TextView) findViewById(R.id.textView5);
        trk = (TextView) findViewById(R.id.textView6);
        est = (TextView) findViewById(R.id.textView7);

        itemName.setText(MainActivity.itemArray.get(pos));
        carr.setText(MainActivity.carrArray.get(pos));
        trk.setText(MainActivity.trkArray.get(pos));
        est.setText(MainActivity.datArray.get(pos));
    }



}
