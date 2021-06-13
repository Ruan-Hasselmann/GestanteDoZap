package com.example.gestantedozap;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoginActivity();
            }
        }, 3000);
    }

    private void showLoginActivity(){
        Intent intent = new Intent(
                LauncherActivity.this, Login.class
        );
        startActivity(intent);
        finish();
    }
}