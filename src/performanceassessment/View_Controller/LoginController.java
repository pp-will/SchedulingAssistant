/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package performanceassessment.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import performanceassessment.Model.Connector;
import performanceassessment.Model.NewUserValidator;
import performanceassessment.Model.UserOperations;

/**
 *
 * @author wpittman
 */
public class LoginController implements Initializable {
    
    @FXML
    private Button registerBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField userInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label userInputLabel;
    
    @FXML
    void registerNewUserBtnHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = ( Stage ) registerBtn.getScene().getWindow();
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("resources.LangBundle_register", locale);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterUser.fxml"), bundle);
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        RegisterUserController controller = loader.getController();
    }

    @FXML
    void loginBtnHandler(ActionEvent event) throws SQLException {
        NewUserValidator validator = new NewUserValidator();
        Connector conn = new Connector();
        String userName = userInput.getText();
        String password = passwordInput.getText();
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("resources.LangBundle_login", locale);
        List<String> errors = new ArrayList<>();
        Preferences prefs;
        prefs = Preferences.userNodeForPackage(UserOperations.class);
        
        if(!validator.userNameLengthCheck(userName)) {
            errors.add(bundle.getString("userNameLengthError"));
        }
        if(!validator.passwordLengthCheck(password)) {
            errors.add(bundle.getString("passwordLengthError"));
        }
        
        if(errors.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("errorTitle"));
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
            ResultSet rs = UserOperations.loginUserQuery(userName, password);
            int rowCount = 0;
            while(rs.next()) {
                rowCount++;
            }
            String prefsError = prefs.get("errorMessage", "");
            if(prefsError.length() > 0) {
                errors.add(prefsError);
            }
     
            ////////////////////////////
            ////RETHINKKKKKKKKKKK
            /////////////
            if(rowCount < 1) {
                String errorMessage = prefs.get("errorMessage", "");
            }
            String rsPassword = rs.getString(3);
            
            if(!password.equals(rsPassword)) {
                
            }
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
