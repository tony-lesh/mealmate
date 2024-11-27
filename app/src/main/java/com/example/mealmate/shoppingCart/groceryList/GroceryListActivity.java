package com.example.mealmate.shoppingCart.groceryList;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealmate.R;
import com.example.mealmate.general.SpoonAcularAPI;
import com.example.mealmate.shoppingCart.ShoppingCartAdapter;
import com.example.mealmate.shoppingCart.ShoppingCartBean;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroceryListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<GrocerySearchResponse.GroceryItem> grocery;
    private GroceryListAdapter groceryAdapter;

    private static final String API_KEY = "4fc61c7816c2497f8512e16e1cf4e863";
    private static final String BASE_URL = "https://api.spoonacular.com/";

    // Firebase instances
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;
    private CollectionReference groceryRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize shopping cart items list
        grocery = new ArrayList<>();

        recyclerView = findViewById(R.id.groceryListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        Integer recipeId = getIntent().getIntExtra("id", -1);
//        String recipeName = getIntent().getStringExtra("name");
//
//        // Check if the recipe data is valid
//        if (recipeId != -1 && recipeName != null) {
//            GrocerySearchResponse.GroceryItem item = new GrocerySearchResponse.GroceryItem();
//            item.setName(recipeName);
//            item.setAmount(1);  // Assuming amount is 1, adjust based on logic
//            item.setUnit("unit");  // Assuming a default unit, update accordingly
//
//            grocery.add(item);
//            groceryAdapter = new GroceryListAdapter(this, grocery);
//            recyclerView.setAdapter(groceryAdapter);
//        }

        // Get the current authenticated user
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // Initialize Firestore collection with the user's UID
            groceryRef = db.collection("groceryList").document(currentUser.getUid()).collection("items");

            Integer recipeId = getIntent().getIntExtra("id", -1);
            String recipeName = getIntent().getStringExtra("name");

            // Check if the recipe data is valid
            if (recipeId != -1 && recipeName != null) {
                GrocerySearchResponse.GroceryItem item = new GrocerySearchResponse.GroceryItem();
                item.setName(recipeName);
                item.setAmount(1);  // Assuming amount is 1, adjust based on logic
                item.setUnit("unit");  // Assuming a default unit, update accordingly

                grocery.add(item);
                groceryAdapter = new GroceryListAdapter(this, grocery);
                recyclerView.setAdapter(groceryAdapter);

                // Save grocery item to Firestore for the authenticated user
                saveGroceryItemToFirestore(item);
            }
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    // Function to save grocery item to Firestore
    private void saveGroceryItemToFirestore(GrocerySearchResponse.GroceryItem item) {
        // Create a map for the grocery item
        Map<String, Object> groceryItem = new HashMap<>();
        groceryItem.put("name", item.getName());
        groceryItem.put("amount", item.getAmount());
        groceryItem.put("unit", item.getUnit());

        // Add the item to the Firestore collection for the current user
        groceryRef.add(groceryItem)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(GroceryListActivity.this, "Item added to grocery list in Firebase", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(GroceryListActivity.this, "Error adding item to Firebase", Toast.LENGTH_SHORT).show();
                });
    }

    // Function to retrieve grocery items from Firestore
    private void fetchGroceryItemsFromFirestore() {
        groceryRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<GrocerySearchResponse.GroceryItem> retrievedGroceryItems = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // Assuming GrocerySearchResponse.GroceryItem has a constructor or setters to accept this data
                        GrocerySearchResponse.GroceryItem item = document.toObject(GrocerySearchResponse.GroceryItem.class);
                        retrievedGroceryItems.add(item);
                    }

                    // Set retrieved data to the adapter
                    groceryAdapter = new GroceryListAdapter(GroceryListActivity.this, retrievedGroceryItems);
                    recyclerView.setAdapter(groceryAdapter);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(GroceryListActivity.this, "Error retrieving items from Firebase", Toast.LENGTH_SHORT).show();
                });
    }

    // Fetch grocery items when the activity starts
    @Override
    public void onStart() {
        super.onStart();
        if (currentUser != null) {
            fetchGroceryItemsFromFirestore(); // Fetch data from Firestore when the activity starts
        }
    }

//    private void fetchIngredients(int recipeId) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        SpoonAcularAPI spoonacularApi = retrofit.create(SpoonAcularAPI.class);
//        GroceryListBean groceryListBean = new GroceryListBean(recipeId);
//        Call<GrocerySearchResponse> call = spoonacularApi.shopList(API_KEY, groceryListBean.getServings());
//
//        call.enqueue(new Callback<GrocerySearchResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<GrocerySearchResponse> call, @NonNull Response<GrocerySearchResponse> response) {
//                if (response.body() != null) {
//                    Log.d("API Response", response.body().toString());
//
//                    if (response.isSuccessful()) {
//                        GrocerySearchResponse responseBody = response.body();
//
//                        if (responseBody != null && responseBody.getItems() != null) {
//                            grocery = responseBody.getItems();
//
//                            if (grocery != null && !grocery.isEmpty()) {
//                                groceryAdapter = new GroceryListAdapter(GroceryListActivity.this, grocery);
//                                recyclerView.setAdapter(groceryAdapter);
//                            } else {
//                                Log.e("GroceryListActivity", "No grocery items found in the response");
//                                Toast.makeText(GroceryListActivity.this, "No grocery items found", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Log.e("GroceryListActivity", "Response body or items are null");
//                            Toast.makeText(GroceryListActivity.this, "Error: No grocery items found", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Log.e("GroceryListActivity", "API call failed. Response code: " + response.code());
//                        Log.e("GroceryListActivity", "Response message: " + response.message());
//                        Toast.makeText(GroceryListActivity.this, "Error fetching data. Response code: " + response.code(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Log.e("GroceryListActivity", "Response body is null");
//                    Toast.makeText(GroceryListActivity.this, "Error: Response is null", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<GrocerySearchResponse> call, @NonNull Throwable t) {
//                Log.e("GroceryListActivity", "Network error: " + t.getMessage());
//                Toast.makeText(GroceryListActivity.this, "Please check your connection and try again", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//        private boolean isNetworkAvailable () {
//            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//        }
}
