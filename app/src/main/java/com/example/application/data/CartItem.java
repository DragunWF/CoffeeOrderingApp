package com.example.application.data;

public class CartItem extends Model {
    private Coffee coffee;
    private int quantity;

    public CartItem(Coffee coffee, int quantity) {
        this.coffee = coffee;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "coffee=" + coffee +
                ", quantity=" + quantity +
                ", id=" + id +
                '}';
    }

    public double getTotalPrice() {
        return quantity * coffee.getPrice();
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity() {
        quantity++;
    }

    public void subtractQuantity() {
        quantity--;
    }
}
