package com.grinddesign.soundsofgrind;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

/**
 * Author:  Robert Warren
 *
 * Project:  MDF3
 *
 * Package: com.grinddesign.soundsofgrind
 *
 * Purpose:
 */


public class playService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    Button play;
    Button pause;
    Button stop;
    Button prev;
    Button next;
    MediaPlayer mPlayer;
    boolean mActivityResumed;
    boolean mPrepared;
    int mAudioPosition;
    Context context;
    playService myService;
    int [] resID = {R.raw.blackmail, R.raw.die_dead_enough, R.raw.kick_the_chair, R.raw.scorpion, R.raw.tears_in_a_vial};
    String[] stringArray = new String[]{"/raw/blackmail", "/raw/die_dead_enough", "/raw/kick_the_chair", "/raw/scorpion", "/raw/tears in a vial"};

    public class BoundServiceBinder extends Binder {
        public playService getService() {
            return playService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return new BoundServiceBinder();
    }

    public void showToast() {
        Toast.makeText(this, "Finally, The Service Has Hit", Toast.LENGTH_SHORT).show();

    }

    protected void play() {
        Log.i("test", "playMain");
        mAudioPosition = 0;

        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Log.i("test", "playMain1");
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnCompletionListener(this);
        Log.i("test", "play");


        //mPlayer = MediaPlayer.create(main.this, resID[mAudioPosition]);
        try {
            mPlayer.setDataSource(playService.this, Uri.parse("android.resource://" + getPackageName() + stringArray[mAudioPosition]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("Test", "7");
        mPlayer.prepareAsync();

    }



    public void onPause() {
        mPlayer.pause();


    }

    public void onResume() {
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

    public void onStop() {

        mPlayer.stop();
        mPrepared = false;
        mPlayer.release();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mPlayer != null) {
            mPlayer.release();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPrepared = true;

        if(mPlayer != null && mActivityResumed) {
            mPlayer.start();
        }
    }

    public void onPrev() {
        mAudioPosition--;
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(playService.this);
        try {
            mPlayer.setDataSource(playService.this, Uri.parse("android.resource://" + getPackageName() + stringArray[mAudioPosition]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.prepareAsync();
    }

    protected void onNext() {
        mAudioPosition++;
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(playService.this);
        try {
            mPlayer.setDataSource(playService.this, Uri.parse("android.resource://" + getPackageName() + stringArray[mAudioPosition]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.prepareAsync();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i("Am I", "Complete?");
        mAudioPosition++;
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(playService.this);
        try {
            mPlayer.setDataSource(playService.this, Uri.parse("android.resource://" + getPackageName() + stringArray[mAudioPosition]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.prepareAsync();
    }


}
