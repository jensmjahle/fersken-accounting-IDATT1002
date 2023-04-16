package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Contact;
import edu.ntnu.idatt1002.Expense;
import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.registers.ContactRegister;
import edu.ntnu.idatt1002.viewmanagement.View;
import java.io.IOException;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller for the Edit Sale fxml file.
 */
public class EditExpenseController extends CreateExpenseController {

  @FXML
  private ChoiceBox<String> supplierChoiceBox;

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
    fillSupplierChoiceBox();
  }

  /**
   * Tries to create a new sale. If the sale is created the old sale will be deleted.
   */
  @FXML
  private void changeExpense(MouseEvent event) throws IOException {

    try {

      createExpense();
      RegisterManager.getInstance().getExpenseRegister().removeObject(expense);

    } catch (NumberFormatException e){
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
   * @throws IOException if the resource path is invalid.
   */
  @FXML
  public void switchToListOfExpenses(MouseEvent mouseEvent) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath(View.LIST_OF_ALL_EXPENSES.getFileName()));
    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Fills the text input fields with the current info about the Expense that will be edited.
   */
  @FXML
  public void fillFieldsWithCurrentInfo() {
    try {
      supplierChoiceBox.setValue(expense.getSupplierName());
      amountTextField.setText(String.valueOf(expense.getAmount()));
      datePicker.setValue(
          expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      productTextField.setText(expense.getProduct());
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR, "Cannot present all fields for selected sale");
      alert.showAndWait();
      e.printStackTrace();
    }
  }


  public void fillSupplierChoiceBox() {
    ContactRegister contactRegister = RegisterManager.getInstance().getSupplierRegister();
    for (Contact contact : contactRegister.getObjects()) {
      supplierChoiceBox.getItems().add(contact.getName());
    }
  }
}
