package com.umbell.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.umbell.utils.ValidUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

public class SignInController implements Initializable {

    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private VBox emailContainer;
    
    @FXML
    private VBox passwordContainer;
    
    @FXML
    private Label emailErrorLabel;
    
    private Label passwordErrorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupEmailValidation();
        setupPasswordValidation();
    }

    private void setupEmailValidation() {
        emailField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateEmail();
            }
        });
    }

    private void setupPasswordValidation() {
        passwordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validatePassword();
            }
        });
    }

    private boolean validateEmail() {
        String email = emailField.getText();
        boolean isValid = ValidUtils.isValidEmail(email);
        
        if (!isValid && !email.isEmpty()) {
            emailField.setStyle("-fx-border-color: red;");
            if (emailErrorLabel == null) {
                emailErrorLabel = new Label("email inválido");
                emailErrorLabel.setStyle("-fx-text-fill: red;");
                emailContainer.getChildren().add(emailErrorLabel);
            }
            return false;
        } else {
            emailField.setStyle("");
            if (emailErrorLabel != null) {
                emailContainer.getChildren().remove(emailErrorLabel);
                emailErrorLabel = null;
            }
            return !email.isEmpty();
        }
    }

    private boolean validatePassword() {
        String password = passwordField.getText();
        
        if (password.length() < 8 && !password.isEmpty()) {
            passwordField.setStyle("-fx-border-color: red;");
            if (passwordErrorLabel == null) {
                passwordErrorLabel = new Label("senha inválida!");
                passwordErrorLabel.setStyle("-fx-text-fill: red;");
                passwordContainer.getChildren().add(passwordErrorLabel);
            }
            return false;
        } else {
            passwordField.setStyle("");
            if (passwordErrorLabel != null) {
                passwordContainer.getChildren().remove(passwordErrorLabel);
                passwordErrorLabel = null;
            }
            return !password.isEmpty();
        }
    }

    @FXML
    private void onLoginClick(ActionEvent event) {
        boolean isEmailValid = validateEmail();
        boolean isPasswordValid = validatePassword();

        if (!isEmailValid || !isPasswordValid) {
            ValidUtils.createCenteredAlert("Erro de validação","preencha todos os campos corretamente!").showAndWait();
            return;
        }

        System.out.println("Login realizado com sucesso!");
    }
} 