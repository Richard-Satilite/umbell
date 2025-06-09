package com.umbell.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.umbell.models.User;
import com.umbell.models.Account;
import com.umbell.service.UserService;
import com.umbell.repository.UserRepositoryImpl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class AccountSelectController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private Label welcomeLabel;

    @FXML
    private ListView<Account> accountsListView;

    @FXML
    private Button createAccountButton;

    private User user;
    private UserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserService(new UserRepositoryImpl());
        setupAccountsListView();
    }

    /**
     * Define o usuário logado no sistema
     * @param user O usuário que fez login/registro
     */
    public void setUser(User user) {
        this.user = user;
        welcomeLabel.setText("Bem-vindo(a), " + user.getName() + "!");
        loadUserAccounts();
    }

    private void setupAccountsListView() {
        accountsListView.setCellFactory(lv -> new AccountListCell());
        accountsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Account selectedAccount = accountsListView.getSelectionModel().getSelectedItem();
                if (selectedAccount != null) {
                    // TODO: Implementar a navegação para a tela da conta selecionada
                    System.out.println("Conta selecionada: " + selectedAccount.getCode());
                }
            }
        });
    }

    private void loadUserAccounts() {
        if (user != null) {
            var accounts = userService.getUserAccounts(user);
            if (accounts.isEmpty()) {
                // Se não houver contas, mostra o card de nova conta
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewAccountCard.fxml"));
                    VBox newAccountCard = loader.load();
                    
                    // Adiciona o evento de clique no card
                    newAccountCard.setOnMouseClicked(e -> onCreateAccountClick());
                    
                    accountsListView.getItems().clear();
                    accountsListView.setPlaceholder(newAccountCard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                accountsListView.getItems().setAll(accounts);
            }
        }
    }

    @FXML
    private void onCreateAccountClick() {
        // TODO: Implementar a criação de nova conta
        System.out.println("Criar nova conta");
    }
} 