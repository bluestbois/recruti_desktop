/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Entities.Project;
import Services.GestionProjet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.NativeJava.type;

/**
 *
 * @author NOUSSA
 */
public class ProjectController {
    
    
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtDescription;
    @FXML
    private Button btnajout;
   
    @FXML
    private TableView<Project> tv;
    @FXML
    private TableColumn<Project, Integer> colid;
    @FXML
    private TableColumn<Project, String> coltitle;
    @FXML
    private TableColumn<Project, String> coldescription;
    @FXML
    private Button btnSupp;

    /**
     * Initializes the controller class.
     */
 
    public void initialize(URL url, ResourceBundle rb) {
        GestionProjet ps = new GestionProjet();
        ArrayList<Project> publiciteList =  (ArrayList<Project>) ps.getProjects() ;
        ObservableList<Project> data = FXCollections.observableArrayList(publiciteList); 
        tv.setItems(data);
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
       
        /* tv.setEditable(true);
        colid.setCellFactory(TextFieldTableCell.forTableColumn());
        colcategorie.setCellFactory(TextFieldTableCell.forTableColumn());
        colcouleur.setCellFactory(TextFieldTableCell.forTableColumn()); */
    }    

    @FXML
    private void addProject(ActionEvent event) {
        if(txtTitle.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "verifer les champs");  
        }
        else
        {
        String title = txtTitle.getText();
        String description = txtDescription.getText();
        Project c = new Project(5,title,description);
        GestionProjet sc = new GestionProjet();
        sc.ajouterProjet(c);
                    JOptionPane.showMessageDialog(null, "ajout avec succes");
        txtTitle.clear();
       
         ArrayList<Project> publiciteList =  (ArrayList<Project>) sc.getProjects() ; 
    ObservableList<Project> data = FXCollections.observableArrayList(publiciteList);
    
    tv.setItems(data);
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
         }
    }

    @FXML
    private void deleteProject(ActionEvent event) {
        
          Project selectedItem = tv.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Etes vous sure de supprimer cet element ?");   
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        GestionProjet ps = new GestionProjet() ; 
        tv.getItems().remove(selectedItem);
        ps.supprimerProjet(selectedItem);
        }
        
        }
        else {
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un element à supprimer.");

        alert.showAndWait();
        }
        
    }

    

}
