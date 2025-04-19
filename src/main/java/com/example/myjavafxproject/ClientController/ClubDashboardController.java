package com.example.myjavafxproject.ClientController;

import com.example.myjavafxproject.CustomSceneLoader;
import com.example.myjavafxproject.cricketdatabase.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ClubDashboardController {

    private ClientConnection clientConnection;
    private List<Player> players;
    private String clubName;

    @FXML
    private ImageView clubLogo;
    @FXML
    private Label ClubNameField;
    @FXML
    private VBox playerListVBox;
    @FXML
    private ToggleGroup showStatsPlayersGroup;

    private String currentSearchType;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button cancelButton;


    public void fetchClubPlayers(){
        try {
            System.out.println("Fetching players for club: " + clubName);
            clientConnection.sendMessage("FETCH_CLUB_PLAYER,"+clubName);

            Object response=clientConnection.receiveMessage();

            if (response instanceof List){
                players = new ArrayList<>((List<Player>) response);
                System.out.println("Updated players fetched from server: "+ ((List<Player>) response).size() + " and players.size()" + players.size());
            }
            else {
                players= Collections.emptyList();
                System.err.println("Invalid response received for club players.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            players = Collections.emptyList();
        }
    }

    public void ConstructClub(String clubName, ClientConnection connection) {
        this.clubName = clubName;
        this.clientConnection = connection;

        fetchClubPlayers();
        System.out.println("Club: " + clubName + " - Players fetched: " + players.size());

        ClubNameField.setText(clubName.toUpperCase());

        String imagePath = "/images/"+clubName+".png";
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        clubLogo.setImage(image);

        showPlayers();
    }

    public void showPlayers() {
        System.out.println("Refreshing player list in dashboard...");
        playerListVBox.getChildren().clear();
        try {
            for (Player player : players) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myjavafxproject/Player-Node.fxml"));
                AnchorPane playerNode = loader.load();

                PlayerNodeController controller = loader.getController();
                controller.setPlayerNodeData(player,clubName,false);
                controller.setClubDashboardController(this);
                controller.setClientConnection(clientConnection);

                playerListVBox.getChildren().add(playerNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerListAfterSale() {
        System.out.println("Fetching updated club players...");
        fetchClubPlayers();

        System.out.println("Updated club players list size: " + players.size());
        showPlayers();
    }


    @FXML
    void onDashboard(ActionEvent event) {

    }


    @FXML
    void onMarketplace(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myjavafxproject/marketplace.fxml"));
            AnchorPane root = loader.load();

            MarketplaceController marketplaceController = loader.getController();

            marketplaceController.setClientConnection(clientConnection);
            marketplaceController.initializeMarketplace(clubName);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void maxAge(ActionEvent event) {
        disableSearch();
        int maxAge=0;
        List<Player> filteredPlayer=new ArrayList<>();

        for (Player player : players){
            if (player.getAge() > maxAge) {
                maxAge = player.getAge();
                filteredPlayer.clear();
                filteredPlayer.add(player);
            } else if (player.getAge() == maxAge) {
                filteredPlayer.add(player);
            }
        }
        showFilteredPlayers(filteredPlayer, "maxAge");
    }

    @FXML
    void maxHeight(ActionEvent event) {
        disableSearch();
        double maxHeight=0;
        List<Player> filteredPlayer=new ArrayList<>();

        for (Player player : players){
            if (player.getHeight() > maxHeight) {
                maxHeight = player.getHeight();
                filteredPlayer.clear();
                filteredPlayer.add(player);
            } else if (player.getHeight() == maxHeight) {
                filteredPlayer.add(player);
            }
        }
        showFilteredPlayers(filteredPlayer, "maxHeight");
    }

    @FXML
    void maxSalary(ActionEvent event) {
        disableSearch();
        int maxSalary=0;
        List<Player> filteredPlayer=new ArrayList<>();

        for (Player player : players){
            if (player.getWeeklySalary() > maxSalary) {
                maxSalary = player.getWeeklySalary();
                filteredPlayer.clear();
                filteredPlayer.add(player);
            } else if (player.getWeeklySalary() == maxSalary) {
                filteredPlayer.add(player);
            }
        }
        showFilteredPlayers(filteredPlayer, "maxSalary");
    }

    @FXML
    void onCountry(ActionEvent event) {
        enableSearch("Enter country");
        currentSearchType = "country";
    }

    @FXML
    void onCountryWisePlayerCount(ActionEvent event) {
        Map<String, Long> countryCounts = players.stream()
                .collect(Collectors.groupingBy(Player::getCountry, Collectors.counting()));

        VBox contentVBox = new VBox();
        contentVBox.setSpacing(15);
        contentVBox.setAlignment(Pos.CENTER);
        contentVBox.setStyle("-fx-background-color: #19398a; -fx-padding: 20;");

        Label title = new Label("Country-wise Player Count");
        title.setStyle("-fx-font-size: 30; -fx-text-fill: white; -fx-font-weight: bold;");
        contentVBox.getChildren().add(title);

        countryCounts.forEach((country, count) -> {
            Label label = new Label(country + " : " + count);
            label.setStyle("-fx-font-size: 20; -fx-text-fill: white;");
            contentVBox.getChildren().add(label);
        });

        // Create a new Scene and Stage
        Scene scene = new Scene(contentVBox, 600, 300);
        Stage stage = new Stage();
        stage.setTitle("Country-wise Player Count");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void onPlayerName(ActionEvent event) {
        enableSearch("Enter player name");
        currentSearchType = "playerName";
    }

    @FXML
    void onPosition(ActionEvent event) {
        enableSearch("Enter position");
        currentSearchType = "position";
    }

    private void enableSearch(String promptText) {
        if (showStatsPlayersGroup.getSelectedToggle() != null) {
            showStatsPlayersGroup.selectToggle(null);
            showPlayers();
        }

        searchField.setOpacity(1.0);
        searchField.setPromptText(promptText);
        searchField.setDisable(false);

        searchButton.setOpacity(1.0);
        searchButton.setDisable(false);

        cancelButton.setOpacity(1.0);
        cancelButton.setDisable(false);
    }
    private void disableSearch() {
        searchField.setOpacity(0.25);
        searchField.setPromptText("Enter");
        searchField.clear();

        searchButton.setOpacity(0.25);
        searchButton.setDisable(true);

        cancelButton.setOpacity(0.25);
        cancelButton.setDisable(true);
    }

    @FXML
    void onSearch(ActionEvent event) {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            return;
        }

        List<Player> filteredPlayers = new ArrayList<>();
        switch (currentSearchType) {
            case "playerName":
                filteredPlayers = players.stream()
                        .filter(player -> player.getName().equalsIgnoreCase(searchText))
                        .toList();
                break;
            case "country":
                filteredPlayers = players.stream()
                        .filter(player -> player.getCountry().equalsIgnoreCase(searchText))
                        .toList();
                break;
            case "position":
                filteredPlayers = players.stream()
                        .filter(player -> player.getPosition().equalsIgnoreCase(searchText))
                        .toList();
                break;
        }

        showFilteredPlayers(filteredPlayers, currentSearchType);

    }

    @FXML
    void onCancelSearch(ActionEvent event) {
        disableSearch();
        showPlayers();
    }

    @FXML
    void resetStats(ActionEvent event) {
        if (showStatsPlayersGroup != null) {
            showStatsPlayersGroup.selectToggle(null);
        }
        showPlayers();
    }
    public void unselectRadioStats(){
        if (showStatsPlayersGroup != null) {
            showStatsPlayersGroup.selectToggle(null);
        }
    }


    @FXML
    void onLogout(ActionEvent event) {
        CustomSceneLoader.loadNewScene(event, "Login-page.fxml");
        try {
            clientConnection.sendMessage("LOG_OUT");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void showFilteredPlayers(List<Player> filteredPlayer, String searchType){
        System.out.println("Refreshing filteredPlayer list in dashboard...");
        playerListVBox.getChildren().clear();
        if (filteredPlayer.isEmpty()) {
            Label emptyLabel = new Label("No players found!!!.");
            emptyLabel.setStyle("-fx-font-size: 16; -fx-text-fill: gray;");
            playerListVBox.getChildren().add(emptyLabel);
            return;
        }

        try {
            for (Player player : filteredPlayer) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myjavafxproject/Player-Node.fxml"));
                AnchorPane playerNode = loader.load();

                PlayerNodeController controller = loader.getController();
                controller.setPlayerNodeData(player,clubName,false);
                controller.setClubDashboardController(this);
                controller.setClientConnection(clientConnection);
                controller.setSellButton(false);

                if (searchType.equals("maxAge")){
                    controller.setFilteredResultLebel("Age: "+player.getAge());
                } else if(searchType.equals("maxHeight")){
                    controller.setFilteredResultLebel("Height: "+player.getHeight());
                } else if(searchType.equals("maxSalary")){
                    controller.setFilteredResultLebel("Salary: "+player.getWeeklySalary());
                }
                else {
                    controller.setFilteredResultLebel("");
                }


                playerListVBox.getChildren().add(playerNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
