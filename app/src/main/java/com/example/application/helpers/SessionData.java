package com.example.application.helpers;

import com.example.application.data.CartItem;
import com.example.application.data.Coffee;

import java.util.ArrayList;
import java.util.List;

public class SessionData {
    private static List<CartItem> cart = new ArrayList<>();

    public static List<CartItem> getCart() {
        return cart;
    }

    public static void setCart(List<CartItem> cart) {
        SessionData.cart = cart;
    }
}
