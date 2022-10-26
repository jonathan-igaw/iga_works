package com.hig.iga_works;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.hig.iga_works_sdk.IGAMenuClickListener;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


}