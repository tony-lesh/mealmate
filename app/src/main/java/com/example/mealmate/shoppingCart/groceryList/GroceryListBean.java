package com.example.mealmate.shoppingCart.groceryList;

import com.google.gson.annotations.SerializedName;

public class GroceryListBean {
    @SerializedName("name")
    private String name;

    @SerializedName("amount")
    private Amount amount; // Change to Amount type

    @SerializedName("unit")
    private String unit;

    public GroceryListBean() {
    }

    public GroceryListBean(String name, Amount amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "GroceryListBean{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                '}';
    }

    // Define the Amount class as a nested class
    public static class Amount {
        @SerializedName("value")
        private double value;

        @SerializedName("unit")
        private String unit;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        @Override
        public String toString() {
            return "Amount{" +
                    "value=" + value +
                    ", unit='" + unit + '\'' +
                    '}';
        }
    }
}
