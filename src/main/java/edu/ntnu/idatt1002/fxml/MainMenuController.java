package edu.ntnu.idatt1002.fxml;

import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.Statistics;
import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
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

    for (int i = 0; i < 12; i++) {
      income.getData()
          .add(new XYChart.Data<>(Month.of(i + 1).toString(), statistics.getSaleTotalForMonth(i)));
      expenses.getData().add(
          new XYChart.Data<>(Month.of(i + 1).toString(), -statistics.getExpenseTotalForMonth(i)));
      difference.getData()
          .add(new XYChart.Data<>(Month.of(i + 1).toString(), statistics.getDifferenceForMonth(i)));
    }

    lineChart.getData().addAll(Arrays.asList(expenses, difference, income));
    lineChart.setAnimated(false);
    lineChart.setCreateSymbols(false);
    lineChart.setPadding(new Insets(0, 0, 0, 0));
    lineChart.getXAxis().setAutoRanging(true);
    lineChart.getYAxis().setAutoRanging(true);

  }

  public void switchToCreateBudgetScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("CreateBudget"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToCreateCustomerScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("CreateCustomer"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToCreateSupplierScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("CreateSupplier"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToListOfAllBudgetsScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("ListOfAllBudgets"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToListOfAllCustomersScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("ListOfAllCustomers"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToListOfAllExpensesScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("ListOfAllExpenses"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToListOfAllSalesScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("ListOfAllSales"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToListOfAllSuppliersScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("ListOfAllSuppliers"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToRegisterExpenseScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("RegisterExpense"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToRegisterSaleScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("RegisterSale"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
