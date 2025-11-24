package com.tubes.ui;

import com.tubes.report.ReportFacade;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TransactionDetailController {

    @FXML private Label labelId;
    @FXML private Label labelTanggal;
    @FXML private Label labelTotal;

    @FXML private TableView<DetailRow> tableDetail;
    @FXML private TableColumn<DetailRow, String> colMenu;
    @FXML private TableColumn<DetailRow, Integer> colQty;
    @FXML private TableColumn<DetailRow, Integer> colHarga;
    @FXML private TableColumn<DetailRow, Integer> colSubtotal;

    private TransactionRow transaksi;

    public void setData(TransactionRow trx) {
        this.transaksi = trx;

        labelId.setText("ID Transaksi: " + trx.getIdTransaksi());
        labelTanggal.setText("Tanggal: " + java.time.LocalDate.now());

        colMenu.setCellValueFactory(new PropertyValueFactory<>("menu"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        ReportFacade report = new ReportFacade();
        var details = report.getTransactionDetails(trx.getOrderId());

        var detailRows = FXCollections.observableArrayList(
            details.stream()
                .map(d -> new DetailRow(
                    d.getNamaMenu(),
                    d.getJumlah(),
                    (int) (d.getSubtotal() / d.getJumlah()) 
                ))
                .toList()
        );

        tableDetail.setItems(detailRows);

        int total = detailRows.stream().mapToInt(DetailRow::getSubtotal).sum();
        labelTotal.setText("Total Harga: Rp " + total);
    }

    public static void showDetailWindow(TransactionRow trx) {
        try {
            FXMLLoader loader = new FXMLLoader(
                TransactionDetailController.class.getResource("/com/tubes/ui/TransactionDetail.fxml")
            );
            Parent root = loader.load();
            TransactionDetailController controller = loader.getController();
            controller.setData(trx);
            Stage stage = new Stage();
            stage.setTitle("Detail Transaksi");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class DetailRow {
        private final String menu;
        private final int qty;
        private final int harga;
        private final int subtotal;

        public DetailRow(String menu, int qty, int harga) {
            this.menu = menu;
            this.qty = qty;
            this.harga = harga;
            this.subtotal = qty * harga;
        }

        public String getMenu() { return menu; }
        public int getQty() { return qty; }
        public int getHarga() { return harga; }
        public int getSubtotal() { return subtotal; }
    }
}