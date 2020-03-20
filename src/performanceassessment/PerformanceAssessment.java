/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package performanceassessment;

import java.util.Locale;
import java.util.ResourceBundle;
import performanceassessment.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author wpittman
 */
public class PerformanceAssessment extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("resources.LangBundle_login", locale);
        Parent root = FXMLLoader.load(getClass().getResource("View_Controller/Login.fxml"), bundle);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
