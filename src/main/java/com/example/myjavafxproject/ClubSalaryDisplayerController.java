package com.example.myjavafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClubSalaryDisplayerController {

    @FXML
    private Button backButton;

    @FXML
    Label showClubNameField;

    @FXML
    Label showSalaryField;

    @FXML
    void backToClubSearch(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("club-searching-options.fxml"));
            AnchorPane root=new AnchorPane();
            fxmlLoader.setRoot(root);
            fxmlLoader.load();

            Scene scene = new Scene(root, 800, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Failed to go back to the Club Search scene", e);
        }
    }


}

