package com.tubes.command;

import java.util.List;

import com.tubes.cart.CartItem;
import com.tubes.order.OrderService;
import com.tubes.order.OrderServiceException;

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
    public void execute() throws OrderServiceException {
        orderService.saveOrder(items, namaPelanggan);
    }
}