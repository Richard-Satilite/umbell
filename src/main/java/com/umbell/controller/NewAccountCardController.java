package com.umbell.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class NewAccountCardController {
    
    @FXML
    private VBox root;
    
    @FXML
    public void initialize() {
        if (root != null) {
            // Adiciona efeito de hover
            root.setOnMouseEntered(e -> root.getStyleClass().add("new-account-card-hover"));
            root.setOnMouseExited(e -> root.getStyleClass().remove("new-account-card-hover"));
        }
    }
} 