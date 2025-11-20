package com.tubes.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class MenuFormController {

    @FXML private TextField inputNama;
    @FXML private TextField inputHarga;
    @FXML private ComboBox<String> inputStatus;

    public void setData(String nama, String harga, String status) {
        inputNama.setText(nama);
        inputHarga.setText(harga);
        inputStatus.setValue(status);
    }
}
