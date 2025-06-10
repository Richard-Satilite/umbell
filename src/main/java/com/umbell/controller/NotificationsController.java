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

    public void initialize() {
        notificationRepository = new NotificationRepositoryImpl();
    }

    public void setUser(User user) {
        this.user = user;
        loadNotifications();
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    private void loadNotifications() {
        if (user != null) {
            List<Notification> notifications = notificationRepository.findByUserId(user.getId());
            
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

    @FXML
    private void onBackToDashboardClick() {
        if (dashboardController != null) {
            dashboardController.updateUI();
        }
    }
} 