package com.example.mealmate.shoppingCart.groceryList;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealmate.R;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.MapView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.GroceryListViewHolder> {
    private final Context context;
    private final List<GroceryListBean> groceryList;

    public GroceryListAdapter(Context context, List<GroceryListBean> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
    }

    @NonNull
    @Override
    public GroceryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grocery_list_item, parent, false);  // Use a proper item layout
        return new GroceryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryListViewHolder holder, int position) {
        GroceryListBean item = groceryList.get(position);
        holder.bindData(item);

        holder.mpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize the FusedLocationProviderClient
                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(v.getContext());

                // Check if the app has the necessary location permission
                if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Open Google Maps with user's location
                    fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                        if (location != null) {
                            double userLatitude = location.getLatitude();
                            double userLongitude = location.getLongitude();
                            Uri uri = Uri.parse("geo:" + userLatitude + "," + userLongitude + "?q=grocery+store");

                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setPackage("com.google.android.apps.maps");

                            if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                                v.getContext().startActivity(intent);
                            } else {
                                Uri webUri = Uri.parse("https://www.google.com/maps?q=" + userLatitude + "," + userLongitude);
                                Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
                                v.getContext().startActivity(webIntent);
                                Toast.makeText(v.getContext(), "Google Maps not installed. Opening in browser.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(v.getContext(), "Unable to fetch location", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Request permission if not granted
                    ActivityCompat.requestPermissions((Activity) v.getContext(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public static class GroceryListViewHolder extends RecyclerView.ViewHolder {

        TextView itemList, price;
        CheckBox selectCheckBox;
        ImageView moreOptions, shareOption;
        MapView mpView;
        private TabLayout tabLayout;
        private ImageButton groceryListMore;

        public GroceryListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemList = itemView.findViewById(R.id.shoppingList);
            price = itemView.findViewById(R.id.shoppingListPrice);
            selectCheckBox = itemView.findViewById(R.id.groceryRecipeListCheckBox);
            moreOptions = itemView.findViewById(R.id.viewMoreImageView);
            shareOption = itemView.findViewById(R.id.shareImageView);
            mpView = itemView.findViewById(R.id.mapLocationView);
        }

        public void bindData(GroceryListBean item) {
            if (item.getName() != null && !item.getName().isEmpty()) {
                // Get the amount from the Amount object
                double amountValue = item.getAmount() != null ? item.getAmount().getValue() : 0.0;
                String amountUnit = item.getAmount() != null ? item.getAmount().getUnit() : "";

                // Format the grocery item information
                String groceryText = amountValue + " " +
                        amountUnit + " of " +
                        item.getName();

                // Set the formatted text to the TextView
                itemList.setText(groceryText);
            }
        }

    }
}
