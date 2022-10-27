package com.hig.iga_works_sdk;

import android.app.Application;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import com.hig.iga_works_sdk.util.CustomLocationManager;
import java.util.HashMap;
import java.util.Map;

public class IGASDKApplication extends Application {
    private static final String TAG = "IGASDKApplication";
    private CustomLocationManager clm;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        IGASDK.init("inqbator@naver.com");
        IGASDK.setIgasdkApplication(this);
        IGASDK.setApplicationContext(getApplicationContext());

        Map<String, Object> mapOfUserProperty = new HashMap<>();
        mapOfUserProperty.put("birthyear", 1986);
        mapOfUserProperty.put("gender", "m");
        mapOfUserProperty.put("level", 36);
        mapOfUserProperty.put("gold", 300);
        IGASDK.setUserProperty(mapOfUserProperty);

        // Set LocationManager before request location.
        setCustomLocationManager();
        // request location when application is Created.
        requestLocationUpdates();
    }

    public void setCustomLocationManager() {
        clm = new CustomLocationManager(getApplicationContext());
    }

    public void requestLocationUpdates() {
        Log.d(TAG, "setLocationManager: ");
        clm.requestLocationUpdates();
    }

    public Location getLocation() {
        Log.d(TAG, "getLocation: ");
        return clm.getLastLocation();
    }
}
