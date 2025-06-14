package com.umbell.controller;

import com.umbell.models.Movement;
import com.umbell.models.Notification;
import com.umbell.repository.GoalRepository;
import com.umbell.repository.GoalRepositoryImpl;
import com.umbell.repository.NotificationRepository;
import com.umbell.repository.NotificationRepositoryImpl;
import com.umbell.models.Account;
import com.umbell.models.Goal;
import com.umbell.service.AccountService;
import com.umbell.service.MovementService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador responsável pela tela de transações.
 * Gerencia a exibição e interação com a lista de transações do usuário.
 *
 * @author Richard Satilite
 */
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

    /**
     * Inicializa o controlador e configura os componentes.
     */
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

    /**
     * Define o usuário atual e carrega suas transações.
     *
     * @param user O usuário que está visualizando as transações
     */
    public void setDashboardController(DashboardController controller) {
        this.dashboardController = controller;
        if (controller != null) {
            this.currentAccount = controller.getCurrentAccount();
        }
    }

    @FXML
    private void handleSalvar() {
        try {
            String tipo = tipoTransacaoCombo.getValue();
            if (tipo == null || descricaoField.getText().isEmpty() || valorField.getText().isEmpty() || dataField.getValue() == null) {
                showAlert("Erro", "Por favor, preencha todos os campos obrigatórios.");
                return;
            }

            if (currentAccount == null) {
                showAlert("Erro", "Nenhuma conta selecionada.");
                return;
            }

            Movement movement = new Movement();
            movement.setAccount(currentAccount);
            
            Integer addOrReduce = null;
            switch (tipo) {
                case "Entrada":
                    movement.setType("Income");
                    addOrReduce = 1;
                    break;
                case "Gasto":                
                    movement.setType("Expense");
                    addOrReduce = -1;
                    break;
                case "Investimento":
                    movement.setType("Investment");
                    addOrReduce = 1;
                    break;
                default:
                    showAlert("Erro", "Tipo de transação inválido.");
                    return;
            }

            movement.setDescription(descricaoField.getText());
            movement.setAmount(new BigDecimal(valorField.getText()));
            movement.setDate(dataField.getValue());
            movement.setNotes(observacaoField.getText());

            movementService.save(movement);

            if(addOrReduce != null){
                if (addOrReduce == 1) {
                    currentAccount.setTotalBalance(currentAccount.getTotalBalance().add(movement.getAmount()));
                } else {
                    currentAccount.setTotalBalance(currentAccount.getTotalBalance().subtract(movement.getAmount()));
                }
            }

            accountService.updateAccount(currentAccount);

            // Se uma meta foi atinginda, então deve gerar uma nova notificação
            if(verifyIfGoalIsAchieved()){
                try {
                    String message = "Com a movimentação de R$ %.2f, você atingiu uma ou mais metas!".formatted(movement.getAmount());
                    Notification notification = new Notification("Meta atingida", message , currentAccount.getUserEmail());
                    NotificationRepository notificationRepository = new NotificationRepositoryImpl();

                    notificationRepository.save(notification);
                } catch (Exception e) {
                    System.out.println("Erro ao conectar com o notificationRepository: " + e);
                }
            }

            // Atualiza a UI do dashboard
            dashboardController.updateUI();

            // Fecha a janela
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

    private boolean validateInputs() {
        if (tipoTransacaoCombo.getValue() == null || descricaoField.getText().isEmpty() || valorField.getText().isEmpty() || dataField.getValue() == null) {
            showAlert("Erro", "Por favor, preencha todos os campos obrigatórios.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean verifyIfGoalIsAchieved(){
        try {
            GoalRepository goalRepository = new GoalRepositoryImpl();
            List<Goal> goals = goalRepository.findByAccountId(currentAccount.getCode());

            if(!goals.isEmpty()){

                //Verifica se com a transação adicionada a conta atingiu uma meta ainda não atingida
                for (Goal goal : goals)
                    if(currentAccount.getTotalBalance().doubleValue() >= goal.getTargetAmount() && !goal.isAchieved())
                        return true; 
            }
        } catch (Exception e) {
            System.out.println("Problema ao se conectar ao goalRepository: " + e);
        }

        return false;
    }
}