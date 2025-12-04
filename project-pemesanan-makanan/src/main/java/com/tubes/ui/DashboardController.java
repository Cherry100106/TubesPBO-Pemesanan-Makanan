package com.tubes.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.tubes.report.ReportFacade;
import com.tubes.util.OrderEventBroker;
import javafx.application.Platform;

public class DashboardController {

    @FXML private Label txtOrderToday;
    @FXML private Label txtIncomeToday;
    @FXML private Label txtTotalItems;
    @FXML private Label txtBestSeller;

    private final ReportFacade report = new ReportFacade();

    @FXML
    public void initialize() {
        refreshDashboard();
        OrderEventBroker.addListener(() -> {
            Platform.runLater(this::refreshDashboard);
        });
    }

    private void refreshDashboard() {
        int count = report.getOrderCountToday();
        double income = report.getIncomeToday();
        int totalItems = report.getTotalItemsSoldToday();
        String bestSeller = report.getBestSellingItemToday();
        txtOrderToday.setText(String.valueOf(count));
        txtIncomeToday.setText("Rp " + (int) income);
        txtTotalItems.setText(String.valueOf(totalItems));
        txtBestSeller.setText(bestSeller);
        
        System.out.println("Dashboard refreshed!");
    }
}