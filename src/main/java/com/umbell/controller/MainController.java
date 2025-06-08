package com.umbell.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainController implements Initializable {

    @FXML
    private VBox vbox;
    private Parent fxml;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(vbox.getLayoutX() * 20);
        t.play();
        t.setOnFinished((e) -> {
            loadFXMLWithFade("/fxml/SignIn.fxml");
        });
    }

    @FXML
    private void openSignIn(ActionEvent event) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(vbox.getLayoutX() * 20);
        t.play();
        t.setOnFinished((e) -> {
            loadFXMLWithFade("/fxml/SignIn.fxml");
        });
    }

    @FXML
    private void openSignUp(ActionEvent event) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(0);
        t.play();
        t.setOnFinished((e) -> {
            loadFXMLWithFade("/fxml/SignUp.fxml");
        });
    }

    private void loadFXMLWithFade(String fxmlPath) {
        try {
            fxml = FXMLLoader.load(getClass().getResource(fxmlPath));

            fxml.setOpacity(0);

            vbox.getChildren().setAll(fxml);

            FadeTransition fade = new FadeTransition(Duration.millis(500), fxml);
            fade.setFromValue(0.0);
            fade.setToValue(1.0);
            fade.play();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
