package com.rnbdmap;

import android.Manifest;

import com.facebook.react.ReactActivity;

public class MainActivity extends ReactActivity {

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */


    private static int REQUEST_EXTERNAL_STORAGE = 1;
    private static int ACCESS_FINE_LOCATION = 2;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    @Override
    protected String getMainComponentName() {
        return "BDMap";
    }



}
