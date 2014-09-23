package com.grinddesign.geogrind;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.URISyntaxException;
import java.util.ArrayList;


public class MainFrag extends MapFragment {

    int i = 0;




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GoogleMap map = getMap();
        if (MyActivity.bundle != null) {
            ArrayList<String> imgNames = MyActivity.bundle.getStringArrayList("name");
            ArrayList<Integer> imgLats = MyActivity.bundle.getIntegerArrayList("lat");
            ArrayList<Integer> imgLongs = MyActivity.bundle.getIntegerArrayList("long");

            Log.i("Names", imgNames.toString());

            if (i < imgNames.size()) {
                double latitude =  imgLats.get(i);
                double longitude = imgLongs.get(i);
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(imgNames.get(i)));
                i++;
            }
        }








        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(33.40777455, -111.67087746), 14));
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
