package com.tubes.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML private Label txtOrderToday;
    @FXML private Label txtIncomeToday;
    @FXML private Label txtMenuHabis;

    @FXML
    public void initialize() {
        txtOrderToday.setText("0");
        txtIncomeToday.setText("Rp 0");
        txtMenuHabis.setText("0");
    }
}
