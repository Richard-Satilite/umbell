package com.umbell.controller;

import com.umbell.models.User;
import com.umbell.models.Notification;
import com.umbell.repository.NotificationRepository;
import com.umbell.repository.NotificationRepositoryImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import java.util.List;

/**
 * Controlador responsável pela tela de notificações.
 * Gerencia a exibição e interação com a lista de notificações do usuário.
 *
 * @author Richard Satilite
 */
public class NotificationsController {
    @FXML
    private Label noNotificationsLabel;

    @FXML
    private ListView<Notification> notificationsListView;

    @FXML
    private BorderPane root;

    private User user;
    private NotificationRepository notificationRepository;
    private DashboardController dashboardController;

    /**
     * Inicializa o controlador e configura os componentes.
     */
    @FXML
    public void initialize() {
        notificationRepository = new NotificationRepositoryImpl();
    }

    /**
     * Define o usuário atual e carrega suas notificações.
     *
     * @param user O usuário que está visualizando as notificações
     */
    public void setUser(User user) {
        this.user = user;
        loadNotifications();
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    /**
     * Carrega a lista de notificações do usuário.
     */
    private void loadNotifications() {
        if (user != null) {
            List<Notification> notifications = notificationRepository.findByUserEmail(user.getEmail());
            
            if (notifications.isEmpty()) {
                noNotificationsLabel.setVisible(true);
                notificationsListView.setVisible(false);
            } else {
                noNotificationsLabel.setVisible(false);
                notificationsListView.setVisible(true);
                notificationsListView.getItems().setAll(notifications);
                notificationsListView.setCellFactory(lv -> new NotificationListCell());
            }
        }
    }

    /**
     * Manipula o evento de clique no botão de voltar ao dashboard.
     * Atualiza a interface do dashboard.
     */
    @FXML
    private void onBackToDashboardClick() {
        if (dashboardController != null) {
            dashboardController.updateUI();
        }
    }
} 