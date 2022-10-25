package com.hig.iga_works_sdk;

import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Dictionary;
import java.util.Map;
import java.util.Objects;

public class IGASDK {
    private static final String TAG = "IGASDK";
    private static final String DOMAIN = "http://adbrix-sdk-assignment-backend-115895936.ap-northeast-1.elb.amazonaws.com";

    // SDK를 초기화 합니다. appkey가 필수적으로 필요합니다.
    public static void init(String appkey) {
        Log.d(TAG, "init: appkey = "+appkey);
    }

    // 사용자 속성을 입력합니다.
    public static void setUserProperty(Dictionary<String, String> keyValue) {
        Log.d(TAG, "setUserProperty: ");
    }

    private static JSONObject makeAddEventBody(Map<String, String> map) {
        Log.d(TAG, "makeAddEventBody: ");
        JSONObject jsonObject = new JSONObject();

        try {
            JSONObject evt = new JSONObject();
            evt.put("created_at", map.get("created_at"));
            evt.put("event", map.get("event"));

            JSONObject locationJsonObject = new JSONObject();
            locationJsonObject.put("lat", map.get("lat"));
            locationJsonObject.put("lng", map.get("lng"));
            evt.put("location", locationJsonObject);

            // input by customer
            JSONObject paramJsonObject = new JSONObject();
            paramJsonObject.put("menu_name", map.get("menu_name"));
            paramJsonObject.put("menu_id", map.get("menu_id"));
            evt.put("param", paramJsonObject);

            // Customer Info
            JSONObject userPropertiesJsonObject = new JSONObject();
            userPropertiesJsonObject.put("birthyear", map.get("birthyear"));
            userPropertiesJsonObject.put("gender", map.get("gender"));
            userPropertiesJsonObject.put("level", map.get("level"));
            userPropertiesJsonObject.put("character_class", map.get("character_class"));
            userPropertiesJsonObject.put("gold", map.get("gold"));
            evt.put("user_properties", userPropertiesJsonObject);
            jsonObject.put("evt", evt);

            // common
            JSONObject common = new JSONObject();
            JSONObject identityJsonObject = new JSONObject();
            identityJsonObject.put("adid", "a5f21bc1-4a1d-48e0-8829-6dee007da8c7");
            identityJsonObject.put("adid_opt_out", false);

            JSONObject deviceInfoObject = new JSONObject();
            deviceInfoObject.put("os", map.get("os"));                // version of os
            deviceInfoObject.put("model", map.get("model"));          // name of User Phone
            deviceInfoObject.put("resolution", map.get("resolution"));    // current Display pixel
            deviceInfoObject.put("is_portrait", map.get("is_portrait"));         // isPortrait?
            deviceInfoObject.put("platform", map.get("platform"));        // Platform
            deviceInfoObject.put("network", map.get("network"));            // Check NetworkStatus
            deviceInfoObject.put("carrier", map.get("carrier"));     // Telecom company
            deviceInfoObject.put("language", map.get("language"));             // Device Language
            deviceInfoObject.put("country", map.get("country"));              // Device Country
            common.put("identity", identityJsonObject);
            common.put("device_info", deviceInfoObject);

            String packageName = Objects.requireNonNull(IGASDK.class.getPackage()).getName();
            common.put("package_name", packageName);
            common.put("appkey", map.get("appkey"));
            jsonObject.put("common", common);
        } catch (Exception e) {
            Log.w(TAG, "makeAddEventBody: "+e.getMessage(), e);
        }

        return jsonObject;
    }

    // 이벤트를 입력합니다.
    public static boolean addEvent(String eventName, Map<String, String> map) {
        Log.d(TAG, "runAddEventThread: ");
        Log.d(TAG, "addEvent: ");
        String suffix = "/api/AddEvent";
        String httpMethod = "POST";
        String contentType = "application/json";
        String jsonValue = makeAddEventBody(map).toString();

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

            // 리턴된 결과 읽기
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
        Log.d(TAG, "getEvent: ");
        String host = "assignment.ad-brix.com";
        String url = "/api/GetEvent";
        String httpMethod = "post";
        String contentType = "application/json";
    }

    private static void readResponseCode(HttpURLConnection connection){
        try{
            int responseCode = connection.getResponseCode();
            if(responseCode == 400){
                System.out.println("400 : command error");
            } else if (responseCode == 500){
                System.out.println("500 : Server error");
            } else {
                System.out.println("response code : " + responseCode);
            }
        } catch (IOException e){
            Log.w(TAG, "readResponseCode: ", e);
        }
    }
}
