package com.example.myjavafxproject.ClientController;

import com.example.myjavafxproject.cricketdatabase.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class PlayerNodeController {

    private Player player;

    @FXML
    private ImageView playerImage;

    @FXML
    private Text playerName;

    @FXML
    private Text askingPrice;

    @FXML
    private Text CountryAndPosition;

    @FXML
    private Button sellButton;
    public void setSellButton(boolean b){
        sellButton.setVisible(b);
    }

    @FXML
    private Button buyButton;

    @FXML
    private Button detailsButton;
    @FXML
    private Text filteredResultLebel;
    public void setFilteredResultLebel(String s){
        filteredResultLebel.setVisible(true);
        filteredResultLebel.setText(s);
    }

    private ClubDashboardController clubDashboardController;
    private ClientConnection clientConnection;

    private MarketplaceController marketplaceController;
    private String Club_which_is_viewing_marketplace;

    public void setMarketplaceController_and_club(MarketplaceController controller, String club) {
        this.marketplaceController = controller;
        this.Club_which_is_viewing_marketplace=club;
    }

    public void setClubDashboardController(ClubDashboardController controller) {
        this.clubDashboardController = controller;
    }
    public void setClientConnection(ClientConnection connection) {
        this.clientConnection = connection;
    }



    public void setPlayerNodeData(Player player, String currentClub, boolean isMarketplace) {
        this.player=player;
        playerName.setText(player.getName());
        CountryAndPosition.setText(player.getCountry() + ", " + player.getPosition());

        if (isMarketplace) {
            sellButton.setVisible(false);
            buyButton.setVisible(true);

            if (currentClub.equals(player.getClub())) {
                buyButton.setDisable(true);
            } else {
                buyButton.setDisable(false);
            }

            askingPrice.setVisible(true);
            askingPrice.setText("Asking Price: "+player.getAskingPrice());
        } else {
            sellButton.setVisible(true);
            buyButton.setVisible(false);
            sellButton.setDisable(false);
        }
    }
    @FXML
    private void onSell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myjavafxproject/confirm-sell.fxml"));
            AnchorPane root = loader.load();

            ConfirmSellController confirmSellController = loader.getController();
            confirmSellController.setPlayer(player);

            confirmSellController.setCallback((playerName, sellingClub, price) ->  {
                player.setAskingPrice(String.valueOf(price));

                notifyServerAboutSale(player.getName(), price);
                refreshClubDashboard(player.getName());
            });

            Stage sellStage = new Stage();
            sellStage.setTitle("Confirm Sell");
            sellStage.setScene(new Scene(root));
            sellStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyServerAboutSale(String playerName, String price) {
        try {
            String message = "SELL_PLAYER," + playerName + "," + price + "," + player.getClub();
            clientConnection.sendMessage(message);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void refreshClubDashboard(String playerName) {
        if (clubDashboardController != null) {
            clubDashboardController.updatePlayerListAfterSale();
            sellButton.setDisable(true);
            System.out.println("ClubDashboard refreshed for player: " + playerName);
        } else {
            System.err.println("Failed to refresh ClubDashboard: Controller not found.");
        }
    }

    @FXML
    private void onDetails() {
        System.out.println("Details button clicked for player: " + playerName.getText());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myjavafxproject/player-details-box.fxml"));
            AnchorPane root = loader.load();

            PlayerDetailsController controller = loader.getController();
            controller.setPlayerDetails(player);

            Stage detailsStage = new Stage();
            detailsStage.setTitle("Player Details");
            detailsStage.setScene(new Scene(root));
            detailsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onBuy(ActionEvent event) {
        try {
            String message = "BUY_PLAYER," + player.getName() + "," + Club_which_is_viewing_marketplace; // Notify server
            clientConnection.sendMessage(message);

            String response = (String) clientConnection.receiveMessage();

            if ("NOT_FOUND_PLAYER_SOLD".equals(response)) {
                showAlert("Oops!", "Oops! The player has been bought by another club. Please refresh the Marketplace.", Alert.AlertType.ERROR);
            } else if ("BUY_SUCCESS".equals(response)) {
                showAlert("Success!", "Congratulations! The player is now in your club.", Alert.AlertType.INFORMATION);
                marketplaceController.refreshMarketplace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

