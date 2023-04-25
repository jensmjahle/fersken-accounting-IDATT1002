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

/**
 * Controller for the main menu fxml. Has methods for switching to other scenes in the application.
 * Includes a chart of the statistics of the current year.
 */
public class MainMenuController implements Initializable {

  @FXML
  private LineChart<String, Double> lineChart;

  /**
   * Initializes the controller with a chart.
   *
   * @param url            The location used to resolve relative paths for the root object, or null
   *                       if the location is not known.
   * @param resourceBundle The resources used to localize the root object, or null if the root
   *                       object was not localized.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    createGraph();
  }

  /**
   * Creates a line chart of statistics of the current year.
   */
  private void createGraph() {
    addAxis();
    Result result = getResult();
    setChartStyling();
    setLineStyling(result.income(), result.expenses(), result.difference());
  }

  /**
   * Creates a Result object containing income, expense and difference for the current year.
   *
   * @return A Result object containing income, expense and difference for the current year.
   */
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

  /**
   * Record containing 3 series of data.
   *
   * @param income     Income data series.
   * @param expenses   Income data series.
   * @param difference Income data series.
   */
  private record Result(Series<String, Double> income, Series<String, Double> expenses,
                        Series<String, Double> difference) {

  }

  /**
   * Adds an x and y-axis to the chart, with the "month" and "kr" labels.
   */
  private void addAxis() {
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Month");
    yAxis.setLabel("Kr");
  }

  /**
   * Sets line styling to colorblind friendly colors.
   *
   * @param income     Series of income data.
   * @param expenses   Series of expense data.
   * @param difference Series of difference data.
   */
  private void setLineStyling(Series<String, Double> income, Series<String, Double> expenses,
                              Series<String, Double> difference) {
    income.setName("Income");
    expenses.setName("Expenses");
    difference.setName("Difference");
    income.getNode().setStyle("-fx-stroke: #384c6b;");
    difference.getNode().setStyle("-fx-stroke: #e28a2b; -fx-stroke-dash-array: 2 6 12 2 ");
    expenses.getNode().setStyle("-fx-stroke: #859bba");
  }

  /**
   * Sets chart settings to increase performance and to make it fit in the frame.
   */
  private void setChartStyling() {
    lineChart.setTitle("Yearly result");
    lineChart.setAnimated(false);
    lineChart.setCreateSymbols(false);
    lineChart.setPadding(new Insets(0, 0, 0, 0));
    lineChart.getXAxis().setAutoRanging(true);
    lineChart.getYAxis().setAutoRanging(true);
    lineChart.setLegendVisible(false);
  }

  /**
   * Loads and switches to the "create budget" scene.
   *
   * @param event The event that triggers the switch to the new scene.
   */
  @FXML
  private void switchToCreateBudgetScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.CREATE_BUDGET);
  }

  /**
   * Loads and switches to the "create customer" scene.
   *
   * @param event The event that triggers the switch to the new scene.
   */
  @FXML
  private void switchToCreateCustomerScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.CREATE_CUSTOMER);
  }

  /**
   * Loads and switches to the "create supplier" scene.
   *
   * @param event The event that triggers the switch to the new scene.
   */
  @FXML
  private void switchToCreateSupplierScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.CREATE_SUPPLIER);
  }

  /**
   * Loads and switches to the "list of all budgets" scene.
   *
   * @param event The event that triggers the switch to the new scene.
   */
  @FXML
  private void switchToListOfAllBudgetsScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_BUDGETS);
  }

  /**
   * Loads and switches to the "list of all customers" scene.
   *
   * @param event The event that triggers the switch to the new scene.
   */
  @FXML
  private void switchToListOfAllCustomersScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_CUSTOMERS);
  }

  /**
   * Loads and switches to the "list of all expenses" scene.
   *
   * @param event The event that triggers the switch to the new scene.
   */
  @FXML
  private void switchToListOfAllExpensesScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_EXPENSES);
  }

  /**
   * Loads and switches to the "list of all sales" scene.
   *
   * @param event The event that triggers the switch to the new scene.
   */
  @FXML
  private void switchToListOfAllSalesScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_SALES);
  }

  /**
   * Loads and switches to the "list of all suppliers" scene.
   *
   * @param event The event that triggers the switch to the new scene.
   */
  @FXML
  private void switchToListOfAllSuppliersScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_SUPPLIERS);
  }

  /**
   * Loads and switches to the "Create expense" scene.
   *
   * @param event The event that triggers the switch to the new scene.
   */
  @FXML
  private void switchToRegisterExpenseScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.CREATE_EXPENSE);
  }

  /**
   * Loads and switches to the "create sale scene" scene.
   *
   * @param event The event that triggers the switch to the new scene.
   */
  @FXML
  private void switchToRegisterSaleScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.CREATE_SALE);
  }

  /**
   * Loads and switches to the "Show statistics" scene.
   *
   * @param event The event that triggers the switch to the new scene.
   */
  @FXML
  private void switchToShowStatsScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.STATS);
  }

  /**
   * Loads and switches to the "login" scene.
   *
   * @param event The event that triggers the switch to the new scene.
   */
  @FXML
  private void switchToLoginScene(MouseEvent event) {
    ViewManager.switchToScene(event, View.LOGIN);
  }

  /**
   * Logs out the user from the application and switches to the login screen.
   *
   * @param event Event that triggers the logout.
   */
  @FXML
  private void handleLogOutClicked(MouseEvent event) {
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

  /**
   * Highlights the input text by setting the font to bold, adding an underline and increasing font
   * to 16.
   *
   * @param text The text to be highlighted.
   */
  @FXML
  private void highlightText(Text text) {
    text.setFont(Font.font("System", FontWeight.BOLD, 16));
    text.setUnderline(true);
  }

  /**
   * Highlights the selected element if it is a text object.
   *
   * @param event The event that will be used to find the text.
   */
  @FXML
  private void onMouseEntered(MouseEvent event) {
    highlightText((Text) event.getSource());
  }

  /**
   * Returns the selected text to normal, if the exited element is text.
   *
   * @param event Event that triggers the method.
   */
  @FXML
  private void onMouseExited(MouseEvent event) {
    returnTextToNormal((Text) event.getSource());
  }

  /**
   * Returns the input text to normal.
   *
   * @param text The text to be put to normal.
   */
  private void returnTextToNormal(Text text) {
    text.setFont(Font.font("System", 16));
    text.setUnderline(false);
  }

}
