package com.hig.iga_works;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.hig.iga_works_sdk.IGAMenuClickListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestLocationPermission();

        Button buttonMenu = findViewById(R.id.button_menu);
        buttonMenu.setOnClickListener(new IGAMenuClickListener(TAG + " - Click Button") {
            @Override
            public void onClick(View view) {
                super.onClick(view);
                Log.d(TAG, "IGA Clicked Listener called: ");
                Toast.makeText(MainActivity.this.getApplicationContext(), "button is clicked", Toast.LENGTH_LONG).show();
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