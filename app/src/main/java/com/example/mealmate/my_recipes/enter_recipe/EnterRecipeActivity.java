package com.example.mealmate.my_recipes.enter_recipe;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mealmate.R;
import com.example.mealmate.my_recipes.MyRecipeBean;
import com.example.mealmate.my_recipes.MyRecipesActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Calendar;

//public class EnterRecipeActivity extends AppCompatActivity {
//
//    //Firebase Database Reference
//    private DatabaseReference db;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_enter_recipe);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        //Initializing firebase db reference
//        db = FirebaseDatabase.getInstance().getReference("My_Recipes");
//        EditText edtTitle = findViewById(R.id.titleEditText);
//        EditText edtIngredients = findViewById(R.id.ingredientsEditText);
//        EditText edtInstructions = findViewById(R.id.instructionsEditText);
//        ImageView imageBackBtn = findViewById(R.id.backImageView);
//        ImageView imageRecipe = findViewById(R.id.enterRecipeImageView);
//
//
//        Button submitBtn = findViewById(R.id.submitRecipeBtn);
//
//
//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String strTitle = edtTitle.getText().toString().trim(); //trim to remove white space
//                String strIngredients = edtIngredients.getText().toString().trim();
//                String strInstructions = edtInstructions.getText().toString().trim();
//                String strImage = String.valueOf(imageRecipe.getImageAlpha());
//
//                if (TextUtils.isEmpty(strTitle) && TextUtils.isEmpty(strIngredients) && TextUtils.isEmpty(strInstructions)){
//                    Toast.makeText(EnterRecipeActivity.this, "Empty Field", Toast.LENGTH_SHORT).show();
//                }
//                // Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_LONG).show();
////                Intent intentUserDetails = new Intent(MainActivity.this, DisplayActivity.class);
////                intentUserDetails.putExtra("namesKey", strNames);
////                intentUserDetails.putExtra("emailKey", strEmail);
////                startActivity(intentUserDetails);
//
//                String userId = db.push().getKey();
//                MyRecipeBean bean = new MyRecipeBean(userId, strTitle, strImage, strIngredients, strInstructions);
//                if(userId !=null){
//
//                    db.child(userId).setValue(bean);
//                    Toast.makeText(EnterRecipeActivity.this, "Data successfully saved!", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(EnterRecipeActivity.this, MyRecipesActivity.class);
//                    startActivity(intent);
//                }
//                else {
//                    Toast.makeText(EnterRecipeActivity.this, "Failed to save data!", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//
//    }
//}


public class EnterRecipeActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int REQUEST_PERMISSIONS = 100;
    private Uri imageUri;
    private DatabaseReference db;
    private StorageReference storageReference;
    private EditText edtTitle, edtIngredients, edtInstructions;
    private ImageView imageRecipe;
    private Button submitBtn;
    private ImageView imageBackBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enter_recipe);

        // Firebase initialization
        db = FirebaseDatabase.getInstance().getReference("My_Recipes");

        storageReference = FirebaseStorage.getInstance().getReference("Recipe_Images");

        edtTitle = findViewById(R.id.titleEditText);
        edtIngredients = findViewById(R.id.ingredientsEditText);
        edtInstructions = findViewById(R.id.instructionsEditText);
        imageRecipe = findViewById(R.id.enterRecipeImageView);
        submitBtn = findViewById(R.id.submitRecipeBtn);
        imageBackBtn = findViewById(R.id.backImageView);

        imageRecipe.setOnClickListener(v -> {
            // Choose between Camera or Gallery
            showImageSourceDialog();
        });

        submitBtn.setOnClickListener(v -> {
            String strTitle = edtTitle.getText().toString().trim();
            String strIngredients = edtIngredients.getText().toString().trim();
            String strInstructions = edtInstructions.getText().toString().trim();

            if (TextUtils.isEmpty(strTitle) || TextUtils.isEmpty(strIngredients) || TextUtils.isEmpty(strInstructions)) {
                Toast.makeText(EnterRecipeActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                uploadImageAndSaveRecipe(strTitle, strIngredients, strInstructions);
            }
        });
    }

    private void showImageSourceDialog() {
        String[] options = {"Camera", "Gallery"};
        new AlertDialog.Builder(this)
                .setTitle("Select Image Source")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        // Open Camera
                        captureFromCamera();
                    } else {
                        // Open Gallery
                        pickFromGallery();
                    }
                })
                .show();
    }

    private void captureFromCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void pickFromGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                imageRecipe.setImageBitmap(imageBitmap);
                // Convert bitmap to URI (you may need to save it locally before converting)
                imageUri = getImageUriFromBitmap(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                imageUri = data.getData();
                imageRecipe.setImageURI(imageUri);
            }
        }
    }

    private Uri getImageUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "TempImage", null);
        return Uri.parse(path);
    }

