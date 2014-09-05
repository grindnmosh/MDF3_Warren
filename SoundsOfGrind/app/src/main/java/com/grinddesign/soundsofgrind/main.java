package com.grinddesign.soundsofgrind;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

/**
 * Author:  Robert Warren
 *
 * Project:  MDF3
 *
 * Package: com.grinddesign.soundsofgrind
 *
 * Purpose: This is where all the action happens to actually present the application on the device screen and to direct the traffic of what needs to run and when.
 */


public class main extends Activity implements ServiceConnection {

    private static final String SAVED = "MainActivity.SAVE";

    Button play;
    Button pause;
    Button stop;
    Button prev;
    Button next;
    public TextView tS;
    playService myService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        //mPrepared = mActivityResumed = false;
        //mAudioPosition = 0;
        Log.i("Test", "1");

        if(savedInstanceState != null && savedInstanceState.containsKey(SAVED)) {
            //mAudioPosition = savedInstanceState.getInt(SAVED, 0);
        }

        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);
        prev = (Button) findViewById(R.id.back);
        next = (Button) findViewById(R.id.fwd);
        tS = (TextView)findViewById(R.id.titleShot);
        play.setOnClickListener(playClick);
        pause.setOnClickListener(pauseClick);
        stop.setOnClickListener(stopClick);
        prev.setOnClickListener(playPrev);
        next.setOnClickListener(playNext);

        pause.setEnabled(false);
        stop.setEnabled(false);
        prev.setEnabled(false);
        next.setEnabled(false);

        Log.i("Test", "2");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("test", "hit 1");
        Intent playIntent = new Intent(this, playService.class);
        bindService(playIntent, this, Context.BIND_AUTO_CREATE);
        Log.i("test", "hit 2");
        startService(playIntent);
    }

    View.OnClickListener playClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myService.play();
            play.setEnabled(false);
            pause.setEnabled(true);
            stop.setEnabled(true);
            prev.setEnabled(true);
            next.setEnabled(true);
            tS.setText(myService.songNames[myService.mAudioPosition]);

        }

    };

    View.OnClickListener pauseClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (myService.mPlayer.isPlaying()){
                myService.onPause();
                prev.setEnabled(false);
                next.setEnabled(false);
                pause.setText("Resume");
            } else {
                myService.onResume();
                prev.setEnabled(true);
                next.setEnabled(true);
                pause.setText("Pause");
            }

        }

    };

    View.OnClickListener stopClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myService.onStop();
            play.setEnabled(true);
            pause.setEnabled(false);
            stop.setEnabled(false);
            prev.setEnabled(false);
            next.setEnabled(false);
            unbindService(main.this);
        }
    };

    View.OnClickListener playPrev = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myService.onPrev();
            tS.setText(myService.songNames[myService.mAudioPosition]);

        }
    };

    View.OnClickListener playNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myService.onNext();
            tS.setText(myService.songNames[myService.mAudioPosition]);

        }
    };


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("Test", "5");
        if(myService.mPlayer != null) {
            outState.putInt(SAVED, myService.mPlayer.getCurrentPosition());
            Log.i("Test", "6");
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.i("Here I Am", "Deal With It");
        playService.BoundServiceBinder binder = (playService.BoundServiceBinder)service;
        myService = binder.getService();
        myService.showToast();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }



    /*@Override
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
