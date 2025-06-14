package com.umbell.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import com.umbell.models.User;
import com.umbell.models.Account;
import com.umbell.service.UserService;
import com.umbell.repository.UserRepositoryImpl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controlador responsável pela seleção e gerenciamento de contas do usuário.
 * 
 * @author Richard Satilite
 */
public class AccountSelectController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private Label welcomeLabel;

    @FXML
    private ListView<Account> accountsListView;

    @FXML
    private Button createAccountButton;

    private User user;
    private UserService userService;

    /**
     * Inicializa o controlador e configura a célula personalizada para a lista de contas.
     * 
     * @param location Localização usada para resolver caminhos relativos
     * @param resources Recursos usados para localizar o objeto raiz
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserService();
        setupAccountsListView();
    }

    /**
     * Define o usuário atual e atualiza a interface.
     * 
     * @param user O usuário a ser definido
     */
    public void setUser(User user) {
        this.user = user;
        this.userService = new UserService();
        updateWelcomeLabel();
        loadAccounts();
    }

    private void updateWelcomeLabel() {
        if (user != null) {
            welcomeLabel.setText("Olá, " + user.getName() + "!");
        }
    }

    private void setupAccountsListView() {
        accountsListView.setCellFactory(lv -> new AccountListCell());
        accountsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Account selectedAccount = accountsListView.getSelectionModel().getSelectedItem();

                if (selectedAccount != null) {
                    System.out.println("Conta selecionada: " + selectedAccount.getName());
                    Stage currentStage = (Stage) root.getScene().getWindow();
                    
                    // Fecha a janela Base.fxml (janela principal)
                    Stage baseStage = (Stage) currentStage.getOwner();
                    if (baseStage != null) {
                        baseStage.close();
                        System.out.println("onCreateAccountClick: Base.fxml fechado.");
                    }
                    
                    currentStage.close();
                    
                    // Abre o Dashboard.fxml e passa o usuário e a conta selecionada
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/fxml/Dashboard.fxml"));
                        Parent dashboardRoot = loader.load();
                        
                        // Passa o usuário e a conta selecionada para o controller do dashboard
                        Object controller = loader.getController();
                        if (controller instanceof DashboardController) {
                            DashboardController dashboardController = (DashboardController) controller;
                            dashboardController.setUser(user);
                            dashboardController.setCurrentAccount(selectedAccount);
                            System.out.println("onCreateAccountClick: Usuário e conta passados para DashboardController.");
                        }
                        
                        Stage dashboardStage = new Stage();
                        dashboardStage.setTitle("Dashboard");
                        dashboardStage.setScene(new Scene(dashboardRoot));
                        dashboardStage.show();
                        System.out.println("onCreateAccountClick: Dashboard.fxml aberto.");
                    } catch (IOException e) {
                        System.err.println("Erro ao carregar Dashboard.fxml: " + e.getMessage());
                        e.printStackTrace();
                        throw new RuntimeException("Erro ao carregar Dashboard.fxml", e);
                    }
                }
            }
        });
    }

    /**
     * Carrega as contas do usuário atual e configura a interface apropriadamente.
     * Se o usuário não tiver contas, mostra o card de nova conta.
     */
    private void loadAccounts() {
        if (user != null) {
            List<Account> accounts = userService.getUserAccounts(user);
            accountsListView.getItems().setAll(accounts);
            
            // Se não houver contas, mostra o card de nova conta
            if (accounts.isEmpty()) {
                try {
                    createAccountButton.setVisible(false);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewAccountCard.fxml"));
                    Parent newAccountCard = loader.load();
                    
                    // Passa o usuário para o NewAccountCardController
                    NewAccountCardController newAccountCardController = loader.getController();
                    newAccountCardController.setUser(this.user);

                    accountsListView.setPlaceholder(newAccountCard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else{
                createAccountButton.setVisible(true);
            }
        }
    }

    /**
     * Manipula o evento de clique no botão de criar nova conta.
     * Abre uma nova janela para criar uma conta.
     */
    @FXML
    private void onCreateAccountClick(){
        try {
            // Carrega o FXML do AccountName
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AccountName.fxml"));
            Parent accountNameRoot = loader.load();

            // Obtém o controller do AccountName.fxml e passa o usuário
            AccountNameController accountNameController = loader.getController();
            accountNameController.setUser(this.user);
            System.out.println("onCardClick: Usuário passado para AccountNameController.");
            
            // Cria uma nova janela modal para AccountName
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(root.getScene().getWindow()); // Define o owner da janela modal
            stage.setTitle("Nova Conta");
            
            Scene scene = new Scene(accountNameRoot);
            stage.setScene(scene);
            
            // Centraliza a janela em relação à janela principal
            Stage mainStage = (Stage) root.getScene().getWindow();
            stage.setX(mainStage.getX() + (mainStage.getWidth() - scene.getWidth()) / 2);
            stage.setY(mainStage.getY() + (mainStage.getHeight() - scene.getHeight()) / 2);
            
            stage.showAndWait();
            System.out.println("onCardClick: Modal AccountName fechado.");

        } catch (IOException e) {
            System.err.println("onCardClick: Erro de IO ao carregar AccountName.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("onCardClick: Erro inesperado ao abrir modal de conta: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 