package com.grinddesign.soundsofgrind;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

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




    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }


}
