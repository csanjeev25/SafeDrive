package com.insomniac.safedrive;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by Sanjeev on 3/6/2018.
 */

public class GPSTracker extends Service implements LocationListener,ActivityCompat.OnRequestPermissionsResultCallback{

    private Context mContext;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    Location mLocation;
    double mLatitide;
    double mLongitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION=2;
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION=3;

    protected LocationManager mLocationManager;

    public GPSTracker(Context context) {
        mContext = context;
        getLocation();
    }

    public double getLatitude(){
        if(mLocation != null){
            mLatitide = mLocation.getLatitude();
        }
        return mLatitide;
    }

    public double getLongitude() {
        if (mLocation != null) {
            mLongitude = mLocation.getLongitude();
        }
        return mLongitude;
    }

    public Location getLocation() {
        try {
            mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                Toast.makeText(mContext, "Please enable GPS", Toast.LENGTH_SHORT).show();
                return null;
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        PermissionHelper.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_FINE_LOCATION,"Grant Permission","Need Location for GPS",R.drawable.ic_launcher_background);
                    }
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if(mLocationManager!=null){
                        mLocation=mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(mLocation!=null){
                            mLatitide=mLocation.getLatitude();
                            mLongitude=mLocation.getLongitude();
                        }
                    }

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return mLocation;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION:if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getLocation();break;
            }
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION:if(grantResults[1]==PackageManager.PERMISSION_GRANTED){
                getLocation();break;
            }
        }
    }

    public void stopUsingGPS(){
        if(mLocationManager!=null){
            mLocationManager.removeUpdates(GPSTracker.this);
        }

    }

    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    public void showSettingsAlert(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alertDialog.show();
    }
}
