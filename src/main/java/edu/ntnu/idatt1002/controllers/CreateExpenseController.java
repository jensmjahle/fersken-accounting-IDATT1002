package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Contact;
import edu.ntnu.idatt1002.Expense;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.registers.ContactRegister;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Controller class for the create expense page.
 */
public class CreateExpenseController implements Initializable {

  @FXML
  private ComboBox<String> supplierComboBox;

  @FXML
  private TextField amountTextField;
  @FXML
  private DatePicker datePicker;
  @FXML
  private TextField productTextField;
  private View pageToReturnTo;


  /**
   * Method for switching back to main menu.
   *
   * @param event type for event that brings you back to main menu
   * @throws IOException if method can't find filepath
   */
  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    try {
      ViewManager.switchToScene(event, pageToReturnTo);
    } catch (Exception e) {
      ViewManager.switchToScene(event, View.MAIN_MENU);
    }
  }

  /**
   * Sets a new page to return on return button pressed or when sale has been created.
   *
   * @param pageToReturnTo Page to return to.
   */
  public void setPageToReturnTo(View pageToReturnTo) {
    this.pageToReturnTo = pageToReturnTo;
  }


  /**
   * Method for creating an expense.
   */

  public void createExpense() throws IllegalArgumentException, NullPointerException {

    if (supplierComboBox.getValue() == null || supplierComboBox.getValue().isBlank()) {
      throw new IllegalArgumentException("Supplier has to be chosen");
    }
    if (datePicker.getValue() == null) {
      throw new IllegalArgumentException("Date has to be chosen");
    }
    if (amountTextField.getText().isEmpty()) {
      throw new IllegalArgumentException("Amount has to be chosen");
    }
    if (productTextField.getText() == null) {
      throw new IllegalArgumentException("Product has to be chosen");
    }

    String supplier = supplierComboBox.getValue();
    Contact contact = RegisterManager.getInstance().getSupplierRegister()
        .findContactFromName(supplier);

    Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    double amount = (Double.parseDouble(amountTextField.getText()));
    String product = productTextField.getText();

    if (contact != null) {
      System.out.println(contact);
      Expense newExpense = new Expense(contact, amount, date, product);
      RegisterManager.getInstance().getExpenseRegister().addObject(newExpense);
    } else {
      Expense newExpense = new Expense(supplierComboBox.getValue(), amount, date, product);
      RegisterManager.getInstance().getExpenseRegister().addObject(newExpense);
    }

    clearAllFields();
  }

  /**
   * Tries to create an expense, if it's not able to, an error message will be shown to the user
   * with the cause of error.
   */
  public void onCreateExpense() {
    try {
      createExpense();
    } catch (NumberFormatException e) {
      Alert alert = new Alert(AlertType.WARNING, e.getMessage() + " is not a valid number");
      alert.showAndWait();

    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save expense because: " + e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * Method for clearing all fields of input.
   */
  @FXML
  public void clearAllFields() {
    supplierComboBox.setValue(null);
    datePicker.setValue(null);
    amountTextField.clear();
    productTextField.clear();
  }

  /**
   * Initializes the controller with necessary data by updating the supplier choice box.
   *
   * @param url            The location used to resolve relative paths for the root object, or null
   *                       if the location is not known.
   * @param resourceBundle The resources used to localize the root object, or null if the root
   *                       object was not localized.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    fillSupplierComboBox();

  }

  /**
   * Fills the supplier combo box with all suppliers.
   */
  public void fillSupplierComboBox() {
    supplierComboBox.setEditable(true);
    ContactRegister supplierRegister = RegisterManager.getInstance().getSupplierRegister();
    for (Contact supplier : supplierRegister.getObjects()) {
      supplierComboBox.getItems().add(supplier.getName());
    }
  }
}