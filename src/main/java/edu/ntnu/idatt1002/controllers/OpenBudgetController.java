package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Budget;
import edu.ntnu.idatt1002.Expense;
import edu.ntnu.idatt1002.Sale;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for open budget page.
 * Displays expenses and income about a given budget.
 */
public class OpenBudgetController {
  @FXML
  private Label budgetNameField;
  @FXML
  private TableView<Expense> expensesTableView;
  @FXML
  private TableColumn<Expense, String> expenseSupplierColumn;
  @FXML
  private TableColumn<Expense, String> expenseDateColumn;
  @FXML
  private TableColumn<Expense, Double> expenseAmountColumn;
  @FXML
  private TableColumn<Expense, String> expenseProductColumn;
  @FXML
  private TableView<Sale> incomeTableView;
  @FXML
  private TableColumn<Sale, String> incomeCustomerColumn;
  @FXML
  private TableColumn<Sale, String> incomeDateColumn;
  @FXML
  private TableColumn<Sale, Double> incomeAmountColumn;
  @FXML
  private TableColumn<Sale, String> incomeProductColumn;
  private Budget budget;
  private List<Expense> listOfExpenses;
  private List<Sale> listOfIncome;

  /**
   * Set the budget to be shown and retrieves a list of expenses
   * and a list of sales from the budget.
   * Fills fields with current info about the budget.
   *
   * @param budget The budget to be displayed.
   */
  public void budgetToBeShown(Budget budget) {
    this.budget = budget;
    listOfExpenses = budget.getListOfExpenses();
    listOfIncome = budget.getListOfSales();
    fillFieldsWithCurrentInfo();
  }

  /**
   * Switches to list of all budgets page.
   *
   * @param event Event that triggers switch to list of all budgets.
   * @throws IOException If resource path is invalid.
   */
  @FXML
  private void switchToListOfAllBudgetsScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_BUDGETS);
  }

  /**
   * Fills fields with current info about the budget.
   */
  @FXML
  private void fillFieldsWithCurrentInfo() {
    try {
      budgetNameField.setText(budget.getProjectName());

      expenseSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));

      expenseDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

      expenseAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

      expenseProductColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

      incomeCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

      incomeDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

      incomeAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

      incomeProductColumn.setCellValueFactory(new PropertyValueFactory<>("product"));


      listOfIncome = budget.getListOfSales();
      listOfExpenses = budget.getListOfExpenses();
      for (Expense expense : listOfExpenses) {
        expensesTableView.getItems().add(expense);
      }

      for (Sale sale : listOfIncome) {
        incomeTableView.getItems().add(sale);
      }
    } catch (Exception e) {
      Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
      e.printStackTrace();
      alert.showAndWait();
    }
  }
}
