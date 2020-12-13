package com.example.alarmproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {
    Button login;
    Button signup;
    public final String TAG = "Logcat";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity_sub);

        login = findViewById(R.id.login_page);
        signup = findViewById(R.id.sign_up_page);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent = new Intent(AccountActivity.this, LoginPageActivity.class);
                startActivity(login_intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup_intent = new Intent(AccountActivity.this, SignUpPageActivity.class);
                startActivity(signup_intent);

            }
        });
    }
}
