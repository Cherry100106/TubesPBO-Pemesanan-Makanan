package com.tubes.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainLayoutController {

    @FXML private Button btnDashboard;
    @FXML private Button btnOrder;
    @FXML private Button btnMenu;
    @FXML private Button btnReport;

    @FXML private AnchorPane contentArea;
    @FXML private Label headerTitle;

    @FXML
    public void initialize() {
        loadPage("DashboardPage.fxml", "Dashboard");

        btnDashboard.setOnAction(e -> loadPage("DashboardPage.fxml", "Dashboard"));
        btnOrder.setOnAction(e -> loadPage("OrderPage.fxml", "Kelola Pesanan"));
        btnMenu.setOnAction(e -> loadPage("MenuPage.fxml", "Menu Makanan"));
        btnReport.setOnAction(e -> loadPage("ReportPage.fxml", "Laporan"));
    }

    private void loadPage(String fileName, String title) {
        try {
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
