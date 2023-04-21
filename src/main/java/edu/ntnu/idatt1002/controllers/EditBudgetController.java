package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Budget;
import edu.ntnu.idatt1002.Expense;
import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.Sale;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.io.IOException;
import java.util.List;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller for the Edit Budget fxml file.
 */

public class EditBudgetController {

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
  @FXML
  private Button deleteExpenseButton;
  @FXML
  private Button deleteIncomeButton;


  private List<Expense> listOfExpenses;
  private List<Sale> listOfIncomes;

  private Budget budgetToBeEdited;

  /**
   * Method that initializes the scene.
   */
  public void initialize() {
    disableDeleteExpenseButtonWhileInvalid();
    disableDeleteIncomeButtonWhileInvalid();
  }

  /**
   * Method that retrieves info about the budget to be edited.
   *
   * @param budgetToBeEdited the budget to be edited
   */
  public void setBudgetToRemove(Budget budgetToBeEdited) {
    this.budgetToBeEdited = budgetToBeEdited;
    listOfIncomes = budgetToBeEdited.getListOfSales();
    listOfExpenses = budgetToBeEdited.getListOfExpenses();
    fillFieldsWithCurrentInfo();
  }

  /**
   * Method for replacing the original budget with an updated version.
   *
   * @param event Event that brings you back to the list of budgets,
   *              with the updated budget.
   * @throws IOException if the resource path is invalid.
   */
  @FXML
  private void replaceBudget(MouseEvent event) throws IOException {
    try {
      createBudget();
      RegisterManager.getInstance().getBudgetRegister().removeObject(budgetToBeEdited);
      switchToListOfBudgets(event);
    } catch (NumberFormatException e) {
      Alert alert = new Alert(AlertType.WARNING, e.getMessage() + " is not a valid number");
      alert.showAndWait();

    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save Budget because: " + e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * Method that creates a new budget.
   *
   * @throws IllegalArgumentException if project name is empty.
   * @throws NullPointerException if the project name is null.
   */
  public void createBudget() throws IllegalArgumentException, NullPointerException {
    if (projectNameField.getText().isEmpty()) {
      throw new IllegalArgumentException("Project name cannot be empty");
    }

    String projectName = projectNameField.getText();
    Budget newBudget = new Budget(projectName);
    newBudget.addListOfExpenses(listOfExpenses);
    newBudget.addListOfSales(listOfIncomes);

    RegisterManager.getInstance().getBudgetRegister().addObject(newBudget);

    clearAllFields();
  }

  /**
   * Method that switches to list of all budgets.
   *
   * @param mouseEvent event that triggers switch back to list of all budgets.
   * @throws IOException if the resource file is invalid.
   */
  @FXML
  public void switchToListOfBudgets(MouseEvent mouseEvent) throws IOException {
    Parent root = FXMLLoader.load(
        PathUtility.getResourcePath(View.LIST_OF_ALL_BUDGETS.getFileName()));
    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Method that fills the fields with current info about the relevant budget.
   */
  public void fillFieldsWithCurrentInfo() {
    try {

      projectNameField.setText(budgetToBeEdited.getProjectName());

      expenseNameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

      expenseAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

      incomeNameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

      incomeAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

      expenseTable.getItems().clear();
      incomeTable.getItems().clear();

      listOfIncomes = budgetToBeEdited.getListOfSales();
      listOfExpenses = budgetToBeEdited.getListOfExpenses();
      for (Expense expense : listOfExpenses) {
        expenseTable.getItems().add(expense);
      }

      for (Sale sale : listOfIncomes) {
        incomeTable.getItems().add(sale);
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.WARNING, e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * Method that clears all fields.
   */
  public void clearAllFields() {
    projectNameField.clear();
    incomeNameField.clear();
    incomeAmountField.clear();
    expenseNameField.clear();
    expenseAmountField.clear();
  }

  /**
   * Method that switches back to main menu.
   *
   * @param event event that brings you back to main menu.
   * @throws IOException if there is an error loading the FXML file.
   */
  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.MAIN_MENU);
  }

  /**
   * Method that tries to add an expense.
   * Shows warning alerts if any fields are incorrectly filled out.
   */
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

  /**
   * Method that creates a new sale.
   *
   * @throws IllegalArgumentException If any text fields are not filled out.
   * @throws NullPointerException If any text fields are null.
   */
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
    listOfIncomes.add(newIncome);

    incomeNameField.clear();
    incomeAmountField.clear();

    updateTables();
  }

  /**
   * Method that creates a new expense.
   *
   * @throws IllegalArgumentException If any text fields are not filled out.
   * @throws NullPointerException If any text fields are null.
   */
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
    listOfExpenses.add(newExpense);

    expenseNameField.clear();
    expenseAmountField.clear();

    updateTables();


  }

  /**
   * Method that tries to add a sale.
   * Shows warning alert if any fields are incorrectly filled out.
   */
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

  /**
   * Method that updates tables with recently added values.
   */
  public void updateTables() {
    expenseNameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

    expenseAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

    incomeNameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

    incomeAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

    expenseTable.getItems().clear();
    incomeTable.getItems().clear();

    for (Expense expense : listOfExpenses) {
      expenseTable.getItems().add(expense);
    }

    for (Sale sale : listOfIncomes) {
      incomeTable.getItems().add(sale);
    }
  }

  /**
   * Method that deletes an expense from the budget.
   */
  public void onDeleteExpenseClicked() {
    try {
      Expense expenseToRemove = expenseTable.getSelectionModel().getSelectedItem();
      listOfExpenses.remove(expenseToRemove);
      updateTables();
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot remove expense");
      alert.showAndWait();
    }
  }

  /**
   * Method that deletes a sale from the budget.
   */
  public void onDeleteIncomeClicked() {
    try {
      Sale saleToRemove = incomeTable.getSelectionModel().getSelectedItem();
      listOfIncomes.remove(saleToRemove);
      updateTables();
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot remove sale");
      alert.showAndWait();
    }
  }

  /**
   * Method that disables delete expense button if no expenses are selected.
   */
  private void disableDeleteExpenseButtonWhileInvalid() {
    deleteExpenseButton.disableProperty().bind(selectedItemsNotNullBindingExpense().not());
  }

  /**
   * Method that disables delete income if no sales are selected.
   */
  private void disableDeleteIncomeButtonWhileInvalid() {
    deleteIncomeButton.disableProperty().bind(selectedItemsNotNullBindingIncome().not());
  }

  /**
   * Boolean binding that checks if there are any selected items.
   *
   * @return a Boolean binding that represents if there are any selected items or not.
   */
  private BooleanBinding selectedItemsNotNullBindingExpense() {
    return expenseTable.getSelectionModel().selectedItemProperty().isNotNull();
  }

  /**
   * Boolean binding that checks if there are any selected items.'
   *
   * @return a Boolean binding that represents if there are any selected items or not.
   */
  private BooleanBinding selectedItemsNotNullBindingIncome() {
    return incomeTable.getSelectionModel().selectedItemProperty().isNotNull();
  }
}
