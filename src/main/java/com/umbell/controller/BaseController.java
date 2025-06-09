package com.umbell.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.umbell.models.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class BaseController implements Initializable {

    @FXML
    private VBox contentArea;

    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Não carrega a tela de seleção de conta aqui
        // Será carregada após o usuário ser definido
    }

    /**
     * Define o usuário logado no sistema
     * @param user O usuário que fez login/registro
     */
    public void setUser(User user) {
        this.user = user;
        System.out.println("Usuário na tela base: " + user.getName());
        // Carrega a tela de seleção de conta após o usuário ser definido
        loadAccountSelect();
    }

    /**
     * Carrega a tela de seleção de conta
     */
    private void loadAccountSelect() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AccountSelect.fxml"));
            Parent accountSelect = loader.load();
            
            // Passa o usuário para o controller da tela de seleção de conta
            AccountSelectController controller = loader.getController();
            controller.setUser(user);
            
            contentArea.getChildren().setAll(accountSelect);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 