package com.tubes.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuCardController {

    @FXML private AnchorPane cardRoot;
    @FXML private Label txtNama;
    @FXML private Label txtHarga;
    @FXML private Label txtStatus;
    @FXML private ImageView imgPhoto; // ← WAJIB

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

        // gambar default sementara
        imgPhoto.setImage(null);
    }

    @FXML
    public void initialize() {
        cardRoot.setOnMouseClicked(e -> openEditForm());
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

            Scene scene = new Scene(root, 350, 400);

            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
