/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package performanceassessment.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import performanceassessment.Model.Connector;
import performanceassessment.Model.NewUserValidator;
import performanceassessment.Model.UserOperations;

/**
 * FXML Controller class
 *
 * @author wpittman
 */
public class RegisterUserController implements Initializable {
    
    @FXML
    private Button submitBtn;

    @FXML
    private PasswordField password2Input;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField userNameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    void cancelNewUser(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = ( Stage ) cancelBtn.getScene().getWindow();
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("resources.LangBundle_login", locale);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"), bundle);
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void submitNewUser(ActionEvent event) throws SQLException {
        NewUserValidator validator = new NewUserValidator();
        Connector conn = new Connector();
        String userName = userNameInput.getText();
        String password = passwordInput.getText();
        String password2 = password2Input.getText();
        
        List<String> errors = new ArrayList<>();
        
        
        if(!validator.userNameLengthCheck(userName)) {
            errors.add("Username must be between 6 and 32 characters");
        }
        if(!validator.passwordLengthCheck(password)) {
            errors.add("Password must be between 6 and 32 characters");
        }
        if(!validator.passwordMatchCheck(password, password2)) {
            errors.add("Passwords do not match");
        }
        
        if(errors.size() > 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            
            StringBuilder builder = new StringBuilder();
            
            errors.forEach((err) -> {
                builder.append(err + "\n");
            });
            
            String errorString = builder.toString();
            System.out.println(errorString);
            alert.setContentText(errorString);
            errors.clear();
            alert.showAndWait();
        } else {
            Connection db = conn.db();
            
            int active = 0;
            String createdBy = userName;
            String lastUpdateBy = userName;
            LocalDateTime createDate = LocalDateTime.now();
            
            Preferences prefs;
            prefs = Preferences.userNodeForPackage(UserOperations.class);
            
            UserOperations addUser = new UserOperations();
            addUser.createNewUserQuery(userName, password, active, createDate, createdBy, lastUpdateBy);
            System.out.println("PREFS::");
            String user = prefs.get("userName", "");
            System.out.println(user);
            System.out.println("PREFS2::");
            String err = prefs.get("errorMessage", "");
            System.out.println(err);
            if(err.length() > 0) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Whoops!");
                alert.setHeaderText(null);
                alert.setContentText(err);
                prefs.remove("errorMessage");
                prefs.remove("userName");
                alert.showAndWait();
            } else {
                try {
                    Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText(null);
                alert.setContentText("Account for user " + userName + " was successfully created!\nPlease proceed to login screen");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    Stage stage;
                    Parent root;
                    stage = ( Stage ) submitBtn.getScene().getWindow();
                    Locale locale = Locale.getDefault();
                    ResourceBundle bundle = ResourceBundle.getBundle("resources.LangBundle_login", locale);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"), bundle);
                    root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    //clear prefs of userName, password
                    prefs.remove("errorMessage");
                    prefs.remove("userName");
                    stage.show();
                }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
 