package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Budget;
import edu.ntnu.idatt1002.Expense;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.Sale;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import edu.ntnu.idatt1002.registers.ExpenseRegister;
import edu.ntnu.idatt1002.registers.SaleRegister;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateBudgetController implements Initializable {

  @FXML
  private TextField projectNameField;
  @FXML
  private ImageView infoIcon;
  @FXML
  private TableView expenseTable;
  @FXML
  private TableColumn expenseProductColumn;
  @FXML
  private TableColumn expenseAmountColumn;
  @FXML
  private TableView incomeTable;
  @FXML
  private TableColumn customerColumn;
  @FXML
  private TableColumn incomeProductColumn;
  @FXML
  private TableColumn incomeAmountColumn;

  private ExpenseRegister expenseRegister;
  private SaleRegister saleRegister;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    updateTables();
    enableMultiSelection();
  }

  private void enableMultiSelection() {
    expenseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    incomeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
  }


  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.MAIN_MENU);
  }

  public void switchToRegisterExpenseScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.REGISTER_EXPENSE);
  }

  public void switchToRegisterSaleScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.REGISTER_SALE);
  }

  private void updateTables() {
    expenseTable.getItems().removeAll(expenseTable.getItems());
    expenseRegister = RegisterManager.getInstance().getExpenseRegister();
    expenseProductColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

    expenseAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

    for (Expense expense : expenseRegister.getObjects()) {
      expenseTable.getItems().add(expense);
    }

    incomeTable.getItems().removeAll(incomeTable.getItems());
    saleRegister = RegisterManager.getInstance().getSaleRegister();
    customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));

    incomeProductColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

    incomeAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

    for (Sale sale : saleRegister.getObjects()) {
      incomeTable.getItems().add(sale);
    }
  }

  public void createBudgetClicked() {
    try {
      createBudget();
    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save Budget because: " + e.getMessage());
      alert.showAndWait();
    }
  }

  private void createBudget() throws IllegalArgumentException, NullPointerException {
    if (projectNameField.getText().isEmpty()) {
      throw new IllegalArgumentException("Project name cannot be empty");
    }
    String projectName = projectNameField.getText();
    Budget newBudget = new Budget(projectName);
    ObservableList<Expense> selectedExpenses = expenseTable.getSelectionModel().getSelectedItems();
    ObservableList<Sale> selectedSales = incomeTable.getSelectionModel().getSelectedItems();
    newBudget.addListOfExpenses(selectedExpenses);
    newBudget.addListOfSales(selectedSales);

    RegisterManager.getInstance().getBudgetRegister().addObject(newBudget);
  }

  public void clearAllFields() {
    projectNameField.clear();
    expenseTable.getItems().clear();
    incomeTable.getItems().clear();

  }
}
