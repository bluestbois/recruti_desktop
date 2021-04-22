/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recrutini.GUI;

import Entite.Job;
import Entite.Recruiter;
import Service.ServiceJob;
import Service.ServiceRecruiter;
import Service.SessionJob;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class JobsController implements Initializable {

  
    @FXML
    private TableView<Job> tableview;
    @FXML
    private TableColumn<Job, String> Title;
    @FXML
    private TableColumn<Job, String> Description;
    @FXML
    private TableColumn<Job, String> Date;
    @FXML
    private Pagination pagination;
    @FXML
    private Label ads;
int from = 0, to = 0;
    int itemPerPage = 6;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
  
   
       
        int count =0;
        ServiceJob sj = new ServiceJob();
         count = sj.count2();
         int pageCount=(count/itemPerPage)+1;
         pagination.setPageCount(pageCount);

         pagination.setPageFactory(this::page);

        try {
           // displayAll();
            addButtonToTable();
            
       
        } catch (SQLException ex) {
            Logger.getLogger(JobsReController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
      public Node page( int pageIndex)   {
        
    from = pageIndex * itemPerPage;
    to = itemPerPage;
    //   System.out.println("test" + from +" "+ pageIndex);
        ObservableList listReserv = FXCollections.observableArrayList(displayAll());
        tableview.setItems(listReserv);
  
return tableview;
    }
      private void addButtonToTable() throws SQLException {
        TableColumn actionCol = new TableColumn("Action");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("button"));

        Callback<TableColumn<Job, Void>, TableCell<Job, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<Job, Void>, TableCell<Job, Void>>() {
            @Override
            public TableCell<Job, Void> call(final TableColumn<Job, Void> param) {
                final TableCell<Job, Void> cell = new TableCell<Job, Void>() {

               
                    private final Button get = new Button("get");
             
            
                    private final HBox pane = new HBox( get);

                    {
              
                  
                         Image apply = new Image(getClass().getResourceAsStream("images/get.png"));
                   
                        get.setGraphic(new ImageView(apply));
                     
           
                        get.setMaxSize(10, 10);
               
                        final Tooltip tooltip = new Tooltip();
                        tooltip.setText("get job ");
                        get.setTooltip(tooltip);
                      
                  
                      
              
                        get.setOnAction((ActionEvent event) -> {

                  

                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        setGraphic(empty ? null : pane);
                    }
                };
                return cell;
            }
        };

        actionCol.setCellFactory(cellFactory);

        tableview.getColumns().add(actionCol);
}
       public List displayAll()  {
                  ServiceRecruiter R = new ServiceRecruiter();
        Recruiter Re = null;
        try {
      
             ServiceJob sj = new ServiceJob();
            List listcs = sj.DisplayAll(from,to);
    
            return listcs;
        } catch (SQLException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
   return null;

    }
}
