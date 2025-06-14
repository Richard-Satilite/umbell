package com.umbell.utils;

import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Classe utilitária para validações comuns, como validação de email
 * e criação de alertas de erro centralizados.
 * 
 * Contém padrão regex para validação de email e métodos auxiliares
 * para criar alertas de interface.
 * 
 * @author Richard Satilite
 */
public class ValidUtils {
    
    /**
     * Expressão regular para validação básica de emails.
     */
    public static final String EMAIL_PATTERN = 
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    /**
     * Objeto Pattern compilado a partir do padrão de email para reuso.
     */
    public static final Pattern EMAIL_REGEX = Pattern.compile(EMAIL_PATTERN);
    
    /**
     * Verifica se a string fornecida corresponde a um email válido.
     * 
     * @param email A string que será validada como email.
     * @return {@code true} se o email for válido, {@code false} caso contrário.
     */
    public static boolean isValidEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

    /**
     * Cria um alerta de erro do JavaFX com título e mensagem,
     * posicionando-o centralizado na tela.
     * 
     * @param title O título do alerta.
     * @param message A mensagem a ser exibida no alerta.
     * @return O objeto Alert já configurado e pronto para exibição.
     */
    public static Alert createCenteredAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
    
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setOnShown(e -> stage.centerOnScreen());
    
        return alert;
    }
}
