package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.registers.ContactRegister;
import edu.ntnu.idatt1002.registers.RegisterManager;
import edu.ntnu.idatt1002.storageitems.Contact;
import edu.ntnu.idatt1002.storageitems.Expense;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controller for the Edit Sale fxml file.
 */
public class EditExpenseController extends CreateExpenseController {

  @FXML
  private ComboBox<String> supplierComboBox;
  @FXML
  private TextField amountTextField;
  @FXML
  private DatePicker datePicker;
  @FXML
  private TextField productTextField;
  private Expense expense;

  /**
   * Sets the expense and updates the fields with current info.
   *
   * @param expense Expense that will be edited and displayed
   */
  public void setExpense(Expense expense) {
    this.expense = expense;
    fillFieldsWithCurrentInfo();

  }

  /**
   * Fills the customer choice box with all customers during initialization.
   */

  public void initialize() {
    fillSupplierComboBox();
  }

  /**
   * Tries to create a new sale. If the sale is created the old sale will be deleted.
   */
  @FXML
  private void changeExpense(InputEvent event) {

    try {

      createExpense();
      RegisterManager.getInstance().getExpenseRegister().removeObject(expense);

    } catch (NumberFormatException e) {
      Alert alert = new Alert(AlertType.WARNING, e.getMessage() + " is not a valid number");
      alert.showAndWait();

    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save expense because: " + e.getMessage());
      alert.showAndWait();
    }
    switchToListOfExpenses(event);
  }

  /**
   * Loads the list of Expenses fxml file.
   *
   * @param mouseEvent the event that triggers the switch back to list of sales.
   */
  @FXML
  public void switchToListOfExpenses(InputEvent mouseEvent) {
    ViewManager.switchToScene(mouseEvent, View.LIST_OF_ALL_EXPENSES);
  }

  /**
   * Fills the text input fields with the current info about the Expense that will be edited.
   */
  @FXML
  public void fillFieldsWithCurrentInfo() {
    try {
      supplierComboBox.setEditable(true);
      supplierComboBox.setValue(expense.getSupplierName());
      amountTextField.setText(String.valueOf(expense.getAmount()));
      datePicker.setValue(
          expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      productTextField.setText(expense.getProduct());
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR, "Cannot present all fields for selected expense");
      alert.showAndWait();
      e.printStackTrace();
    }
  }

  /**
   * Fills ComboBox with existing suppliers from the supplier register.
   */
  public void fillSupplierComboBox() {
    ContactRegister contactRegister = RegisterManager.getInstance().getSupplierRegister();
    for (Contact contact : contactRegister.getObjects()) {
      supplierComboBox.getItems().add(contact.getName());
    }
  }

  /**
   * Handles key shortcuts, executing the shortcut linked to each KeyCode.
   *
   * @param keyEvent Event that triggers the shortcut.
   */
  @FXML
  private void handleKeyPressed(KeyEvent keyEvent) {
    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
      changeExpense(keyEvent);
    } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
      switchToListOfExpenses(keyEvent);
    }
  }
}
