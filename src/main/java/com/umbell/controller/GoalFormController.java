package com.umbell.controller;

import com.umbell.models.Account;
import com.umbell.models.User;
import com.umbell.models.Goal;
import com.umbell.service.GoalService;
import com.umbell.service.AccountService;
import com.umbell.utils.ValidUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class GoalFormController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField targetAmountField;
    @FXML
    private Label errorLabel;

    private User user;
    private Account account;
    private GoalService goalService;
    private AccountService accountService;
    private Consumer<Void> onGoalCreated;

    @FXML
    public void initialize() {
        goalService = new GoalService();
        accountService = new AccountService();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setOnGoalCreated(Consumer<Void> onGoalCreated) {
        this.onGoalCreated = onGoalCreated;
    }

    @FXML
    private void onCreateGoalClick() {
        String title = titleField.getText();
        String targetAmountText = targetAmountField.getText();

        errorLabel.setText("");

        if (title.isEmpty()) {
            errorLabel.setText("O título da meta não pode estar vazio.");
            return;
        }

        double targetAmount;
        try {
            targetAmount = Double.parseDouble(targetAmountText);
            if (targetAmount <= 0) {
                errorLabel.setText("O valor alvo deve ser um número positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Por favor, insira um valor numérico válido para o alvo.");
            return;
        }

        if (account == null) {
            errorLabel.setText("Erro: conta não selecionada.");
            return;
        }

        Goal newGoal = goalService.createGoal(title, targetAmount, account);

        if (newGoal != null) {
            if (onGoalCreated != null) {
                onGoalCreated.accept(null);
            }
            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.close();
        } else {
            errorLabel.setText("Erro ao criar a meta. Tente novamente.");
        }
    }

    @FXML
    private void onCancelClick() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}