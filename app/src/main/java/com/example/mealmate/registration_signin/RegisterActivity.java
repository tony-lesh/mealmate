//package com.example.mealmate.registration_signin;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.mealmate.R;
//import com.example.mealmate.general.User;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class RegisterActivity extends AppCompatActivity {
//
//    //Firebase Database Reference
//    private DatabaseReference db;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_register);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        //Initializing firebase db reference
//        db = FirebaseDatabase.getInstance().getReference("Users");
//
//        TextView textViewLogin = findViewById(R.id.loginTextView);
//        EditText editTextNames = findViewById(R.id.editTextPersonNames);
//        EditText editTextEmail = findViewById(R.id.editTextPersonEmail);
//        EditText editTextPassword = findViewById(R.id.editTextPersonPassword);
//        EditText editTextConfirmPassword = findViewById(R.id.editTextPersonConfirmPassword);
//        Button registerButton = findViewById(R.id.registerBtn);
//
//        textViewLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getApplicationContext(), "Login Clicked!", Toast.LENGTH_LONG).show();
//                Intent signupIntent = new Intent(RegisterActivity.this, MainActivity.class);
//                startActivity(signupIntent);
//            }
//        });
//
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String strNames = editTextNames.getText().toString();
//                String strEmail = editTextEmail.getText().toString();
//                String strPassword = editTextPassword.getText().toString();
//                String strConfirmPassword = editTextConfirmPassword.toString();
//
//
//                if (strNames.isEmpty() || strEmail.isEmpty() || strPassword.isEmpty() || strConfirmPassword.isEmpty()) {
//                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
//                }
//                else if (!strEmail.endsWith("@gmail.com") && !strEmail.endsWith(".co.bw")){
//                    Toast.makeText(RegisterActivity.this, "Incorrect Email Format!", Toast.LENGTH_SHORT).show();
//                }else {
//                    // Save the signup details to a text file
//                  //  saveSignupDetails(strNames, strEmail, strPassword);
//                }
//
//                String userId = db.push().getKey();
//                User user = new User(userId, strNames, strEmail, strPassword, strConfirmPassword);
//                if(userId !=null){
//
//                    db.child(userId).setValue(user);
//                    Toast.makeText(RegisterActivity.this, "Successfully Registered!", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class); // Replace SignUpActivity with your signup activity
//                    startActivity(intent);
//                }
//                else {
//                    Toast.makeText(RegisterActivity.this, "Registration Failed!", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
//}
//
//
//

package com.example.mealmate.registration_signin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mealmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth; // Firebase Authentication reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        TextView textViewLogin = findViewById(R.id.loginTextView);
        EditText editTextNames = findViewById(R.id.editTextPersonNames);
        EditText editTextEmail = findViewById(R.id.editTextPersonEmail);
        EditText editTextPassword = findViewById(R.id.editTextPersonPassword);
        EditText editTextConfirmPassword = findViewById(R.id.editTextPersonConfirmPassword);
        Button registerButton = findViewById(R.id.registerBtn);

        textViewLogin.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Login Clicked!", Toast.LENGTH_LONG).show();
            Intent signupIntent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(signupIntent);
        });

        registerButton.setOnClickListener(v -> {
            String strNames = editTextNames.getText().toString().trim();
            String strEmail = editTextEmail.getText().toString().trim();
            String strPassword = editTextPassword.getText().toString().trim();
            String strConfirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (strNames.isEmpty() || strEmail.isEmpty() || strPassword.isEmpty() || strConfirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                Toast.makeText(RegisterActivity.this, "Invalid Email Format!", Toast.LENGTH_SHORT).show();
            } else if (!strPassword.equals(strConfirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            } else if (strPassword.length() < 6) {
                Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            } else {
                // Register user with Firebase Authentication
                auth.createUserWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Registration successful
                                FirebaseUser user = auth.getCurrentUser();
                                if (user != null) {
                                    Toast.makeText(RegisterActivity.this, "Successfully Registered!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                // Registration failed
                                Toast.makeText(RegisterActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}

