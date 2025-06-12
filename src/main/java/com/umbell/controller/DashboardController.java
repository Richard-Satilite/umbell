package com.umbell.controller;

import com.umbell.models.User;
import com.umbell.models.Account;
import com.umbell.models.Movement;
import com.umbell.models.Notification;
import com.umbell.repository.NotificationRepository;
import com.umbell.repository.NotificationRepositoryImpl;
import com.umbell.service.AccountService;
import com.umbell.service.MovementService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.math.BigDecimal;
import java.sql.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DashboardController {
    @FXML private Label greetingLabel;
    @FXML private Label dateLabel;
    @FXML private Label balanceValueLabel;
    @FXML private Label incomeLabel;
    @FXML private Label expenseLabel;
    @FXML private Label summaryIncomeLabel;
    @FXML private Label summaryExpenseLabel;
    @FXML private Label noMovementsLabel;
    @FXML private ListView<Movement> movementsListView;
    @FXML private Label noNotificationsLabel;
    @FXML private ListView<Notification> notificationsListView;
    @FXML private BorderPane root;
    @FXML private Label saldoLabel;
    @FXML private Button notificationButton;
    
    private User user;
    private Account currentAccount;
    private NotificationRepository notificationRepository;
    private VBox originalContent;
    private MovementService movementService;
    private AccountService accountService;

    @FXML
    public void initialize() {
        notificationRepository = new NotificationRepositoryImpl();
        originalContent = (VBox) root.getCenter();
        movementService = new MovementService();
        accountService = new AccountService();
        refreshData();
        checkUnreadNotifications();
    }

    private void checkUnreadNotifications() {
        if (user != null) {
            List<Notification> unreadNotifications = notificationRepository.findUnreadByUserEmail(user.getEmail());
            if (!unreadNotifications.isEmpty()) {
                notificationButton.getStyleClass().add("notification-unread");
            } else {
                notificationButton.getStyleClass().remove("notification-unread");
            }
        }
    }

    @FXML
    private void onNotificationButtonClick() {
        try {
            // Marca todas as notificações como lidas
            markAllNotificationsAsRead();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Notifications.fxml"));
            VBox notificationsView = loader.load();
            
            // Get the controller and set the user and dashboard controller
            NotificationsController controller = loader.getController();
            controller.setUser(user);
            controller.setDashboardController(this);
            
            // Remove o estilo de notificação não lida
            notificationButton.getStyleClass().remove("notification-unread");
            
            // Replace the center content
            root.setCenter(notificationsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void markAllNotificationsAsRead() {
        if (user != null) {
            List<Notification> unreadNotifications = notificationRepository.findUnreadByUserEmail(user.getEmail());
            for (Notification notification : unreadNotifications) {
                notificationRepository.markAsRead(notification.getId());
            }
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

    @FXML
    private void handleNovaTransacao() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Transacao.fxml"));
            Scene scene = new Scene(loader.load());
            
            TransacaoController controller = loader.getController();
            controller.setDashboardController(this);
            
            Stage stage = new Stage();
            stage.setTitle("Nova Transação");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            refreshData();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erro", "Não foi possível abrir a janela de nova transação.");
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
        
        // Obtendo data atual
        LocalDate currDate = LocalDate.now();
        DateTimeFormatter currentDateFormatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", Locale.forLanguageTag("pt-BR"));
        dateLabel.setText(currDate.format(currentDateFormatter));

        //Obtendo total de ganhos e despesas
        BigDecimal incomes = accountService.getTotalIncomesByAccountId(currentAccount.getCode());
        BigDecimal expenses = accountService.getTotalExpensesByAccountId(currentAccount.getCode());

        // Garantir que os valores não sejam nulos
        if (incomes == null) incomes = BigDecimal.ZERO;
        if (expenses == null) expenses = BigDecimal.ZERO;

        // Restaurando conteudo original
        root.setCenter(originalContent);
        
        if (user != null) {
            // Update user's name
            greetingLabel.setText("Olá, " + user.getName() + ", bem-vindo(a) de volta!");
            System.out.println("DashboardController: greetingLabel atualizado para: " + greetingLabel.getText());
            
            if (currentAccount != null) {
                // Format balance as currency
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                String formattedBalance = currencyFormat.format(currentAccount.getTotalBalance());
                
                // Update balance
                balanceValueLabel.setText(formattedBalance);
                System.out.println("DashboardController: Saldo atualizado para: " + formattedBalance);
                
                // Set income and expense to 0 for now
                // TODO: Implement actual income and expense calculation
                incomeLabel.setText(String.format("+%.2f", incomes.doubleValue()));
                expenseLabel.setText(String.format("-%.2f", expenses.doubleValue()));
                summaryIncomeLabel.setText(String.format("+%.2f", incomes.doubleValue()));
                summaryExpenseLabel.setText(String.format("-%.2f", expenses.doubleValue()));

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
                incomeLabel.setText("+0.00");
                expenseLabel.setText("-0.00");
                summaryIncomeLabel.setText("+0.00");
                summaryExpenseLabel.setText("-0.00");
            }

            // Verifica notificações não lidas
            checkUnreadNotifications();
        }
    }

    public void refreshData() {
        if (currentAccount != null) {
            // Recarrega os movimentos da conta
            List<Movement> movements = movementService.findByAccountId(currentAccount.getCode());
            currentAccount.setMovements(movements);
            updateUI();
        }
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    private BigDecimal calculateSaldo(List<Movement> movements) {
        return movements.stream()
            .map(movement -> {
                BigDecimal amount = movement.getAmount();
                return movement.getType().equals("Expense") ? 
                    amount.negate() : amount;
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 