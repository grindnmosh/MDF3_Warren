package com.grinddesign.trakme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;


public class DetailActivity extends Activity {

    TextView itemName;
    TextView carr;
    TextView trk;
    TextView est;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        itemName = (TextView) findViewById(R.id.cellText);
        carr = (TextView) findViewById(R.id.textView5);
        trk = (TextView) findViewById(R.id.textView6);
        est = (TextView) findViewById(R.id.textView7);
        Intent intent = getIntent();

        itemName.setText(intent.getStringExtra("item"));
        carr.setText(intent.getStringExtra("carr"));
        trk.setText(intent.getStringExtra("trk"));
        est.setText(intent.getStringExtra("dat"));
    }



}
