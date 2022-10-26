package com.hig.iga_works_sdk;

import android.app.Application;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class IGASDKApplication extends Application {
    private static final String TAG = "IGASDKApplication";

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        IGASDK.init("inqbator@naver.com");
        Map<String, Object> mapOfUserProperty = new HashMap<>();
        mapOfUserProperty.put("birthyear", 1986);
        mapOfUserProperty.put("gender", "m");
        mapOfUserProperty.put("level", 36);
        mapOfUserProperty.put("gold", 300);
        IGASDK.setUserProperty(mapOfUserProperty);
        IGASDK.setApplicationContext(getApplicationContext());
    }
}
