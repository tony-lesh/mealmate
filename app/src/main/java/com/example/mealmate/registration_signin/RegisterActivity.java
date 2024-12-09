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
import com.example.mealmate.general.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
// Uncomment the following if you want to use Firestore instead of Realtime Database
// import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth; // Firebase Authentication reference
    private DatabaseReference db; // Firebase Realtime Database reference
    // Uncomment the following if you want to use Firestore instead
    // private FirebaseFirestore firestore; // Firestore reference

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

        // Initialize Firebase services
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("Users");
        // Uncomment the following if you want to use Firestore instead
        // firestore = FirebaseFirestore.getInstance();

        TextView textViewLogin = findViewById(R.id.loginTextView);
        EditText editTextNames = findViewById(R.id.editTextPersonNames);
        EditText editTextEmail = findViewById(R.id.editTextPersonEmail);
        EditText editTextPassword = findViewById(R.id.editTextPersonPassword);
        EditText editTextConfirmPassword = findViewById(R.id.editTextPersonConfirmPassword);
        Button registerButton = findViewById(R.id.registerBtn);

        textViewLogin.setOnClickListener(v -> {
            Intent loginIntent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(loginIntent);
        });

        registerButton.setOnClickListener(v -> {
            String strNames = editTextNames.getText().toString().trim();
            String strEmail = editTextEmail.getText().toString().trim();
            String strPassword = editTextPassword.getText().toString().trim();
            String strConfirmPassword = editTextConfirmPassword.getText().toString().trim();

            // Input validation
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
                                FirebaseUser user = auth.getCurrentUser();
                                if (user != null) {
                                    String userId = user.getUid();
                                    saveUserDetailsToDatabase(userId, strNames, strEmail, strPassword, strConfirmPassword);
                                    Toast.makeText(RegisterActivity.this, "Successfully Registered!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    /**
     * Save the user's details to Realtime Database (or Firestore if preferred)
     * @param userId The UID of the registered user
     * @param name The name of the user
     * @param email The email of the user
     */
    private void saveUserDetailsToDatabase(String userId, String name, String email, String password, String confirmPassword) {
        // Use this for Realtime Database
        User user = new User(userId, name, email, password, confirmPassword);
        db.child(userId).setValue(user)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(RegisterActivity.this, "User info saved successfully!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(RegisterActivity.this, "Failed to save user info: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );

        // Uncomment this section to use Firestore instead of Realtime Database
        /*
        Map<String, Object> user = new HashMap<>();
        user.put("userId", userId);
        user.put("name", name);
        user.put("email", email);

        firestore.collection("Users").document(userId).set(user)
                .addOnSuccessListener(aVoid ->
                    Toast.makeText(RegisterActivity.this, "User info saved successfully!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                    Toast.makeText(RegisterActivity.this, "Failed to save user info: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
        */
    }
}

//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Patterns;
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
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//public class RegisterActivity extends AppCompatActivity {
//
//    private FirebaseAuth auth; // Firebase Authentication reference
//
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
//        // Initialize Firebase Auth
//        auth = FirebaseAuth.getInstance();
//
//        TextView textViewLogin = findViewById(R.id.loginTextView);
//        EditText editTextNames = findViewById(R.id.editTextPersonNames);
//        EditText editTextEmail = findViewById(R.id.editTextPersonEmail);
//        EditText editTextPassword = findViewById(R.id.editTextPersonPassword);
//        EditText editTextConfirmPassword = findViewById(R.id.editTextPersonConfirmPassword);
//        Button registerButton = findViewById(R.id.registerBtn);
//
//        textViewLogin.setOnClickListener(v -> {
//            Toast.makeText(getApplicationContext(), "Login Clicked!", Toast.LENGTH_LONG).show();
//            Intent signupIntent = new Intent(RegisterActivity.this, MainActivity.class);
//            startActivity(signupIntent);
//        });
//
//        registerButton.setOnClickListener(v -> {
//            String strNames = editTextNames.getText().toString().trim();
//            String strEmail = editTextEmail.getText().toString().trim();
//            String strPassword = editTextPassword.getText().toString().trim();
//            String strConfirmPassword = editTextConfirmPassword.getText().toString().trim();
//
//            if (strNames.isEmpty() || strEmail.isEmpty() || strPassword.isEmpty() || strConfirmPassword.isEmpty()) {
//                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
//            } else if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
//                Toast.makeText(RegisterActivity.this, "Invalid Email Format!", Toast.LENGTH_SHORT).show();
//            } else if (!strPassword.equals(strConfirmPassword)) {
//                Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
//            } else if (strPassword.length() < 6) {
//                Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
//            } else {
//                // Register user with Firebase Authentication
//                auth.createUserWithEmailAndPassword(strEmail, strPassword)
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                // Registration successful
//                                FirebaseUser user = auth.getCurrentUser();
//                                if (user != null) {
//                                    Toast.makeText(RegisterActivity.this, "Successfully Registered!", Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            } else {
//                                // Registration failed
//                                Toast.makeText(RegisterActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                            }
//                        });
//            }
//        });
//    }
//}
//
