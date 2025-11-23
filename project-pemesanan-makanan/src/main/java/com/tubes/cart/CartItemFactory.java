package com.tubes.cart;

import com.tubes.menu.MenuItem;

public interface CartItemFactory {
    CartItem create(MenuItem menu, int quantity);
}
