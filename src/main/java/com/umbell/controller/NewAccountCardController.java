package com.umbell.controller;

import com.umbell.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.io.IOException;
import java.net.URL;

public class NewAccountCardController {
    
    @FXML
    private VBox root;
    
    private User user;

    public void setUser(User user) {
        this.user = user;
        System.out.println("NewAccountCardController: Usuário definido como: " + (user != null ? user.getEmail() : "null"));
    }

    @FXML
    private void onCardClick() {
        System.out.println("onCardClick: Card clicado. Abrindo modal AccountName...");
        try {
            // Carrega o FXML do AccountName
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AccountName.fxml"));
            Parent accountNameRoot = loader.load();

            // Obtém o controller do AccountName.fxml e passa o usuário
            AccountNameController accountNameController = loader.getController();
            accountNameController.setUser(this.user);
            System.out.println("onCardClick: Usuário passado para AccountNameController.");
            
            // Cria uma nova janela modal para AccountName
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(root.getScene().getWindow()); // Define o owner da janela modal
            stage.setTitle("Nova Conta");
            
            Scene scene = new Scene(accountNameRoot);
            stage.setScene(scene);
            
            // Centraliza a janela em relação à janela principal
            Stage mainStage = (Stage) root.getScene().getWindow();
            stage.setX(mainStage.getX() + (mainStage.getWidth() - scene.getWidth()) / 2);
            stage.setY(mainStage.getY() + (mainStage.getHeight() - scene.getHeight()) / 2);
            
            stage.showAndWait();
            System.out.println("onCardClick: Modal AccountName fechado.");

        } catch (IOException e) {
            System.err.println("onCardClick: Erro de IO ao carregar AccountName.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("onCardClick: Erro inesperado ao abrir modal de conta: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 