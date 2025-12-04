package com.tubes.report;

import java.util.List;

import com.tubes.order.Order;
import com.tubes.order.OrderDetail;
import com.tubes.order.OrderRepository;

public class ReportFacade {
    private final OrderRepository orderRepo;

    public ReportFacade() {
        this.orderRepo = new OrderRepository();
    }

    public ReportFacade(OrderRepository repo) {
        this.orderRepo = repo;
    }

    public int getOrderCountToday() {
        return orderRepo.getOrderCountToday();
    }

    public double getIncomeToday() {
        return orderRepo.getTotalIncomeToday();
    }

    public int getOutOfStockItemsSoldToday() {
        return orderRepo.getOutOfStockItemsSoldToday();
    }

    public List<Order> getAllTransactions() {
        return orderRepo.getAllOrders();
    }

    public List<OrderDetail> getTransactionDetails(int orderId) {
        return orderRepo.getOrderDetails(orderId);
    }

    public int getTotalItemsSoldToday() {
        return orderRepo.getTotalItemsSoldToday();
    }

    public String getBestSellingItemToday() {
        return orderRepo.getBestSellingItemToday();
    }
}