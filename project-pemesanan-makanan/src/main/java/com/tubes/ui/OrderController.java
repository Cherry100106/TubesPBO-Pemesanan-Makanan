package com.tubes.ui;

import com.tubes.cart.CartItem;
import com.tubes.cart.CartService;
import com.tubes.cart.DefaultCartItemFactory;
import com.tubes.menu.MenuItem;
import com.tubes.menu.MenuRepository;
import com.tubes.order.OrderService;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class OrderController {

    @FXML private TableView<MenuItem> tableMenu;
    @FXML private TableColumn<MenuItem, String> colNamaMenu;
    @FXML private TableColumn<MenuItem, Double> colHarga;
    @FXML private TableColumn<MenuItem, String> colKategori;
    @FXML private TableColumn<MenuItem, String> colAvailable;

    @FXML private TextField inputQty;
    @FXML private TextField inputNamaPelanggan; 
    @FXML private Button btnAdd;

    @FXML private TableView<CartItem> tableCart;
    @FXML private TableColumn<CartItem, String> colCartNama;
    @FXML private TableColumn<CartItem, Integer> colCartQty;
    @FXML private TableColumn<CartItem, Double> colCartSubtotal;
    @FXML private TableColumn<CartItem, Void> colCartAksi;

    @FXML private Label labelTotal;
    @FXML private Button btnKonfirmasi;

    private final MenuRepository menuRepo = new MenuRepository();
    private final CartService cartService = new CartService(new DefaultCartItemFactory());
    private ObservableList<MenuItem> menuList;
    private final OrderService orderService = new OrderService();

    @FXML
    public void initialize() {
        System.out.println("Order Page Loaded");
        loadMenuTable();
        setupCartTable();
        initCartTable();
        setupAddButton();
        setupConfirmButton();
    }

    private void loadMenuTable() {
        colNamaMenu.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNamaMakanan()));
        colHarga.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getHarga()).asObject());
        colKategori.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKategori()));
        colAvailable.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().isAvailable() ? "Tersedia" : "Tidak Tersedia"));

        menuList = FXCollections.observableArrayList(menuRepo.getAllMenus());
        tableMenu.setItems(menuList);
    }

    private void initCartTable() {
        colCartNama.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMenu().getNamaMakanan()));
        colCartQty.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
        colCartSubtotal.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSubtotal()).asObject());
        tableCart.setItems(cartService.getCartItems());
    }

    private void setupAddButton() {
        btnAdd.setOnAction(e -> addToCart());
    }

    private void setupConfirmButton() {
        btnKonfirmasi.setOnAction(e -> {
            String namaPelanggan = inputNamaPelanggan.getText().trim();
            if (namaPelanggan.isEmpty()) {
                showAlert("Nama pelanggan wajib diisi!");
                return;
            }

            if (cartService.getCartItems().isEmpty()) {
                showAlert("Keranjang masih kosong!");
                return;
            }

            try {
                orderService.saveOrder(cartService.getCartItems(), namaPelanggan);
                System.out.println("Pesanan berhasil disimpan untuk: " + namaPelanggan);
            } catch (Exception ex) {
                showAlert("Gagal simpan pesanan: " + ex.getMessage());
                return;
            }

            String struk = generateStruk(namaPelanggan);
            printStruk(struk);

            // Reset
            inputNamaPelanggan.clear();
            inputQty.clear();
            cartService.getCartItems().clear();
            tableCart.setItems(FXCollections.observableArrayList());
            updateTotal();

            // Kembali ke Dashboard
            kembaliKeDashboard();
        });
    }

    private void kembaliKeDashboard() {
        try {
            Stage stage = (Stage) btnKonfirmasi.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/tubes/ui/main_layout.fxml"));
            stage.setScene(new Scene(root, 1200, 700));
            stage.setTitle("Kasir App");
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Gagal kembali ke dashboard.");
        }
    }

    private void addToCart() {
        MenuItem selected = tableMenu.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Pilih menu terlebih dahulu!");
            return;
        }
        if (!selected.isAvailable()) {
            showAlert("Menu tidak tersedia!");
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(inputQty.getText());
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            showAlert("Jumlah harus angka > 0!");
            return;
        }

        cartService.addToCart(selected, qty);
        tableCart.refresh();
        updateTotal();
    }

    private void updateTotal() {
        double total = cartService.getTotal();
        labelTotal.setText("Total: Rp " + (int) total);
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void setupCartTable() {
        colCartAksi.setCellFactory(column -> new TableCell<CartItem, Void>() {
            private final Button btnHapus = new Button("Hapus");
            {
                btnHapus.setStyle(
                    "-fx-background-color: #d32f2f; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 9px; " +
                    "-fx-padding: 2 2; " +
                    "-fx-background-radius: 4;"
                );
                btnHapus.setPrefWidth(40);
                btnHapus.setPrefHeight(10);
                btnHapus.setOnAction(e -> {
                    CartItem item = getTableView().getItems().get(getIndex());
                    cartService.getCartItems().remove(item);
                    updateTotal();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnHapus);
            }
        });
        tableCart.setItems(cartService.getCartItems());
    }

    private String generateStruk(String namaPelanggan) {
        StringBuilder sb = new StringBuilder();
        sb.append("======= STRUK PEMBAYARAN =======\n");
        sb.append("Pelanggan : ").append(namaPelanggan).append("\n");
        sb.append("Tanggal   : ").append(LocalDateTime.now()).append("\n");
        sb.append("--------------------------------\n");

        int total = 0;
        for (CartItem item : cartService.getCartItems()) {
            String line = String.format("%-20s x%-2d = Rp %d\n",
                item.getMenu().getNamaMakanan(),
                item.getQuantity(),
                (int) item.getSubtotal()
            );
            sb.append(line);
            total += (int) item.getSubtotal();
        }

        sb.append("--------------------------------\n");
        sb.append(String.format("TOTAL     : Rp %d\n", total));
        sb.append("================================\n");
        return sb.toString();
    }

    private void printStruk(String struk) {
        Alert preview = new Alert(Alert.AlertType.CONFIRMATION);
        preview.setTitle("Preview Struk");
        preview.setHeaderText("Struk yang akan dicetak:");

        TextArea area = new TextArea(struk);
        area.setEditable(false);
        area.setWrapText(true);
        area.setFont(javafx.scene.text.Font.font("Consolas", 12));
        area.setPrefSize(480, 360);
        preview.getDialogPane().setContent(area);

        ButtonType cetak = new ButtonType("Cetak", ButtonBar.ButtonData.OK_DONE);
        ButtonType batal = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        preview.getButtonTypes().setAll(cetak, batal);

        if (preview.showAndWait().orElse(batal) == cetak) {
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(null)) {
                TextArea printArea = new TextArea(struk);
                printArea.setFont(javafx.scene.text.Font.font("Consolas", 12));
                if (job.printPage(printArea)) {
                    job.endJob();
                    new Alert(Alert.AlertType.INFORMATION, "Struk berhasil dicetak!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Gagal mencetak struk.").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Pencetakan dibatalkan.").show();
            }
        }
    }
}