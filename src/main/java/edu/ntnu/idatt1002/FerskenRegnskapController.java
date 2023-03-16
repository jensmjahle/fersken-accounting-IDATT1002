package edu.ntnu.idatt1002;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class FerskenRegnskapController implements Initializable {

  @FXML
  private BarChart barChart;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    XYChart.Series series1 = new XYChart.Series();

    series1.setName("Inntekt");
    series1.getData().add(new XYChart.Data("Januar", 0));
    series1.getData().add(new XYChart.Data("Februar", 0));
    series1.getData().add(new XYChart.Data("Mars", 0));
    series1.getData().add(new XYChart.Data("April", 0));
    series1.getData().add(new XYChart.Data("Mai", 0));
    series1.getData().add(new XYChart.Data("Juni", 0));
    series1.getData().add(new XYChart.Data("Juli", 0));
    series1.getData().add(new XYChart.Data("August", 0));

    barChart.getData().add(series1);


  }
}
