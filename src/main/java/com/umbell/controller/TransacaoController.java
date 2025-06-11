package com.umbell.controller;

import com.umbell.models.Movement;
import com.umbell.models.Account;
import com.umbell.service.AccountService;
import com.umbell.service.MovementService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoController {
    @FXML private ComboBox<String> tipoTransacaoCombo;
    @FXML private TextField descricaoField;
    @FXML private TextField valorField;
    @FXML private DatePicker dataField;
    @FXML private TextArea observacaoField;

    private MovementService movementService;
    private DashboardController dashboardController;
    private Account currentAccount;
    private AccountService accountService;

    @FXML
    public void initialize() {
        movementService = new MovementService();
        accountService = new AccountService();
        dataField.setValue(LocalDate.now());
        
        // Configurar validação do campo de valor
        valorField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                valorField.setText(oldValue);
            }
        });
    }

    public void setDashboardController(DashboardController controller) {
        this.dashboardController = controller;
        if (controller != null) {
            this.currentAccount = controller.getCurrentAccount();
        }
    }

    @FXML
    private void handleSalvar() {
        try {
            Integer addOrReduce = null;
            String tipo = tipoTransacaoCombo.getValue();
            String descricao = descricaoField.getText();
            BigDecimal valor = new BigDecimal(valorField.getText());
            LocalDate data = dataField.getValue();
            String observacao = observacaoField.getText();

            if (tipo == null || descricao.isEmpty() || valor.compareTo(BigDecimal.ZERO) <= 0 || data == null) {
                showAlert("Erro", "Por favor, preencha todos os campos obrigatórios.");
                return;
            }

            if (currentAccount == null) {
                showAlert("Erro", "Nenhuma conta selecionada.");
                return;
            }

            Movement movement = new Movement();
            movement.setAccount(currentAccount);
            
            switch (tipo) {
                case "Entrada":
                    movement.setType("Income");
                    addOrReduce = 1;
                    break;
                case "Gasto":                
                    movement.setType("Expense");
                    addOrReduce = 0;
                    break;
                case "Investimento":
                    movement.setType("Investment");
                    addOrReduce = 1;
                    break;
                default:
                    showAlert("Erro", "Tipo de transação inválido.");
                    return;
            }
            movement.setDescription(descricao);
            movement.setAmount(valor);
            movement.setDate(data);
            movement.setNotes(observacao);

            movementService.save(movement);
            
            if(addOrReduce != null){
                if (addOrReduce == 1) {
                    currentAccount.setTotalBalance(currentAccount.getTotalBalance().add(valor));
                } else{
                    currentAccount.setTotalBalance(currentAccount.getTotalBalance().subtract(valor));
                }
            }

            accountService.updateAccount(currentAccount);

            // Atualizar o dashboard
            if (dashboardController != null) {
                dashboardController.refreshData();
            }
            // Fechar o modal
            Stage stage = (Stage) descricaoField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showAlert("Erro", "Por favor, insira um valor válido.");
        } catch (Exception e) {
            showAlert("Erro", "Ocorreu um erro ao salvar a transação: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelar() {
        Stage stage = (Stage) descricaoField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}