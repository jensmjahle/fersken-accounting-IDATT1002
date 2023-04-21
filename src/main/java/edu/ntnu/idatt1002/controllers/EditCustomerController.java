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
 * Controller class for the edit customer FXML file.
 */
public class EditCustomerController extends CreateCustomerController {

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
  private Contact customer;

  /**
   * Sets the customer and updates fields with current info.
   *
   * @param customer Customer to be displayed and edited.
   */
  public void setCustomer(Contact customer) {
    this.customer = customer;
    fillFieldsWithExistingInfo();
  }

  /**
   * Method for displaying info about the customer.
   * Throws exception if fields could not be filled.
   */
  @FXML
  private void fillFieldsWithExistingInfo() {
    try {
      nameField.setText(customer.getName());
      emailField.setText(customer.getEmail());
      phoneNumberField.setText(customer.getPhoneNumber());
      organizationNumberField.setText(customer.getOrganizationNumber());
      accountNumberField.setText(customer.getAccountNumber());
      postCodeField.setText(customer.getPostCode());
      streetField.setText(customer.getStreet());
      streetNumberField.setText(String.valueOf(customer.getStreetNumber()));
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.WARNING, "Could not fill out all fields correctly");
      alert.showAndWait();

    }
  }

  /**
   * Creates new customer with updated info.
   *
   * @param mouseEvent Event that creates customer and
   *                   switches back to list of all customers.
   * @throws IOException If the resource path is invalid.
   */
  @FXML
  private void onConfirmChanges(MouseEvent mouseEvent) throws IOException {
    try {
      createCustomer();
      RegisterManager.getInstance().getCustomerRegister().removeObject(customer);
      switchToListOfAllCustomersScene(mouseEvent);
    } catch (NumberFormatException e) {
      Alert alert = new Alert(AlertType.WARNING, e.getMessage() + " is not a valid number");
      alert.showAndWait();

    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save customer because: " + e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * Method that switches to list of all customers.
   *
   * @param event Event that triggers switch back to list of all customers.
   * @throws IOException If the resource path is invalid.
   */
  @FXML
  private void switchToListOfAllCustomersScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_CUSTOMERS);
  }


}
