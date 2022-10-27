package com.hig.iga_works_sdk;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.hig.iga_works_sdk.util.CustomLocationManager;

public class IGASDKApplication extends Application {
    private static final String TAG = "IGASDKApplication";
    private CustomLocationManager clm;
    private WindowManager wm;
    private ConnectivityManager connMgr;
    private TelephonyManager tm;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        setConnectivityManager();
        setWindowManager();
        setTelephoneManager();
        setCustomLocationManager();

        IGASDK.init("inqbator@naver.com");
        IGASDK.setIgasdkApplication(this);

        // Set LocationManager before request location.
        setCustomLocationManager();
        // request location when application is Created.
        requestLocationUpdates();
    }

    private void setWindowManager() {
        wm = (WindowManager) getSystemService(
                Context.WINDOW_SERVICE
        );
    }

    private void setConnectivityManager() {
        connMgr = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE
        );
    }

    private void setTelephoneManager() {
        tm = (TelephonyManager) getSystemService(
                TELEPHONY_SERVICE
        );
    }

    private void setCustomLocationManager() {
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

    public int[] getResolution() {
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        return new int[] {width, height};
    }

    public boolean isPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public String getNetworkStatus() {
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected()) {
                return "wifi";
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE && networkInfo.isConnected()) {
                return "mobile";
            }
        }

        return null;
    }

    public String getNetworkOperatorName() {
        return tm.getNetworkOperatorName();
    }
}
