package com.example.mealmate.registration_signin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.mealmate.R;

public class RegistrationSuccessActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_registration);

//        new Runnable() {
//            public void postDelayed(int i) {
//            }
//
//            @Override
//            public void run() {
//                Intent intent = new Intent(RegistrationSuccessActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }.postDelayed(2000);

    }
}
