package com.tubes.cart;

import java.util.function.ToDoubleFunction;

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

    public static <T> double sumDouble(ObservableList<T> items, ToDoubleFunction<T> mapper) {
        if (items == null || items.isEmpty()) return 0.0;
        return items.stream().mapToDouble(mapper).sum();
    }

    public double getTotal() {
        return sumDouble(cartItems, CartItem::getSubtotal);
    }
}
