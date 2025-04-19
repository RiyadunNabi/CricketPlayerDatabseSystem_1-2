package com.example.myjavafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
//import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class MainMenuController {


    @FXML
    private void onSearchPlayersClick(ActionEvent event) {
        CustomSceneLoader.loadNewScene(event,"search-players.fxml");
    }

    @FXML
    private void onSearchClubsClick(ActionEvent event) {
        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("club-searching-options.fxml"));
//
//            // Set the root dynamically
//            AnchorPane root = new AnchorPane();
//            fxmlLoader.setRoot(root);
//
//            // Load the FXML file
//            fxmlLoader.load();
//
//            Scene clubSearchScene = new Scene(root, 800, 600);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(clubSearchScene);
//            stage.setTitle("Club Searching Options");
//            stage.show();
            CustomSceneLoader.loadNewSceneWithDynamicRoot(event,"club-searching-options.fxml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onAddPlayerClick(ActionEvent event) {
        CustomSceneLoader.loadNewScene(event,"add-new-player.fxml");
    }

    @FXML
    void onWelcomePageClick(ActionEvent event) {
        CustomSceneLoader.loadNewScene(event, "hello-view.fxml");
    }

    @FXML
    private void onExitClick() {
        System.out.println("Exiting the application...");
        System.exit(0);
    }


}
