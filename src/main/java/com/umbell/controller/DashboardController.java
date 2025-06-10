package com.umbell.controller;

import com.umbell.models.User;
import com.umbell.models.Account;
import com.umbell.models.Movement;
import com.umbell.models.Notification;
import com.umbell.repository.NotificationRepository;
import com.umbell.repository.NotificationRepositoryImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.List;

public class DashboardController {
    @FXML
    private Label greetingLabel;
    
    @FXML
    private Label balanceValueLabel;
    
    @FXML
    private Label balanceNumberLabel;
    
    @FXML
    private Label incomeLabel;
    
    @FXML
    private Label expenseLabel;
    
    @FXML
    private Label summaryIncomeLabel;
    
    @FXML
    private Label summaryExpenseLabel;

    @FXML
    private Label noMovementsLabel; // New Label for no movements message

    @FXML
    private ListView<Movement> movementsListView; // ListView for dynamic movements
    
    @FXML
    private Label noNotificationsLabel;

    @FXML
    private ListView<Notification> notificationsListView;

    @FXML
    private BorderPane root;
    
    private User user;
    private Account currentAccount;
    private NotificationRepository notificationRepository;
    private VBox originalContent;

    public void initialize() {
        notificationRepository = new NotificationRepositoryImpl();
        // Store the original content
        originalContent = (VBox) root.getCenter();
    }

    @FXML
    private void onNotificationButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Notifications.fxml"));
            VBox notificationsView = loader.load();
            
            // Get the controller and set the user and dashboard controller
            NotificationsController controller = loader.getController();
            controller.setUser(user);
            controller.setDashboardController(this);
            
            // Replace the center content
            root.setCenter(notificationsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBackToDashboardClick() {
        updateUI();
    }

    @FXML
    private void onGoalsClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Goals.fxml"));
            VBox goalsView = loader.load();
            
            GoalsController goalsController = loader.getController();
            goalsController.setUser(user);
            goalsController.setDashboardController(this);
            
            root.setCenter(goalsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUser(User user) {
        this.user = user;
        System.out.println("DashboardController: setUser chamado com usuário: " + (user != null ? user.getEmail() : "null"));
        if (user != null && !user.getAccounts().isEmpty()) {
            // Pega a primeira conta do usuário
            this.currentAccount = user.getAccounts().get(0);
            System.out.println("DashboardController: Conta atual definida como: " + this.currentAccount.getCode());
            updateUI();
        } else if (user != null) {
            System.out.println("DashboardController: Usuário sem contas. Apenas o nome será exibido.");
            updateUI(); // Still call updateUI to display the user's name
        } else {
            System.out.println("DashboardController: Usuário é null. Nenhum dado de UI será atualizado.");
        }
    }
    
    public void updateUI() {
        System.out.println("DashboardController: updateUI chamado.");
        // Restore the original content
        root.setCenter(originalContent);
        
        if (user != null) {
            // Update user's name
            greetingLabel.setText("Olá, " + user.getName() + ", bem-vindo(a) de volta.");
            System.out.println("DashboardController: greetingLabel atualizado para: " + greetingLabel.getText());
            
            if (currentAccount != null) {
                // Format balance as currency
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                String formattedBalance = currencyFormat.format(currentAccount.getTotalBalance());
                
                // Update balance
                balanceValueLabel.setText(formattedBalance);
                balanceNumberLabel.setText("*****");
                System.out.println("DashboardController: Saldo atualizado para: " + formattedBalance);
                
                // Set income and expense to 0 for now
                // TODO: Implement actual income and expense calculation
                incomeLabel.setText("+0.00");
                expenseLabel.setText("-0.00");
                summaryIncomeLabel.setText("+0.00");
                summaryExpenseLabel.setText("-0.00");

                // Load and display movements
                List<Movement> movements = currentAccount.getMovements();
                if (movements != null && !movements.isEmpty()) {
                    movementsListView.getItems().setAll(movements);
                    movementsListView.setCellFactory(lv -> new MovementListCell()); // Set custom cell factory
                    noMovementsLabel.setVisible(false);
                    movementsListView.setVisible(true);
                    System.out.println("DashboardController: Movimentos carregados e exibidos: " + movements.size());
                } else {
                    noMovementsLabel.setVisible(true);
                    movementsListView.setVisible(false);
                    System.out.println("DashboardController: Nenhuma movimentação encontrada.");
                }
            } else {
                System.out.println("DashboardController: currentAccount é null. Saldo e informações financeiras não atualizadas.");
                balanceValueLabel.setText("R$ 0,00"); // Default value
                balanceNumberLabel.setText("*****");
                incomeLabel.setText("+0.00");
                expenseLabel.setText("-0.00");
                summaryIncomeLabel.setText("+0.00");
                summaryExpenseLabel.setText("-0.00");
                noMovementsLabel.setVisible(true);
                movementsListView.setVisible(false);
            }
        } else {
            System.out.println("DashboardController: Usuário é null. Nenhum dado de UI será atualizado.");
        }
    }
} 