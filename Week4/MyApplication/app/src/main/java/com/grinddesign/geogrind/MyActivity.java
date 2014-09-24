package com.grinddesign.geogrind;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author:  Robert Warren
 * <p/>
 * Project:  MDF3
 * <p/>
 * Package: com.grinddesign.geogrind
 * <p/>
 * Purpose:
 */

public class MyActivity extends Activity implements Serializable, MainFrag.onItemClicked {

    ArrayList<MarkerData> mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        MainFrag frag = new MainFrag();
        getFragmentManager().beginTransaction().replace(R.id.container, frag).commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add) {
            Intent addNew = new Intent(this, Form.class);
            this.startActivity(addNew);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ItemSelected(MarkerData data) {
        Log.i("DATADATADATA", String.valueOf(data));
        Intent detail = new Intent(this, Detail.class);
        detail.putExtra("uri", data.getUri());
        Log.i("IMAGE", data.getUri());
        detail.putExtra("name", data.getNamed());
        detail.putExtra("date", data.getDat());
        detail.putExtra("lat", data.getLatit());
        detail.putExtra("long", data.getLongit());
        this.startActivity(detail);
    }
}