//    private void uploadImageAndSaveRecipe(String title, String ingredients, String instructions) {
//        if (imageUri != null) {
//            // Get the MIME type of the image
//            ContentResolver contentResolver = getContentResolver();
//            String mimeType = contentResolver.getType(imageUri);
//
//            // Get the file extension from the MIME type
//            String fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
//
//            if (fileExtension == null) {
//                // Default to jpg if unable to detect the file extension
//                fileExtension = "jpg";
//            }
//
//            // Create a reference in Firebase Storage using the correct extension
//            StorageReference imageRef = storageReference.child(System.currentTimeMillis() + "." + fileExtension);
//
//            imageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
//                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
//                    String imageUrl = uri.toString();
//                    saveRecipeToDatabase(title, imageUrl, ingredients, instructions);
//                });
//            }).addOnFailureListener(e -> {
//                Toast.makeText(EnterRecipeActivity.this, "Image upload failed!", Toast.LENGTH_SHORT).show();
//            });
//        }
//
//    }
//
//    private void saveRecipeToDatabase(String title, String imageUrl, String ingredients, String instructions) {
//        String userId = db.push().getKey();
//        MyRecipeBean bean = new MyRecipeBean(userId, title, imageUrl, ingredients, instructions);
//        if (userId != null) {
//            db.child(userId).setValue(bean);
//            Toast.makeText(EnterRecipeActivity.this, "Recipe successfully saved!", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(EnterRecipeActivity.this, MyRecipesActivity.class);
//            startActivity(intent);
//        } else {
//            Toast.makeText(EnterRecipeActivity.this, "Failed to save recipe!", Toast.LENGTH_LONG).show();
//        }
//    }

    private void uploadImageAndSaveRecipe(String title, String ingredients, String instructions) {
        if (imageUri != null) {
            // Get the MIME type of the image
            ContentResolver contentResolver = getContentResolver();
            String mimeType = contentResolver.getType(imageUri);

            // Get the file extension from the MIME type
            String fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);

            if (fileExtension == null) {
                // Default to jpg if unable to detect the file extension
                fileExtension = "jpg";
            }

            // Create a reference in Firebase Storage using the correct extension
            StorageReference imageRef = storageReference.child(System.currentTimeMillis() + "." + fileExtension);

            imageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    saveRecipeToDatabase(title, imageUrl, ingredients, instructions); // Save with image URL
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(EnterRecipeActivity.this, "Image upload failed! Saving recipe without image.", Toast.LENGTH_SHORT).show();
                saveRecipeToDatabase(title, null, ingredients, instructions); // Save without image URL
            });
        } else {
            // If no image is selected, save the recipe without an image URL
            saveRecipeToDatabase(title, null, ingredients, instructions);
        }
    }

//    private void saveRecipeToDatabase(String title, String imageUrl, String ingredients, String instructions) {
//        String userId = db.push().getKey();
//
//        // If no image URL is provided (null), you can set a default or leave it as null
//        if (imageUrl == null) {
//            imageUrl = "default_image_url";  // Optional: Add a placeholder URL if you have one
//        }
//
//        MyRecipeBean bean = new MyRecipeBean(userId, title, imageUrl, ingredients, instructions);
//        if (userId != null) {
//            db.child(userId).setValue(bean);
//            Toast.makeText(EnterRecipeActivity.this, "Recipe successfully saved!", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(EnterRecipeActivity.this, MyRecipesActivity.class);
//            startActivity(intent);
//        } else {
//            Toast.makeText(EnterRecipeActivity.this, "Failed to save recipe!", Toast.LENGTH_LONG).show();
//        }
//    }


    private void saveRecipeToDatabase(String title, String imageUrl, String ingredients, String instructions) {
        // Get current Firebase user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userInitials = "";

        if (currentUser != null) {
            // Extract initials from user's display name or email
            String displayName = currentUser.getDisplayName();
            if (displayName != null && !displayName.isEmpty()) {
                userInitials = getInitialsFromName(displayName);
            } else if (currentUser.getEmail() != null) {
                userInitials = getInitialsFromEmail(currentUser.getEmail());
            }
        }

        // Proceed with saving the recipe
        String userId = db.push().getKey();

        // If no image URL is provided (null), you can set a default or leave it as null
        if (imageUrl == null) {
            imageUrl = "default_image_url";  // Optional: Add a placeholder URL if you have one
        }

        MyRecipeBean bean = new MyRecipeBean(userId, title, imageUrl, ingredients, instructions);
        if (userId != null) {
            db.child(userId).setValue(bean);
            Toast.makeText(EnterRecipeActivity.this, "Recipe saved! User initials: " + userInitials, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EnterRecipeActivity.this, MyRecipesActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(EnterRecipeActivity.this, "Failed to save recipe!", Toast.LENGTH_LONG).show();
        }
    }

    private String getInitialsFromName(String name) {
        String[] nameParts = name.split(" ");
        StringBuilder initials = new StringBuilder();
        for (String part : nameParts) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0));
            }
        }
        return initials.toString().toUpperCase();  // Return initials in uppercase
    }

    private String getInitialsFromEmail(String email) {
        if (email == null || !email.contains("@")) return "";
        String[] emailParts = email.split("@")[0].split("\\.");
        StringBuilder initials = new StringBuilder();
        for (String part : emailParts) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0));
            }
        }
        return initials.toString().toUpperCase();  // Return initials in uppercase
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

