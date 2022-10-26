package com.hig.iga_works_sdk;

import static org.junit.Assert.*;
import android.os.Build;
import android.util.Log;
import org.junit.Before;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTest";
    HashMap<String, String> map;

    @Before
    public void init() {
        Log.d(TAG, "init: ");
        map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        map.put("created_at", sdf.format(new Date(System.currentTimeMillis())));

        String menuInputByUser = "open_menu";
        map.put("event", menuInputByUser);

        String menuName = "menu1";
        map.put("menu_name", menuName);
        map.put("menu_id", "30");

        double lat = 32.13;
        double lng = -123.123;
        map.put("lat", String.valueOf(lat));
        map.put("lng", String.valueOf(lng));

        // Customer Info
        map.put("birthyear", String.valueOf(1982));
        map.put("gender", "m");
        map.put("level", String.valueOf(37));
        map.put("character_class", "knight");
        map.put("gold", String.valueOf(300));

        // Device Information
        map.put("os", String.valueOf(Build.VERSION.SDK_INT));
        map.put("model", Build.MODEL);          // name of User Phone
        int[] widthAndHeight = new int[] {1020, 980};
        map.put("resolution", widthAndHeight[0]+"x"+widthAndHeight[1]);    // current Display pixel
        map.put("is_portrait", "true");         // isPortrait?
        map.put("platform", "android");        // Platform
        map.put("network", "wifi");            // Check NetworkStatus
        map.put("carrier", "name of carrier");     // Telecom company
        map.put("language", Locale.getDefault().getLanguage());             // Device Language
        map.put("country", Locale.getDefault().getCountry());              // Device Country

        // app key
        map.put("appkey", "inqbator@naver.com");
    }

    @Test
    public void addEvent_isCorrect() {
        Log.d(TAG, "addEvent_isCorrect: ");
        assertTrue(IGASDK.addEvent("test_event", map));
    }

    @Test
    public void addEventWithWrongAppKey_isCorrect() {
        map.put("appkey", null);
        assertFalse(IGASDK.addEvent("test_event", map));
    }
}