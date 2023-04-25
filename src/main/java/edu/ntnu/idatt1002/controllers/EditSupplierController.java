package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.registers.RegisterManager;
import edu.ntnu.idatt1002.storageitems.Contact;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controller class for edit supplier FXML file.
 */
public class EditSupplierController extends CreateSupplierController {

  @FXML
  private TextField nameField;
  @FXML
  private TextField emailField;
  @FXML
  private TextField phoneNumberField;
  @FXML
  private TextField organizationNumberField;
  @FXML
  private TextField accountNumberField;
  @FXML
  private TextField postCodeField;
  @FXML
  private TextField streetField;
  @FXML
  private TextField streetNumberField;
  private Contact supplier;

  /**
   * Sets the supplier and updates fields with current info.
   *
   * @param supplier Supplier to be displayed.
   */
  public void setSupplier(Contact supplier) {
    this.supplier = supplier;
    fillFieldsWithExistingInfo();
  }

  /**
   * Displays info about supplier to be edited. Throws exception if fields could not be filled.
   */
  @FXML
  @SuppressWarnings("Duplicates")
  private void fillFieldsWithExistingInfo() {
    try {
      nameField.setText(supplier.getName());
      emailField.setText(supplier.getEmail());
      phoneNumberField.setText(supplier.getPhoneNumber());
      organizationNumberField.setText(supplier.getOrganizationNumber());
      accountNumberField.setText(supplier.getAccountNumber());
      postCodeField.setText(supplier.getPostCode());
      streetField.setText(supplier.getStreet());
      streetNumberField.setText(String.valueOf(supplier.getStreetNumber()));
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.WARNING, "Could not fill out all fields correctly");
      alert.showAndWait();
    }
  }

  /**
   * Creates new supplier with updated info.
   *
   * @param mouseEvent Event that creates supplier and switches back to list of all suppliers.
   */
  @FXML
  private void onConfirmChanges(InputEvent mouseEvent) {
    try {
      createSupplier();
      RegisterManager.getInstance().getSupplierRegister().removeObject(supplier);
      switchToListOfAllSuppliersScene(mouseEvent);
    } catch (NumberFormatException e) {
      Alert alert = new Alert(AlertType.WARNING, e.getMessage() + " is not a valid number");
      alert.showAndWait();

    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save supplier because: " + e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * Switches back to list of all suppliers page.
   *
   * @param event Event that triggers switch back to list of all suppliers page.
   */
  @FXML
  private void switchToListOfAllSuppliersScene(InputEvent event) {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_SUPPLIERS);
  }

  /**
   * Handles key shortcuts, executing the shortcut linked to each KeyCode.
   *
   * @param keyEvent Event that triggers the shortcut.
   */
  @FXML
  private void handleKeyPressed(KeyEvent keyEvent) {
    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
      onConfirmChanges(keyEvent);
    } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
      switchToListOfAllSuppliersScene(keyEvent);
    }
  }
}
