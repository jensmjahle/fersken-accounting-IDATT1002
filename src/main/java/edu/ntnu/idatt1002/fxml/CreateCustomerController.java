package edu.ntnu.idatt1002.fxml;

import edu.ntnu.idatt1002.Contact;
import edu.ntnu.idatt1002.registers.ContactRegister.*;

import edu.ntnu.idatt1002.registers.ContactRegister;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateCustomerController {

  private Stage stage;
  private Scene scene;
  private Parent root;

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

  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void createCustomer(){
    String name = nameField.getText();
    String email = emailField.getText();
    String street = streetField.getText();
    int streetNumber = Integer.parseInt(streetNumberField.getText());
    String phoneNumber = phoneNumberField.getText();
    String organizationNumber = organizationNumberField.getText();
    String accountNumber = accountNumberField.getText();
    String postCode = postCodeField.getText();

    Contact newCustomer = new Contact(name, email, street, streetNumber, phoneNumber, accountNumber, postCode, organizationNumber);
    ContactRegister contactRegister = new ContactRegister("contacts");
    contactRegister.addObject(newCustomer);

    clearAllFields();
  }

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