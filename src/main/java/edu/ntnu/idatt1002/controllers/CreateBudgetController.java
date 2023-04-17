package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Budget;
import edu.ntnu.idatt1002.Expense;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.Sale;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class CreateBudgetController {


  @FXML
  private TextField projectNameField;
  @FXML
  private TextField expenseNameField;
  @FXML
  private TextField expenseAmountField;
  @FXML
  private TextField incomeNameField;
  @FXML
  private TextField incomeAmountField;
  @FXML
  private TableView<Expense> expenseTable;
  @FXML
  private TableColumn<Expense, String> expenseNameColumn;
  @FXML
  private TableColumn<Expense, String> expenseAmountColumn;
  @FXML
  private TableView<Sale> incomeTable;
  @FXML
  private TableColumn<Sale, String> incomeNameColumn;
  @FXML
  private TableColumn<Sale, String> incomeAmountColumn;


  ArrayList<Expense> newListOfExpenses = new ArrayList<>();
  ArrayList<Sale> newListOfIncomes = new ArrayList<>();

  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.MAIN_MENU);
  }

  public void addExpense() throws IllegalArgumentException, NullPointerException {
    if (expenseNameField.getText().isEmpty()) {
      throw new IllegalArgumentException("Expense name cannot be empty");
    }
    if (expenseAmountField.getText().isEmpty()) {
      throw new IllegalArgumentException("Expense amount cannot be empty");
    }
    String expenseName = expenseNameField.getText();
    String expenseAmount = expenseAmountField.getText();

    Expense newExpense = new Expense(Double.parseDouble(expenseAmount), expenseName);
    newListOfExpenses.add(newExpense);

    expenseNameField.clear();
    expenseAmountField.clear();


    updateTables();


  }

  @FXML
  private void onAddExpenseClicked() {
    try {
      addExpense();
    } catch (NumberFormatException e) {
      Alert alert = new Alert(AlertType.WARNING, e.getMessage() + " is not a valid number");
      alert.showAndWait();

    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save Expense because: " + e.getMessage());
      alert.showAndWait();
    }
  }

  public void addIncome() throws IllegalArgumentException, NullPointerException {
    if (incomeNameField.getText().isEmpty()) {
      throw new IllegalArgumentException("Expense name cannot be empty");
    }
    if (incomeAmountField.getText().isEmpty()) {
      throw new IllegalArgumentException("Expense amount cannot be empty");
    }
    String incomeName = incomeNameField.getText();
    String incomeAmount = incomeAmountField.getText();

    Sale newIncome = new Sale(incomeName, Double.parseDouble(incomeAmount));
    newListOfIncomes.add(newIncome);




    updateTables();


  }

  @FXML
  private void onAddIncomeClicked() {
    try {
      addIncome();
    } catch (NumberFormatException e) {
      Alert alert = new Alert(AlertType.WARNING, e.getMessage() + " is not a valid number");
      alert.showAndWait();

    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save Sale because: " + e.getMessage());
      alert.showAndWait();
    }
  }

  public void updateTables() {
    expenseNameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

    expenseAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

    incomeNameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

    incomeAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

    expenseTable.getItems().clear();
    incomeTable.getItems().clear();

    for (Expense expense : newListOfExpenses) {
      expenseTable.getItems().add(expense);
    }

    for (Sale sale : newListOfIncomes) {
      incomeTable.getItems().add(sale);
    }
  }

  public void createBudget() throws IllegalArgumentException, NullPointerException {
    if (projectNameField.getText().isEmpty()) {
      throw new IllegalArgumentException("Project name cannot be empty");
    }

    String projectName = projectNameField.getText();

    Budget newBudget = new Budget(projectName);
    newBudget.addListOfExpenses(newListOfExpenses);
    newBudget.addListOfSales(newListOfIncomes);

    RegisterManager.getInstance().getBudgetRegister().addObject(newBudget);

    clearAllFields();
  }

  public void onCreateBudgetClicked(MouseEvent mouseEvent) {
    try {
      createBudget();

    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save Budget because: " + e.getMessage());
      alert.showAndWait();
    }
  }


  public void clearAllFields() {
    projectNameField.clear();
    expenseTable.getItems().clear();
    incomeTable.getItems().clear();
    incomeNameField.clear();
    incomeAmountField.clear();
    expenseNameField.clear();
    expenseAmountField.clear();
  }
}
