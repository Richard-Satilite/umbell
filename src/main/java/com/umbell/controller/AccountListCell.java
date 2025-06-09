package com.umbell.controller;

import com.umbell.models.Account;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class AccountListCell extends ListCell<Account> {
    private HBox content;
    private Label codeLabel;
    private Label balanceLabel;

    public AccountListCell() {
        super();
        content = new HBox();
        content.setSpacing(10);
        content.setPadding(new Insets(5));
        content.getStyleClass().add("account-card");

        codeLabel = new Label();
        balanceLabel = new Label();

        content.getChildren().addAll(codeLabel, balanceLabel);
    }

    @Override
    protected void updateItem(Account account, boolean empty) {
        super.updateItem(account, empty);
        
        if (empty || account == null) {
            setText(null);
            setGraphic(null);
        } else {
            codeLabel.setText("Conta: " + account.getCode());
            
            // Formata o saldo como moeda
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            String formattedBalance = currencyFormat.format(account.getTotalBalance());
            balanceLabel.setText("Saldo: " + formattedBalance);
            
            setGraphic(content);
        }
    }
} 