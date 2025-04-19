package com.example.myjavafxproject;

import com.example.myjavafxproject.cricketdatabase.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;

import java.io.IOException;
import java.util.List;

public class PlayerInfoController {

    @FXML
    private TableView<Player> playerTableView;

    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TableColumn<Player, String> countryColumn;

    @FXML
    private TableColumn<Player, Integer> ageColumn;

    @FXML
    private TableColumn<Player, Double> heightColumn;

    @FXML
    private TableColumn<Player, String> clubColumn;

    @FXML
    private TableColumn<Player, String> positionColumn;

    @FXML
    private TableColumn<Player, Integer> jerseyNoColumn;

    @FXML
    private TableColumn<Player, Double> weeklySalaryColumn;

    @FXML
    private Button backButton;

    private ObservableList<Player> playerList;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        jerseyNoColumn.setCellValueFactory(new PropertyValueFactory<>("jerseyNumber"));
        weeklySalaryColumn.setCellValueFactory(new PropertyValueFactory<>("weeklySalary"));
    }

    private void showPlayerInfo(List<Player> players) {
        try {
            // Load the Player Info FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("player-info.fxml"));
            Parent playerInfoRoot = loader.load();

            // Get the controller and pass the player data
            PlayerInfoController controller = loader.getController();
            controller.setPlayerList(players);

            // Display the Player Info stage
            Stage stage = new Stage();
            stage.setTitle("Player Information");
            stage.setScene(new Scene(playerInfoRoot));
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void setPlayerList(List<Player> players) {
        // Convert the list to an observable list and set it to the TableView
        playerList = FXCollections.observableArrayList(players);
        playerTableView.setItems(playerList);
    }

    @FXML
    private void onBackButtonClick(ActionEvent event) {
        // Close the current stage when Back button is clicked
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

