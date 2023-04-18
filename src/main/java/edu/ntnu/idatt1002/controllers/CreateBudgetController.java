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

import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateBudgetController implements Initializable {

  @FXML
  private TextField projectNameField;
  @FXML
  private ImageView infoIcon;
  @FXML
  private TableView<Expense> expenseTable;
  @FXML
  private TableColumn<Expense, String> expenseProductColumn;
  @FXML
  private TableColumn<Expense, String> expenseAmountColumn;
  @FXML
  private TableView<Sale> incomeTable;
  @FXML
  private TableColumn<Sale, String> customerColumn;
  @FXML
  private TableColumn<Sale, String> incomeProductColumn;
  @FXML
  private TableColumn<Sale, String> incomeAmountColumn;

  private ObservableList<Expense> selectedExpenses;
  private ObservableList<Sale> selectedSales;

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
    ExpenseRegister expenseRegister = RegisterManager.getInstance().getExpenseRegister();
    expenseProductColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

    expenseAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

    for (Expense expense : expenseRegister.getObjects()) {
      expenseTable.getItems().add(expense);
    }

    incomeTable.getItems().removeAll(incomeTable.getItems());
    SaleRegister saleRegister = RegisterManager.getInstance().getSaleRegister();
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
      Alert alert = new Alert(AlertType.WARNING, "Cannot create budget because: " + e.getMessage());
      alert.showAndWait();
    }
  }

  private void createBudget() throws IllegalArgumentException, NullPointerException {
    if (projectNameField.getText().isEmpty()) {
      throw new IllegalArgumentException("Project name cannot be empty");
    }
    if (checkDifferenceIsZero()) {
      String projectName = projectNameField.getText();
      Budget newBudget = new Budget(projectName);
      selectedExpenses = expenseTable.getSelectionModel().getSelectedItems();
      selectedSales = incomeTable.getSelectionModel().getSelectedItems();
      newBudget.addListOfExpenses(selectedExpenses);
      newBudget.addListOfSales(selectedSales);
      RegisterManager.getInstance().getBudgetRegister().addObject(newBudget);
      confirmBudgetIsCreated();
      projectNameField.clear();
    }

  }

  private boolean checkDifferenceIsZero() {
    selectedExpenses = expenseTable.getSelectionModel().getSelectedItems();
    selectedSales = incomeTable.getSelectionModel().getSelectedItems();

    double sumOfExpenses = selectedExpenses.stream().mapToDouble(Expense::getAmount).sum();
    double sumOfSales = selectedSales.stream().mapToDouble(Sale::getAmount).sum();
    double difference = sumOfExpenses - sumOfSales;
    if (difference != 0) {
      Alert alert = new Alert(AlertType.CONFIRMATION, "Budget has a difference of: " + difference + "NOK" +
              "\nAre you sure you want to create this budget?");
      alert.showAndWait();

      if (alert.getResult() == ButtonType.OK) {
        return true;

      }else return alert.getResult() != ButtonType.CANCEL;

    }
    return true;
  }

  private void confirmBudgetIsCreated() {
    Alert budgetHasBeenCreated = new Alert(AlertType.CONFIRMATION, "Budget has been created");
    budgetHasBeenCreated.show();
    PauseTransition delay = new PauseTransition(Duration.seconds(2));
    delay.setOnFinished(event -> budgetHasBeenCreated.close());
    delay.play();
  }

  public void clearAllFields() {
    projectNameField.clear();
    expenseTable.getItems().clear();
    incomeTable.getItems().clear();

  }
}
