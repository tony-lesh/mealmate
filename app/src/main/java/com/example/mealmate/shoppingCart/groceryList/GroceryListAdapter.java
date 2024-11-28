package com.example.mealmate.shoppingCart.groceryList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealmate.R;
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
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public static class GroceryListViewHolder extends RecyclerView.ViewHolder {

        TextView itemList, price;
        CheckBox selectCheckBox;
        ImageView moreOptions, shareOption;

        private TabLayout tabLayout;
        private ImageButton groceryListMore;

        public GroceryListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemList = itemView.findViewById(R.id.shoppingList);
            price = itemView.findViewById(R.id.shoppingListPrice);
            selectCheckBox = itemView.findViewById(R.id.groceryRecipeListCheckBox);
            moreOptions = itemView.findViewById(R.id.viewMoreImageView);
            shareOption = itemView.findViewById(R.id.shareImageView);
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
