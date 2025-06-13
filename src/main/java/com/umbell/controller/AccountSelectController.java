package com.umbell.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import com.umbell.models.User;
import com.umbell.models.Account;
import com.umbell.service.UserService;
import com.umbell.repository.UserRepositoryImpl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccountSelectController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private Label welcomeLabel;

    @FXML
    private ListView<Account> accountsListView;

    private User user;
    private UserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserService();
        setupAccountsListView();
    }

    /**
     * Define o usuário logado no sistema
     * @param user O usuário que fez login/registro
     */
    public void setUser(User user) {
        this.user = user;
        this.userService = new UserService();
        updateWelcomeLabel();
        loadAccounts();
    }

    private void updateWelcomeLabel() {
        if (user != null) {
            welcomeLabel.setText("Olá, " + user.getName() + "!");
        }
    }

    private void setupAccountsListView() {
        accountsListView.setCellFactory(lv -> new AccountListCell());
        accountsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Account selectedAccount = accountsListView.getSelectionModel().getSelectedItem();
                if (selectedAccount != null) {
                    System.out.println("Conta selecionada: " + selectedAccount.getName());
                    Stage currentStage = (Stage) root.getScene().getWindow();
                    
                    // Fecha a janela Base.fxml (janela principal)
                    Stage baseStage = (Stage) currentStage.getOwner();
                    if (baseStage != null) {
                        baseStage.close();
                        System.out.println("onCreateAccountClick: Base.fxml fechado.");
                    }
                    
                    currentStage.close();
                    
                    // Abre o Dashboard.fxml e passa o usuário e a conta selecionada
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/fxml/Dashboard.fxml"));
                        Parent dashboardRoot = loader.load();
                        
                        // Passa o usuário e a conta selecionada para o controller do dashboard
                        Object controller = loader.getController();
                        if (controller instanceof DashboardController) {
                            DashboardController dashboardController = (DashboardController) controller;
                            dashboardController.setUser(user);
                            dashboardController.setCurrentAccount(selectedAccount);
                            System.out.println("onCreateAccountClick: Usuário e conta passados para DashboardController.");
                        }
                        
                        Stage dashboardStage = new Stage();
                        dashboardStage.setTitle("Dashboard");
                        dashboardStage.setScene(new Scene(dashboardRoot));
                        dashboardStage.show();
                        System.out.println("onCreateAccountClick: Dashboard.fxml aberto.");
                    } catch (IOException e) {
                        System.err.println("Erro ao carregar Dashboard.fxml: " + e.getMessage());
                        e.printStackTrace();
                        throw new RuntimeException("Erro ao carregar Dashboard.fxml", e);
                    }
                }
            }
        });
    }

    private void loadAccounts() {
        if (user != null) {
            List<Account> accounts = userService.getUserAccounts(user);
            accountsListView.getItems().setAll(accounts);
            
            // Se não houver contas, mostra o card de nova conta
            if (accounts.isEmpty()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewAccountCard.fxml"));
                    Parent newAccountCard = loader.load();
                    
                    // Passa o usuário para o NewAccountCardController
                    NewAccountCardController newAccountCardController = loader.getController();
                    newAccountCardController.setUser(this.user);

                    accountsListView.setPlaceholder(newAccountCard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
} 