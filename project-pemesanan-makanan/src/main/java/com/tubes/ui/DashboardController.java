package com.tubes.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.tubes.report.ReportFacade;

public class DashboardController {

    @FXML private Label txtOrderToday;
    @FXML private Label txtIncomeToday;

    @FXML
    public void initialize() {
        ReportFacade report = new ReportFacade();
        txtOrderToday.setText(String.valueOf(report.getOrderCountToday()));
        txtIncomeToday.setText("Rp " + (int) report.getIncomeToday());
    }
}