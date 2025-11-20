package com.tubes.ui;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportController {

    @FXML private DatePicker tanggalMulai;
    @FXML private DatePicker tanggalSelesai;
    @FXML private Button btnFilter;

    @FXML private TableView<ReportRow> tableLaporan;
    @FXML private TableColumn<ReportRow, String> colTanggal;
    @FXML private TableColumn<ReportRow, Integer> colTotalOrder;
    @FXML private TableColumn<ReportRow, Integer> colPendapatan;

    @FXML
    public void initialize() {

        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colTotalOrder.setCellValueFactory(new PropertyValueFactory<>("jumlahOrder"));
        colPendapatan.setCellValueFactory(new PropertyValueFactory<>("pendapatan"));

        btnFilter.setOnAction(e -> loadData());
    }

    private void loadData() {

        LocalDate mulai = tanggalMulai.getValue();
        LocalDate selesai = tanggalSelesai.getValue();

        if (mulai == null || selesai == null || selesai.isBefore(mulai)) {
            tableLaporan.setItems(FXCollections.observableArrayList());
            return;
        }

        ObservableList<ReportRow> data = FXCollections.observableArrayList();

        // data dummy
        data.add(new ReportRow("2025-11-01", 12, 125000));
        data.add(new ReportRow("2025-11-02", 9, 98000));
        data.add(new ReportRow("2025-11-03", 14, 141000));

        tableLaporan.setItems(data);
    }

    public static class ReportRow {
        private String tanggal;
        private int jumlahOrder;
        private int pendapatan;

        public ReportRow(String tanggal, int jumlahOrder, int pendapatan) {
            this.tanggal = tanggal;
            this.jumlahOrder = jumlahOrder;
            this.pendapatan = pendapatan;
        }

        public String getTanggal() { return tanggal; }
        public int getJumlahOrder() { return jumlahOrder; }
        public int getPendapatan() { return pendapatan; }
    }
}
