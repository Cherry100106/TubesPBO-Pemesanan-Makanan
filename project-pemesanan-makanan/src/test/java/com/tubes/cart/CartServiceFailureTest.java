package com.tubes.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tubes.menu.MenuItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CartServiceFailureTest {

    private CartService service;

    @BeforeEach
    public void setup() {
        service = new CartService(new DefaultCartItemFactory());
    }

    @Test
    public void addingNullMenu_leadsToNullPointerWhenSubtotalAccessed() {
        // adding a null menu will create a CartItem with a null Menu internally
        service.addToCart(null, 1);

        assertEquals(1, service.getCartItems().size());

        // accessing subtotal should throw NullPointerException because menu is null
        assertThrows(NullPointerException.class, () -> service.getCartItems().get(0).getSubtotal());
    }

    @Test
    public void addingNegativeQuantity_resultsInNegativeSubtotal() {
        MenuItem menu = new MenuItem(1, "Pisang Goreng", "Snack", 10000, true);

        // current implementation does not validate quantity; it will accept negatives
        service.addToCart(menu, -3);

        assertEquals(1, service.getCartItems().size());
        CartItem item = service.getCartItems().get(0);

        // subtotal should be negative according to current logic
        assertEquals(-30000.0, item.getSubtotal(), 0.0001);
    }

    @Test
    public void sumDouble_withThrowingMapper_propagatesException() {
        MenuItem a = new MenuItem(10, "Es Teh", "Minuman", 5000, true);
        service.addToCart(a, 1);

        ObservableList<CartItem> items = FXCollections.observableArrayList(service.getCartItems());

        assertThrows(RuntimeException.class, () -> 
                CartService.sumDouble(items, it -> { throw new RuntimeException("mapper fail"); })
        );
    }
}
