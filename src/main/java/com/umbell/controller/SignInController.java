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

public class SignInController implements Initializable {

    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private VBox emailContainer;
    
    @FXML
    private Label emailErrorLabel;

    @FXML
    private Label errorLabel;

    private UserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupEmailValidation();
        userService = new UserService(new UserRepositoryImpl());
    }

    private void setupEmailValidation() {
        emailField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateEmail();
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

    @FXML
    private void onLoginClick(ActionEvent event) {
        // Limpa mensagens de erro anteriores
        errorLabel.setText("");

        boolean isEmailValid = validateEmail();
        boolean isPasswordEmpty = passwordField.getText().isEmpty();

        if (!isEmailValid || isPasswordEmpty) {
            errorLabel.setText("Por favor, preencha todos os campos corretamente!");
            return;
        }

        try {
            // Tenta fazer login usando o UserService
            User user = userService.loadUser(emailField.getText(), passwordField.getText());
            
            // Se chegou aqui, o login foi bem sucedido
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Login realizado com sucesso!");
            alert.showAndWait();

            // Abre a tela base com a seleção de conta
            openBaseScreen(user);
            
        } catch (IllegalArgumentException e) {
            errorLabel.setText("Erro de validação: " + e.getMessage());
        } catch (RuntimeException e) {
            errorLabel.setText("Erro ao fazer login: " + e.getMessage());
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
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Erro ao abrir tela base: " + e.getMessage());
        }
    }
}