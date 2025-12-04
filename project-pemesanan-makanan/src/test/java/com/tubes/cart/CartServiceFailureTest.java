package com.tubes.cart;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tubes.menu.MenuItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class CartServiceFailureTest {

    private CartService service;

    @SuppressWarnings("unused")
    @BeforeEach
    void setup() {
        service = new CartService(new DefaultCartItemFactory());
    }

    @Test
    void addingNullMenu_throwsNullPointerWhenAccessingSubtotal() {
        service.addToCart(null, 1);

        List<CartItem> items = service.getCartItems();
        assertEquals(1, items.size(), "Cart should contain exactly one item");

        CartItem item = items.get(0);

        NullPointerException exception = assertThrows(NullPointerException.class, item::getSubtotal);
        assertNotNull(exception);
    }

    @Test
    void addingNegativeQuantity_resultsInNegativeSubtotal() {
        MenuItem menu = new MenuItem(1, "Pisang Goreng", "Snack", 10000, true);

        // current implementation does not validate quantity; it will accept negatives
        service.addToCart(menu, -3);

        assertEquals(1, service.getCartItems().size());
        CartItem item = service.getCartItems().get(0);

        // subtotal should be negative according to current logic
        assertEquals(-30000.0, item.getSubtotal(), 0.0001);
    }

    @Test
    void sumDouble_withThrowingMapper_propagatesException() {
        MenuItem a = new MenuItem(10, "Es Teh", "Minuman", 5000, true);
        service.addToCart(a, 1);

        ObservableList<CartItem> items = FXCollections.observableArrayList(service.getCartItems());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
                CartService.sumDouble(items, it -> { throw new RuntimeException("mapper fail"); })
        );
        assertNotNull(exception);
    }
}