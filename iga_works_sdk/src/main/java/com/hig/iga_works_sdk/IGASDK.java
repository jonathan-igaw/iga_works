package com.hig.iga_works_sdk;

import android.location.Location;
import android.os.Build;
import android.util.Log;
import com.hig.iga_works_sdk.dto.UserInfo;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class IGASDK {
    public static class LocalPushProperties {
        public LocalPushProperties(
                String contentTitle,
                String contentText,
                String summaryText,
                long millisecondForDelay,
                int eventId,
                int importance
        ) {
            this.contentText = contentText;
            this.contentTitle = contentTitle;
            this.subText = summaryText;
            this.millisecondForDelay = millisecondForDelay;
            this.eventId = eventId;
            this.importance = importance;
        }

        private final String contentText;
        private final String contentTitle;
        private final String subText;
        private final long millisecondForDelay;
        private final int eventId;
        private final int importance;

        public String getContentText() {
            return contentText;
        }

        public String getContentTitle() {
            return contentTitle;
        }

        public String getSubText() {
            return subText;
        }

        public long getMillisecondForDelay() {
            return millisecondForDelay;
        }

        public int getEventId() {
            return eventId;
        }

        public int getImportance() {
            return importance;
        }
    }
    
    private static final String TAG = "IGASDK";
    private static final String DOMAIN = "http://adbrix-sdk-assignment-backend-115895936.ap-northeast-1.elb.amazonaws.com";
    private static String APP_KEY = "inqbator@naver.com";
    private static UserInfo userInfo = new UserInfo();
    private static IGASDKApplication igasdkApplication;

    // SDK??? ????????? ?????????.
    public static void init(String appkey) {
        Log.d(TAG, "init: appkey : "+appkey);
        APP_KEY = appkey;   //appkey??? ??????????????? ???????????????.
    }

    public static void login(String userId) {
        Log.d(TAG, "login: ");
        igasdkApplication.saveUserId(userId);
    }

    public static void logout() {
        Log.d(TAG, "logout: ");
        igasdkApplication.deleteUserId();
    }

    public static void setLocalPushNotification(LocalPushProperties lpp) {
        igasdkApplication.setLocalPushNotification(lpp);
    }

    public static void setIGASDKApplication(IGASDKApplication application) {
        igasdkApplication = application;
    }

    public static void setUserProperty(Map<String, Object> keyValue) {
        Log.d(TAG, "setUserProperty: ");
        igasdkApplication.saveUserProperty(keyValue);
        userInfo = igasdkApplication.getUserProperty();
    }

    private static JSONObject getAddEventJsonBody(String eventName, Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        if (map == null) {
            setEvtContent(eventName, jsonObject);
        } else {
            setEvtContent(eventName, jsonObject, map);
        }
        setCommonContent(jsonObject);

        return jsonObject;
    }

    private static void setUserPropertiesJsonObject(JSONObject userPropertiesJsonObject) {
        // user_properties
        try {
            userPropertiesJsonObject.put("birthyear", userInfo.getBirthYear());
            userPropertiesJsonObject.put("gender", userInfo.getGender());
            userPropertiesJsonObject.put("level", userInfo.getLevel());
            userPropertiesJsonObject.put("character_class", userInfo.getCharacterClass());
            userPropertiesJsonObject.put("gold", userInfo.getGold());
        } catch (JSONException e) {
            Log.w(TAG, "putUserInfoToJsonObject: "+e.getMessage(), e);
        }
    }

    private static void setEvtContent(String eventName, JSONObject jsonObject) {
        JSONObject evt = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String createdAt = sdf.format(System.currentTimeMillis());

        try {
            evt.put("created_at", createdAt);
            evt.put("event", eventName);

            // param
            JSONObject paramJsonObject = new JSONObject();
            paramJsonObject.put("menu_name", eventName);
            paramJsonObject.put("menu_id", "menu_id");
            evt.put("param", paramJsonObject);

            // user_properties
            JSONObject userPropertiesJsonObject = new JSONObject();
            setUserPropertiesJsonObject(userPropertiesJsonObject);
            evt.put("user_properties", userPropertiesJsonObject);
            jsonObject.put("evt", evt);
            evt.put("user_properties", userPropertiesJsonObject);
            jsonObject.put("evt", evt);
        } catch (JSONException e) {
            Log.w(TAG, "putEvtContent: "+e.getMessage(), e);
        }
    }

    private static void setEvtContent(String eventName, JSONObject jsonObject, Map<String, Object> map) {
        JSONObject evt = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String createdAt = sdf.format(System.currentTimeMillis());

        try {
            evt.put("created_at", createdAt);
            evt.put("event", eventName);

            Location currentLocation = getLastLocation();
            // if : location is not null. add location info to jsonBody.
            if (currentLocation != null) {
                JSONObject locationJsonObject = new JSONObject();
                locationJsonObject.put("lat", currentLocation.getLatitude());
                evt.put("location", locationJsonObject);
                locationJsonObject.put("lng", currentLocation.getLongitude());
            }

            // param
            JSONObject paramJsonObject = new JSONObject();
            paramJsonObject.put("menu_name", map.get("menu_name"));
            paramJsonObject.put("menu_id", map.get("menu_id"));
            evt.put("param", paramJsonObject);

            // user_properties
            JSONObject userPropertiesJsonObject = new JSONObject();
            setUserPropertiesJsonObject(userPropertiesJsonObject);
            evt.put("user_properties", userPropertiesJsonObject);
            jsonObject.put("evt", evt);
            evt.put("user_properties", userPropertiesJsonObject);
            jsonObject.put("evt", evt);
        } catch (JSONException e) {
            Log.w(TAG, "putEvtContent: "+e.getMessage(), e);
        }
    }

    private static void setCommonContent(JSONObject jsonObject) {
        try {
            // common
            JSONObject common = new JSONObject();

            // identity
            JSONObject identityJsonObject = new JSONObject();
            identityJsonObject.put("adid", "a5f21bc1-4a1d-48e0-8829-6dee007da8c7");
            identityJsonObject.put("adid_opt_out", false);

            // deviceInfo
            JSONObject deviceInfoObject = new JSONObject();
            deviceInfoObject.put("os", Build.VERSION.SDK_INT);                // version of os
            deviceInfoObject.put("model", Build.MODEL);          // name of User Phone
            int[] widthAndHeight = getResolution();
            deviceInfoObject.put("resolution", widthAndHeight[0]+"x"+widthAndHeight[1]);    // current Display pixel
            deviceInfoObject.put("is_portrait", isPortrait());         // isPortrait?
            deviceInfoObject.put("platform", "android");        // Platform
            deviceInfoObject.put("network", getNetworkStatus());            // Check NetworkStatus
            deviceInfoObject.put("carrier", getNetworkOperatorName());     // Telecom company

            deviceInfoObject.put("language", Locale.getDefault().getLanguage());             // Device Language
            deviceInfoObject.put("country", Locale.getDefault().getCountry());              // Device Country
            common.put("identity", identityJsonObject);
            common.put("device_info", deviceInfoObject);

            // package and appkey
            String packageName = Objects.requireNonNull(IGASDK.class.getPackage()).getName();
            common.put("package_name", packageName);
            common.put("appkey", APP_KEY);
            jsonObject.put("common", common);
        } catch (JSONException e) {
            Log.w(TAG, "setCommonContent: "+e.getMessage(), e);
        }
    }

    // ???????????? ???????????????.
    public static boolean addEvent(String eventName, Map<String, Object> map) {
        String suffix = "/api/AddEvent";
        String httpMethod = "POST";
        String contentType = "application/json";
        String jsonValue = getAddEventJsonBody(eventName, map).toString();
        Log.d(TAG, "request jsonBody : "+jsonValue);

        try {
            URL url = new URL(DOMAIN+suffix);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(httpMethod);
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setConnectTimeout(10 * 1000);
            conn.setReadTimeout(10 * 1000);

            OutputStream os = conn.getOutputStream();
            os.write(jsonValue.getBytes(StandardCharsets.UTF_8));
            os.flush();

            // ????????? ?????? ??????
            String inputLine;
            StringBuilder outResult = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            while ((inputLine = in.readLine()) != null) {
                outResult.append(inputLine);
            }
            readResponseCode(conn);
            conn.disconnect();

            JSONObject resultJson = new JSONObject(outResult.toString());
            Log.d(TAG, "response : "+outResult);
            return (boolean) resultJson.get("result");
        } catch (Exception e) {
            Log.w(TAG, "addEvent: "+e.getMessage(), e);
        }

        return false;
    }

    public static void getEvent(String appKey, int length) {
        String host = "assignment.ad-brix.com";
        String url = "/api/GetEvent";
        String httpMethod = "post";
        String contentType = "application/json";
    }

    private static void readResponseCode(HttpURLConnection connection){
        try{
            int responseCode = connection.getResponseCode();
            if(responseCode == 400){
                Log.d(TAG, "readResponseCode: 400 : command error");
            } else if (responseCode == 500){
                Log.d(TAG, "readResponseCode: 500 : Server error");
            } else {
                Log.d(TAG, "response code : " + responseCode);
            }
        } catch (IOException e){
            Log.w(TAG, "readResponseCode: ", e);
        }
    }

    private static int[] getResolution() {
        return igasdkApplication.getResolution();
    }

    private static boolean isPortrait() {
        return igasdkApplication.isPortrait();
    }

    private static String getNetworkStatus() {
        return igasdkApplication.getNetworkStatus();
    }

    private static String getNetworkOperatorName() {
        return igasdkApplication.getNetworkOperatorName();
    }

    private static Location getLastLocation() {
        return igasdkApplication.getLocation();
    }
}
