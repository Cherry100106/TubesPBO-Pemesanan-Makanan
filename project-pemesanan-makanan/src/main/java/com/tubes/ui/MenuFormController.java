package com.tubes.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class MenuFormController {

    @FXML private TextField inputNama;
    @FXML private TextField inputHarga;
    @FXML private ComboBox<String> inputStatus;
    @FXML private ImageView imgPreview;

    public void setData(String nama, String harga, String status) {
        inputNama.setText(nama);
        inputHarga.setText(harga);
        inputStatus.setValue(status);
    }

    @FXML
    public void initialize() {
        // backend 
    }
}
