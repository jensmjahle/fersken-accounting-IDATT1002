package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Contact;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Represents a controller used to create a supplier, used in the "createSupplier" fxml file.
 */
public class CreateSupplierController {


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
   * Creates a supplier if all input fields have valid fields.
   *
   * @throws IllegalArgumentException If input fields are empty or the supplier cannot be created
   *                                  because of restrictions in constructor
   */
  public void createSupplier() throws IllegalArgumentException {
    if (nameField.getText().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty");
    }
    if (emailField.getText().isEmpty()) {
      throw new IllegalArgumentException("E-mail cannot be empty");
    }
    if (streetField.getText().isEmpty()) {
      throw new IllegalArgumentException("Street cannot be empty");
    }
    if (streetNumberField.getText().isEmpty()) {
      throw new IllegalArgumentException("Street number cannot be empty");
    }
    if (phoneNumberField.getText().isEmpty()) {
      throw new IllegalArgumentException("Phone number cannot be empty");
    }
    if (organizationNumberField.getText().isEmpty()) {
      throw new IllegalArgumentException("Organization number cannot be empty");
    }
    if (accountNumberField.getText().isEmpty()) {
      throw new IllegalArgumentException("Account number cannot be empty");
    }
    if (postCodeField.getText().isEmpty()) {
      throw new IllegalArgumentException("Post code cannot be empty");
    }

    String name = nameField.getText();
    String email = emailField.getText();
    String street = streetField.getText();
    int streetNumber = Integer.parseInt(streetNumberField.getText());
    String phoneNumber = phoneNumberField.getText();
    String organizationNumber = organizationNumberField.getText();
    String accountNumber = accountNumberField.getText();
    String postCode = postCodeField.getText();

    Contact newCustomer = new Contact(name, email, street, streetNumber, phoneNumber, accountNumber,
        postCode, organizationNumber);
    RegisterManager.getInstance().getSupplierRegister().addObject(newCustomer);
    clearAllFields();

  }

  /**
   * Tries to create a supplier, if it's not possible, an alert will be shown to the user.
   */
  public void onCreateSupplier() {
    try {
      createSupplier();
    } catch (NumberFormatException e) {
      Alert alert = new Alert(AlertType.WARNING, e.getMessage() + " is not a valid number");
      alert.showAndWait();

    } catch (Exception e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save Customer because: " + e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * Clears all input fields.
   */
  public void clearAllFields() {
    nameField.clear();
    emailField.clear();
    streetField.clear();
    streetNumberField.clear();
    phoneNumberField.clear();
    organizationNumberField.clear();
    accountNumberField.clear();
    postCodeField.clear();
  }
}