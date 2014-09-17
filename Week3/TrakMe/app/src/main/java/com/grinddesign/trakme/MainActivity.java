package com.grinddesign.trakme;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaActionSound;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.conn.routing.RouteTracker;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener, Serializable {

    public static ArrayList itemArray = new ArrayList<String>();
    public static ArrayList carrArray = new ArrayList<String>();
    public static ArrayList trkArray = new ArrayList<String>();
    public static ArrayList datArray = new ArrayList<String>();
    private static final long serialVersionUID = 491345791112131449L;
    public static ArrayAdapter<String> mainListAdapter;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        Tracker tracker = new Tracker();



        try {
            Log.i("step", "1");
            FileInputStream fis = openFileInput("items.dat");
            Log.i("step", "2");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Log.i("read", "trying to read saved file");

            tracker = (Tracker) ois.readObject();
            Log.i("read", String.valueOf(tracker));
            itemArray = tracker.getItem();
            TrakMeRemoteViewsFactory.arr = (itemArray);
            carrArray = tracker.getCarrier();
            trkArray = tracker.getTracking();
            datArray = tracker.getDat();
            //itemArray = new ArrayList<String>();

            loadIt();

            Log.i("test", MainActivity.itemArray.toString());


            ois.close();
            MainActivity.mainListAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //MainActivity.mainListAdapter.notifyDataSetChanged();
        Log.i("read result", tracker.getItem().toString());

        if( savedInstanceState != null ) {


        }



    }

    public void loadIt() {
        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(this);
        mainListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemArray);
        Log.i("list", itemArray.toString());
        lv.setAdapter(mainListAdapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DetailActivity.pos = position;
        Intent detailPass = new Intent(this, DetailActivity.class);

        startActivity(detailPass);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add) {
            Intent addNew = new Intent(this, FormActivity.class);
            Log.i("TAPPED OUT", "Reaching For Me");
            this.startActivity(addNew);
        }
        return super.onOptionsItemSelected(item);
    }

}

