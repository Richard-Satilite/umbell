package com.umbell.controller;

import com.umbell.models.Goal;
import com.umbell.models.Notification;
import com.umbell.models.User;
import com.umbell.repository.NotificationRepository;
import com.umbell.repository.NotificationRepositoryImpl;
import com.umbell.repository.GoalRepository;
import com.umbell.repository.GoalRepositoryImpl;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Célula personalizada para exibir metas em uma ListView.
 * Gerencia a apresentação visual de uma meta individual na lista de metas.
 * 
 * @author Richard Satilite
 */
public class GoalListCell extends ListCell<Goal> {
    private final VBox content;
    private final Label titleLabel;
    private final Label targetLabel;
    private final Label currentLabel;
    private final ProgressBar progressBar;
    private final NotificationRepository notificationRepository;
    private final GoalRepository goalRepository;
    private User user;
    private DashboardController dashboardController;

    /**
     * Construtor da célula.
     * Inicializa os componentes visuais e configura o estilo da célula.
     */
    public GoalListCell() {
        content = new VBox();
        content.setSpacing(5);
        content.setPadding(new Insets(10));
        content.getStyleClass().add("goal-cell");

        titleLabel = new Label();
        titleLabel.getStyleClass().add("goal-title");

        targetLabel = new Label();
        targetLabel.getStyleClass().add("goal-target");

        currentLabel = new Label();
        currentLabel.getStyleClass().add("goal-current");

        progressBar = new ProgressBar();
        progressBar.getStyleClass().add("goal-progress");
        progressBar.setMaxWidth(Double.MAX_VALUE);

        content.getChildren().addAll(titleLabel, targetLabel, currentLabel, progressBar);
        
        notificationRepository = new NotificationRepositoryImpl();
        goalRepository = new GoalRepositoryImpl();
    }

    /**
     * Define o usuário atual.
     * 
     * @param user O usuário que está visualizando as metas
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Define o controlador do dashboard para atualização após ações.
     * 
     * @param dashboardController O controlador do dashboard
     */
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    /**
     * Atualiza o conteúdo da célula com os dados da meta.
     * Verifica se a meta foi atingida e cria notificações quando necessário.
     * 
     * @param goal A meta a ser exibida
     * @param empty Indica se a célula está vazia
     */
    @Override
    protected void updateItem(Goal goal, boolean empty) {
        super.updateItem(goal, empty);

        if (empty || goal == null) {
            setGraphic(null);
        } else {
            titleLabel.setText(goal.getTitle());
            targetLabel.setText(String.format("Meta: R$ %.2f", goal.getTargetAmount()));
            
            // Usa o saldo total da conta como valor atual
            BigDecimal currentBalance = goal.getAccount().getTotalBalance();
            currentLabel.setText(String.format("Atual: R$ %.2f", currentBalance.doubleValue()));
            
            // Calcula o progresso baseado no saldo total da conta
            double progress = currentBalance.doubleValue() / goal.getTargetAmount();
            progressBar.setProgress(Math.min(progress, 1.0));

            // Atualiza o status de atingida baseado no saldo total
            boolean isAchieved = currentBalance.doubleValue() >= goal.getTargetAmount();
            if (isAchieved) {
                content.getStyleClass().add("goal-achieved");
                
                // Se a meta foi atingida e ainda não foi notificada, cria uma notificação
                if (!goal.isAchieved() && user != null) {
                    String message = String.format("Parabéns! Você atingiu a meta '%s' de R$ %.2f!", 
                        goal.getTitle(), goal.getTargetAmount());
                    
                    // Cria a notificação usando o email do usuário
                    Notification notification = new Notification(
                        "Meta Atingida",
                        message,
                        user.getEmail()
                    );
                    notificationRepository.save(notification);
                    
                    // Atualiza o status da meta no banco
                    goal.setAchieved(true);
                    goalRepository.update(goal);

                    // Atualiza a UI do dashboard para mostrar a notificação
                    if (dashboardController != null) {
                        dashboardController.updateUI();
                    }
                }
            } else {
                content.getStyleClass().remove("goal-achieved");
            }

            setGraphic(content);
        }
    }
} 