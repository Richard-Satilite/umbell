package com.umbell.controller;

import com.umbell.models.Goal;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class GoalListCell extends ListCell<Goal> {
    private final VBox content;
    private final Label titleLabel;
    private final Label targetLabel;
    private final Label currentLabel;
    private final ProgressBar progressBar;

    public GoalListCell() {
        content = new VBox();
        content.setSpacing(5);
        content.setPadding(new Insets(10));
        content.getStyleClass().add("goal-cell");

        titleLabel = new Label();
        titleLabel.getStyleClass().add("goal-title");

        targetLabel = new Label();
        targetLabel.getStyleClass().add("goal-target");

        currentLabel = new Label();
        currentLabel.getStyleClass().add("goal-current");

        progressBar = new ProgressBar();
        progressBar.getStyleClass().add("goal-progress");
        progressBar.setMaxWidth(Double.MAX_VALUE);

        content.getChildren().addAll(titleLabel, targetLabel, currentLabel, progressBar);
    }

    @Override
    protected void updateItem(Goal goal, boolean empty) {
        super.updateItem(goal, empty);

        if (empty || goal == null) {
            setGraphic(null);
        } else {
            titleLabel.setText(goal.getTitle());
            targetLabel.setText(String.format("Meta: R$ %.2f", goal.getTargetAmount()));
            currentLabel.setText(String.format("Atual: R$ %.2f", goal.getCurrentAmount()));
            
            double progress = goal.getCurrentAmount() / goal.getTargetAmount();
            progressBar.setProgress(Math.min(progress, 1.0));

            if (goal.isAchieved()) {
                content.getStyleClass().add("goal-achieved");
            } else {
                content.getStyleClass().remove("goal-achieved");
            }

            setGraphic(content);
        }
    }
} 