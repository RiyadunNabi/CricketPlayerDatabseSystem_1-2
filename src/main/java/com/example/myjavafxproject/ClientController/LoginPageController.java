package com.example.myjavafxproject.ClientController;

import com.example.myjavafxproject.CustomSceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginPageController {

    @FXML
    private TextField passwordField;

    @FXML
    private TextField userNameField;

    @FXML
    private Label statusLabel;

    private ClientConnection clientConnection;

    public LoginPageController() {
        try {
            clientConnection = new ClientConnection();
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error connecting to server.");
        }
    }

    @FXML
    void onLoginButton(ActionEvent event) {
        String username = userNameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both username and password.");
            return;
        }

        try {
            clientConnection.sendMessage("LOGIN," + username + "," + password);
            String response = (String) clientConnection.receiveMessage();

            if ("LOGIN_SUCCESS".equals(response)) {
                statusLabel.setText("Login successful!");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myjavafxproject/club-dashboard.fxml"));
                Parent root = loader.load();

                ClubDashboardController controller = loader.getController();
                controller.ConstructClub(username, clientConnection);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                statusLabel.setText("Invalid username or password.");
            }
        } catch (IOException | ClassNotFoundException e) {
            statusLabel.setText("Error connecting to server.");
            e.printStackTrace();
        }
    }

    @FXML
    void onBackButton(ActionEvent event) {
        CustomSceneLoader.loadNewScene(event, "hello-view.fxml");
    }
}
