package com.umbell.controller;

import com.umbell.models.Notification;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Célula personalizada para exibir notificações em uma ListView.
 * Gerencia a apresentação visual de uma notificação individual na lista de notificações.
 *
 * @author Richard Satilite
 */
public class NotificationListCell extends ListCell<Notification> {
    private VBox content;
    private Label messageLabel;
    private Label dateLabel;
    private DateTimeFormatter formatter;

    public NotificationListCell() {
        content = new VBox();
        content.setSpacing(5);
        content.setPadding(new Insets(10));
        
        messageLabel = new Label();
        messageLabel.getStyleClass().add("notification-message");
        
        dateLabel = new Label();
        dateLabel.getStyleClass().add("notification-date");
        
        content.getChildren().addAll(messageLabel, dateLabel);
        formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    }

    /**
     * Atualiza o conteúdo da célula com os dados da notificação.
     *
     * @param notification A notificação a ser exibida
     * @param empty Indica se a célula está vazia
     */
    @Override
    protected void updateItem(Notification notification, boolean empty) {
        super.updateItem(notification, empty);
        
        if (empty || notification == null) {
            setGraphic(null);
        } else {
            messageLabel.setText(notification.getMessage());
            dateLabel.setText(notification.getCreatedAt().format(formatter));
            
            if (!notification.isRead()) {
                content.getStyleClass().add("unread-notification");
            } else {
                content.getStyleClass().remove("unread-notification");
            }
            
            setGraphic(content);
        }
    }
} 