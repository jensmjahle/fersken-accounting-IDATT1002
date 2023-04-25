package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.registers.ExpenseRegister;
import edu.ntnu.idatt1002.registers.RegisterManager;
import edu.ntnu.idatt1002.registers.SaleRegister;
import edu.ntnu.idatt1002.storageitems.Budget;
import edu.ntnu.idatt1002.storageitems.Expense;
import edu.ntnu.idatt1002.storageitems.Sale;
import edu.ntnu.idatt1002.utility.PathUtility;
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
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

  /**
   * Initializes the controller with necessary methods.
   *
   * @param url            The location used to resolve relative paths for the root object, or null
   *                       if the location is not known.
   * @param resourceBundle The resources used to localize the root object, or null if the root
   *                       object was not localized.
   */
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
   */
  public void switchToMainMenuScene(InputEvent event) {
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
        PathUtility.getResourcePath(View.CREATE_EXPENSE.getFileName()));
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
        PathUtility.getResourcePath(View.CREATE_SALE.getFileName()));
    Parent root = loader.load();
    CreateSaleController controller = loader.getController();
    controller.setPageToReturnTo(View.CREATE_BUDGET);

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Updates both tables with recently added values.
   */
  private void updateTables() {
    updateExpenseTable();
    updateSalesTable();
  }

  /**
   * Updates expense table with all recently added expenses.
   */
  private void updateExpenseTable() {
    expenseTable.getItems().removeAll(expenseTable.getItems());
    ExpenseRegister expenseRegister = RegisterManager.getInstance().getExpenseRegister();
    expenseProductColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

    expenseAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

    for (Expense expense : expenseRegister.getObjects()) {
      expenseTable.getItems().add(expense);
    }
  }

  /**
   * Updates sales table with all recently added sales.
   */
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
   * Tries to create a budget, if any errors occur, the user will be shown an alert with an error
   * message.
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
   * Tries to create a budget with the selected sales and expenses.
   *
   * @throws IllegalArgumentException If the project name is empty
   * @throws NullPointerException     If the project name is null
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

  /**
   * Method for checking the difference in a budget. Presents a confirmation alert if difference is
   * not zero.
   *
   * @return true if difference is zero or if user wants to proceed, false if user cancels the
   *        operation.
   */
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

  /**
   * Method for presenting a confirmation box to the user. Confirming that a budget has been
   * created.
   */
  private void confirmBudgetIsCreated() {
    Alert budgetHasBeenCreated = new Alert(AlertType.CONFIRMATION, "Budget has been created");
    budgetHasBeenCreated.show();
    PauseTransition delay = new PauseTransition(Duration.seconds(2));
    delay.setOnFinished(event -> budgetHasBeenCreated.close());
    delay.play();
  }

  /**
   * Method that clears all input fields and tables.
   */
  public void clearAllFields() {
    projectNameField.clear();
    expenseTable.getItems().clear();
    incomeTable.getItems().clear();

  }

  /**
   * Handles key shortcuts, executing the shortcut linked to each KeyCode.
   *
   * @param keyEvent Event that triggers the shortcut.
   */
  @FXML
  private void handleKeyPressed(KeyEvent keyEvent) {
    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
      createBudgetClicked();
    } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
      switchToMainMenuScene(keyEvent);
    }
  }
}
