package com.example.mealmate.shoppingCart.groceryList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealmate.R;
import com.example.mealmate.general.SpoonAcularAPI;
import com.example.mealmate.recipe.ingredients.Ingredient;
import com.example.mealmate.shoppingCart.ShoppingCartAdapter;
import com.example.mealmate.shoppingCart.ShoppingCartBean;
import com.google.android.material.tabs.TabLayout;
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
    private List<GroceryListBean> grocery;
    private GroceryListAdapter groceryAdapter;

    private static final String API_KEY = "290447a9ac5f4260a69b9d1abd513523";
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



        Integer recipeId = getIntent().getIntExtra("id", -1);

        // Initialize shopping cart items list
        grocery = new ArrayList<>();
        recyclerView = findViewById(R.id.groceryListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (recipeId != -1) {
            if (isNetworkAvailable()) {
                fetchIngredients(recipeId);
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No Recipe ID passed", Toast.LENGTH_SHORT).show();
        }

//        // Get references to the views
//        TabLayout tabLayout = findViewById(R.id.tabLayout);
//     //   TabLayout shareTabLayout = findViewById(R.id.shareTabLayout);
//        ImageButton showMoreButton = findViewById(R.id.groceryListShowMore);
//        ImageButton deleteAllButton = findViewById(R.id.groceryListDeleteAll);

//        // Setup the "Share" tab
//        shareTabLayout.addTab(shareTabLayout.newTab().setText("Share"));
//
//        // Initially hide both TabLayouts
//        tabLayout.setVisibility(View.GONE);
//        shareTabLayout.setVisibility(View.GONE);

        // Set up the "Show More" button to toggle TabLayout visibility
//        showMoreButton.setOnClickListener(v -> {
//            if (shareTabLayout.getVisibility() == View.VISIBLE) {
//                shareTabLayout.setVisibility(View.GONE);
//            } else {
//                shareTabLayout.setVisibility(View.VISIBLE);
//            }
//        });

//        // Set a click listener on the "Share" tab
//        shareTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getText().equals("Share")) {
//                    showShareOptionsDialog(); // Show share options when "Share" tab is selected
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {}
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {}
//        });

        // Get references to the views
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ImageView showMoreButton = findViewById(R.id.groceryListShowMore);

        // Initially hide the TabLayout
        tabLayout.setVisibility(View.GONE);

        // Add tabs to TabLayout (example tabs)
        tabLayout.addTab(tabLayout.newTab().setText("WhatsApp"));
        tabLayout.addTab(tabLayout.newTab().setText("SMS"));
        tabLayout.addTab(tabLayout.newTab().setText("Other"));

        // Set up the "Show More" button to toggle TabLayout visibility
        showMoreButton.setOnClickListener(v -> {
            if (tabLayout.getVisibility() == View.VISIBLE) {
                tabLayout.setVisibility(View.GONE);  // Hide TabLayout if it's visible
            } else {
                tabLayout.setVisibility(View.VISIBLE);  // Show TabLayout if it's hidden
            }
        });

        // Optionally: Set up a listener to react when a tab is selected
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Handle the tab selection here
                String selectedTab = tab.getText().toString();
                if (selectedTab.equals("WhatsApp")) {
                    // Call your WhatsApp sharing method
                    shareViaWhatsApp();
                } else if (selectedTab.equals("SMS")) {
                    // Call your SMS sharing method
                    shareViaSMS();
                } else if (selectedTab.equals("Other")) {
                    // Call your "Other" sharing method
                    shareViaOther();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Optional: Handle unselected tab
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Optional: Handle reselected tab
            }
        });

    }

    private void fetchIngredients(int recipeId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpoonAcularAPI spoonacularApi = retrofit.create(SpoonAcularAPI.class);
        Call<GrocerySearchResponse> call = spoonacularApi.shopList(recipeId, API_KEY);
        call.enqueue(new Callback<GrocerySearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<GrocerySearchResponse> call, @NonNull Response<GrocerySearchResponse> response) {
                if (response.isSuccessful()) {
                    GrocerySearchResponse responseBody = response.body();
                    if (responseBody != null && responseBody.getItems() != null) {
                        grocery = responseBody.getItems();
                        groceryAdapter = new GroceryListAdapter(GroceryListActivity.this, grocery);
                        recyclerView.setAdapter(groceryAdapter);
                    } else {
                        Log.e("GroceryListActivity", "Response body or items are null");
                        Toast.makeText(GroceryListActivity.this, "Error: No grocery items found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("GroceryListActivity", "API call failed. Response code: " + response.code());
                    Toast.makeText(GroceryListActivity.this, "Failed to fetch data. Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GrocerySearchResponse> call, @NonNull Throwable t) {
                Log.e("GroceryListActivity", "Network error: " + t.getMessage());
                Toast.makeText(GroceryListActivity.this, "Please check your connection and try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

        private boolean isNetworkAvailable () {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int selectedID = item.getItemId();
//
//        if (selectedID == R.id.menu_share) {
//            // Show a dialog or prompt to let the user choose between different sharing options
//            showShareOptionsDialog();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void showShareOptionsDialog() {
//        // Create a dialog or popup to select SMS, WhatsApp, or other sharing options
//        final CharSequence[] options = {"WhatsApp", "SMS", "Other"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Share via");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("WhatsApp")) {
//                    shareViaWhatsApp();
//                } else if (options[item].equals("SMS")) {
//                    shareViaSMS();
//                } else if (options[item].equals("Other")) {
//                    shareViaOther();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    private void shareViaWhatsApp() {
//        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
//        whatsappIntent.setType("text/plain");
//        whatsappIntent.setPackage("com.whatsapp"); // Set WhatsApp's package name
//        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool app!");
//
//        try {
//            startActivity(whatsappIntent);
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void shareViaSMS() {
//        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//        smsIntent.setData(Uri.parse("sms:")); // Direct the user to the SMS app
//        smsIntent.putExtra("sms_body", "Check out this cool app!");
//
//        try {
//            startActivity(smsIntent);
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(this, "SMS app not available.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void shareViaOther() {
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("text/plain");
//        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool app!");
//
//        startActivity(Intent.createChooser(shareIntent, "Share via"));
//    }

    private String getGroceryListText() {
    StringBuilder groceryListText = new StringBuilder();
    groceryListText.append("Here is my grocery list:\n");

    // Loop through the grocery list and format each item
    for (GroceryListBean item : grocery) {
        groceryListText.append("- ").append(item.getName()).append(" (")
                .append(item.getAmount()).append(")")
                .append("").append(item.getUnit())
                .append("\n");
    }

    return groceryListText.toString();
}


    private void shareViaWhatsApp() {
        String groceryListText = getGroceryListText(); // Get formatted grocery list

        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp"); // Set WhatsApp's package name
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, groceryListText); // Share the grocery list

        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareViaSMS() {
        String groceryListText = getGroceryListText(); // Get formatted grocery list

        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("sms:")); // Direct the user to the SMS app
        smsIntent.putExtra("sms_body", groceryListText); // Share the grocery list

        try {
            startActivity(smsIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "SMS app not available.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareViaOther() {
        String groceryListText = getGroceryListText(); // Get formatted grocery list

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, groceryListText); // Share the grocery list
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }



}
