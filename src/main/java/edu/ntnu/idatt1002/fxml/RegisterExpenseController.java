package edu.ntnu.idatt1002.fxml;

import edu.ntnu.idatt1002.Expense;
import edu.ntnu.idatt1002.RegisterManager;
import javafx.fxml.FXML;
import edu.ntnu.idatt1002.PathUtility;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class RegisterExpenseController {

  @FXML
  private TextField amountTextField;
  @FXML
  private DatePicker datePicker;
  @FXML
  private TextField productTextField;

  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("MainMenu"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
  @FXML
  public void createExpense() {
    // String supplier = supplierTextField.getText();
    Date date = java.sql.Date.valueOf(datePicker.getValue());
    double amount = (Double.parseDouble(amountTextField.getText()));
    String product = productTextField.getText();

    // Expense newExpense = new Expense(supplier, amount, date, product);
    try {
      Expense newExpense = new Expense(amount, date, product);
      RegisterManager.getInstance().getExpenseRegister().addObject(newExpense);
    } catch (Exception e) {
      e.printStackTrace();
    }

    clearAllFields();
  }
  @FXML
  public void clearAllFields() {
    datePicker.setValue(null);
    amountTextField.clear();
    productTextField.clear();
  }
}