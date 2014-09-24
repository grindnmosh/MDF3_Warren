package com.grinddesign.geogrind;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainFrag extends MapFragment implements GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapClickListener{

    int i;
    Context context;
    GoogleMap map;
    ArrayList<MarkerData> mData;




    public interface onItemClicked {

        void ItemSelected(MarkerData data);

    }

    private onItemClicked parentActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        map = getMap();

        mData = new ArrayList<MarkerData>();
        try {
            Log.i("step", "1");
            FileInputStream fis = getActivity().openFileInput("geo.dat");
            Log.i("step", "2");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Log.i("read", "trying to read saved file");

            mData = (ArrayList<MarkerData>) ois.readObject();
            Log.i("read", String.valueOf(mData));
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        for (i = 0; i < mData.size(); i++) {
            if (mData != null) {
                MarkerData data = mData.get(i);
                Double latitude = Double.parseDouble(data.getLatit());
                Double longitude = Double.parseDouble(data.getLongit());
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(data.getNamed()));
                Log.i("LATITUDE", data.getLatit());
            }
        }
        if (map != null) {
            map.setOnInfoWindowClickListener(this);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(33.40777455, -111.67087746), 12));
        }


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof onItemClicked) {
            Log.i("Click It", "attached");
            this.context = activity;
            parentActivity = (onItemClicked) activity;
            Log.i("Click It", parentActivity.toString());
        }
        else {
            throw new ClassCastException(activity.toString() + "must implement method" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        double latitude = marker.getPosition().latitude;
        double newLat = Math.round(latitude*100.0)/100.0;
        DecimalFormat df = new DecimalFormat("####.######");

        double longitude = marker.getPosition().longitude;
        double newLong = Math.round(latitude*100.0)/100.0;
        DecimalFormat df2 = new DecimalFormat("####.######");
        Log.i("FFFFFFFFFFFF", String.valueOf(df.format(latitude)));

        for (i = 0; i < mData.size(); i++) {

            MarkerData data = mData.get(i);
            double markerLat = Double.parseDouble(data.getLatit());
            double markerLong = Double.parseDouble(data.getLongit());
            Log.i("FFFFFFFFFFFF", String.valueOf(markerLat));
            if (Double.compare(markerLat, Double.parseDouble(df.format(latitude))) == 0 && Double.compare(markerLong, Double.parseDouble(df2.format(longitude))) == 0) {

                parentActivity.ItemSelected(data);

            }
        }

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }



}
