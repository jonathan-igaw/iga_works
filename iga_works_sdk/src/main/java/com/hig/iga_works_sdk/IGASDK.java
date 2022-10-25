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

public class IGASDK {
    private static final String TAG = "IGASDK";
    private static final String DOMAIN = "http://adbrix-sdk-assignment-backend-115895936.ap-northeast-1.elb.amazonaws.com";

    // SDK를 초기화 합니다. appkey가 필수적으로 필요합니다.
    public static void init(String appkey) {
        Log.d(TAG, "init: appkey = "+appkey);
    }

    // 사용자 속성을 입력합니다.
    public static void setUserProperty(Dictionary<String,Object> keyValue) {
        Log.d(TAG, "setUserProperty: ");
    }

    // 이벤트를 입력합니다.
    public static boolean addEvent(String eventName, String jsonValue) {
        Log.d(TAG, "runAddEventThread: ");
        Log.d(TAG, "addEvent: ");
        String suffix = "/api/AddEvent";
        String httpMethod = "POST";
        String contentType = "application/json";

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
