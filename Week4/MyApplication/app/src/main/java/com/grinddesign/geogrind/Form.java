package com.grinddesign.geogrind;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Form extends Activity {

    private static final int REQUEST_TAKE_PICTURE = 0x01001;

    Button cam;
    ImageView img;
    Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        cam = (Button) findViewById(R.id.camera);
        img = (ImageView) findViewById(R.id.savedImage);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mImageUri = getOutputUrl();
                if(mImageUri != null) {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                }
                startActivityForResult(cameraIntent, REQUEST_TAKE_PICTURE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PICTURE && resultCode != RESULT_CANCELED) {
            if (data == null) {
                img.setImageBitmap(BitmapFactory.decodeFile(mImageUri.getPath()));
                addImageToGallery(mImageUri);
            } else {
                img.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
            }


        }
    }

    private Uri getOutputUrl() {
        String imageName = new SimpleDateFormat("MMddyyyy_HHmmss").format(new Date(System.currentTimeMillis()));
        File imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // Creating our own folder in the default directory.
        File appDir = new File(imageDir, "CameraExample");
        appDir.mkdirs();
        File image = new File(appDir, imageName + ".jpg");
        try {
            image.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return Uri.fromFile(image);
    }



    private void addImageToGallery(Uri imageUri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        sendBroadcast(scanIntent);
    }
}