package com.rnbdmap;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class CheckPermission extends ReactContextBaseJavaModule {

    private static int REQUEST_EXTERNAL_STORAGE = 1;
    private static int ACCESS_FINE_LOCATION = 2;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private Promise promise;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    public CheckPermission(ReactApplicationContext reactContext) {
        super(reactContext);

    }

    @Override
    public String getName() {
        return "CheckPermission";
    }

    public void getPermission()
    {

    }


    @ReactMethod
    private void showPhoneStatePermission(Promise promise) {
        int permissionCheck = ContextCompat.checkSelfPermission(
                getCurrentActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getCurrentActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
//                ActivityCompat.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_EXTERNAL_STORAGE);
                ActivityCompat.requestPermissions(getCurrentActivity(), PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } else {
            Toast.makeText(getCurrentActivity(), "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
        }

        this.promise= promise;
    }


    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        } else {
        }
    }

    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_EXTERNAL_STORAGE:
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getCurrentActivity(), "Permission Granted!", Toast.LENGTH_SHORT).show();

            this.promise.resolve("YES");

        } else {
            Toast.makeText(getCurrentActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
            this.promise.resolve("NO");
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(getCurrentActivity(),
                new String[]{permissionName}, permissionRequestCode);
    }
}
