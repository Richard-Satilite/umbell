package com.umbell.controller;

import com.umbell.models.Account;
import com.umbell.models.User;
import com.umbell.service.AccountService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccountNameController {
    
    @FXML
    private TextField accountNameField;
    
    @FXML
    private VBox root;
    
    private User user;
    private final AccountService accountService = new AccountService();
    
    public void setUser(User user) {
        this.user = user;
        System.out.println("AccountNameController: Usuário definido como: " + (user != null ? user.getEmail() : "null"));
    }
    
    @FXML
    private void onCreateAccountClick() {
        System.out.println("onCreateAccountClick: Botão 'Criar Conta' clicado.");
        String accountName = accountNameField.getText().trim();
        System.out.println("onCreateAccountClick: Nome da conta: '" + accountName + "'");
        
        if (accountName.isEmpty()) {
            System.out.println("onCreateAccountClick: Erro: Nome da conta está vazio.");
            // TODO: Mostrar mensagem de erro
            return;
        }
        
        if (user == null) {
            System.out.println("onCreateAccountClick: Erro: Usuário é null. Não é possível criar conta.");
            // TODO: Mostrar mensagem de erro (usuário não definido)
            return;
        }
        
        // Cria a conta e associa ao usuário
        Account account = new Account();
        account.setUserEmail(user.getEmail());
        // Aqui você pode adicionar o nome da conta como um campo extra, se desejar
        // account.setName(accountName);
        
        System.out.println("onCreateAccountClick: Tentando registrar a conta para o email: " + user.getEmail());
        try {
            // Salva a conta no banco
            Account savedAccount = accountService.registerAccount(account);
            System.out.println("onCreateAccountClick: Conta salva com sucesso! Código: " + savedAccount.getCode());
            
            // Adiciona ao usuário
            user.getAccounts().add(savedAccount);
            System.out.println("onCreateAccountClick: Conta adicionada à lista do usuário.");
            
            // Fecha a janela atual (modal)
            Stage currentStage = (Stage) root.getScene().getWindow();
            
            // Fecha a janela Base.fxml (janela principal)
            Stage baseStage = (Stage) currentStage.getOwner();
            if (baseStage != null) {
                baseStage.close();
                System.out.println("onCreateAccountClick: Base.fxml fechado.");
            }
            
            // Fecha a janela AccountName.fxml (modal)
            currentStage.close();
            System.out.println("onCreateAccountClick: Modal fechado.");
            
            // Abre o Dashboard.fxml e passa o usuário
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
            Parent dashboardRoot = loader.load();
            // Passa o usuário para o controller do dashboard
            Object controller = loader.getController();
            if (controller instanceof DashboardController) {
                ((DashboardController) controller).setUser(user);
                System.out.println("onCreateAccountClick: Usuário passado para DashboardController.");
            }
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Dashboard");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.show();
            System.out.println("onCreateAccountClick: Dashboard.fxml aberto.");
        } catch (Exception e) {
            System.err.println("onCreateAccountClick: Erro ao criar conta ou abrir dashboard: " + e.getMessage());
            e.printStackTrace();
            // TODO: Mostrar mensagem de erro ao usuário
        }
    }
} 