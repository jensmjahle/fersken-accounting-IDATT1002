package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Contact;
import edu.ntnu.idatt1002.Expense;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.registers.ContactRegister;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import javafx.fxml.FXML;
import edu.ntnu.idatt1002.PathUtility;
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
 * Controller class for the register expense page.
 */
public class RegisterExpenseController {
  @FXML
  private ChoiceBox supplierChoiceBox;

  @FXML
  private TextField amountTextField;
  @FXML
  private DatePicker datePicker;
  @FXML
  private TextField productTextField;

  /**
   * Method for switching back to main menu.
   *
   * @param event type for event that brings you back to main menu
   * @throws IOException if method can't find filepath
   */
  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.MAINMENU);
  }

  /**
   * Method for initializing nodes on the page.
   */
  @FXML
  public void initialize() {
    ContactRegister contactRegister = RegisterManager.getInstance().getSupplierRegister();
    for (Contact contact : contactRegister.getObjects()) {
      supplierChoiceBox.getItems().add(contact.getName());
    }
  }

  /**
   * Method for creating an expense.
   */
  @FXML
  public void createExpense() {
    String supplier = (String) supplierChoiceBox.getValue();
    Contact contact = RegisterManager.getInstance().getSupplierRegister().findContactFromName(supplier);
    Date date = java.sql.Date.valueOf(datePicker.getValue());
    double amount = (Double.parseDouble(amountTextField.getText()));
    String product = productTextField.getText();

    // Expense newExpense = new Expense(supplier, amount, date, product);
    try {
      Expense newExpense = new Expense(contact, amount, date, product);
      RegisterManager.getInstance().getExpenseRegister().addObject(newExpense);
    } catch (Exception e) {
      e.printStackTrace();
    }

    clearAllFields();
  }

  /**
   * Method for clearing all fields of input.
   */
  @FXML
  public void clearAllFields() {
    supplierChoiceBox.setValue(null);
    datePicker.setValue(null);
    amountTextField.clear();
    productTextField.clear();
  }
}