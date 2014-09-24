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


public class MyActivity extends Activity implements Serializable, MainFrag.onItemClicked {

    ArrayList<MarkerData> marker;

    public static Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        try {
            Log.i("step", "1");
            FileInputStream fis = openFileInput("geo.dat");
            Log.i("step", "2");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Log.i("read", "trying to read saved file");

            marker = (ArrayList<MarkerData>) ois.readObject();
            Log.i("read", String.valueOf(marker));
            if (marker == null) {
                marker = new ArrayList<MarkerData>();
            }

            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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
        Uri uri = Uri.parse(data.getUri());
        detail.putExtra("data", uri);
        Log.i("IMAGE", data.getUri());
        detail.putExtra("name", data.getNamed());
        detail.putExtra("date", data.getDat());
        detail.putExtra("lat", data.getLatit());
        detail.putExtra("long", data.getLongit());
        this.startActivity(detail);
    }
}
