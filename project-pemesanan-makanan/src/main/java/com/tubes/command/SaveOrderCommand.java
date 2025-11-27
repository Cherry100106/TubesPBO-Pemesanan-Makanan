package com.tubes.command;

import com.tubes.cart.CartItem;
import com.tubes.order.OrderService;

import java.util.List;

public class SaveOrderCommand implements Command {
    private final OrderService orderService;
    private final List<CartItem> items;
    private final String namaPelanggan;

    public SaveOrderCommand(OrderService orderService, List<CartItem> items, String namaPelanggan) {
        this.orderService = orderService;
        this.items = items;
        this.namaPelanggan = namaPelanggan;
    }

    @Override
    public void execute() {
        orderService.saveOrder(items, namaPelanggan);
    }
}