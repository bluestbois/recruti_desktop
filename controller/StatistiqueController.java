/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import esprit.recrutini.services.ServiceAnnonce;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ines
 */
public class StatistiqueController implements Initializable {

    @FXML
    private Pane pane2;
    @FXML
    private Pane pane21;
    @FXML
    private Text nb;
    @FXML
    private Hyperlink retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  loadData();
    }    
    
    private void loadData()
    {  
        pane21.getChildren().clear();
  ObservableList<PieChart.Data> graph=FXCollections.observableArrayList();
    ObservableList<PieChart.Data> graph2=FXCollections.observableArrayList();
  BorderPane root ;
 PieChart pieChart;
  PieChart pieChart2;
     ServiceAnnonce SA=new ServiceAnnonce();
     int active = SA.count_active();
     int non_active = SA.count_non_active();
       System.out.println(non_active);
        System.out.println(active);
     int Total= active+non_active;
     graph2.addAll( new PieChart.Data("Active", active),
             new PieChart.Data("Non Active", non_active)
             
     );
     root=new BorderPane();
     pieChart2 =new PieChart();
     pieChart =new PieChart();
     pieChart.setData(graph);
     pieChart2.setData(graph2);
     nb.setText((Integer.toString(Total)));
     pieChart.setLegendSide(Side.BOTTOM);
     pieChart.setLabelsVisible(true);
     pieChart2.setLegendSide(Side.BOTTOM);
     pieChart2.setLabelsVisible(true);
     final Label caption = new Label("");
     caption.setTextFill(Color.WHITESMOKE);
     caption.setStyle("-fx-font: 24 arial;");
     final Label caption2 = new Label("");
     caption2.setTextFill(Color.WHITESMOKE);
     caption2.setStyle("-fx-font: 24 arial;");
     for (final PieChart.Data data : pieChart.getData()) {
         data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                 e -> {
                     double total = 0;
                     for (PieChart.Data d : pieChart.getData()) {
                         total += d.getPieValue();
                     }
                     
                     caption.setTranslateX(e.getSceneX());
                     caption.setTranslateY(e.getSceneY());
                     String text = String.format("%.1f%%", 100*data.getPieValue()/total) ;
                     caption.setText(text);
                     caption.setVisible(true);
                 }
         );
     }
     for (final PieChart.Data data : pieChart2.getData()) {
         data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                 e -> {
                     double total = 0;
                     for (PieChart.Data d : pieChart2.getData()) {
                         total += d.getPieValue();
                     }
                     
                     caption2.setTranslateX(e.getSceneX());
                     caption2.setTranslateY(e.getSceneY());
                     String text = String.format("%.1f%%", 100*data.getPieValue()/total) ;
                     caption2.setText(text);
                     caption2.setVisible(true);
                 }
         );
     }
 
     pane21.getChildren().addAll(pieChart2,caption2);
   
         
        retour.setOnAction((ActionEvent event) -> {
            Parent page2;
            try {
                page2 = FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Annonce.fxml"));
                Scene scene2 = new Scene(page2);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene2);
                window.show();

            } catch (IOException ex) {
                Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    
    }
    
}
