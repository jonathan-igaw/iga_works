package com.hig.iga_works_sdk;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import androidx.core.app.NotificationCompat;
import com.hig.iga_works_sdk.dto.UserInfo;
import com.hig.iga_works_sdk.util.CustomLocationManager;
import java.util.Map;

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
        IGASDK.setIGASDKApplication(this);

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

    public void saveUserProperty(Map<String, Object> keyValue) {
        Log.d(TAG, "setUserProperty: ");
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preference.edit();

        Integer birthYear = (Integer) keyValue.getOrDefault("birthyear", 0);
        if (birthYear != null) editor.putInt("birthyear", birthYear);

        String gender = (String) keyValue.getOrDefault("gender", "m");
        if (gender != null) editor.putString("gender", gender);

        Integer level = (Integer) keyValue.getOrDefault("level", 0);
        if (level != null) editor.putInt("level", level);

        Integer gold = (Integer) keyValue.getOrDefault("gold", 0);
        if (gold != null) editor.putInt("gold", gold);

        editor.apply();
    }

    public UserInfo getUserProperty() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        UserInfo userInfo = new UserInfo();
        userInfo.setBirthYear(preferences.getInt("birthyear", 0));
        userInfo.setGender(preferences.getString("gender", ""));
        userInfo.setLevel(preferences.getInt("level", 0));
        userInfo.setGold(preferences.getInt("gold", 0));
        return userInfo;
    }

    public void requestLocationUpdates() {
        clm.requestLocationUpdates();
    }

    public Location getLocation() {
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

    public void saveUserId(String userId) {
        Log.d(TAG, "saveUserId: ");
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("user_id", userId).apply();
    }

    public void deleteUserId() {
        Log.d(TAG, "deleteUserId: ");
        PreferenceManager.getDefaultSharedPreferences(this).edit().remove("user_id").apply();
    }

    private void createNotificationChannel(String id) {
        Log.d(TAG, "createNotificationChannel: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(id,
                    "녹음용 알림",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            notificationChannel.setDescription("녹음용 채널입니다.");
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.createNotificationChannel(notificationChannel);
        }
    }

    public void setLocalPushNotification(IGASDK.LocalPushProperties lpp) {
        Log.d(TAG, "setNotification: second : "+lpp.getMillisecondForDelay());
        createNotificationChannel(String.valueOf(lpp.getEventId()));
        Notification notification = new NotificationCompat.Builder(this, String.valueOf(lpp.getEventId()))
                .setContentTitle(lpp.getContentTitle())
                .setContentText(lpp.getContentText())
                .setSubText(lpp.getSubText())
                .setPriority(lpp.getImportance())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Handler nHanlder = new Handler(Looper.myLooper());
        nHanlder.postDelayed(
                () ->  nm.notify(1, notification),
                lpp.getMillisecondForDelay()
        );
    }
}
