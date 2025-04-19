package com.example.myjavafxproject;

import com.example.myjavafxproject.cricketdatabase.CricketPlayerDatabase;
import com.example.myjavafxproject.cricketdatabase.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPlayerController implements Initializable {

    @FXML
    private TextField ageField;

    @FXML
    private ChoiceBox<String> clubBox;

    @FXML
    private ChoiceBox<String> countryBox;

    @FXML
    private TextField heightField;

    @FXML
    private TextField jerseyNoField;

    @FXML
    private TextField nameField;

    @FXML
    private ChoiceBox<String> positionBox;

    @FXML
    private TextField salaryField;

    private String[] countries = {"India", "Australia", "England", "South Africa", "New Zealand", "Pakistan", "Sri Lanka", "West Indies", "Bangladesh", "Afghanistan"};
    private String[] clubs = {"Mumbai Indians", "Chennai Super Kings", "Royal Challengers Bangalore", "Kolkata Knight Riders", "Delhi Capitals", "Punjab Kings", "Rajasthan Royals", "Sunrisers Hyderabad", "Gujarat Titans", "Lucknow Super Giants"};
    private String[] positions = {"Batsman", "Bowler", "Allrounder", "Wicketkeeper"};

    CricketPlayerDatabase database;

    public AddPlayerController(){
        database=CricketPlayerDatabase.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add options to ChoiceBoxes using arrays
        countryBox.getItems().addAll(countries);
        clubBox.getItems().addAll(clubs);
        positionBox.getItems().addAll(positions);
    }

    @FXML
    void AddButtonClick(ActionEvent event) {
        String name = nameField.getText();
        String age = ageField.getText();
        String height = heightField.getText();
        String jerseyNo = jerseyNoField.getText();
        String salary = salaryField.getText();
        String club = clubBox.getValue();
        String country = countryBox.getValue();
        String position = positionBox.getValue();

        // Validate inputs
        if (name.isEmpty() || age.isEmpty() || height.isEmpty() || jerseyNo.isEmpty() || salary.isEmpty() ||
                club == null || country == null || position == null) {
            System.out.println("Please fill in all fields correctly.");
            return;
        }

        try {
            int ageInt = Integer.parseInt(age);
            double heightDouble = Double.parseDouble(height);
            int jerseyNoInt = Integer.parseInt(jerseyNo);
            int salaryInt=Integer.parseInt(salary);

            Player newPlayer = new Player(name,country, ageInt, heightDouble,club, position, jerseyNoInt, salaryInt);

            for (Player player : database.getPlayers()){
                if (player.getName().equalsIgnoreCase(name)) {
                    showAlert("Error", "Player with this name already exists.", Alert.AlertType.ERROR);
                    clearFields();
                    return;
                }
            }

            database.addNewPlayer(newPlayer);
            clearFields();
            System.out.println("Player added successfully: ");
            showAlert("Success", "Player added successfully!", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers for age, height, jersey number, and salary.");
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        heightField.setText("");
        jerseyNoField.setText("");
        salaryField.setText("");
        clubBox.setValue(null);
        countryBox.setValue(null);
        positionBox.setValue(null);
    }

    @FXML
    void backButtonClick(ActionEvent event) {
        CustomSceneLoader.loadNewScene(event,"main-menu.fxml");
    }

}

