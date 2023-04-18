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

  public void setSupplier(Contact supplier){
    this.supplier = supplier;
    fillFieldsWithExistingInfo();
  }

  @FXML
  private void fillFieldsWithExistingInfo(){
    try {
      nameField.setText(supplier.getName());
      emailField.setText(supplier.getEmail());
      phoneNumberField.setText(supplier.getPhoneNumber());
      organizationNumberField.setText(supplier.getOrganizationNumber());
      accountNumberField.setText(supplier.getAccountNumber());
      postCodeField.setText(supplier.getPostCode());
      streetField.setText(supplier.getStreet());
      streetNumberField.setText(String.valueOf(supplier.getStreetNumber()));
    } catch (Exception e){
      Alert alert = new Alert(AlertType.WARNING, "Could not fill out all fields correctly");
      alert.showAndWait();
    }
  }


  @FXML
  private void onConfirmChanges(MouseEvent mouseEvent) throws IOException {
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

  @FXML
  private void switchToListOfAllSuppliersScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.LIST_OF_ALL_SUPPLIERS);
  }
}
