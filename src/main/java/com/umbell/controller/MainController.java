package com.umbell.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button primaryButton;

    @FXML
    private void printHello() throws IOException {
        System.out.println("Hello");
    }
}
