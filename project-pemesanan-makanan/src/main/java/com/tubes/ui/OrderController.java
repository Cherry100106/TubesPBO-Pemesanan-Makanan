package com.tubes.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class OrderController {

    @FXML private TableView<?> tableMenu;
    @FXML private TableColumn<?, ?> colNamaMenu;
    @FXML private TableColumn<?, ?> colHarga;
    @FXML private TableColumn<?, ?> colStatus;

    @FXML private TextField inputQty;
    @FXML private Button btnAdd;

    @FXML private TableView<?> tableCart;
    @FXML private TableColumn<?, ?> colCartNama;
    @FXML private TableColumn<?, ?> colCartQty;
    @FXML private TableColumn<?, ?> colCartSubtotal;

    @FXML private Label labelTotal;
    @FXML private Button btnKonfirmasi;

    @FXML
    public void initialize() {
        System.out.println("Order Page Loaded");
        // logic
    }
}
