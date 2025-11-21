package com.tubes.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;

public class MenuController {

    @FXML
    private FlowPane menuContainer;

    @FXML
    public void initialize() {
        addMenuCard("Nasi Goreng", "12000", "Tersedia");
        addMenuCard("Ayam Geprek", "15000", "Habis");
    }

    private void addMenuCard(String nama, String harga, String status) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/tubes/ui/MenuCard.fxml")
            );

            Parent card = loader.load();
            MenuCardController controller = loader.getController();
            controller.setData(nama, harga, status);

            menuContainer.getChildren().add(card);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
