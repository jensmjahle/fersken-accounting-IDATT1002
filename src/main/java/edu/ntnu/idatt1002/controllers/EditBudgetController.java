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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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


  private List<Expense> listOfExpenses;
  private List<Sale> listOfIncomes;

  private Budget budgetToRemove;


  public void setBudgetToRemove(Budget budgetToRemove) {
    this.budgetToRemove = budgetToRemove;
    listOfIncomes = budgetToRemove.getListOfSales();
    listOfExpenses = budgetToRemove.getListOfExpenses();
    fillFieldsWithCurrentInfo();
  }

  @FXML
  private void replaceBudget(MouseEvent event) throws IOException {
    try {
      createBudget();
      RegisterManager.getInstance().getBudgetRegister().removeObject(budgetToRemove);
      switchToListOfBudgets(event);
    } catch (NumberFormatException e) {
      Alert alert = new Alert(AlertType.WARNING, e.getMessage() + " is not a valid number");
      alert.showAndWait();

    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save Budget because: " + e.getMessage());
      alert.showAndWait();
    }
  }

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


  @FXML
  public void switchToListOfBudgets(MouseEvent mouseEvent) throws IOException {
    Parent root = FXMLLoader.load(
        PathUtility.getResourcePath(View.LIST_OF_ALL_BUDGETS.getFileName()));
    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void fillFieldsWithCurrentInfo() {
    try {

      projectNameField.setText(budgetToRemove.getProjectName());

      expenseNameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

      expenseAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

      incomeNameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

      incomeAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

      expenseTable.getItems().clear();
      incomeTable.getItems().clear();

      listOfIncomes = budgetToRemove.getListOfSales();
      listOfExpenses = budgetToRemove.getListOfExpenses();
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


  public void clearAllFields() {
    projectNameField.clear();
    incomeNameField.clear();
    incomeAmountField.clear();
    expenseNameField.clear();
    expenseAmountField.clear();
  }

  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.MAIN_MENU);
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
    listOfIncomes.add(newIncome);

    incomeNameField.clear();
    incomeAmountField.clear();

    updateTables();


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
    listOfExpenses.add(newExpense);

    expenseNameField.clear();
    expenseAmountField.clear();

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

    for (Expense expense : listOfExpenses) {
      expenseTable.getItems().add(expense);
    }

    for (Sale sale : listOfIncomes) {
      incomeTable.getItems().add(sale);
    }
  }

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
}
