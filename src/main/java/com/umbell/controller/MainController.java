package com.umbell.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.umbell.models.User;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controlador principal da aplicação.
 * Gerencia a navegação entre as telas de login e registro.
 *
 * @author Richard Satilite
 */
public class MainController implements Initializable {

    @FXML
    private VBox vbox;
    private Parent fxml;
    private User user;

    /**
     * Inicializa o controlador e configura a animação inicial.
     * Carrega a tela de login após a animação.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(vbox.getLayoutX() * 20);
        t.play();
        t.setOnFinished((e) -> {
            loadFXMLWithFade("/fxml/SignIn.fxml");
        });
    }

    /**
     * Define o usuário logado no sistema
     * @param user O usuário que fez login
     */
    public void setUser(User user) {
        this.user = user;
        // Aqui você pode adicionar lógica adicional para atualizar a UI com os dados do usuário
        System.out.println("Usuário logado: " + user.getName());
    }

    /**
     * Manipula o evento de clique no botão de login.
     * Abre a tela de login com animação.
     *
     * @param event O evento de clique
     */
    @FXML
    private void openSignIn(ActionEvent event) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(vbox.getLayoutX() * 20);
        t.play();
        t.setOnFinished((e) -> {
            loadFXMLWithFade("/fxml/SignIn.fxml");
        });
    }

    /**
     * Manipula o evento de clique no botão de registro.
     * Abre a tela de registro com animação.
     *
     * @param event O evento de clique
     */
    @FXML
    private void openSignUp(ActionEvent event) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(0);
        t.play();
        t.setOnFinished((e) -> {
            loadFXMLWithFade("/fxml/SignUp.fxml");
        });
    }

    /**
     * Carrega um arquivo FXML com efeito de fade.
     *
     * @param fxmlPath O caminho do arquivo FXML a ser carregado
     */
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
