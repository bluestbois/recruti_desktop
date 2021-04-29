/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import esprit.recrutini.entities.Projet;
import esprit.recrutini.services.ServiceProjet;
import esprit.recrutini.services.SessionProjet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
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
 * @author ines
 */
public class ProjetsController implements Initializable {

    @FXML
    private Pane pnlOverview;
    @FXML
    private TableView<Projet> view;
    @FXML
    private Button ajouter;
    @FXML
    private TextField rechercher;
    @FXML
    private Label nombre;
    @FXML
    private RadioButton tri1;
    @FXML
    private ToggleGroup tri;
    @FXML
    private RadioButton tri2;
    @FXML
    private TableColumn<Projet, String> col_titre;
    @FXML
    private TableColumn<Projet, String> col_Description;
    @FXML
    private Button profile;
    @FXML
    private ImageView btnprofile;
    @FXML
    private Button btnforum;
    @FXML
    private Button btnFreelance;
    @FXML
    private Button btnProjects;
    @FXML
    private Button btnJobs;
    @FXML
    private Button btnCandidature;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            displayAll();
            addButtonToTable();
            rechercher();
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(ProjetsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

  
    private void addButtonToTable() throws SQLException {
        TableColumn actionCol = new TableColumn("Action");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>>() {
            @Override
            public TableCell<Projet, Void> call(final TableColumn<Projet, Void> param) {
                final TableCell<Projet, Void> cell = new TableCell<Projet, Void>() {

                    private final Button update = new Button();
                    private final Button delete = new Button("");
                    private final Button details = new Button("");
                    Label lb = new Label("  ");
                    Label lb2 = new Label("  ");
                    private final HBox pane = new HBox(details, lb2, update, lb, delete);

                    {
                        Image btn_details = new Image(getClass().getResourceAsStream("/esprit/recrutini/img/details.png"));
                        Image btn_update = new Image(getClass().getResourceAsStream("/esprit/recrutini/img/updatee.png"));
                        Image btn_delete = new Image(getClass().getResourceAsStream("/esprit/recrutini/img/delete.png"));
                        update.setGraphic(new ImageView(btn_update));
                        delete.setGraphic(new ImageView(btn_delete));
                        details.setGraphic(new ImageView(btn_details));
                        update.setMaxSize(10, 10);
                        delete.setMaxSize(10, 10);
                        details.setMaxSize(10, 10);
                        final Tooltip tooltip = new Tooltip();
                        tooltip.setText("supprimer ");
                        delete.setTooltip(tooltip);
                        final Tooltip tooltip2 = new Tooltip();
                        tooltip2.setText("voir details ");
                        details.setTooltip(tooltip2);
                        final Tooltip tooltip3 = new Tooltip();
                        tooltip3.setText("modifier ");
                        update.setTooltip(tooltip3);
                        details.setOnAction((ActionEvent event) -> {
                            Parent page2;
                            try {
                               Projet projet = getTableView().getItems().get(getIndex());
                                SessionProjet.getInstace(projet.getId());

                                page2 = FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/ProjetDetails.fxml"));
                                Scene scene2 = new Scene(page2);
                                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                window.setScene(scene2);
                                window.show();

                            } catch (IOException ex) {
                                Logger.getLogger(ProjetsController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        update.setOnAction((ActionEvent event) -> {
                            Parent page2;
                            try {
                                Projet projet = getTableView().getItems().get(getIndex());
                                SessionProjet.getInstace(projet.getId());

                                page2 = FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/UpdateProjet.fxml"));
                                Scene scene2 = new Scene(page2);
                                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                window.setScene(scene2);
                                window.show();

                            } catch (IOException ex) {
                                Logger.getLogger(ProjetsController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        delete.setOnAction((ActionEvent event) -> {

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("suppression");
                            alert.setHeaderText("Voulez-vous vraiment supprimer cet evenement?");
                            Optional<ButtonType> option = alert.showAndWait();

                            if (option.get() == ButtonType.OK) {

                               ServiceProjet sP = new ServiceProjet();
                                Projet forma = getTableView().getItems().get(getIndex());

                                try {
                                    sP.delete(forma.getId());
                                    displayAll();
                                } catch (SQLException ex) {
                                    Logger.getLogger(ProjetsController.class.getName()).log(Level.SEVERE, null, ex);
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

        view.getColumns().add(actionCol);

    }

    @FXML
    void rechercher() throws SQLException {
     ServiceProjet sP = new ServiceProjet();
        ArrayList listcs = (ArrayList) sP.DisplayAll();
        ObservableList OCours = FXCollections.observableArrayList(listcs);
        FilteredList<Projet> filteredData = new FilteredList<>(OCours, p -> true);
        rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getTitle()).toLowerCase().contains(lowerCaseFilter) || String.valueOf(myObject.getDescription()).toLowerCase().contains(lowerCaseFilter) ) {
                    return true;

                }
                return false;
            });
        });
        SortedList<Projet> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(view.comparatorProperty());
        view.setItems(sortedData);
    }

    public void displayAll() throws SQLException {

     ServiceProjet sP = new ServiceProjet();
        List lists = sP.DisplayAll();
        int number = sP.count();
        ObservableList ListProjet = FXCollections.observableArrayList(lists);

        view.setItems(ListProjet);

        nombre.setText(Integer.toString(number));
       col_titre.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_Description.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

    @FXML
    public void tri_date() throws SQLException {

        ServiceProjet sP = new ServiceProjet();
        List listcs = sP.triParDate();

        ObservableList ListProjet = FXCollections.observableArrayList(listcs);

        view.setItems(ListProjet);

        col_titre.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
      
    }

    @FXML
    public void tri_Titre() throws SQLException {

        ServiceProjet sP = new ServiceProjet();
        List listcs = sP.triParTitre();

        ObservableList ListProjet = FXCollections.observableArrayList(listcs);

        view.setItems(ListProjet);

      col_titre.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/AjouterProjet.fxml")));
        stage.setScene(scene);
        stage.show();

    }

 
    
    private void Anonnces(ActionEvent event) throws IOException {
    Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Annonce.fxml")));
        stage.setScene(scene);
        stage.show();
    }
      private void Accueil(ActionEvent event) throws IOException {
    Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/AnnonceFront.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Profils(ActionEvent event) {
         try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/UserInterface.fxml")));
            stage.setScene(scene);  
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Forum(ActionEvent event) {
          try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/BackForum.fxml")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Projects(ActionEvent event) {
         try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Projets.fxml")));
            stage.setScene(scene);  
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Jobs(ActionEvent event) {
         try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/JobsBack.fxml")));
            stage.setScene(scene);  
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Candidature(ActionEvent event) {
         try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Candidature.fxml")));
            stage.setScene(scene);  
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
