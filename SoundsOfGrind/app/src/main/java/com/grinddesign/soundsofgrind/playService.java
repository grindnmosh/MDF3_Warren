package com.grinddesign.soundsofgrind;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
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

    ProgressBar song;
    MediaPlayer mPlayer;
    boolean mActivityResumed;
    boolean mPrepared;
    int mAudioPosition;
    String[] stringArray = new String[]{"/raw/blackmail", "/raw/die_dead_enough", "/raw/kick_the_chair", "/raw/scorpion", "/raw/tears_in_a_vial"};
    String[] songNames = new String[]{"Blackmail The Universe", "Die Dead Enough", "Kick The Chair", "Scorpion", "Tears In A Vial"};
    private static final int ID = 1975;

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
        mPlayer.setOnPreparedListener(playService.this);
        mPlayer.setOnCompletionListener(playService.this);
        Log.i("test", "play");
        try {
            mPlayer.setDataSource(playService.this, Uri.parse("android.resource://" + getPackageName() + stringArray[mAudioPosition]));
            mPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Log.i("Test", "7");


    }


    public void onPause() {
        mPlayer.pause();


    }

    public void onResume() {
        Log.i("Test", "7");
        mActivityResumed = true;
        if (mPlayer != null && !mPrepared) {
            mPlayer.prepareAsync();
            Log.i("kidding", "me");
        } else if (mPlayer != null && mPrepared) {
            Log.i("Oh My", "God");

            mPlayer.start();
        }

        Log.i("Test", "8");
    }

    public void onStop() {

        mPlayer.stop();
        mPrepared = false;
        mPlayer.release();
        onDestroy();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPlayer != null) {
            mPlayer.release();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onPrepared(MediaPlayer mp) {
        mPrepared = true;
        mPlayer.start();
        String currentSong = songNames[mAudioPosition];
        Intent notIntent = new Intent(this, main.class);
        notIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0, notIntent, 0);
        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.ic_sog)
                .setTicker(currentSong)
                .setOngoing(true)
                .setContentTitle("Current Song")
                .setContentText(currentSong);
        Notification notifier = builder.build();

        startForeground(ID, notifier);
    }

    public void onPrev() {
        if (mAudioPosition >= 1) {
            mAudioPosition--;
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(playService.this);
            mPlayer.setOnCompletionListener(playService.this);
            try {
                mPlayer.setDataSource(playService.this, Uri.parse("android.resource://" + getPackageName() + stringArray[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        } else {
            mPlayer.reset();
            mAudioPosition = 0;
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(playService.this);
            mPlayer.setOnCompletionListener(playService.this);
            try {
                mPlayer.setDataSource(playService.this, Uri.parse("android.resource://" + getPackageName() + stringArray[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        }


    }

    protected void onNext() {
        if (mAudioPosition < stringArray.length - 1) {
            mAudioPosition++;
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(playService.this);
            mPlayer.setOnCompletionListener(playService.this);
            try {
                mPlayer.setDataSource(playService.this, Uri.parse("android.resource://" + getPackageName() + stringArray[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        } else {
            Log.i("Am I", "Hitting?");
            mPlayer.reset();
            mAudioPosition = 0;
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(playService.this);
            mPlayer.setOnCompletionListener(playService.this);
            try {
                mPlayer.setDataSource(playService.this, Uri.parse("android.resource://" + getPackageName() + "/" + stringArray[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        }
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mAudioPosition < stringArray.length - 1) {
            Log.i("Am I", "Complete?");
            mPlayer.reset();
            mAudioPosition++;
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(playService.this);
            mPlayer.setOnCompletionListener(playService.this);
            try {
                mPlayer.setDataSource(playService.this, Uri.parse("android.resource://" + getPackageName() + "/" + stringArray[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        } else {
            Log.i("Am I", "Here?");
            mPlayer.reset();
            mAudioPosition = 0;
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(playService.this);
            mPlayer.setOnCompletionListener(playService.this);
            try {
                mPlayer.setDataSource(playService.this, Uri.parse("android.resource://" + getPackageName() + "/" + stringArray[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        }

    }


}
