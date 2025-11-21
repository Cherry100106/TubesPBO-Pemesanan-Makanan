package com.tubes.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TransactionHistoryController {

    @FXML private TableView<TransactionRow> tableTransaksi;
    @FXML private TableColumn<TransactionRow, String> colId;
    @FXML private TableColumn<TransactionRow, String> colNama;
    @FXML private TableColumn<TransactionRow, Integer> colTotal;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idTransaksi"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaPemesan"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalHarga"));

        tableTransaksi.setItems(getDummyData());

        // klik 1 row = buka detail transaksi
        tableTransaksi.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                TransactionRow selected = tableTransaksi.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    TransactionDetailController.showDetailWindow(selected);
                }
            }
        });
    }

    // Data dummy
    private ObservableList<TransactionRow> getDummyData() {
        return FXCollections.observableArrayList(
                new TransactionRow("TRX001", "Budi", 42000),
                new TransactionRow("TRX002", "Siti", 42000),
                new TransactionRow("TRX003", "Aldi", 42000)
        );
    }
}
