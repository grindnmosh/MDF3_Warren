package com.grinddesign.soundsofgrind;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

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

    /**
     * global variables
     */
    Button play;
    Button pause;
    Button stop;
    Button prev;
    Button next;
    Button loop;
    SeekBar seekBar;
    int prog = 0;
    public TextView tS;
    playService myService;
    playService.BoundServiceBinder binder;
    public static final String EXTRA_RECEIVER = "main.EXTRA_RECEIVER";
    public static final String DATA_RETURNED = "main.DATA_RETURNED";
    public static final int RESULT_DATA_RETURNED = 0;
    Handler seekHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Log.i("Test", "1");



        /**
         * variables and listeners from the UI for the main activity
          */
        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);
        prev = (Button) findViewById(R.id.back);
        next = (Button) findViewById(R.id.fwd);
        loop = (Button) findViewById(R.id.loopy);
        tS = (TextView) findViewById(R.id.titleShot);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        play.setOnClickListener(playClick);
        pause.setOnClickListener(pauseClick);
        stop.setOnClickListener(stopClick);
        prev.setOnClickListener(playPrev);
        next.setOnClickListener(playNext);
        loop.setOnClickListener(loopIt);



        /**
         * set intent to handler in service
         */
        Intent intent = new Intent(this, playService.class);
        intent.putExtra(EXTRA_RECEIVER, new DataReceiver());
        startService(intent);

        if(savedInstanceState != null) {


        }

        play.setEnabled(true);
        pause.setEnabled(false);
        stop.setEnabled(false);
        prev.setEnabled(false);
        next.setEnabled(false);

        Log.i("Test", "2");
    }

    /**
     * handler to handle returned data
     */
    private final Handler handleMe = new Handler();

    public class DataReceiver extends ResultReceiver {
        public DataReceiver() {
            super(handleMe);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if(resultData != null && resultData.containsKey(DATA_RETURNED)) {
                tS.setText(resultData.getString(DATA_RETURNED, "New Song"));
            }
        }
    }

    /**
     * start up song service
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("test", "hit 1");
        Intent playIntent = new Intent(this, playService.class);
        bindService(playIntent, this, Context.BIND_AUTO_CREATE);
        Log.i("test", "hit 2");
        startService(playIntent);
    }

    /**
     * title function
     */
    public void songTitle() {
        tS.setText(myService.songNames[myService.mAudioPosition]);
    }

    /**
     * play button function which changes button settings
     */
    View.OnClickListener playClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myService.play();
            play.setEnabled(false);
            pause.setEnabled(true);
            stop.setEnabled(true);
            prev.setEnabled(true);
            next.setEnabled(true);
            //songTitle();
            seekBar.setProgress(0);
            myService.loopOn = 0;
            seekUpdates();
            Log.i("Here I Am", "Deal With It");

        }

    };

    /**
     * pause button function which changes button settings
     */
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

    /**
     * stop button function which changes button settings
     */
    View.OnClickListener stopClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myService.onStop();
            play.setEnabled(true);
            pause.setEnabled(false);
            stop.setEnabled(false);
            prev.setEnabled(false);
            next.setEnabled(false);
            seekHandler.removeCallbacks(run);

        }
    };

    /**
     * back skip button function
     */
    View.OnClickListener playPrev = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myService.onPrev();
            songTitle();
        }
    };

    /**
     * skip forward button function
     */
    View.OnClickListener playNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myService.onNext();
            songTitle();
        }
    };

    View.OnClickListener loopIt = new View.OnClickListener() {

        @Override
        public void onClick(View v){
            Log.e("Is It Reading", "My Clicks");
            String test = "Play All";
            if (test.equals(loop.getText())) {
                myService.loopOn = 0;
                loop.setText("Repeat Song");
            }
            else {
                myService.loopOn = 1;
                loop.setText("Play All");
            }
        }
    };


    Runnable run = new Runnable() {
        @Override public void run() {

            seekUpdates();
        } };

    public void seekUpdates() {
        tS.setText(myService.songNames[myService.mAudioPosition]);
        seekBar.setMax(myService.mPlayer.getDuration());
        seekBar.setProgress(myService.mPlayer.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }



    /**
     * bind service when its connected
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (playService.BoundServiceBinder)service;
        myService = binder.getService();
    }

    /**
     * unbind service when its not connected
     */
    @Override
    public void onServiceDisconnected(ComponentName name) {
        unbindService(main.this);
    }

}
