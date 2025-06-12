package com.umbell.controller;

import com.umbell.models.Movement;

import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MovementListCell extends ListCell<Movement> {
    private HBox content;
    private Label descriptionLabel;
    private Label categoryLabel;
    private Label valueLabel;
    private Label dateLabel;

    public MovementListCell() {
        super();
        content = new HBox(10);
        content.setPadding(new Insets(10, 5, 10, 5));
        content.getStyleClass().add("transaction-item");

        descriptionLabel = new Label();
        descriptionLabel.getStyleClass().add("transaction-title");

        categoryLabel = new Label();
        categoryLabel.getStyleClass().add("transaction-type");

        valueLabel = new Label();
        valueLabel.getStyleClass().add("transaction-value");

        dateLabel = new Label();
        dateLabel.getStyleClass().add("transaction-date");

        VBox leftVBox = new VBox(5); // For description and category
        leftVBox.getChildren().addAll(descriptionLabel, categoryLabel);

        HBox.setHgrow(leftVBox, javafx.scene.layout.Priority.ALWAYS);

        content.getChildren().addAll(leftVBox, valueLabel, dateLabel);
    }

    @Override
    protected void updateItem(Movement movement, boolean empty) {
        super.updateItem(movement, empty);
        
        if (empty || movement == null) {
            setText(null);
            setGraphic(null);
        } else {
            descriptionLabel.setText(movement.getDescription() != null ? movement.getDescription() : "N/A");
            if (movement.getType() != null) {
                switch (movement.getType()) {
                    case "Income":
                        categoryLabel.setText("Entrada");
                        break;
                    case "Expense":
                        categoryLabel.setText("Gasto");
                        break;
                    default:
                        categoryLabel.setText("Investimento");
                        break;
                }
            } else{
                categoryLabel.setText("N/A");
            }
            
            // Format value as currency and set color based on value
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            String formattedValue = currencyFormat.format(movement.getAmount());
            valueLabel.setText(formattedValue);
            
            if (movement.getType().equals("Expense")) {
                valueLabel.getStyleClass().add("negative");
                valueLabel.getStyleClass().remove("positive");
            } else {
                valueLabel.getStyleClass().add("positive");
                valueLabel.getStyleClass().remove("negative");
            }

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            dateLabel.setText(movement.getDate() != null ? movement.getDate().format(dateFormatter) : "N/A");
            
            setGraphic(content);
        }
    }
} 