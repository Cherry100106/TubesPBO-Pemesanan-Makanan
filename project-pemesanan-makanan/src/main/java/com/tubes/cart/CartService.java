package com.tubes.cart;

import com.tubes.menu.MenuItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CartService {

    private final CartItemFactory factory;
    private final ObservableList<CartItem> cartItems = FXCollections.observableArrayList();

    public CartService(CartItemFactory factory) {
        this.factory = factory;
    }

    public ObservableList<CartItem> getCartItems() {
        return cartItems;
    }

    public void addToCart(MenuItem menu, int qty) {
        for (CartItem item : cartItems) {
            if (item.getMenu().getNamaMakanan().equals(menu.getNamaMakanan())) {
                item.addQuantity(qty);
                return;
            }
        }
        cartItems.add(factory.create(menu, qty));
    }

    public double getTotal() {
        return cartItems.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }
}
