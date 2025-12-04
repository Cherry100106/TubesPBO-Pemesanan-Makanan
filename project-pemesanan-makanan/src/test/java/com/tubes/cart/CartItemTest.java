package com.tubes.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.tubes.menu.MenuItem;

class CartItemTest {

    @Test
    void testSubtotalAndAddQuantity() {
        MenuItem menu = new MenuItem(1, "Nasi Goreng", "Makanan", 10000, true);
        CartItem item = new CartItem(menu, 2);

        assertEquals(2, item.getQuantity());
        assertEquals(20000.0, item.getSubtotal(), 0.0001);

        item.addQuantity(3);
        assertEquals(5, item.getQuantity());
        assertEquals(50000.0, item.getSubtotal(), 0.0001);
    }
}
