package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.Statistics;
import java.io.IOException;
import java.net.URL;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.ResourceBundle;

import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class MainMenuController implements Initializable {

  private Stage stage;
  private Scene scene;

  @FXML
  private LineChart<String, Double> lineChart;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    createGraph();

  }


  private void createGraph() {
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Month");
    yAxis.setLabel("Kr");

    final XYChart.Series<String, Double> income = new XYChart.Series<>();
    final XYChart.Series<String, Double> expenses = new XYChart.Series<>();
    final XYChart.Series<String, Double> difference = new XYChart.Series<>();

    Statistics statistics = new Statistics(RegisterManager.getInstance());
    lineChart.setTitle("Yearly result");

    income.setName("Income");
    expenses.setName("Expenses");
    difference.setName("Difference");

    YearMonth firstMonth = YearMonth.of(YearMonth.now().getYear(), 1);
    YearMonth endMonth = YearMonth.of(YearMonth.now().getYear(), 12);

    YearMonth currentMonth = firstMonth;

    while (!currentMonth.isAfter(endMonth)) {
      income.getData().add(new XYChart.Data<>(currentMonth.toString(),
          statistics.getSaleTotalForMonth(currentMonth)));

      expenses.getData().add(new XYChart.Data<>(currentMonth.toString(),
          -statistics.getExpenseTotalForMonth(currentMonth)));
      difference.getData()

          .add(new XYChart.Data<>(currentMonth.toString(),
              statistics.getDifferenceTotalForMonth(currentMonth)));
      currentMonth = currentMonth.plusMonths(1);
    }

    lineChart.getData().addAll(Arrays.asList(expenses, difference, income));
    lineChart.setAnimated(false);
    lineChart.setCreateSymbols(false);
    lineChart.setPadding(new Insets(0, 0, 0, 0));
    lineChart.getXAxis().setAutoRanging(true);
    lineChart.getYAxis().setAutoRanging(true);


  }

  public void switchToCreateBudgetScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.CREATEBUDGET);
  }

  public void switchToCreateCustomerScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.CREATECUSTOMER);
  }

  public void switchToCreateSupplierScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.CREATESUPPLIER);
  }

  public void switchToListOfAllBudgetsScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.LISTOFALLBUDGETS);
  }

  public void switchToListOfAllCustomersScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.LISTOFALLCUSTOMERS);
  }

  public void switchToListOfAllExpensesScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.LISTOFALLEXPENSES);
  }

  public void switchToListOfAllSalesScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.LISTOFALLSALES);
  }

  public void switchToListOfAllSuppliersScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.LISTOFALLSUPPLIERS);
  }

  public void switchToRegisterExpenseScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.REGISTEREXPENSE);
  }

  public void switchToRegisterSaleScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.REGISTERSALE);
  }

  public void switchToShowStatsScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.STATS);
  }
}