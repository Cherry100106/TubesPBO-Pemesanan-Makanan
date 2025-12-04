package com.tubes.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.tubes.menu.MenuItem;

class DefaultCartItemFactoryTest {

    @Test
    void factoryCreatesCorrectCartItem() {
        MenuItem menu = new MenuItem(10, "Ayam Bakar", "Makanan", 30000, true);
        DefaultCartItemFactory factory = new DefaultCartItemFactory();

        CartItem item = factory.create(menu, 2);

        assertNotNull(item);
        assertEquals(menu, item.getMenu());
        assertEquals(2, item.getQuantity());
        assertEquals(60000.0, item.getSubtotal(), 0.0001);
    }
}
