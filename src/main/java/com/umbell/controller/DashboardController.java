package com.umbell.controller;

import com.umbell.models.User;
import com.umbell.models.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.text.NumberFormat;
import java.util.Locale;

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
    
    private User user;
    private Account currentAccount;

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
            updateUI(); // Ainda chama updateUI para exibir o nome do usuário
        }
    }
    
    private void updateUI() {
        System.out.println("DashboardController: updateUI chamado.");
        if (user != null) {
            greetingLabel.setText("Olá, " + user.getName() + ", bem-vindo(a) de volta.");
            System.out.println("DashboardController: greetingLabel atualizado para: " + greetingLabel.getText());
            
            if (currentAccount != null) {
                // Formata o saldo como moeda
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                String formattedBalance = currencyFormat.format(currentAccount.getTotalBalance());
                
                // Atualiza o saldo
                balanceValueLabel.setText(formattedBalance);
                balanceNumberLabel.setText("*****");
                System.out.println("DashboardController: Saldo atualizado para: " + formattedBalance);
                
                // Por enquanto, vamos deixar os valores de receita e despesa como 0
                // TODO: Implementar o cálculo real de receitas e despesas
                incomeLabel.setText("+0.00");
                expenseLabel.setText("-0.00");
                summaryIncomeLabel.setText("+0.00");
                summaryExpenseLabel.setText("-0.00");
            } else {
                System.out.println("DashboardController: currentAccount é null. Saldo e informações financeiras não atualizadas.");
                balanceValueLabel.setText("R$ 0,00"); // Default value
                balanceNumberLabel.setText("*****");
                incomeLabel.setText("+0.00");
                expenseLabel.setText("-0.00");
                summaryIncomeLabel.setText("+0.00");
                summaryExpenseLabel.setText("-0.00");
            }
        } else {
            System.out.println("DashboardController: Usuário é null. Nenhum dado de UI será atualizado.");
        }
    }
} 