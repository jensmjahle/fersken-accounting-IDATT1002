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


  public void setCustomer(Contact customer) {
    this.customer = customer;
    fillFieldsWithExistingInfo();
  }

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

  @FXML
  private void switchToListOfAllCustomersScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_CUSTOMERS);
  }


}
