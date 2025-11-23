package com.tubes.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.tubes.report.ReportFacade;

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

        tableTransaksi.setItems(loadRealData());

        tableTransaksi.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                TransactionRow selected = tableTransaksi.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    TransactionDetailController.showDetailWindow(selected);
                }
            }
        });
    }

    private ObservableList<TransactionRow> loadRealData() {
        ReportFacade report = new ReportFacade();
        return FXCollections.observableArrayList(
            report.getAllTransactions().stream()
                .map(order -> new TransactionRow(
                    order.getId(),
                    order.getNamaPemesan(),
                    (int) order.getTotalHarga()
                ))
                .toList()
        );
    }
}