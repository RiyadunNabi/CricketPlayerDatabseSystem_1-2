package com.example.myjavafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CustomSceneLoader {

    public static void loadNewScene(ActionEvent event, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(CustomSceneLoader.class.getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(CustomSceneLoader.class.getResource("/styles.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadNewSceneWithDynamicRoot(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CustomSceneLoader.class.getResource(fxmlPath));

            AnchorPane root = new AnchorPane();
            fxmlLoader.setRoot(root);
            fxmlLoader.load();

            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(Objects.requireNonNull(CustomSceneLoader.class.getResource("/styles.css")).toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadNewSceneWithController(ActionEvent event, String fxmlPath, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CustomSceneLoader.class.getResource(fxmlPath));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(CustomSceneLoader.class.getResource("/styles.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
