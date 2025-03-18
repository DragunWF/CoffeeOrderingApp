package com.example.application.services;

import com.example.application.data.Coffee;
import com.example.application.helpers.DatabaseHelper;
import com.example.application.helpers.ModelBank;

public class CoffeeService {
    public static void add(Coffee coffee) {
        ModelBank<Coffee> bank = DatabaseHelper.getCoffeeBank();
        bank.add(coffee);
    }

    public static void update(Coffee updatedCoffee) {
        ModelBank<Coffee> bank = DatabaseHelper.getCoffeeBank();
        bank.update(updatedCoffee);
    }

    public static void delete(int id) {
        ModelBank<Coffee> bank = DatabaseHelper.getCoffeeBank();
        bank.delete(id);
    }
}
