package com.grinddesign.soundsofgrind;

import android.app.Activity;
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


public class main extends Activity implements MediaPlayer.OnPreparedListener {

    private static final String SAVED = "MainActivity.SAVE";

    Button play;
    Button pause;
    Button stop;
    MediaPlayer mPlayer;
    boolean mActivityResumed;
    boolean mPrepared;
    int mAudioPosition;

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
        play.setOnClickListener(playClick);
        pause.setOnClickListener(pauseClick);
        stop.setOnClickListener(stopClick);

        Log.i("Test", "2");
    }

    View.OnClickListener playClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onResume();
        }
    };

    View.OnClickListener pauseClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onPause();
        }
    };

    View.OnClickListener stopClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onStop();
            onDestroy();
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Test", "3");

        if(mPlayer == null) {
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(this);

            try {
                mPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/raw/blackmail"));
            } catch(IOException e) {
                e.printStackTrace();

                mPlayer.release();
                mPlayer = null;
            }
            Log.i("Test", "4");
        }
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
    public void onPause() {
        super .onPause();
        Log.i("Test", "9");
        mActivityResumed = false;

        if(mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
        Log.i("Test", "10");
    }

    @Override
    public void onStop() {
        super .onStop();
        Log.i("Test", "11");
        if(mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
            mPrepared = false;
        }
        Log.i("Test", "12");
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
            mPlayer.seekTo(mAudioPosition);
            mPlayer.start();
        }
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
