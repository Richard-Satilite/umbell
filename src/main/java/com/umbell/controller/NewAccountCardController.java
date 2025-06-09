package com.umbell.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.io.IOException;

public class NewAccountCardController {
    
    @FXML
    private VBox root;
    
    @FXML
    private void onCardClick() {
        try {
            // Carrega o FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AccountName.fxml"));
            Parent accountNameRoot = loader.load();
            
            // Cria uma nova janela modal
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Nova Conta");
            
            // Cria uma nova cena com o FXML carregado
            Scene scene = new Scene(accountNameRoot);
            stage.setScene(scene);
            
            // Centraliza a janela em relação à janela principal
            Stage mainStage = (Stage) root.getScene().getWindow();
            stage.setX(mainStage.getX() + (mainStage.getWidth() - scene.getWidth()) / 2);
            stage.setY(mainStage.getY() + (mainStage.getHeight() - scene.getHeight()) / 2);
            
            // Mostra a janela
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 