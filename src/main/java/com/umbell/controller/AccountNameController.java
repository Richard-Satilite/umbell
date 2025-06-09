package com.umbell.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AccountNameController {
    
    @FXML
    private TextField accountNameField;
    
    @FXML
    private VBox root;
    
    @FXML
    private void onCreateAccountClick() {
        String accountName = accountNameField.getText().trim();
        
        if (accountName.isEmpty()) {
            // TODO: Mostrar mensagem de erro
            return;
        }
        
        // TODO: Criar a conta com o nome fornecido
        System.out.println("Criando conta: " + accountName);
    }
} 