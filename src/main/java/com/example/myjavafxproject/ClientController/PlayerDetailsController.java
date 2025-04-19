package com.example.myjavafxproject.ClientController;


import com.example.myjavafxproject.cricketdatabase.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class PlayerDetailsController {

    @FXML
    private Label name;

    @FXML
    private Label country;

    @FXML
    private Label age;

    @FXML
    private Label height;

    @FXML
    private Label position;

    @FXML
    private Label salary;

    @FXML
    private Label jersey;

    public void setPlayerDetails(Player player) {
        name.setText(player.getName());
        country.setText(player.getCountry());
        age.setText(String.valueOf(player.getAge()));
        height.setText(String.valueOf(player.getHeight())+"m");
        position.setText(player.getPosition());
        salary.setText(String.valueOf(player.getWeeklySalary()));
        jersey.setText(String.valueOf(player.getJerseyNumber()));
    }

    @FXML
    void onClose(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }
}
