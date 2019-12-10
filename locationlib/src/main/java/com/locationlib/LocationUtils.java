package com.locationlib;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationUtils {
    Context mContext;

    public LocationUtils(Context context) {
        this.mContext = context;
    }

    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private LocationAddressResultReceiver addressResultReceiver;
    private Location currentLocation;
    private LocationCallback locationCallback;
    private String mCurrentAddress;

    /**
     * Call in onCreate method in Activity/fragment for initialize
     * receiver
     */
    public void startLocation() {
        addressResultReceiver = new LocationAddressResultReceiver(new Handler());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress(mContext);
            }

            ;
        };
        startLocationUpdates();
    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress(Context context) {

        if (!Geocoder.isPresent()) {
            Toast.makeText(context,
                    "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(context, GetLocationIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        context.startService(intent);
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    //return
                }
                return;
            }

        }
    }*/

    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == 0) {
                //Last Location can be null for various reasons
                //for example the api is called first time
                //so retry till location is set
                //since intent service runs on background thread, it doesn't block main thread
                Log.d("Address", "Location null retrying");
                getAddress(mContext);
            }

            if (resultCode == 1) {
                /*Toast.makeText(AddressListActivity.this,
                        "Address not found, " ,
                        Toast.LENGTH_SHORT).show();*/
            }

            mCurrentAddress = resultData.getString("address_result");

            //showResults(currentAdd);
        }
    }

    public String getCurrentAddress() {
        return mCurrentAddress;
    }

    /**
     * Call in onResume method in Activity/fragment for registration
     * receiver
     */
    public void registrationReceiver() {
        startLocationUpdates();
    }

    /**
     * Call in onPause method in Activity/fragment for un-registration
     * receiver
     */
    public void unRegistrationReceiver() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}
