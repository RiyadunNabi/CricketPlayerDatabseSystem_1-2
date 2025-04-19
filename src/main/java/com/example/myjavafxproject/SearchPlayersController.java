package com.example.myjavafxproject;

import com.example.myjavafxproject.cricketdatabase.CricketPlayerDatabase;
import com.example.myjavafxproject.cricketdatabase.Player;
import com.example.myjavafxproject.cricketdatabase.SearchPlayers;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SearchPlayersController {

    private SearchPlayers searchPlayers;
    private CricketPlayerDatabase allDatabase;

    public SearchPlayersController() {
        this.searchPlayers = new SearchPlayers();

    }


    @FXML
    private void onSearchByName(ActionEvent event) {
        try {
            // Load the FXML for "search-by-name.fxml"
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search-players-by-name.fxml"));
            Parent root = fxmlLoader.load();

            TextField playerNameField = (TextField) root.lookup("#playerNameField");
            Button searchButton = (Button) root.lookup("#searchButton");
            Button backButton = (Button) root.lookup("#backButton");

            // search functionality
            searchButton.setOnAction(e -> {
                String playerName = playerNameField.getText().trim();
                if (!playerName.isEmpty()) {
                    List<Player> players = searchPlayers.searchByName(playerName);
                    if (players.isEmpty()) {
                        showAlert("No Results", "No players found with the name: " + playerName);
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("player-info-displayer.fxml"));
                            Parent PlayerInfoRoot = loader.load();

                            PlayerInfoController controller = loader.getController();
                            controller.setPlayerList(players);

                            Stage stage = new Stage();
                            stage.setTitle("Player Information");
                            stage.setScene(new Scene(PlayerInfoRoot));
                            stage.show();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else {
                    showAlert("Input Error", "Please enter a player name.");
                }
            });

            backButton.setOnAction(this::backToSearchPlayers);

            Scene scene = new Scene(root, 800, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void onSearchByClubAndCountry(ActionEvent event) {
        try {
            // Load the FXML for "search-by-name.fxml"
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search-players-by-club-country.fxml"));
            Parent root = fxmlLoader.load();

            TextField clubNameField = (TextField) root.lookup("#clubNameField");
            TextField countryNameField = (TextField) root.lookup("#countryNameField");
            Button searchButton = (Button) root.lookup("#searchButton");
            Button backButton = (Button) root.lookup("#backButton");

            //search
            searchButton.setOnAction(e -> {
                String clubName = clubNameField.getText().trim();
                String countryName = countryNameField.getText().trim();

                if (!countryName.isEmpty()) {
                    List<Player> players = searchPlayers.getPlayersByClubAndCountry(clubName, countryName);
                    if (players.isEmpty()) {
                        showAlert("No Results", "No players found with the country name: " + countryName);
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("player-info-displayer.fxml"));
                            Parent PlayerInfoRoot = loader.load();

                            PlayerInfoController controller = loader.getController();
                            controller.setPlayerList(players);

                            Stage stage = new Stage();
                            stage.setTitle("Player Information");
                            stage.setScene(new Scene(PlayerInfoRoot));
                            stage.show();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else {
                    showAlert("Input Error", "Please enter a player name.");
                }
            });

            backButton.setOnAction(this::backToSearchPlayers);

            Scene scene = new Scene(root, 800, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onSearchByPosition(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search-players-by-position.fxml"));
            Parent root = fxmlLoader.load();
            TextField positionField = (TextField) root.lookup("#positionField");
            Button searchButton = (Button) root.lookup("#searchButton");
            Button backButton = (Button) root.lookup("#backButton");

            //search
            searchButton.setOnAction(e -> {
                String position = positionField.getText().trim();

                if (!position.isEmpty()) {
                    List<Player> players = searchPlayers.getPlayersByPosition(position);
                    if (players.isEmpty()) {
                        showAlert("No Results", "No players found with the position: " + position);
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("player-info-displayer.fxml"));
                            Parent PlayerInfoRoot = loader.load();

                            PlayerInfoController controller = loader.getController();
                            controller.setPlayerList(players);

                            Stage stage = new Stage();
                            stage.setTitle("Player Information");
                            stage.setScene(new Scene(PlayerInfoRoot));
                            stage.show();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else {
                    showAlert("Input Error", "Please enter correct position.");
                }
            });

            backButton.setOnAction(this::backToSearchPlayers);
            Scene scene = new Scene(root, 800, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onSearchBySalaryRange(ActionEvent event) {
        try {
            // Load the FXML for "search-by-name.fxml"
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search-players-by-salary-range.fxml"));
            Parent root = fxmlLoader.load();

            TextField minSalaryField = (TextField) root.lookup("#minSalaryField");
            TextField maxSalaryField = (TextField) root.lookup("#maxSalaryField");
            Button searchButton = (Button) root.lookup("#searchButton");
            Button backButton = (Button) root.lookup("#backButton");

            //search
            searchButton.setOnAction(e -> {
                try {
                    int minSalary = Integer.parseInt(minSalaryField.getText().trim());
                    int maxSalary = Integer.parseInt(maxSalaryField.getText().trim());


                    List<Player> players = searchPlayers.getPlayersBySalaryRange(minSalary, maxSalary);
                    if (players.isEmpty()) {
                        showAlert("No Results", "No players found within the Salary Range.");
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("player-info-displayer.fxml"));
                            Parent PlayerInfoRoot = loader.load();

                            PlayerInfoController controller = loader.getController();
                            controller.setPlayerList(players);

                            Stage stage = new Stage();
                            stage.setTitle("Player Information");
                            stage.setScene(new Scene(PlayerInfoRoot));
                            stage.show();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } catch (Exception ex) {
                    showAlert("Input Error", "Please enter a player name.");
                    throw new RuntimeException(ex);
                }
            });

            backButton.setOnAction(this::backToSearchPlayers);

            Scene scene = new Scene(root, 800, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onCountryWiseCount(ActionEvent event) {
        Map<String, Integer> countryPlayerMap = searchPlayers.getCountryWisePlayerCount();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("countrywise-player-count.fxml"));
            Parent root = fxmlLoader.load();

            TableView<Map.Entry<String, Integer>> countryPlayerTable = (TableView<Map.Entry<String, Integer>>) root.lookup("#countryPlayerTable");

            if (countryPlayerTable != null) {
                TableColumn<Map.Entry<String, Integer>, String> countryName = (TableColumn<Map.Entry<String, Integer>, String>) countryPlayerTable.getColumns().get(0);
                TableColumn<Map.Entry<String, Integer>, Integer> playerCount = (TableColumn<Map.Entry<String, Integer>, Integer>) countryPlayerTable.getColumns().get(1);

                countryName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
                playerCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getValue()).asObject());

                ObservableList<Map.Entry<String, Integer>> data = FXCollections.observableArrayList(countryPlayerMap.entrySet());
                countryPlayerTable.setItems(data);
            }

            Button backButton = (Button) root.lookup("#backButton");
            backButton.setOnAction(this::backToSearchPlayers);
            // Display the scene in a new window
            Scene scene = new Scene(root, 800, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onBackToMainMenu(ActionEvent event) {
        CustomSceneLoader.loadNewScene(event,"main-menu.fxml");
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void backToSearchPlayers(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("search-players.fxml"));
            Scene previousScene = new Scene(fxmlLoader2.load(), 800, 600);
            previousScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(previousScene);

            System.out.println("Back to Main Menu button clicked.");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
