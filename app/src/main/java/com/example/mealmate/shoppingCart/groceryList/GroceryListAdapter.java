package com.example.mealmate.shoppingCart.groceryList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealmate.R;
import java.util.List;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.GroceryListViewHolder> {
    private final Context context;
    private final List<GrocerySearchResponse.GroceryItem> groceryList;

    public GroceryListAdapter(Context context, List<GrocerySearchResponse.GroceryItem> groceryList) {
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
        GrocerySearchResponse.GroceryItem item = groceryList.get(position);
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

        public GroceryListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemList = itemView.findViewById(R.id.shoppingList);
            price = itemView.findViewById(R.id.shoppingListPrice);
            selectCheckBox = itemView.findViewById(R.id.groceryRecipeListCheckBox);
            moreOptions = itemView.findViewById(R.id.viewMoreImageView);
            shareOption = itemView.findViewById(R.id.shareImageView);
        }

        public void bindData(GrocerySearchResponse.GroceryItem item) {
            if (item.getName() != null && !item.getName().isEmpty()) {
                // Format the grocery item information
                String groceryText = item.getAmount() + " " +
                        item.getUnit() + " of " +
                        item.getName();
                itemList.setText(groceryText);
            }
        }
    }
}
