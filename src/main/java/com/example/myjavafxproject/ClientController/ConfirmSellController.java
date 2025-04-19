package com.example.myjavafxproject.ClientController;

import com.example.myjavafxproject.cricketdatabase.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ConfirmSellController {

    @FXML
    private Label AreYouSureToSellField;

    @FXML
    private TextField price;

    private SellCallback callback;

    private Player player;

    public interface SellCallback {
        void onSell(String playerName, String sellingClub, String price);
    }

    public void setCallback(SellCallback callback) {
        this.callback = callback;
    }

    public void setPlayer(Player player) {
        this.player = player;
        AreYouSureToSellField.setText("Are you sure to sell "+ player.getName());
    }

    @FXML
    private void onConfirmSell(ActionEvent event) {
        String sellingPrice = price.getText();

        if (sellingPrice.isEmpty()) {
            showAlert("Error", "Please enter a price.", AlertType.ERROR);
            return;
        }

        try {
//            double sellingPrice = Double.parseDouble(priceText);
            if (callback != null) {
                callback.onSell(player.getName(), player.getClub(), sellingPrice); // Notify main logic
            }
            closeDialog();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number.", AlertType.ERROR);
        }
    }

    @FXML
    private void onClose(ActionEvent event) {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) price.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
