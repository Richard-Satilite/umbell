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
/**
 * Controlador responsável pelo formulário de criação e edição de metas.
 * Gerencia a interface de entrada de dados para criar ou editar uma meta financeira.
 * 
 * @author Richard Satilite
 */
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

    /**
     * Inicializa o controlador e configura as validações dos campos.
     */
    @FXML
    public void initialize() {
        goalService = new GoalService();
        accountService = new AccountService();
    }

    /**
     * Define o usuário atual.
     * 
     * @param user O usuário que está criando/editando a meta
     */
    public void setUser(User user) {
        this.user = user;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Define o callback a ser executado após a criação da meta.
     * 
     * @param callback O callback a ser executado
     */
    public void setOnGoalCreated(Consumer<Void> onGoalCreated) {
        this.onGoalCreated = onGoalCreated;
    }

    /**
     * Manipula o evento de clique no botão de salvar.
     * Valida os campos e salva a meta.
     */
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

    /**
     * Fecha a janela atual.
     */
    @FXML
    private void onCancelClick() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}