package com.grinddesign.geogrind;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.net.URISyntaxException;
import java.util.ArrayList;


public class MainFrag extends MapFragment {

    int i;
    Context context;
    ArrayList<String> imgURI = new ArrayList<String>();
    ArrayList<String> imgNames = new ArrayList<String>();
    ArrayList<String> imgDates = new ArrayList<String>();
    ArrayList<String> imgLats = new ArrayList<String>();
    ArrayList<String > imgLongs = new ArrayList<String>();


    public interface onItemClicked {

        void ItemSelected(String uri, String name, String dated, String latit, String longit);

    }

    private onItemClicked parentActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GoogleMap map = getMap();
        if (MyActivity.bundle.getStringArrayList("name") != null) {
            imgURI = MyActivity.bundle.getStringArrayList("uri");
            imgNames = MyActivity.bundle.getStringArrayList("name");
            imgDates = MyActivity.bundle.getStringArrayList("date");
            Log.i("NAMES", imgNames.toString());
            imgLats = MyActivity.bundle.getStringArrayList("lat");
            imgLongs = MyActivity.bundle.getStringArrayList("long");



            Log.i("Names", imgNames.toString());
            Log.i("Lattitude", imgLats.toString());

            for (i = 0; i < imgNames.size(); i++) {
                //final String str = imgURI.get(i);
                final Double latitude = Double.parseDouble(imgLats.get(i));
                Double longitude = Double.parseDouble(imgLongs.get(i));
                Log.i("Lattitude", String.valueOf(latitude));
                map.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude, longitude))
                                .title(imgNames.get(i))
                                .snippet(imgDates.get(i))
                );
                map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        String uri = imgURI.get(i -1);
                        Log.i("URI", uri);
                        String name = marker.getTitle();
                        String dated = marker.getSnippet();
                        String latit = String.valueOf(marker.getPosition().latitude);
                        String longit = String.valueOf(marker.getPosition().longitude);
                        parentActivity.ItemSelected(uri, name, dated, latit, longit);

                    }
                });
            }

        }

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(33.40777455, -111.67087746), 12));
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



}
