package com.grinddesign.trakme;

import android.app.Activity;
import android.os.Bundle;

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
        setContentView(R.layout.fragment_detail);

        itemName = (TextView) findViewById(R.id.textView);
        carr = (TextView) findViewById(R.id.textView5);
        trk = (TextView) findViewById(R.id.textView6);
        est = (TextView) findViewById(R.id.textView7);

        itemName.setText((CharSequence) MainActivity.itemArray.get(pos));
        carr.setText((CharSequence) MainActivity.carrArray.get(pos));
        trk.setText((CharSequence) MainActivity.trkArray.get(pos));
        est.setText((CharSequence) MainActivity.datArray.get(pos));
    }



}
