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
    
    @FXML
    public void initialize() {
        goalService = new GoalService();
        goalsListView.setCellFactory(lv -> new GoalListCell());
    }
    
    public void setUser(User user) {
        this.user = user;
        loadGoals(null);
    }
    
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
    
    private void loadGoals(Void v) {
        List<Goal> goals = goalService.findByUserId(user.getId());
        
        if (goals.isEmpty()) {
            noGoalsLabel.setVisible(true);
            goalsListView.setVisible(false);
        } else {
            noGoalsLabel.setVisible(false);
            goalsListView.setVisible(true);
            goalsListView.getItems().setAll(goals);
        }
    }
    
    @FXML
    private void onAddGoalClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GoalForm.fxml"));
            VBox goalForm = loader.load();
            
            GoalFormController controller = loader.getController();
            controller.setUser(user);
            controller.setOnGoalCreated(this::loadGoals);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(root.getScene().getWindow());
            stage.setTitle("Nova Meta");
            stage.setScene(new javafx.scene.Scene(goalForm));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void onBackToDashboardClick() {
        dashboardController.updateUI();
    }
} 