package com.grinddesign.trakme;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DetailActivity extends Activity {

    static int pos;
    TextView itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemName = (TextView) findViewById(R.id.textView);

        itemName.setText(MainActivity.itemArray.get(pos));
    }



}
