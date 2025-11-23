package com.tubes.cart;

import com.tubes.menu.MenuItem;

public class CartItem {
    private final MenuItem menu;
    private int quantity;

    public CartItem(MenuItem menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public MenuItem getMenu() {
        return menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int qty) {
        this.quantity += qty;
    }

    public double getSubtotal() {
        return menu.getHarga() * quantity;
    }
}
