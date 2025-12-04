package com.tubes.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tubes.menu.MenuItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class CartServiceTest {

    private CartService service;

    @SuppressWarnings("unused")
    @BeforeEach
    void setup() {
        service = new CartService(new DefaultCartItemFactory());
    }

    @Test
    void addToCart_createsAndMergesItems() {
        MenuItem m1 = new MenuItem(1, "Nasi Goreng", "Makanan", 15000, true);
        MenuItem m2 = new MenuItem(2, "Nasi Goreng", "Makanan", 15000, true); // same name

        service.addToCart(m1, 2);
        assertEquals(1, service.getCartItems().size());
        assertEquals(2, service.getCartItems().get(0).getQuantity());

        service.addToCart(m2, 3);
        assertEquals(1, service.getCartItems().size());
        assertEquals(5, service.getCartItems().get(0).getQuantity());
    }

    @Test
    void getTotal_and_sumDouble_workCorrectly() {
        MenuItem a = new MenuItem(10, "Es Teh", "Minuman", 5000, true);
        MenuItem b = new MenuItem(11, "Kentang Goreng", "Snack", 15000, true);

        service.addToCart(a, 2);
        service.addToCart(b, 1);

        assertEquals(2, service.getCartItems().size());

        assertEquals(25000.0, service.getTotal(), 0.0001);

        ObservableList<CartItem> items = FXCollections.observableArrayList(service.getCartItems());
        double sum = CartService.sumDouble(items, CartItem::getSubtotal);
        assertEquals(25000.0, sum, 0.0001);
    }
}
