package com.hig.iga_works_sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";
    Context context;
    IGASDKApplication igasdkApplication;

    @Before
    public void setApplication() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        igasdkApplication = ApplicationProvider.getApplicationContext();
        IGASDK.setIGASDKApplication(igasdkApplication);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.hig.iga_works_sdk.test", appContext.getPackageName());
    }

    @Test
    public void addEvent_isSuccess() {
        Log.d(TAG, "addEvent_isCorrect: ");
        Map<String, Object> map = new HashMap<>();
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

        assertTrue(IGASDK.addEvent("test_event", map));
    }

    @Test
    public void addEventWithNullMap_isSuccess() {
        IGASDK.setIGASDKApplication(igasdkApplication);
        assertTrue(IGASDK.addEvent("test_event", null));
    }

    @Test
    public void addEventIfLocationIsNull_isSuccess() {
        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        map.put("created_at", sdf.format(new Date(System.currentTimeMillis())));

        String menuInputByUser = "open_menu";
        map.put("event", menuInputByUser);

        String menuName = "menu1";
        map.put("menu_name", menuName);
        map.put("menu_id", "30");

        // app key
        map.put("appkey", "inqbator@naver.com");

        IGASDK.setIGASDKApplication(igasdkApplication);
        map.put("appkey", "inqbator@naver.com");
        assertTrue(IGASDK.addEvent("test_event", map));
    }

    @Test
    public void login_isUserInfoSaved() {
        IGASDK.setIGASDKApplication(igasdkApplication);
        IGASDK.login("tomas kim");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(igasdkApplication);
        assertEquals(sp.getString("user_id", "none"), "tomas kim");
    }

    @Test
    public void logout_isUserInfoDeleted() {
        IGASDK.setIGASDKApplication(igasdkApplication);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(igasdkApplication);
        sp.edit().remove("user_id").apply();
        assertEquals(sp.getString("user_id", "none"), "none");
    }
}