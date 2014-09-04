package com.grinddesign.soundsofgrind;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.lang.reflect.Array;

/**
 * Author:  Robert Warren
 *
 * Project:  MDF3
 *
 * Package: com.grinddesign.soundsofgrind
 *
 * Purpose: This is where all the action happens to actually present the application on the device screen and to direct the traffic of what needs to run and when.
 */


public class main extends Activity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private static final String SAVED = "MainActivity.SAVE";

    Button play;
    Button pause;
    Button stop;
    Button prev;
    Button next;
    MediaPlayer mPlayer;
    boolean mActivityResumed;
    boolean mPrepared;
    int mAudioPosition;
    int [] resID = {R.raw.blackmail, R.raw.die_dead_enough, R.raw.kick_the_chair, R.raw.scorpion, R.raw.tears_in_a_vial};
    String[] stringArray = new String[]{"/raw/blackmail", "/raw/die_dead_enough", "/raw/kick_the_chair", "/raw/scorpion", "/raw/tears in a vial"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPrepared = mActivityResumed = false;
        mAudioPosition = 0;
        Log.i("Test", "1");

        if(savedInstanceState != null && savedInstanceState.containsKey(SAVED)) {
            mAudioPosition = savedInstanceState.getInt(SAVED, 0);
        }

        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);
        prev = (Button) findViewById(R.id.back);
        next = (Button) findViewById(R.id.fwd);
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

    View.OnClickListener playClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            play();
            play.setEnabled(false);
            pause.setEnabled(true);
            stop.setEnabled(true);
            prev.setEnabled(true);
            next.setEnabled(true);
        }
    };

    View.OnClickListener pauseClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mPlayer.isPlaying()){
                mPlayer.pause();
                prev.setEnabled(false);
                next.setEnabled(false);
                pause.setText("Resume");
            } else {
                prev.setEnabled(true);
                next.setEnabled(true);
                mPlayer.start();
                pause.setText("Pause");

            }
        }
    };

    View.OnClickListener stopClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            play.setEnabled(true);
            pause.setEnabled(false);
            stop.setEnabled(false);
            prev.setEnabled(false);
            next.setEnabled(false);
            mPlayer.stop();
            mPrepared = false;
            mPlayer.release();
        }
    };

    View.OnClickListener playPrev = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mAudioPosition--;
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(main.this);
            try {
                mPlayer.setDataSource(main.this, Uri.parse("android.resource://" + getPackageName() + stringArray[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        }
    };

    View.OnClickListener playNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mAudioPosition++;
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(main.this);
            try {
                mPlayer.setDataSource(main.this, Uri.parse("android.resource://" + getPackageName() + stringArray[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        }
    };


    protected void play() {
        Log.i("test", "playMain");
        mAudioPosition = 0;
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(main.this);
        mPlayer.setOnCompletionListener(main.this);
        Log.i("test", "play");


        //mPlayer = MediaPlayer.create(main.this, resID[mAudioPosition]);
        try {
            mPlayer.setDataSource(main.this, Uri.parse("android.resource://" + getPackageName() + stringArray[mAudioPosition]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("Test", "7");
        mPlayer.prepareAsync();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("Test", "5");
        if(mPlayer != null) {
            outState.putInt(SAVED, mPlayer.getCurrentPosition());
            Log.i("Test", "6");
        }
    }

    @Override
    public void onResume() {
        super .onResume();
        Log.i("Test", "7");
        mActivityResumed = true;
        if(mPlayer != null && !mPrepared) {
            mPlayer.prepareAsync();
        } else if(mPlayer != null && mPrepared) {
            mPlayer.seekTo(mAudioPosition);
            mPlayer.start();
        }

        Log.i("Test", "8");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mPlayer != null) {
            mPlayer.release();
        }
    }

    public void onPrepared(MediaPlayer mp) {
        mPrepared = true;

        if(mPlayer != null && mActivityResumed) {
            mPlayer.start();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i("Am I", "Complete?");
        mAudioPosition++;
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(main.this);
        try {
            mPlayer.setDataSource(main.this, Uri.parse("android.resource://" + getPackageName() + stringArray[mAudioPosition]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.prepareAsync();
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
