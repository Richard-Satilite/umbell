package com.umbell.controller;

import com.umbell.models.User;
import javafx.fxml.FXML;

public class DashboardController {
    private User user;

    public void setUser(User user) {
        this.user = user;
        // TODO: Atualizar a interface com os dados do usuário
        System.out.println("Usuário recebido no Dashboard: " + user.getName());
    }
} 