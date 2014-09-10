package com.grinddesign.soundsofgrind;


import android.app.Activity;
import android.content.ComponentName;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Purpose: to handle the fragments
 */


public class portrait extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.activity_main, container, false);

        return view;
    }





    @Override
    public void onDetach() {
        super.onDetach();
    }



}
