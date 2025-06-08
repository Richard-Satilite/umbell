package com.umbell.utils;

import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ValidUtils {
    
    public static final String EMAIL_PATTERN = 
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    public static final Pattern EMAIL_REGEX = Pattern.compile(EMAIL_PATTERN);
    
    public static boolean isValidEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

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