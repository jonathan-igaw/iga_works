package com.hig.iga_works.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hig.iga_works.R;
import com.hig.iga_works_sdk.IGAMenuClickListener;
import com.hig.iga_works_sdk.IGASDK;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText userIdEditText = findViewById(R.id.edit_text_user_id);
        Button loginBtn = findViewById(R.id.button_login);
        loginBtn.setOnClickListener(new IGAMenuClickListener(TAG + "Login Button is Clicked") {
            @Override
            public void onClick(View view) {
                super.onClick(view);

                String userId = userIdEditText.getText().toString();
                IGASDK.login(userId);
            }
        });

        Button logoutBtn = findViewById(R.id.button_logout);
        logoutBtn.setOnClickListener(new IGAMenuClickListener(TAG + "logout") {
            @Override
            public void onClick(View view) {
                super.onClick(view);
                IGASDK.logout();
            }
        });
    }
}