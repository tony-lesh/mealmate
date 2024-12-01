package com.example.mealmate.registration_signin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealmate.R;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Runnable() {
            public void postDelayed(int i) {
            }

            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LandingPageActivity.class);
                startActivity(intent);
                finish();
            }
        }.postDelayed(2000);
    }
}
