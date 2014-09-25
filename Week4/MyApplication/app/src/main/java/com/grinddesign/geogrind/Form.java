package com.grinddesign.geogrind;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Author:  Robert Warren
 * <p/>
 * Project:  MDF3
 * <p/>
 * Package: com.grinddesign.geogrind
 * <p/>
 * Purpose:
 */

public class Form extends Activity implements LocationListener, Serializable {

    private static final int REQUEST_TAKE_PICTURE = 0x01001;
    private static final int REQUEST_ENABLE_GPS = 0x02001;

    Button cam;
    Button saveButt;
    ImageView img;
    Uri mImageUri;

    TextView imgName;
    TextView mLatitude;
    TextView mLongitude;
    TextView imgDate;

    String imageName;
    String imageDate;
    double latitude;
    double longitude;
    ArrayList<MarkerData> mData;

    LocationManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_form);

        cam = (Button) findViewById(R.id.camera);
        saveButt = (Button) findViewById(R.id.saveMe);
        img = (ImageView) findViewById(R.id.savedImage);
        imgName = (TextView) findViewById(R.id.imageName);
        mLatitude = (TextView) findViewById(R.id.latitude);
        mLongitude = (TextView) findViewById(R.id.longitude);
        imgDate = (TextView) findViewById(R.id.dateText);
        mManager = (LocationManager)getSystemService(LOCATION_SERVICE);






        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mImageUri = getOutputUrl();
                if(mImageUri != null) {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);

                }
                startActivityForResult(cameraIntent, REQUEST_TAKE_PICTURE);

                enableGps();
            }
        });

    }

    private void enableGps() {
        if(mManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

            Location loc = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(loc != null) {
                mLatitude.setText(loc.getLatitude() + "");
                mLongitude.setText(loc.getLongitude() + "");
                latitude = loc.getLatitude();
                longitude = loc.getLongitude();
            }

        } else {
            new AlertDialog.Builder(this)
                    .setTitle("GPS Unavailable")
                    .setMessage("Please enable GPS in the system settings.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(settingsIntent, REQUEST_ENABLE_GPS);
                        }

                    })
                    .show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //enableGps();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLatitude.setText("" + location.getLatitude());
        mLongitude.setText("" + location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PICTURE && resultCode != RESULT_CANCELED) {
            if (data == null) {
                img.setImageBitmap(BitmapFactory.decodeFile(mImageUri.getPath()));
                addImageToGallery(mImageUri);
                //enableGps();
            } else {
                img.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
            }
        }
    }

    private Uri getOutputUrl() {
        imageName = new SimpleDateFormat("MMddyyyy_HHmmss").format(new Date(System.currentTimeMillis())) + "_GeoGrind";
        imageDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
        File imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // Creating our own folder in the default directory.
        File appDir = new File(imageDir, "GeoGrind");
        appDir.mkdirs();
        File image = new File(appDir, imageName + ".jpg");
        try {
            image.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        imgName.setText("Image Name = " + imageName);
        imgDate.setText("Date = " + imageDate);
        return Uri.fromFile(image);
    }



    private void addImageToGallery(Uri imageUri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        sendBroadcast(scanIntent);
    }
}