package com.grinddesign.trakme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainCell extends Activity {


    public static final String EXTRA_ITEM = "com.grinddesign.trakme.MainCell.EXTRA_ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cell);

        Intent intent = getIntent();
        ItemName item = (ItemName)intent.getSerializableExtra(EXTRA_ITEM);
        if(item == null) {
            finish();
            return;
        }

        TextView tv = (TextView)findViewById(R.id.cellText);
        tv.setText(item.getItemName());


    }
}
