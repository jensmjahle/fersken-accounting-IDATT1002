package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Contact;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.Sale;
import edu.ntnu.idatt1002.registers.ContactRegister;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * Controller class for the register sale page.
 */
public class CreateSaleController {


  @FXML
  private ComboBox<String> customerComboBox;
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
    ViewManager.switchToScene(event, View.MAIN_MENU);
  }

  /**
   * Initializes nodes on register sale page.
   */
  @FXML
  public void initialize() {
    fillCustomerChoiceBox();


  }

  /**
   * Method for creating a sale from input values.
   */

  public void createSale() throws IllegalArgumentException, NullPointerException {
    if (customerComboBox.getValue() == null){
      throw new IllegalArgumentException("customer has to be chosen");
    }
    if (datePicker.getValue() == null){
      throw new IllegalArgumentException("Date has to be chosen");
    }
    if (amountTextField.getText().isEmpty()){
      throw new IllegalArgumentException("Amount has to be chosen");
    }
    if (accountTextField.getText().isEmpty()){
      throw new IllegalArgumentException("Account has to be chosen");
    }
    if (productTextField.getText().isEmpty()){
      throw new IllegalArgumentException("Product has to be chosen");
    }

    String customer = customerComboBox.getValue();
    Contact contact = RegisterManager.getInstance().getCustomerRegister()
        .findContactFromName(customer);
    Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    String receiverAccount = accountTextField.getText();
    String product = productTextField.getText();
    double amount = (Double.parseDouble(amountTextField.getText()));

    Sale newSale = new Sale(contact, date, product, receiverAccount, amount);
    RegisterManager.getInstance().getSaleRegister().addObject(newSale);
    clearAllFields();

    confirmSaleIsCreated();


  }

  public void onCreateSale() {
    try {
      createSale();
    } catch (NumberFormatException e){
      Alert alert = new Alert(AlertType.WARNING, e.getMessage() + " is not a valid number");
      alert.showAndWait();

    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save expense because: " + e.getMessage());
      alert.showAndWait();
    }
  }

  public void confirmSaleIsCreated() {
    Alert saleHasBeenCreated = new Alert(AlertType.CONFIRMATION, "Sale has been saved");
    saleHasBeenCreated.show();
    PauseTransition delay = new PauseTransition(Duration.seconds(1));
    delay.setOnFinished(event -> saleHasBeenCreated.close());
    delay.play();
  }

  /**
   * Method for clearing all fields of input.
   */
  @FXML
  public void clearAllFields() {
    customerComboBox.setValue(null);
    datePicker.getEditor().clear();
    accountTextField.clear();
    productTextField.clear();
    amountTextField.clear();
  }


  public void fillCustomerChoiceBox() {
    ContactRegister contactRegister = RegisterManager.getInstance().getCustomerRegister();
    for (Contact contact : contactRegister.getObjects()) {
      customerComboBox.getItems().add(contact.getName());
    }
  }
}