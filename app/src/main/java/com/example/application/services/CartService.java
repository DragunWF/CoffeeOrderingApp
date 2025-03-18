package com.example.application.services;

import com.example.application.data.CartItem;
import com.example.application.data.Coffee;
import com.example.application.helpers.SessionData;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    public static void add(Coffee coffee) {
        SessionData.getCart().add(new CartItem(coffee, 1));
    }

    public static void add(Coffee coffee, int quantity) {
        SessionData.getCart().add(new CartItem(coffee, quantity));
    }

    public static void remove(int id) {
        List<CartItem> cartItemList = SessionData.getCart();
        cartItemList.removeIf(item -> item.getId() == id);
        SessionData.setCart(cartItemList);
    }

    public static boolean isCoffeeInCart(int id) {
        List<CartItem> cart = SessionData.getCart();
        for (CartItem item : cart) {
            Coffee coffee = item.getCoffee();
            if (coffee.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
