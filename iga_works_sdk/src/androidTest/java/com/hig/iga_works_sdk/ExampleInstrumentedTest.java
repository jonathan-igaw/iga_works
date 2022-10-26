package com.hig.iga_works_sdk;

import android.content.Context;
import android.util.Log;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";
    HashMap<String, Object> map;
    Context context;

    @Before
    public void setContext() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

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
        map.put("lat", lat);
        map.put("lng", lng);

        // app key
        map.put("appkey", "inqbator@naver.com");
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.hig.iga_works_sdk.test", appContext.getPackageName());
    }

    @Test
    public void addEvent_isCorrect() {
        Log.d(TAG, "addEvent_isCorrect: ");
        IGASDK.setApplicationContext(context);
        assertTrue(IGASDK.addEvent("test_event", map));
    }

    @Test
    public void addEventWithNullMap_isCorrect() {
        IGASDK.setApplicationContext(context);
        assertTrue(IGASDK.addEvent("test_event", null));
    }

    @Test
    public void addEventIfLocationIsNull_isCorrect() {
        IGASDK.setApplicationContext(context);
        map.remove("lat");
        map.remove("lng");
        map.put("appkey", "inqbator@naver.com");
        assertTrue(IGASDK.addEvent("test_event", map));
    }
}