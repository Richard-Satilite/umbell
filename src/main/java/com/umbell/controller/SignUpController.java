package com.umbell.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.umbell.utils.ValidUtils;
import com.umbell.service.UserService;
import com.umbell.repository.UserRepositoryImpl;
import com.umbell.models.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class SignUpController implements Initializable {

    @FXML
    private TextField nameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private VBox nameContainer;
    
    @FXML
    private VBox emailContainer;
    
    @FXML
    private VBox passwordContainer;
    
    @FXML
    private Label errorLabel;
    
    private Label nameErrorLabel;
    private Label emailErrorLabel;
    private Label passwordErrorLabel;

    private UserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupNameValidation();
        setupEmailValidation();
        setupPasswordValidation();
        userService = new UserService();
    }

    private void setupNameValidation() {
        nameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateName();
            }
        });
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

    private boolean validateName() {
        String name = nameField.getText();
        boolean isValid = name.trim().split("\\s+").length >= 2;
        
        if (!isValid && !name.isEmpty()) {
            nameField.setStyle("-fx-border-color: red;");
            if (nameErrorLabel == null) {
                nameErrorLabel = new Label("nome inválido!");
                nameErrorLabel.setStyle("-fx-text-fill: red;");
                nameContainer.getChildren().add(nameErrorLabel);
            }
            return false;
        } else {
            nameField.setStyle("");
            if (nameErrorLabel != null) {
                nameContainer.getChildren().remove(nameErrorLabel);
                nameErrorLabel = null;
            }
            return !name.isEmpty();
        }
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
                passwordErrorLabel = new Label("a senha deve conter pelo menos 8 caracteres!");
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
    private void onSignUpClick(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Limpa mensagens de erro anteriores
        errorLabel.setText("");

        // Validações básicas
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Por favor, preencha todos os campos");
            return;
        }

        // Validações específicas
        if (!validateName()) {
            errorLabel.setText("Nome inválido. Deve conter nome e sobrenome.");
            return;
        }

        if (!validateEmail()) {
            errorLabel.setText("Email inválido.");
            return;
        }

        if (!validatePassword()) {
            errorLabel.setText("Senha inválida. Deve ter pelo menos 8 caracteres.");
            return;
        }

        try {
            // Tenta registrar o usuário usando o UserService
            User newUser = userService.registerUser(name, email, password);
            
            // Se chegou aqui, o registro foi bem sucedido
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Usuário registrado com sucesso! Você já pode fazer login.");
            alert.showAndWait();

            // Abre a tela base com a seleção de conta
            openBaseScreen(newUser);
            
        } catch (IllegalArgumentException e) {
            errorLabel.setText("Erro de validação: " + e.getMessage());
        } catch (RuntimeException e) {
            errorLabel.setText("Erro ao registrar: " + e.getMessage());
            e.printStackTrace(); // Para debug
        }
    }

    private void openBaseScreen(User user) {
        try {
            URL fxmlUrl = getClass().getResource("/fxml/Base.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Não foi possível encontrar o arquivo Base.fxml");
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            // Obtém o controller da tela base e passa o usuário
            BaseController baseController = loader.getController();
            baseController.setUser(user);
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Erro ao abrir tela base: " + e.getMessage());
        }
    }
} 