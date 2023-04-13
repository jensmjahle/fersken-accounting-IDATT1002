package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Contact;
import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.Sale;
import edu.ntnu.idatt1002.registers.ContactRegister;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Date;

/**
 * Controller class for the register sale page.
 */
public class RegisterSaleController {

  @FXML
  private ChoiceBox customerChoiceBox;
  @FXML
  private DatePicker datePicker;
  @FXML
  private TextField accountTextField;
  @FXML
  private TextField productTextField;
  @FXML
  private TextField amountTextField;

  /**
   * Method for switching back to main menu.
   *
   * @param event Type of event that brings you back to main menu.
   * @throws IOException if method can't find filepath.
   */
  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.MAINMENU);
  }

  /**
   * Initializes nodes on register sale page.
   */
  @FXML
  public void initialize() {
    ContactRegister contactRegister = RegisterManager.getInstance().getCustomerRegister();
    for (Contact contact : contactRegister.getObjects()) {
      customerChoiceBox.getItems().add(contact.getName());
    }
  }

  /**
   * Method for creating a sale from input values.
   */
  @FXML
  public void createSale() {
    String customer = (String) customerChoiceBox.getValue();
    Contact contact = RegisterManager.getInstance().getCustomerRegister().findContactFromName(customer);
    Date date = java.sql.Date.valueOf(datePicker.getValue());
    String receiverAccount = accountTextField.getText();
    String product = productTextField.getText();
    double amount = (Double.parseDouble(amountTextField.getText()));

    try {
      Sale newSale = new Sale(contact, date, product, receiverAccount, amount);
      RegisterManager.getInstance().getSaleRegister().addObject(newSale);
    }catch (Exception e) {
      e.printStackTrace();
    }

    clearAllFields();
  }

  /**
   * Method for clearing all fields of input.
   */
  @FXML
  public void clearAllFields() {
    customerChoiceBox.setValue(null);
    datePicker.setValue(null);
    accountTextField.clear();
    productTextField.clear();
    amountTextField.clear();
  }
}