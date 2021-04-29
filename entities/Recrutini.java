/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.entities;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author yessine darmoul
 */
public class Recrutini extends Application {
        public static Stage stage = null;

    @Override
    public void start(Stage stage) throws IOException {
    
        Parent root = FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Welcome.fxml"));

       
        Scene scene = new Scene(root);
        
        
        stage.setScene(scene);
 stage.initStyle(StageStyle.UNDECORATED);
        this.stage = stage;    
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}