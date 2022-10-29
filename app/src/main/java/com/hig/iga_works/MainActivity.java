package com.hig.iga_works;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.hig.iga_works_sdk.IGAMenuClickListener;
import com.hig.iga_works_sdk.IGASDK;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestLocationPermission();

        Button notificationBtn = findViewById(R.id.button_notification);
        notificationBtn.setOnClickListener(new IGAMenuClickListener() {
            @Override
            public void onClick(View view) {
                super.onClick(view);

                // 사용자가 하고 싶은 행위
                IGASDK.LocalPushProperties lpp = new IGASDK.LocalPushProperties(
                        "contentTitle",
                        "contentText",
                        "summaryText",
                        5 * 1000,
                        1,
                        NotificationManager.IMPORTANCE_HIGH
                );
                IGASDK.setLocalPushNotification(lpp);
            }
        });

        Button registerUserInfoBtn = findViewById(R.id.button_register_member_info);
        registerUserInfoBtn.setOnClickListener(new IGAMenuClickListener(TAG + " - Click Register Button") {
            @Override
            public void onClick(View view) {
                super.onClick(view);
                Map<String, Object> mapOfUserProperty = new HashMap<>();
                mapOfUserProperty.put("birthyear", Integer.parseInt(((EditText) findViewById(R.id.edittext_birth_year)).getText().toString()));
                mapOfUserProperty.put("gender", ((EditText) findViewById(R.id.edittext_gender)).getText().toString());
                mapOfUserProperty.put("level", Integer.parseInt(((EditText) findViewById(R.id.edittext_level)).getText().toString()));
                mapOfUserProperty.put("gold", Integer.parseInt(((EditText) findViewById(R.id.edittext_gold)).getText().toString()));
                IGASDK.setUserProperty(mapOfUserProperty);
            }
        });
    }

    private void requestLocationPermission() {
        Log.d(TAG, "requestLocationPermission: ");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    0
            );
        }
    }
}