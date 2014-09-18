package com.grinddesign.trakme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DateSorter;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class FormActivity extends Activity implements Serializable {


    Button sub;
    EditText edit1;
    EditText edit2;
    EditText edit3;
    EditText edit4;
    String item;
    String carrier;
    String trkNum;
    String estDate;
    JSONObject jObj = new JSONObject();
    private static final long serialVersionUID = 491345791112131449L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        sub = (Button) findViewById(R.id.submit);
        edit1 = (EditText) findViewById(R.id.editText);
        edit2 = (EditText) findViewById(R.id.editText2);
        edit3 = (EditText) findViewById(R.id.editText3);
        edit4 = (EditText) findViewById(R.id.editText4);

        Intent intent = getIntent();
        final ArrayList<String> itemString = intent.getStringArrayListExtra("item");
        final ArrayList<String> carrString = intent.getStringArrayListExtra("carr");
        final ArrayList<String> trkString = intent.getStringArrayListExtra("trk");
        final ArrayList<String> dateString = intent.getStringArrayListExtra("dat");






        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = edit1.getText().toString();
                carrier = edit4.getText().toString();
                trkNum = edit3.getText().toString();
                estDate = edit2.getText().toString();

                //run a check for input in the 7 edit text fields
                if (edit1.getText().length() == 0) {

                    edit1.setError("please input item name");

                }
                else if (edit4.getText().length() == 0) {

                    edit2.setError("please input carrier");

                }
                else if (edit3.getText().length() == 0) {

                    edit3.setError("please input tracking number");

                }
                else if (edit2.getText().length() == 0) {

                    edit4.setError("please input estimated delivery date");
                }
                else {
                    edit1.setError(null);
                    edit4.setError(null);
                    edit3.setError(null);
                    edit2.setError(null);

                    //package data for passing through app
                    Log.i("item", item);

                    itemString.add(item);
                    carrString.add(carrier);
                    trkString.add(trkNum);
                    dateString.add(estDate);


                    try {
                        Log.i("jObj2", "am I here");
                        FileOutputStream fos = openFileOutput("items.dat", MODE_PRIVATE);

                        // Wrapping our file stream.
                        Log.i("jObj2", "am I here2");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);

                        // Writing the serializable object to the file
                        Log.i("jObj2", "am I here3");
                        Tracker tracker = new Tracker();
                        tracker.setItem(itemString);
                        tracker.setCarrier(carrString);
                        tracker.setTracking(trkString);
                        tracker.setDate(dateString);
                        oos.writeObject(tracker);
                        Log.i("jObj2", String.valueOf(tracker));

                        // Closing our object stream which also closes the wrapped stream.

                        oos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("jObj2", "Not Doing It");
                    }


                    Intent load = new Intent(FormActivity.this, MainActivity.class);
                    load.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Log.i("TAPPED OUT", "Reaching For Me");
                    startActivity(load);

                }

            }
        });
    }





}
