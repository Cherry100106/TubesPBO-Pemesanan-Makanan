package com.tubes.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuCardController {

    @FXML private AnchorPane root;
    @FXML private Label txtNama;
    @FXML private Label txtHarga;
    @FXML private Label txtStatus;

    private String nama;
    private String harga;
    private String status;

    public void setData(String nama, String harga, String status) {
        this.nama = nama;
        this.harga = harga;
        this.status = status;

        txtNama.setText(nama);
        txtHarga.setText("Rp " + harga);
        txtStatus.setText(status);
    }

    @FXML
    public void initialize() {
        root.setOnMouseClicked(e -> openEditForm());
    }

    private void openEditForm() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/tubes/ui/MenuForm.fxml")
            );

            Parent root = loader.load();
            MenuFormController controller = loader.getController();
            controller.setData(nama, harga, status);

            Stage stage = new Stage();
            stage.setTitle("Edit Menu");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
