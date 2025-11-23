package com.tubes.ui;

import com.tubes.menu.MenuItem;
import com.tubes.menu.MenuRepository;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class OrderController {

    @FXML private TableView<MenuItem> tableMenu;
    @FXML private TableColumn<MenuItem, String> colNamaMenu;
    @FXML private TableColumn<MenuItem, Double> colHarga;
    @FXML private TableColumn<MenuItem, String> colKategori;
    @FXML private TableColumn<MenuItem, String> colAvailable;

    @FXML private TextField inputQty;
    @FXML private Button btnAdd;

    @FXML private TableView<?> tableCart;
    @FXML private TableColumn<?, ?> colCartNama;
    @FXML private TableColumn<?, ?> colCartQty;
    @FXML private TableColumn<?, ?> colCartSubtotal;


    @FXML private Label labelTotal;
    @FXML private Button btnKonfirmasi;

    private final MenuRepository menuRepo = new MenuRepository();
    private ObservableList<MenuItem> menuList;

    @FXML
    public void initialize() {
        System.out.println("Order Page Loaded");

        loadMenuTable();
    }

    private void loadMenuTable() {

        // Mapping kolom ke field pada Model
        colNamaMenu.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getNamaMakanan())
        );

        colHarga.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getHarga())
        );

        colKategori.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getKategori())
        );

        colAvailable.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().isAvailable() ? "Tersedia" : "Tidak Tersedia"
                )
        );

        // Ambil data dari database
        menuList = FXCollections.observableArrayList(menuRepo.getAllMenus());

        // Tampilkan di TableView
        tableMenu.setItems(menuList);
    }
}
