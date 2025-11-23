package com.tubes.cart;

import com.tubes.menu.MenuItem;

public class DefaultCartItemFactory implements CartItemFactory {

    @Override
    public CartItem create(MenuItem menu, int quantity) {
        return new CartItem(menu, quantity);
    }
}
