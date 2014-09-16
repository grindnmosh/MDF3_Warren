package com.grinddesign.trakme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class FormActivity extends Activity {


    Button sub;
    EditText edit1;
    EditText edit2;
    EditText edit3;
    EditText edit4;
    String item;
    String carrier;
    String trkNum;
    String estDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        sub = (Button) findViewById(R.id.submit);
        edit1 = (EditText) findViewById(R.id.editText);
        edit2 = (EditText) findViewById(R.id.editText2);
        edit3 = (EditText) findViewById(R.id.editText3);
        edit4 = (EditText) findViewById(R.id.editText4);





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
                    MainActivity.itemArray.add(item);
                    Log.i("test", MainActivity.itemArray.toString());
                    MainActivity.mainListAdapter.notifyDataSetChanged();
                    Intent load = new Intent(FormActivity.this, MainActivity.class);
                    load.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Log.i("TAPPED OUT", "Reaching For Me");
                    startActivity(load);

                }

            }
        });
    }





}
