/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import esprit.recrutini.entities.Job;
import esprit.recrutini.entities.Recruiter;
import static esprit.recrutini.services.CandidateSession.currentCandidate;
import esprit.recrutini.services.ServiceJob;
import esprit.recrutini.services.ServiceRecruiter;
import esprit.recrutini.services.Session;
import esprit.recrutini.services.SessionJob;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import static java.sql.Types.NULL;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class JobsReController implements Initializable {

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

    int from = 0, to = 0;
    int itemPerPage = 6;
    @FXML
    private Button add;
    @FXML
    private Label ads;
   private String mail = "";
    Session S = Session.getInstance();
    @FXML
    private Button profile;
    @FXML
    private ImageView btnprofile;
    @FXML
    private Button btnForum;
    @FXML
    private Button btnFreelance;
    @FXML
    private Button btnJob;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
      mail = S.getEmail();
              ServiceRecruiter R = new ServiceRecruiter();
        Recruiter Re = null;
        try {
            Re = R.getRecruiter(mail);
            
        } catch (SQLException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        int count =0;
        ServiceJob sj = new ServiceJob();
         count = sj.count(Re.getId());
         int pageCount=(count/itemPerPage)+1;
         pagination.setPageCount(pageCount);

         pagination.setPageFactory(this::page);

        try {
           //displayAll();
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
              
    public List displayAll()  {
                  ServiceRecruiter R = new ServiceRecruiter();
        Recruiter Re = null;
        try {
            Re = R.getRecruiter(mail);
             ServiceJob sj = new ServiceJob();
            List listcs = sj.Display_OF_RECRUITER(Re.getId(),from,to);
    
            return listcs;
        } catch (SQLException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
   return null;

    }
           
           
           
           
           
           
           
           
           
private void addButtonToTable() throws SQLException {
        TableColumn actionCol = new TableColumn("Action");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("button"));

        Callback<TableColumn<Job, Void>, TableCell<Job, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<Job, Void>, TableCell<Job, Void>>() {
            @Override
            public TableCell<Job, Void> call(final TableColumn<Job, Void> param) {
                final TableCell<Job, Void> cell = new TableCell<Job, Void>() {

                    private final Button update = new Button("update");
                    private final Button delete = new Button("delete");
                
                    Label lb = new Label("  ");
               
                    private final HBox pane = new HBox( update, lb, delete);

                    {
              
                            Image del = new Image(getClass().getResourceAsStream("/esprit/recrutini/img/del.png"));
                   
        
                           Image upd = new Image(getClass().getResourceAsStream("/esprit/recrutini/img/upd.png"));
                        update.setGraphic(new ImageView(upd));
                        delete.setGraphic(new ImageView(del));
                     
                   
                        update.setMaxSize(10, 10);
                        delete.setMaxSize(10, 10);
               
                        final Tooltip tooltip = new Tooltip();
                        tooltip.setText("supprimer ");
                        delete.setTooltip(tooltip);
                      
                        final Tooltip tooltip3 = new Tooltip();
                        tooltip3.setText("modifier ");
                        update.setTooltip(tooltip3);
                      
                        update.setOnAction((ActionEvent event) -> {
                            Parent page2;
                            try {
                                Job jo = getTableView().getItems().get(getIndex());
                               SessionJob.getInstace(jo.getId());

                                page2 = FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/UpdateJob.fxml"));
                                Scene scene2 = new Scene(page2);
                                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                window.setScene(scene2);
                                window.show();

                            } catch (IOException ex) {
                                Logger.getLogger(JobsReController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        delete.setOnAction((ActionEvent event) -> {

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("suppression");
                            alert.setHeaderText("Are you sure you want to delete this ad");
                            Optional<ButtonType> option = alert.showAndWait();

                            if (option.get() == ButtonType.OK) {

                               ServiceJob sJ= new ServiceJob();
                                Job jo = getTableView().getItems().get(getIndex());

                                try {
                                    sJ.delete(jo.getId());
                                    displayAll();
                                } catch (SQLException ex) {
                                    Logger.getLogger(JobsReController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

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
          
    @FXML
    private void add(ActionEvent event) throws IOException {
        
          Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/AddJob.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    private void profil(MouseEvent event) throws IOException {
          Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Profil.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Profile(ActionEvent event) {
         if (currentCandidate.getId() != NULL) {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/ProfilCandidate.fxml")));
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else 
        {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Profil.fxml")));
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void Forum(ActionEvent event) {
         try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Forum.fxml")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Freelance(ActionEvent event) {
          try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/AnnonceFront.fxml")));
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Job(ActionEvent event) {
         try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Jobs.fxml")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
                        