package com.rnbdmap;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import org.lovebing.reactnative.baidumap.BaiduMapPackage;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {


  private static int REQUEST_EXTERNAL_STORAGE = 1;
  private static int ACCESS_FINE_LOCATION = 2;
  private static final int MY_CAMERA_REQUEST_CODE = 100;

  private static String[] PERMISSIONS_STORAGE = {
          Manifest.permission.READ_EXTERNAL_STORAGE,
          Manifest.permission.WRITE_EXTERNAL_STORAGE,
          Manifest.permission.CAMERA,
          Manifest.permission.ACCESS_FINE_LOCATION,
  };

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    public boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
            new BaiduMapPackage(),
              new CheckPermissionPackage()
      );
    }

    @Override
    protected String getJSMainModuleName() {
      return "index";
    }
  };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this, /* native exopackage */ false);

  }



  private void showPhoneStatePermission() {
    int permissionCheck = ContextCompat.checkSelfPermission(
            this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
      if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)getApplicationContext(),
              Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        showExplanation("Permission Needed", "Rationale", Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_EXTERNAL_STORAGE);
      } else {
//                ActivityCompat.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_EXTERNAL_STORAGE);
        ActivityCompat.requestPermissions((Activity)getApplicationContext(), PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
      }
    } else {
      Toast.makeText(this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
    }
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
      Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();

    } else {
      Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
    }
  }

  private void showExplanation(String title,
                               String message,
                               final String permission,
                               final int permissionRequestCode) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
    ActivityCompat.requestPermissions((Activity)getApplicationContext(),
            new String[]{permissionName}, permissionRequestCode);
  }
}
