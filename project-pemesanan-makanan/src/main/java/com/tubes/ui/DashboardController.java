package com.tubes.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.tubes.report.ReportFacade;
import com.tubes.util.OrderEventBroker;
import javafx.application.Platform;
import java.util.logging.Logger; // 1. Import Logger

public class DashboardController {

    // 2. Buat instance Logger
    private static final Logger logger = Logger.getLogger(DashboardController.class.getName());

    @FXML private Label txtOrderToday;
    @FXML private Label txtIncomeToday;
    @FXML private Label txtTotalItems;
    @FXML private Label txtBestSeller;

    private ReportFacade report = new ReportFacade(); 
    public void setReportFacade(ReportFacade report) {
        this.report = report;
    }

    @FXML
    public void initialize() {
        refreshDashboard();
        OrderEventBroker.addListener(() -> Platform.runLater(this::refreshDashboard));
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
        
        logger.info("Dashboard refreshed!");
    }
}