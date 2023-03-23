package edu.ntnu.idatt1002.fxml;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

  private Stage stage;
  private Scene scene;
  private Parent root;
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
    series1.getData().add(new XYChart.Data("September", 0));
    series1.getData().add(new XYChart.Data("Oktober", 0));
    series1.getData().add(new XYChart.Data("November", 0));
    series1.getData().add(new XYChart.Data("Desember", 0));

    barChart.getData().add(series1);
  }

  public void switchToCreateBudgetScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("CreateBudget2.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToCreateCustomerScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("CreateCustomer.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToCreateSupplierScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("CreateSupplier.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToListOfAllBudgetsScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("ListOfAllBudgets.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToListOfAllCustomersScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("ListOfAllCustomers.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToListOfAllExpensesScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("ListOfAllExpenses.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToListOfAllSalesScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("ListOfAllSales.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToListOfAllSuppliersScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("ListOfAllSuppliers.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToRegisterExpenseScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("RegisterExpense.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToRegisterSaleScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("RegisterSale.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
