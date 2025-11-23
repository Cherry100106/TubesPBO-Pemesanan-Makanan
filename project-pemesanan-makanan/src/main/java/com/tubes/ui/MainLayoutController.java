package com.tubes.ui;

import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainLayoutController {

    @FXML private Button btnDashboard;
    @FXML private Button btnOrder;
    @FXML private Button btnReport;

    @FXML private AnchorPane contentArea;
    @FXML private Label headerTitle;

    private List<Button> navButtons;

    @FXML
    public void initialize() {
        navButtons = Arrays.asList(btnDashboard, btnOrder, btnReport);
        
        btnDashboard.setOnAction(e -> loadPage("DashboardPage.fxml", "Dashboard", btnDashboard));
        btnOrder.setOnAction(e -> loadPage("OrderPage.fxml", "Kelola Pesanan", btnOrder));
        btnReport.setOnAction(e -> loadPage("TransactionHistory.fxml", "Histori Transaksi", btnReport));
        
        loadPage("DashboardPage.fxml", "Dashboard", btnDashboard);
    }

    private void resetButtonStyles() {
        String activeStyleClass = "active-btn";
        for (Button button : navButtons) {
            button.getStyleClass().remove(activeStyleClass);
        }
    }

    private void loadPage(String fileName, String title, Button activeButton) {
        try {
            resetButtonStyles();
            if (activeButton != null) {
                activeButton.getStyleClass().add("active-btn");
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/tubes/ui/" + fileName)
            );
            Parent root = loader.load();

            contentArea.getChildren().setAll(root);
            headerTitle.setText(title);

            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

        } catch (Exception e) {
            System.out.println("FAILED TO LOAD PAGE: " + fileName);
            e.printStackTrace();
        }
    }
}