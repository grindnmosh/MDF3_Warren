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
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

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

    /**
     * global variables
     */
    MediaPlayer mPlayer;
    boolean mActivityResumed;
    boolean mPrepared;
    int mAudioPosition;
    String[] stringArray = new String[]{"/raw/blackmail", "/raw/die_dead_enough", "/raw/kick_the_chair", "/raw/scorpion", "/raw/tears_in_a_vial"};
    String[] songNames = new String[]{"Blackmail The Universe", "Die Dead Enough", "Kick The Chair", "Scorpion", "Tears In A Vial"};
    private static final int ID = 1975;


    public playService() {
        super();
    }

    /**
     * intent to pass the song title which is not functioning as expected. needed for the song title to update on continuous play.
     */

    protected void onHandleIntent(Intent intent) {
            ResultReceiver receiver = intent.getParcelableExtra(main.EXTRA_RECEIVER);
            Bundle result = new Bundle();
            result.putString(main.DATA_RETURNED, "any text");
            Log.e("result =", String.valueOf(result));
            //receiver.send(main.RESULT_DATA_RETURNED, result);

    }

    /**
     * Service Binder methods
     */
    public class BoundServiceBinder extends Binder {
        public playService getService() {
            return playService.this;
        }
       ;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new BoundServiceBinder();
    }


    /**
     * Actions taken when play button clicked
     */
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
            Intent i = new Intent("New Song");
            onHandleIntent(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("Test", "7");


    }

    /**
     * Actions taken when pause button clicked and music playing
     */
    public void onPause() {
        mPlayer.pause();
    }

    /**
     * Actions taken when pause button clicked and music ic already paused
     */
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

    /**
     * Actions taken when stop button clicked
     */
    public void onStop() {

        mPlayer.stop();
        mPrepared = false;
        mPlayer.release();
        stopForeground(true);
    }

    /**
     * Actions taken when app is closed and no foreground is running
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPlayer != null) {
            mPlayer.release();
            stopForeground(true);
        }
    }

    /**
     * Actions taken for the prepare Async calls
     * @param mp
     */
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

    /**
     * Actions taken when skip back button clicked
     */
    public void onPrev() {
        if (mAudioPosition >= 1) {
            mAudioPosition--;
            mPlayer.reset();
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

    /**
     * Actions taken when skip forward button clicked
     */
    protected void onNext() {
        if (mAudioPosition < stringArray.length - 1) {
            Log.i("Am I", "Hitting Here?");
            mAudioPosition++;
            mPlayer.reset();
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
            mPlayer.reset();
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


    /**
     * Actions taken when song ends and allows next song to play / This is where I am trying to get handeler to feed from. Have had no luck in research or asking for help.
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mAudioPosition < stringArray.length - 1) {
            Log.i("Am I", "Complete?");
            mAudioPosition++;
            mPlayer.reset();
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(playService.this);
            mPlayer.setOnCompletionListener(playService.this);
            try {
                mPlayer.setDataSource(playService.this, Uri.parse("android.resource://" + getPackageName() + "/" + stringArray[mAudioPosition]));
                Intent i = new Intent("New Song");
                onHandleIntent(i);ixed line of code I accidently deleted
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        } else {
            Log.i("Am I", "Here?");
            mAudioPosition = 0;
            //mPlayer.reset();
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(playService.this);
            mPlayer.setOnCompletionListener(playService.this);
            try {
                mPlayer.setDataSource(playService.this, Uri.parse("android.resource://" + getPackageName() + "/" + stringArray[mAudioPosition]));
                Intent i = new Intent("New Song");
                onHandleIntent(i);

            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        }

    }


}
