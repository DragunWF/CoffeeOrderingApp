package com.example.application.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.application.data.CartItem;
import com.example.application.data.Coffee;
import com.example.application.services.CartService;
import com.example.application.services.CoffeeService;

public class DatabaseHelper {
    private static final String FILE_KEY = "db";

    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;

    private static ModelBank<Coffee> coffeeBank;
    private static ModelBank<CartItem> cartBank;

    public static void initialize(Context context) {
        sharedPref = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        coffeeBank = new ModelBank<>(sharedPref, editor, "coffees", Coffee.class);
        cartBank = new ModelBank<>(sharedPref, editor, "cart", CartItem.class);
    }

    public static ModelBank<Coffee> getCoffeeBank() {
        return coffeeBank;
    }

    public static ModelBank<CartItem> getCartBank() {
        return cartBank;
    }

    public static void addDummyData() {
        if (coffeeBank.getAll().isEmpty()) {
            CoffeeService.add(new Coffee("Cappucino", "Espresso Based Coffee", "Hot Brew", 110));
            CoffeeService.add(new Coffee("Frappe", "Espresso Milk Coffee", "Cold Brew", 70));
            CoffeeService.add(new Coffee("Iced Americano", "Chilled Black Coffee", "Cold Brew", 90));
            CoffeeService.add(new Coffee("Latte", "Espresso and Steamed Milk Coffee", "Hot Brew", 60));
            CoffeeService.add(new Coffee("Machiatto", "Foamed Milk with Shot of Espresso", "Hot Brew", 75));
            CoffeeService.add(new Coffee("Black Coffee", "Strong Ground Coffee", "Hot Brew", 60));
            CoffeeService.add(new Coffee("Irish Coffee", "Caffeinated alcoholic drink consisting of Irish whiskey, hot coffee, and sugar", "Hot Brew", 120));
            CoffeeService.add(new Coffee("Freddo Coffee", "Mixed Espresso Coffee over ice", "Cold Brew", 80));
            CoffeeService.add(new Coffee("Affogato", "Espresso poured over gelato", "Cold Brew", 100));

            Coffee preCartCoffee = new Coffee("Breve", "Latte made with half-and-half", "Hot Brew", 85);
            CoffeeService.add(preCartCoffee);

            // CartService.add(preCartCoffee);
        }
    }

    public static void clear() {
        coffeeBank.clear();
        cartBank.clear();
    }
}
