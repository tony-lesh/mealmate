//package com.example.mealmate.shoppingCart;
//
//import android.os.Bundle;
//import android.os.PersistableBundle;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.mealmate.R;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Shopping_Cart extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private ShoppingCartAdapter adapter;
//    private List<ShoppingCartBean> shoppingCartItems;
//
//    private FirebaseFirestore firestore;
//    private FirebaseAuth auth;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Initialize FirebaseAuth and Firestore
//        auth = FirebaseAuth.getInstance();
//        firestore = FirebaseFirestore.getInstance();
//
//        setContentView(R.layout.shopping_cart_display);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // Initialize RecyclerView and set up the GridLayoutManager
//        recyclerView = findViewById(R.id.shoppingCartDisplay);
//        int numberOfColumns = 2; // Set your desired number of columns
//        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
//
//        // Initialize shopping cart items list
//        shoppingCartItems = new ArrayList<>();
//
//        // Retrieve data from the Intent
//        String recipeName = getIntent().getStringExtra("recipeName");
//        String recipeImage = getIntent().getStringExtra("recipeImage");
//        int recipeId = getIntent().getIntExtra("id", -1);
//
//        // Add retrieved data to the shoppingCartItems list
//        String userId = null;
//        if (recipeId != -1 && recipeName != null && recipeImage != null) {
//            shoppingCartItems.add(new ShoppingCartBean(recipeId, recipeName, recipeImage));
//
//            // Firebase Auth for signing in users
//            auth.signInAnonymously()
//                    .addOnCompleteListener(this, task -> {
//                        if (task.isSuccessful()) {
//                            FirebaseUser currentUser = auth.getCurrentUser();
//                            if (recipeId != -1 && recipeName != null && recipeImage != null) {
//                                // Create the new cart item and add it to the shoppingCartItems list
//                                ShoppingCartBean newItem = new ShoppingCartBean(recipeId, recipeName, recipeImage);
//                                shoppingCartItems.add(newItem);
//
//                                // Store the cart item in Firestore
//                                firestore.collection("shoppingCarts")
//                                        .document(currentUser.getUid())  // Use user ID for the document
//                                        .collection("items")  // Sub-collection for items
//                                        .document(String.valueOf(recipeId))  // Unique document ID (recipeId)
//                                        .set(newItem)
//                                        .addOnSuccessListener(aVoid -> {
//                                            Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
//                                        })
//                                        .addOnFailureListener(e -> {
//                                            Toast.makeText(this, "Error adding to cart", Toast.LENGTH_SHORT).show();
//                                        });
//                            }
//                        } else {
//                            Toast.makeText(Shopping_Cart.this, "Authentication failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//            // Retrieve the shopping cart items from Firestore
//            firestore.collection("shoppingCarts")
//                    .document(userId)
//                    .collection("items")
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            shoppingCartItems.clear();  // Clear existing items
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                ShoppingCartBean item = document.toObject(ShoppingCartBean.class);
//                                shoppingCartItems.add(item);  // Add each item to the local list
//                            }
//                            adapter.notifyDataSetChanged();  // Notify adapter to refresh the list
//                        } else {
//                            Toast.makeText(this, "Error loading cart", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//
//            // Initialize the adapter with the updated list and set it to the RecyclerView
//            adapter = new ShoppingCartAdapter(this, shoppingCartItems);
//            recyclerView.setAdapter(adapter);
//
//            // Show a toast if the cart is empty
//            if (shoppingCartItems.isEmpty()) {
//                Toast.makeText(this, "No items in cart", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//}

package com.example.mealmate.shoppingCart;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Shopping_Cart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShoppingCartAdapter adapter;
    private List<ShoppingCartBean> shoppingCartItems;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize FirebaseAuth and Firestore
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        setContentView(R.layout.shopping_cart_display);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView and set up the GridLayoutManager
        recyclerView = findViewById(R.id.shoppingCartDisplay);
        int numberOfColumns = 2; // Set your desired number of columns
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        // Initialize shopping cart items list
        shoppingCartItems = new ArrayList<>();

        // Retrieve data from the Intent
        String recipeName = getIntent().getStringExtra("recipeName");
        String recipeImage = getIntent().getStringExtra("recipeImage");
        int recipeId = getIntent().getIntExtra("id", -1);

        // Check if the recipe data is valid
        if (recipeId != -1 && recipeName != null && recipeImage != null) {
            // Add retrieved data to the shoppingCartItems list
            shoppingCartItems.add(new ShoppingCartBean(recipeId, recipeName, recipeImage));

            // Firebase Auth for signing in users
            auth.signInAnonymously()
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = auth.getCurrentUser();
                            if (currentUser != null) {
                                String userId = currentUser.getUid(); // Get user ID

                                // Add item to Firestore
                                addCartItemToFirestore(userId, recipeId, recipeName, recipeImage);

                                // Retrieve the shopping cart items from Firestore
                                loadCartItemsFromFirestore(userId);
                            }
                        } else {
                            Toast.makeText(Shopping_Cart.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        // You can retrieve the current user's ID from FirebaseAuth (if the user is authenticated)
        String userId = getUserId(); // Replace this with your method of getting the user ID

        // Load cart items from Firestore when the activity is created
        loadCartItemsFromFirestore(userId);
    }

    private String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    private void addCartItemToFirestore(String userId, int recipeId, String recipeName, String recipeImage) {
        ShoppingCartBean newItem = new ShoppingCartBean(recipeId, recipeName, recipeImage);

        firestore.collection("shoppingCarts")
                .document(userId)  // Use user ID for the document
                .collection("items")  // Sub-collection for items
                .document(String.valueOf(recipeId))  // Unique document ID (recipeId)
                .set(newItem)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error adding to cart", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadCartItemsFromFirestore(String userId) {
        firestore.collection("shoppingCarts")
                .document(userId)
                .collection("items")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        shoppingCartItems.clear();  // Clear existing items
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ShoppingCartBean item = document.toObject(ShoppingCartBean.class);
                            shoppingCartItems.add(item);  // Add each item to the local list
                        }

                        // Initialize the adapter with the updated list and set it to the RecyclerView
                        adapter = new ShoppingCartAdapter(this, shoppingCartItems);
                        recyclerView.setAdapter(adapter);

                        // Show a toast if the cart is empty
                        if (shoppingCartItems.isEmpty()) {
                            Toast.makeText(this, "No items in cart", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "Error loading cart", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Ensure cart items are loaded from Firestore when the activity starts
        String userId = getUserId(); // Replace this with your method of getting the user ID
        loadCartItemsFromFirestore(userId);
    }
}

