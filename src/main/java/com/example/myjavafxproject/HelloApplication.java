package com.example.myjavafxproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

            stage.setTitle("CricBase");

            Image icon=new Image(Objects.requireNonNull(getClass().getResource("/images/cricket_player_database_logo_transparent.png")).toString());
            stage.getIcons().add(icon);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Exc: " + e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}