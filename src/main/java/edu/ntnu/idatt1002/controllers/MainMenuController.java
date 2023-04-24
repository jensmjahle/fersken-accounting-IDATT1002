package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.registers.RegisterManager;
import edu.ntnu.idatt1002.utility.Statistics;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.net.URL;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class MainMenuController implements Initializable {


  @FXML
  private LineChart<String, Double> lineChart;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    createGraph();
  }


  private void createGraph() {
    addAxis();
    Result result = getResult();
    setChartStyling();
    setLineStyling(result.income(), result.expenses(), result.difference());
  }


  private Result getResult() {
    final Series<String, Double> income = new Series<>();
    final Series<String, Double> expenses = new Series<>();
    final Series<String, Double> difference = new Series<>();

    Statistics statistics = new Statistics(RegisterManager.getInstance());

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
    return new Result(income, expenses, difference);
  }

  private record Result(Series<String, Double> income, Series<String, Double> expenses,
                        Series<String, Double> difference) {

  }

  private void addAxis() {
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Month");
    yAxis.setLabel("Kr");
  }

  private void setLineStyling(Series<String, Double> income, Series<String, Double> expenses,
                              Series<String, Double> difference) {
    income.setName("Income");
    expenses.setName("Expenses");
    difference.setName("Difference");
    income.getNode().setStyle("-fx-stroke: #384c6b;");
    difference.getNode().setStyle("-fx-stroke: #e28a2b; -fx-stroke-dash-array: 2 6 12 2 ");
    expenses.getNode().setStyle("-fx-stroke: #859bba");
  }

  private void setChartStyling() {
    lineChart.setTitle("Yearly result");
    lineChart.setAnimated(false);
    lineChart.setCreateSymbols(false);
    lineChart.setPadding(new Insets(0, 0, 0, 0));
    lineChart.getXAxis().setAutoRanging(true);
    lineChart.getYAxis().setAutoRanging(true);
    lineChart.setLegendVisible(false);
  }


  public void switchToCreateBudgetScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.CREATE_BUDGET);
  }

  public void switchToCreateCustomerScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.CREATE_CUSTOMER);
  }

  public void switchToCreateSupplierScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.CREATE_SUPPLIER);
  }

  public void switchToListOfAllBudgetsScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_BUDGETS);
  }

  public void switchToListOfAllCustomersScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_CUSTOMERS);
  }

  public void switchToListOfAllExpensesScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_EXPENSES);
  }

  public void switchToListOfAllSalesScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_SALES);
  }

  public void switchToListOfAllSuppliersScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_SUPPLIERS);
  }

  public void switchToRegisterExpenseScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.REGISTER_EXPENSE);
  }

  public void switchToRegisterSaleScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.REGISTER_SALE);
  }

  public void switchToShowStatsScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.STATS);
  }

  public void switchToLoginScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.LOGIN);
  }

  public void handleLogOutClicked(MouseEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to log out?");
    ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
    alert.showAndWait();
    if (alert.getResult() == ButtonType.OK) {
      RegisterManager.getInstance().logOut();
      switchToLoginScene(event);
    } else {
      alert.close();
    }
  }


  public void highlightText(Text text) {
    text.setFont(Font.font("System", FontWeight.BOLD, 16));
    text.setUnderline(true);
  }

  @FXML
  public void onMouseEntered(MouseEvent event) {
    highlightText((Text) event.getSource());
  }

  @FXML
  public void onMouseExited(MouseEvent event) {
    returnTextToNormal((Text) event.getSource());
  }

  public void returnTextToNormal(Text text) {
    text.setFont(Font.font("System", 16));
    text.setUnderline(false);
  }

}
