/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import esprit.recrutini.services.ServiceAnnonce;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author ines
 */
public class Statistiques extends Application {
   private final ObservableList<PieChart.Data> graph=FXCollections.observableArrayList();
   private BorderPane root ;
   private PieChart pieChart;
    @Override
    public void start(Stage primaryStage) throws Exception {
    ServiceAnnonce SA=new ServiceAnnonce();

      graph.addAll(new PieChart.Data("Active", 20),
  new PieChart.Data("Non active", 5)

      
      );
      root=new BorderPane();
        Scene scene= new Scene(root,600,500);
      pieChart =new PieChart();
      pieChart.setData(graph);
      pieChart.setTitle("Test");
      pieChart.setLegendSide(Side.BOTTOM);
      pieChart.setLabelsVisible(true);
      root.setCenter(pieChart);
      primaryStage.setScene(scene);
      primaryStage.show();
    }
    
}
