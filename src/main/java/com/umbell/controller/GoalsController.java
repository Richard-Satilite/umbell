package com.umbell.controller;

import com.umbell.models.User;
import com.umbell.models.Goal;
import com.umbell.service.GoalService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import com.umbell.controller.GoalFormController;

import java.io.IOException;
import java.util.List;

/**
 * Controlador responsável pela tela de metas.
 * Gerencia a exibição e interação com a lista de metas do usuário.
 * 
 * @author Richard Satilite
 */
public class GoalsController {
    @FXML
    private Label noGoalsLabel;
    
    @FXML
    private ListView<Goal> goalsListView;
    
    @FXML
    private VBox root;
    
    private User user;
    private GoalService goalService;
    private DashboardController dashboardController;
    
    /**
     * Inicializa o controlador e configura os componentes.
     */
    @FXML
    public void initialize() {
        goalService = new GoalService();
        goalsListView.setCellFactory(lv -> {
            GoalListCell cell = new GoalListCell();
            cell.setUser(user);
            cell.setDashboardController(dashboardController);
            return cell;
        });
    }
    
    /**
     * Define o usuário atual e carrega suas metas.
     * 
     * @param user O usuário que está visualizando as metas
     */
    public void setUser(User user) {
        this.user = user;
        loadGoals(null);
    }
    
    /**
     * Define o controlador do dashboard para atualização após ações.
     * 
     * @param dashboardController O controlador do dashboard
     */
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
    
    /**
     * Carrega a lista de metas do usuário.
     * 
     * @param v Parâmetro não utilizado, mantido para compatibilidade com o callback
     */
    private void loadGoals(Void v) {
        List<Goal> goals = goalService.findByUserId(user.getId());
        
        if (goals.isEmpty()) {
            noGoalsLabel.setVisible(true);
            goalsListView.setVisible(false);
        } else {
            noGoalsLabel.setVisible(false);
            goalsListView.setVisible(true);
            goalsListView.getItems().clear();
            goalsListView.getItems().addAll(goals);
            goalsListView.refresh();
        }
    }
    
    /**
     * Manipula o evento de clique no botão de adicionar meta.
     * Abre o formulário de criação de meta.
     */
    @FXML
    private void onAddGoalClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GoalForm.fxml"));
            VBox goalForm = loader.load();
            
            GoalFormController controller = loader.getController();
            controller.setUser(user);
            controller.setAccount(dashboardController.getCurrentAccount());
            controller.setOnGoalCreated(this::loadGoals);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(root.getScene().getWindow());
            stage.setTitle("Nova Meta");
            stage.setScene(new javafx.scene.Scene(goalForm));
            stage.showAndWait();
            
            loadGoals(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Manipula o evento de clique no botão de voltar ao dashboard.
     * Atualiza a interface do dashboard.
     */
    @FXML
    private void onBackToDashboardClick() {
        dashboardController.updateUI();
    }
} 