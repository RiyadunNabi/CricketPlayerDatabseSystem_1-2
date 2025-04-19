package com.example.myjavafxproject;

import com.example.myjavafxproject.cricketdatabase.CricketPlayerDatabase;
import com.example.myjavafxproject.cricketdatabase.Player;
import com.example.myjavafxproject.cricketdatabase.SearchClubs;
import com.example.myjavafxproject.cricketdatabase.SearchPlayers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClubSearchController implements Initializable {
    private SearchClubs searchClubs;
    private CricketPlayerDatabase allDatabase;

    public ClubSearchController() {
        this.searchClubs = new SearchClubs();

    }

    @FXML
    private Button backButton;

    @FXML
    private ChoiceBox<String> clubNameBox;

    @FXML
    private RadioButton maxAgeOption;

    @FXML
    private RadioButton maxHeightOption;

    @FXML
    private RadioButton maxSalaryOption;

    @FXML
    private Button searchButton;

    @FXML
    private ToggleGroup searchGroup;

    @FXML
    private RadioButton totalSalaryOption;
    private String[] clubs = {"Mumbai Indians", "Chennai Super Kings", "Royal Challengers Bangalore", "Kolkata Knight Riders", "Delhi Capitals", "Punjab Kings", "Rajasthan Royals", "Sunrisers Hyderabad", "Gujarat Titans", "Lucknow Super Giants"};

    public void initialize(URL location, ResourceBundle resources){
        clubNameBox.getItems().addAll(clubs);
    }

    @FXML
    void onBackToMainMenu(ActionEvent event) {
        CustomSceneLoader.loadNewScene(event, "main-menu.fxml")   ;
    }

    @FXML
    void onSearchButton(ActionEvent event) {

        RadioButton selectedOption=(RadioButton) searchGroup.getSelectedToggle();
        String clubName=clubNameBox.getValue();

        if (selectedOption==null){
            showAlert("No Option Selected", "Please select a search option before clicking Search.");
            return;
        }
        if (clubName.isEmpty()){
            showAlert("Club Name Missing", "Please enter the club name in the text field.");
            return;
        }
        List<Player> filteredPlayers;
        switch (selectedOption.getText()){
            case "Player(s) with the maximum age of a club":
                filteredPlayers=searchClubs.getMaxAgePlayersInClub(clubName);
                showPlayersTable(filteredPlayers);
                break;

            case "Player(s) with the maximum height of a club":
              filteredPlayers=searchClubs.getMaxHeightPlayersInClub(clubName);
              showPlayersTable(filteredPlayers);
              break;

            case "Player(s) with the maximum salary of a club":
                filteredPlayers=searchClubs.getMaxSalaryPlayersInClub(clubName);
                showPlayersTable(filteredPlayers);
                break;

            case "Total yearly salary of a club":
                showClubTotalSalary(clubName,event);
                break;

            default:
                showAlert("Invalid Selection", "An unknown option was selected.");
                break;
        }

    }

    public void showPlayersTable(List<Player> filteredPlayers){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("player-info-displayer.fxml"));
            Parent PlayerInfoRoot = loader.load();

            PlayerInfoController controller = loader.getController();
            controller.setPlayerList(filteredPlayers);

            Stage stage = new Stage();
            stage.setTitle("Player Information");
            stage.setScene(new Scene(PlayerInfoRoot));
            stage.show();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

//    public void showClubTotalSalary(String ClubName,ActionEvent event){
//        try{
//            FXMLLoader loader=new FXMLLoader(getClass().getResource("total-salary-of-a-club.fxml"));
//            Parent root=loader.load();
//
//            Label showClubNameField=(Label) root.lookup("#showClubNameField");
//            Label showSalaryField=(Label) root.lookup("#showSalaryField");
//
//            showClubNameField.setText(ClubName);
//            double totalSalary = searchClubs.getTotalYearlySalaryOfClub(ClubName);
//            showSalaryField.setText(String.format("%.2f", totalSalary));
//
//
//
//            Button backButtonInside = (Button) root.lookup("#backButtonInside");
//            backButtonInside.setOnAction(this::backToClubSearch);
//
//            Scene scene=new Scene(root,800,600);
//            Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.show();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    public void showClubTotalSalary(String clubName, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("total-salary-of-a-club.fxml"));
            Parent root = loader.load();

            ClubSalaryDisplayerController controller = loader.getController();

            int totalSalary = searchClubs.getTotalYearlySalaryOfClub(clubName);
            controller.showClubNameField.setText(clubName);
            controller.showSalaryField.setText(String.format("%d", totalSalary));

            Scene scene = new Scene(root, 800, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load the Total Salary scene", e);
        }
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
