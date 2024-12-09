package com.example.mealmate.registration_signin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mealmate.R;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // Firebase Authentication instance
    private FirebaseAuth auth;

    // UI Components
    private EditText editTextEmail, editTextPassword;
    private Button submitBtn;
    private TextView textViewSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Set window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Link UI components to variables
        textViewSignup = findViewById(R.id.signupTextView);
        editTextEmail = findViewById(R.id.editTextUserEmail);
        editTextPassword = findViewById(R.id.editTextUserPassword);
        submitBtn = findViewById(R.id.loginBtn);

        // Navigate to SignUp page when the signup text is clicked
        textViewSignup.setOnClickListener(v -> {
            Intent signupIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(signupIntent);
        });

        // Handle Login Button Click
        submitBtn.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                loginWithFirebase(email, password);
            }
        });
    }

    /**
     * Handles user login using Firebase Authentication
     *
     * @param email    The user's email
     * @param password The user's password
     */
    private void loginWithFirebase(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                            // Delay navigation to the LandingPageActivity
                            new Handler().postDelayed(() -> {
                                Intent intent = new Intent(MainActivity.this, LandingPageActivity.class);
                                startActivity(intent);
                                finish();
                            }, 2000); // 2-second delay
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(MainActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
