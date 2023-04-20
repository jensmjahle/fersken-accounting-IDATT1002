package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Budget;
import edu.ntnu.idatt1002.Expense;
import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.Sale;
import edu.ntnu.idatt1002.registers.ExpenseRegister;
import edu.ntnu.idatt1002.registers.SaleRegister;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Represents a controller for creating a budget, used for the CreateBudget fxml.
 */
public class CreateBudgetController implements Initializable {

  @FXML
  private TextField projectNameField;
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

  /**
   * Enables the possibility for the user to select multiple items at once.
   */
  private void enableMultiSelection() {
    expenseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    incomeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
  }

  /**
   * Loads the main menu fxml into the scene.
   *
   * @param event event that triggers the method execution
   * @throws IOException if there is an error loading the main menu
   */
  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.MAIN_MENU);
  }

  /**
   * Switches to the register expense scene and sets its page to return to the "create budget"
   * fxml.
   *
   * @param event Event that triggers the method execution
   * @throws IOException if there is an error loading the fxml file
   */
  public void switchToRegisterExpenseScene(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(
        PathUtility.getResourcePath(View.REGISTER_EXPENSE.getFileName()));
    Parent root = loader.load();
    CreateExpenseController controller = loader.getController();
    controller.setPageToReturnTo(View.CREATE_BUDGET);

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Switches to the register sale scene and sets its page to return to the "create budget" fxml.
   *
   * @param event Event that triggers the method execution
   * @throws IOException if there is an error loading the fxml file
   */
  public void switchToRegisterSaleScene(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(
        PathUtility.getResourcePath(View.REGISTER_SALE.getFileName()));
    Parent root = loader.load();
    CreateSaleController controller = loader.getController();
    controller.setPageToReturnTo(View.CREATE_BUDGET);

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  private void updateTables() {
    updateExpenseTable();
    updateSalesTable();
  }

  private void updateExpenseTable() {
    expenseTable.getItems().removeAll(expenseTable.getItems());
    ExpenseRegister expenseRegister = RegisterManager.getInstance().getExpenseRegister();
    expenseProductColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

    expenseAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

    for (Expense expense : expenseRegister.getObjects()) {
      expenseTable.getItems().add(expense);
    }
  }

  private void updateSalesTable() {
    final SaleRegister saleRegister = RegisterManager.getInstance().getSaleRegister();
    incomeTable.getItems().removeAll(incomeTable.getItems());
    customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
    incomeProductColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
    incomeAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

    for (Sale sale : saleRegister.getObjects()) {
      incomeTable.getItems().add(sale);
    }
  }

  /**
   * Tries to create a budget, if any errors occur, the user will be shown an alert with an error message
   */
  public void createBudgetClicked() {
    try {
      createBudget();
    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot create budget because: " + e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * Tries to create a budget with the selected sales and expenses
   * @throws IllegalArgumentException If the project name is empty or the
   * @throws NullPointerException
   */
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
      Alert alert = new Alert(AlertType.CONFIRMATION,
          "Budget has a difference of: " + difference + "NOK"
              + "\nAre you sure you want to create this budget?");
      alert.showAndWait();

      if (alert.getResult() == ButtonType.OK) {
        return true;

      } else {
        return alert.getResult() != ButtonType.CANCEL;
      }

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
