package com.hig.iga_works_sdk.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import androidx.core.app.ActivityCompat;

public class CustomLocationManager {
    private static final String TAG = "CustomLocationManager";
    private final Context context;
    private final LocationManager lm;
    private Location lastLocation;
    private LocationListener locationListener;

    public CustomLocationManager(Context context) {
        this.context = context;
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void requestLocationUpdates() {
        Log.d(TAG, "setLocationManager: ");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        setLocationListener();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15 * 1000, 0, locationListener);
    }

    private void setLocationListener() {
        Log.d(TAG, "setLocationListener: ");
        locationListener = location -> lastLocation = location;
    }

    public Location getLastLocation() {
        Log.d(TAG, "getLastLocation: ");
        return lastLocation;
    }
}
