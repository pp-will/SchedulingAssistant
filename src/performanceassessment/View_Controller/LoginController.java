/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package performanceassessment.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
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
    void loginBtnHandler(ActionEvent event) {
        NewUserValidator validator = new NewUserValidator();
        Connector conn = new Connector();
        String userName = userInput.getText();
        String password = passwordInput.getText();
        
        List<String> errors = new ArrayList<>();
        
        
        if(!validator.userNameLengthCheck(userName)) {
            errors.add("Username must be between 6 and 32 characters");
        }
        if(!validator.passwordLengthCheck(password)) {
            errors.add("Password must be between 6 and 32 characters");
        }
        
        if(errors.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
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
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
