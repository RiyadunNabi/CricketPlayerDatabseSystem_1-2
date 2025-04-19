package com.example.myjavafxproject.ClientController;

import com.example.myjavafxproject.cricketdatabase.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MarketplaceController {

    @FXML
    private Label ClubNameField;

    @FXML
    private ImageView clubLogo;

    @FXML
    private VBox playerMarketplaceVBox;

    private String currentClub;
    private List<Player> currentPlayers=new ArrayList<>();


    private ClientConnection clientConnection;

    public void setClientConnection(ClientConnection connection) {
        this.clientConnection = connection; // Reuse connection
    }

    public void initializeMarketplace(String clubName) {
        this.currentClub = clubName;
        refreshMarketplace();

        ClubNameField.setText(clubName.toUpperCase());

        String imagePath = "/images/"+clubName+".png";
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        clubLogo.setImage(image);
    }


    private List<Player> fetchSoldPlayerListFromServer() {
        try {
            clientConnection.sendMessage("FETCH_SOLD_PLAYER_LIST"); // Request sold players from server
            Object response = clientConnection.receiveMessage();

            if (response instanceof List) {
                return (List<Player>) response;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IOException | ClassNotFoundException e hiiiiiiiiiiiiiiiiiiiiii");
            e.printStackTrace();
        }

        System.out.println("Empty List sent!!!");

        return new ArrayList<>();
    }


    @FXML
    private void onDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myjavafxproject/club-dashboard.fxml"));
            AnchorPane root = loader.load();

            ClubDashboardController controller = loader.getController();
            controller.ConstructClub(currentClub, clientConnection);

            Stage stage = (Stage) playerMarketplaceVBox.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onMarketplace(){ //refresh
        initializeMarketplace(currentClub);
    }

    public void refreshMarketplace() {
        currentPlayers = fetchSoldPlayerListFromServer();
        playerMarketplaceVBox.getChildren().clear();

        if (currentPlayers.isEmpty()) {
            System.out.println("No players available in the marketplace.");
            Label emptyLabel = new Label("No players available in the marketplace.");
            emptyLabel.setStyle("-fx-font-size: 16; -fx-text-fill: gray;");
            playerMarketplaceVBox.getChildren().add(emptyLabel);
            return;
        }

        try {
            for (Player player : currentPlayers) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myjavafxproject/Player-Node.fxml"));
                AnchorPane playerNode = loader.load();

                PlayerNodeController controller = loader.getController();
                controller.setPlayerNodeData(player, currentClub, true);
                controller.setMarketplaceController_and_club(this,currentClub);
                controller.setClientConnection(clientConnection);

                playerMarketplaceVBox.getChildren().add(playerNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void onRefresh(ActionEvent event){
        refreshMarketplace();
    }

}
