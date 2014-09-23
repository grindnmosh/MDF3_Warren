package com.grinddesign.geogrind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class MyActivity extends Activity implements Serializable {

    ArrayList<String> imgURI = new ArrayList<String>();
    ArrayList<String> imgNames = new ArrayList<String>();
    ArrayList<String> imgDates = new ArrayList<String>();
    ArrayList<Integer> imgLats = new ArrayList<Integer>();
    ArrayList<Integer > imgLongs = new ArrayList<Integer>();
    public static Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        GeoData geoData = new GeoData();

        try {
            Log.i("step", "1");
            FileInputStream fis = openFileInput("geo.dat");
            Log.i("step", "2");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Log.i("read", "trying to read saved file");

            geoData = (GeoData) ois.readObject();
            Log.i("read", String.valueOf(geoData));
            imgURI = geoData.getURI();
            imgNames = geoData.getImgName();
            imgDates = geoData.getImgDates();
            imgLats = geoData.getImgLats();
            imgLongs = geoData.getImgLongs();

            bundle.putStringArrayList("name", imgNames);
            bundle.putIntegerArrayList("lat", imgLats);
            bundle.putIntegerArrayList("long", imgLongs);
            MainFrag fragObj = new MainFrag();
            fragObj.setArguments(bundle);

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
            addNew.putExtra("imgArr", imgURI);
            addNew.putExtra("nameArr", imgNames);
            addNew.putExtra("dateArr", imgDates);
            addNew.putExtra("latArr", imgLats);
            addNew.putExtra("longArr", imgLongs);
            this.startActivity(addNew);
        }
        return super.onOptionsItemSelected(item);
    }
}
