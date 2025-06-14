package com.umbell;

import com.umbell.utils.DBInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Classe principal da aplicação JavaFX.
 * Inicializa o banco de dados e carrega a cena principal da interface gráfica.
 * 
 * Controla a troca de telas a partir dos arquivos FXML.
 * 
 * @author Richard Satilite
 */
public class App extends Application {

    private static Scene scene;

    /**
     * Método inicial chamado ao iniciar a aplicação.
     * Inicializa o banco de dados e configura a cena principal da janela.
     * 
     * @param stage O palco principal da aplicação JavaFX.
     * @throws IOException Caso ocorra erro ao carregar o arquivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Inicializa o banco de dados
        DBInitializer.initialize();

        scene = new Scene(loadFXML("/fxml/Main"), 980, 680);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    /**
     * Altera o root da cena para o FXML especificado.
     * 
     * @param fxml O nome do arquivo FXML (sem extensão) a ser carregado como root.
     * @throws IOException Caso ocorra erro ao carregar o arquivo FXML.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Carrega um arquivo FXML e retorna seu conteúdo como um objeto Parent.
     * 
     * @param fxml O nome do arquivo FXML (sem extensão) a ser carregado.
     * @return O nó raiz do layout carregado.
     * @throws IOException Caso ocorra erro ao carregar o arquivo FXML.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Método principal que inicia a aplicação JavaFX.
     * 
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        launch();
    }

}