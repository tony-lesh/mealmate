//package com.example.mealmate.registration_signin;
//
//import static android.widget.Toast.LENGTH_SHORT;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.mealmate.R;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//
//public class MainActivity extends AppCompatActivity {
//
//    private DatabaseReference db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // ... rest of the code for setting up UI ...
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        //Initializing firebase db reference
//        db = FirebaseDatabase.getInstance().getReference("Users");
//
//        TextView textViewSignup = findViewById(R.id.signupTextView);
//        LinearLayout layoutFacebook = findViewById(R.id.facebook);
//        LinearLayout layoutGoogle = findViewById(R.id.google);
//        EditText editTextEmail = findViewById(R.id.editTextUserEmail);
//        EditText editTextPassword = findViewById(R.id.editTextUserPassword);
//        Button submitBtn = findViewById(R.id.loginBtn);
//
//        textViewSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getApplicationContext(), "SignUp Clicked!", Toast.LENGTH_LONG).show();
//                Intent signupIntent = new Intent(MainActivity.this, RegisterActivity.class);
//                startActivity(signupIntent);
//            }
//        });
//
//      /*  submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = editTextEmail.getText().toString();
//                String password = editTextPassword.getText().toString();
//
//                // Check for empty fields
//                if (username.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Fetch user data based on username
//                db.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            User user = snapshot.getValue(User.class);
//                            if (user != null && user.getPassword().equals(password)) {
//                                // Login successful
//                                Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(MainActivity.this, RegistrationSuccessActivity.class);
////                                intent.putExtra("emailKey", username);
////                                intent.putExtra("passwordKey", password);
//                                startActivity(intent);
//
//                                // Navigate to another activity or perform actions after successful login
//                            } else {
//                                // Login failed - Invalid password
//                                Toast.makeText(MainActivity.this, "Login failed. Incorrect credentials.", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            // Login failed - Username not found
//                            Toast.makeText(MainActivity.this, "Login failed. Username not found.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        // Handle database errors
//                        Toast.makeText(MainActivity.this, "Login failed. Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });*/
//
//        // ... rest of the code ...
//
//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String userId = editTextEmail.getText().toString();
//                String password = editTextPassword.getText().toString();
//
//                // Check for empty fields
//                if (userId.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please fill in all fields", LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Fetch user data based on username
//                db.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////                        if (snapshot.exists()) {
//                            User user = snapshot.getValue(User.class);
//                            if (user != null && user.getPassword().equals(password)) {
//                                // Login successful
//                                Toast.makeText(MainActivity.this, "Login successful!", LENGTH_SHORT).show();
//                                Intent intent = new Intent(MainActivity.this, LandingPageActivity.class);
//                                startActivity(intent);
//
////                                // Add a delay before proceeding to the main activity
////                                Handler handler = new Handler();
////                                handler.postDelayed(new Runnable() {
////                                    @Override
////                                    public void run() {
////                                        // Intent to the main activity after the delay
////                                        Intent mainIntent = new Intent(MainActivity.this, LandingPageActivity.class);
////                                        startActivity(mainIntent);
////                                        finish(); // Finish the splash screen activity
////                                    }
////                                }, 3000); // Delay of 3 seconds
//                                // Navigate to another activity or perform actions after successful login
//                            } else {
//                                // Login failed - Invalid password or username not found
//                                Toast.makeText(MainActivity.this, "Login failed. Incorrect credentials.", LENGTH_SHORT).show();
//                            }
////                        } else {
////                            // Login failed - Username not found
////                            Toast.makeText(MainActivity.this, "Login failed. Username not found.", LENGTH_SHORT).show();
////                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        // Handle database errors
//                        Toast.makeText(MainActivity.this, "Login failed. Error: " + error.getMessage(), LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }
//
//}

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mealmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth; // Firebase Authentication instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        TextView textViewSignup = findViewById(R.id.signupTextView);
        LinearLayout layoutFacebook = findViewById(R.id.facebook);
        LinearLayout layoutGoogle = findViewById(R.id.google);
        EditText editTextEmail = findViewById(R.id.editTextUserEmail);
        EditText editTextPassword = findViewById(R.id.editTextUserPassword);
        Button submitBtn = findViewById(R.id.loginBtn);

        textViewSignup.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "SignUp Clicked!", Toast.LENGTH_LONG).show();
            Intent signupIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(signupIntent);
        });

        // Handle Login Click
        submitBtn.setOnClickListener(v -> {
            String strEmail = editTextEmail.getText().toString().trim();
            String strPassword = editTextPassword.getText().toString().trim();

            if (strEmail.isEmpty() || strPassword.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Login with Firebase Authentication
                loginWithFirebase(strEmail, strPassword);
            }
        });
    }

    private void loginWithFirebase(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();

                            // Delay navigation to the main activity
                            new Handler().postDelayed(() -> {
                                Intent intent = new Intent(MainActivity.this, LandingPageActivity.class); // Replace with your landing activity
                                startActivity(intent);
                                finish();
                            }, 2000); // 2 seconds delay
                        }
                    } else {
                        // Login failed
                        Toast.makeText(MainActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}

//package com.example.mealmate.registration_signin;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
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
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class MainActivity extends AppCompatActivity {
//
//    //Firebase Database Reference
//    private DatabaseReference db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);
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
////        TextView textViewRegister = findViewById(R.id.loginTextView);  // Change text to "Register"
////        EditText editTextEmail = findViewById(R.id.editTextPersonEmail);
////        EditText editTextPassword = findViewById(R.id.editTextPersonPassword);
////        Button loginButton = findViewById(R.id.registerBtn);  // Change button text to "Login"
//
//        TextView textViewSignup = findViewById(R.id.signupTextView);
//        LinearLayout layoutFacebook = findViewById(R.id.facebook);
//        LinearLayout layoutGoogle = findViewById(R.id.google);
//        EditText editTextEmail = findViewById(R.id.editTextUserEmail);
//        EditText editTextPassword = findViewById(R.id.editTextUserPassword);
//        Button submitBtn = findViewById(R.id.loginBtn);
//
//        textViewSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getApplicationContext(), "SignUp Clicked!", Toast.LENGTH_LONG).show();
//                Intent signupIntent = new Intent(MainActivity.this, RegisterActivity.class);
//                startActivity(signupIntent);
//            }
//        });
//
//        // Handle Login Click
//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String strEmail = editTextEmail.getText().toString();
//                String strPassword = editTextPassword.getText().toString();
//
//                if (strEmail.isEmpty() || strPassword.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Attempt to login with Firebase
//                    loginWithFirebase(strEmail, strPassword);
//                }
//            }
//        });
//    }
//
//    private void loginWithFirebase(String email, String password) {
//        db.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getValue() != null) {
//                    // User exists, check password
//                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                        User user = userSnapshot.getValue(User.class);
//                        if (user != null && user.getPassword().equals(password)) {
//                            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
//
//                            // Delay navigation to the main activity
//                            new Handler().postDelayed(() -> {
//                                Intent intent = new Intent(MainActivity.this, LandingPageActivity.class);
//                                startActivity(intent);
//                                finish();
//
//                            }, 2000);
//                            // 2 seconds delay
//
//                            // Start main activity or other logged-in activity
//                            Intent intent = new Intent(MainActivity.this, RegistrationSuccessActivity.class);  // Replace with your main activity
//                            startActivity(intent);
//                            return;
//                        }
//                    }
//                    // Password doesn't match
//                    Toast.makeText(MainActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
//                } else {
//                    // User doesn't exist
//                    Toast.makeText(MainActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(MainActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}
