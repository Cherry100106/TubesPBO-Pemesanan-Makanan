package com.tubes.ui;

import com.tubes.cart.CartItem;
import com.tubes.cart.CartService;
import com.tubes.cart.DefaultCartItemFactory;
import com.tubes.menu.MenuItem;
import com.tubes.menu.MenuRepository;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class OrderController {

    @FXML private TableView<MenuItem> tableMenu;
    @FXML private TableColumn<MenuItem, String> colNamaMenu;
    @FXML private TableColumn<MenuItem, Double> colHarga;
    @FXML private TableColumn<MenuItem, String> colKategori;
    @FXML private TableColumn<MenuItem, String> colAvailable;

    @FXML private TextField inputQty;
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

        colNamaMenu.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getNamaMakanan())
        );

        colHarga.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getHarga()).asObject()
        );

        colKategori.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getKategori())
        );

        colAvailable.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().isAvailable() ? "Tersedia" : "Tidak Tersedia")
        );

        menuList = FXCollections.observableArrayList(menuRepo.getAllMenus());
        tableMenu.setItems(menuList);
    }

    private void initCartTable() {

        colCartNama.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getMenu().getNamaMakanan())
        );

        colCartQty.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getQuantity()).asObject()
        );

        colCartSubtotal.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getSubtotal()).asObject()
        );

        tableCart.setItems(cartService.getCartItems());
    }

    private void setupAddButton() {
        btnAdd.setOnAction(e -> addToCart());
    }

    private void setupConfirmButton() {
        btnKonfirmasi.setOnAction(e -> {
            if (cartService.getCartItems().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Keranjang masih kosong!").show();
                return;
            }

            String struk = generateStruk();
            printStruk(struk);

            cartService.getCartItems().clear();
            updateTotal();
        });
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
            showAlert("Jumlah harus angka dan lebih dari 0!");
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
                if (empty) setGraphic(null);
                else setGraphic(btnHapus);
            }
        });

        tableCart.setItems(cartService.getCartItems());
    }

    private String generateStruk() {
        StringBuilder sb = new StringBuilder();

        sb.append("======= STRUK PEMBAYARAN =======\n");
        sb.append("Tanggal : ").append(java.time.LocalDateTime.now()).append("\n");
        sb.append("--------------------------------\n");

        int total = 0;

        for (CartItem item : cartService.getCartItems()) {
            sb.append(item.getMenu().getNamaMakanan())
            .append("  x")
            .append(item.getQuantity())
            .append("   = Rp ")
            .append((int) item.getSubtotal())
            .append("\n");

            total += (int) item.getSubtotal();
        }

        sb.append("--------------------------------\n");
        sb.append("TOTAL : Rp ").append(total).append("\n");
        sb.append("================================\n");

        return sb.toString();
    }

    private void printStruk(String struk) {
        Alert previewAlert = new Alert(Alert.AlertType.CONFIRMATION);
        previewAlert.setTitle("Preview Struk");
        previewAlert.setHeaderText("Struk yang akan dicetak:");

        TextArea previewArea = new TextArea(struk);
        previewArea.setEditable(false);
        previewArea.setWrapText(true);
        previewArea.setFont(javafx.scene.text.Font.font("Consolas", 12));
        previewArea.setPrefWidth(480);
        previewArea.setPrefHeight(360);

        previewAlert.getDialogPane().setContent(previewArea);
        ButtonType cetak = new ButtonType("Cetak", javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
        ButtonType batal = new ButtonType("Batal", javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE);
        previewAlert.getButtonTypes().setAll(cetak, batal);

        var result = previewAlert.showAndWait();
        if (result.isEmpty() || result.get() != cetak) return;

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Printer tidak ditemukan.");
            error.show();
            return;
        }

        if (!job.showPrintDialog(null)) {
            return;
        }

        TextArea printArea = new TextArea(struk);
        printArea.setFont(javafx.scene.text.Font.font("Consolas", 12));
        printArea.setEditable(false);

        boolean success = job.printPage(printArea);

        if (success) {
            job.endJob();
            new Alert(Alert.AlertType.INFORMATION, "Struk berhasil dicetak.").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Gagal mencetak struk.").show();
        }
    }
}
