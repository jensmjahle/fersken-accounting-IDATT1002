package edu.ntnu.idatt1002.fxml;

import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.Sale;
import javafx.fxml.FXML;
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

public class RegisterSaleController {

  @FXML
  private TextField customerTextField;
  @FXML
  private DatePicker datePicker;
  @FXML
  private TextField accountTextField;
  @FXML
  private TextField productTextField;
  @FXML
  private TextField amountTextField;

  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("MainMenu"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
  @FXML
  public void createSale() {
    String customer = customerTextField.getText();
    Date date = java.sql.Date.valueOf(datePicker.getValue());
    String receiverAccount = accountTextField.getText();
    String product = productTextField.getText();
    double amount = (Double.parseDouble(amountTextField.getText()));

    try {
      Sale newSale = new Sale(customer, date, product, receiverAccount, amount);
      RegisterManager.getInstance().getSaleRegister().addObject(newSale);
    }catch (Exception e) {
      e.printStackTrace();
    }

    clearAllFields();
  }
  @FXML
  public void clearAllFields() {
    customerTextField.clear();
    datePicker.setValue(null);
    accountTextField.clear();
    productTextField.clear();
    amountTextField.clear();
  }
}