package com.grinddesign.trakme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    public static ArrayList<String> itemArray = new ArrayList<String>();
    public static JSONArray goldenArray = new JSONArray();
    public static ArrayAdapter<String> mainListAdapter;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //itemArray = new ArrayList<String>();

        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(this);
        mainListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemArray);
        Log.i("list", itemArray.toString());
        lv.setAdapter(mainListAdapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

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
