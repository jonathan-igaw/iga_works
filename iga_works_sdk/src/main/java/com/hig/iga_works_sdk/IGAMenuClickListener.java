package com.hig.iga_works_sdk;

import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public abstract class IGAMenuClickListener implements View.OnClickListener {
    private static final String TAG = "IGAClickListener";
    private final String event;

    public IGAMenuClickListener() {
        this.event = "click";
    }

    public IGAMenuClickListener(String event) {
        this.event = event;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        addEvent(view);
    }

    public void addEvent(View view) {
        Log.d(TAG, "addEvent: ");
        String menuName = view.getResources().getResourceName(view.getId());
        int menuId = view.getId();
        Map<String, Object> map = getEventMap(event, menuName, menuId);

        new Thread() {
            @Override
            public void run() {
                IGASDK.addEvent(event, map);
            }
        }.start();
    }

    private Map<String, Object> getEventMap(String event, String menuName, int menuId) {
        Map<String, Object> mapForJson = new HashMap<>();
        mapForJson.put("event", event);
        mapForJson.put("menu_name", menuName);
        mapForJson.put("menu_id", menuId);

        return mapForJson;
    }
}
