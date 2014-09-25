package com.grinddesign.geogrind;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author:  Robert Warren
 * <p/>
 * Project:  MDF3
 * <p/>
 * Package: com.grinddesign.geogrind
 * <p/>
 * Purpose:
 */

public class Detail extends Activity {

    ImageView img;
    TextView name;
    TextView dateMe;
    TextView latit;
    TextView longit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        img = (ImageView) findViewById(R.id.savedImage);
        name = (TextView) findViewById(R.id.imageName);
        dateMe = (TextView) findViewById(R.id.dateText);
        latit = (TextView) findViewById(R.id.latitude);
        longit = (TextView) findViewById(R.id.longitude);

        Intent intent = getIntent();
        Log.i("IMAGE2", intent.getStringExtra("uri"));
        Uri uri = Uri.parse(intent.getStringExtra("uri"));
        img.setImageBitmap(BitmapFactory.decodeFile(uri.getPath()));
        name.setText(intent.getStringExtra("name"));
        dateMe.setText(intent.getStringExtra("date"));
        latit.setText(intent.getStringExtra("lat"));
        longit.setText(intent.getStringExtra("long"));

    }



}
