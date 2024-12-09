package com.example.mealmate.registration_signin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealmate.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000; // 2 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Delaying the transition to the next activity
        new Handler().postDelayed(() -> {
            // Navigate to the Landing Page Activity
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close SplashScreenActivity to prevent users from navigating back to it
        }, SPLASH_DELAY);
    }
}
