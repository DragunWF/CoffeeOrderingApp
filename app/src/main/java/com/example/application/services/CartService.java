package com.example.application.services;

import com.example.application.data.CartItem;
import com.example.application.data.Coffee;
import com.example.application.helpers.DatabaseHelper;
import com.example.application.helpers.ModelBank;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    public static void add(Coffee coffee) {
        ModelBank<CartItem> bank = DatabaseHelper.getCartBank();
        bank.add(new CartItem(coffee, 1));
    }

    public static void add(Coffee coffee, int quantity) {
        ModelBank<CartItem> bank = DatabaseHelper.getCartBank();
        bank.add(new CartItem(coffee, quantity));
    }

    public static void remove(int id) {
        ModelBank<CartItem> bank = DatabaseHelper.getCartBank();
        bank.delete(id);
    }

    public static void update(CartItem item) {
        ModelBank<CartItem> bank = DatabaseHelper.getCartBank();
        bank.update(item);
    }

    public static void clear() {
        ModelBank<CartItem> bank = DatabaseHelper.getCartBank();
        bank.clear();
    }

    public static boolean isCoffeeInCart(int id) {
        List<CartItem> cart = DatabaseHelper.getCartBank().getAll();
        for (CartItem item : cart) {
            Coffee coffee = item.getCoffee();
            if (coffee.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
