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
import java.io.IOException;
import javafx.fxml.FXMLLoader;

/**
 * Célula personalizada para exibição de contas na lista.
 * Gerencia a apresentação visual de cada item de conta.
 * 
 * @author Richard Satilite
 */
public class AccountListCell extends ListCell<Account> {
    private HBox content;
    private Label codeLabel;
    private Label balanceLabel;

    /**
     * Construtor padrão que carrega o layout da célula.
     */
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

    /**
     * Atualiza o conteúdo da célula com os dados da conta.
     * 
     * @param account A conta a ser exibida
     * @param empty Indica se a célula está vazia
     */
    @Override
    protected void updateItem(Account account, boolean empty) {
        super.updateItem(account, empty);
        
        if (empty || account == null) {
            setText(null);
            setGraphic(null);
        } else {
            codeLabel.setText("Conta: " + account.getName());
            
            // Formata o saldo como moeda
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            String formattedBalance = currencyFormat.format(account.getTotalBalance());
            balanceLabel.setText("Saldo: " + formattedBalance);
            
            setGraphic(content);
        }
    }
} 