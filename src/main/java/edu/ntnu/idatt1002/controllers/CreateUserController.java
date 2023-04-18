package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.User;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CreateUserController {

  @FXML
  public TextField userNameTextField;
  @FXML
  public PasswordField passwordTextField;
  @FXML
  public PasswordField secondaryPasswordTextField;

  public void onCreateUserClicked(MouseEvent event) {
    try {
      createUser();
      RegisterManager.getInstance().setUserName(userNameTextField.getText());
      Alert alert = new Alert(AlertType.CONFIRMATION,
          "User \"" + userNameTextField.getText() + "\"has been created");
      alert.showAndWait();
      switchToMainMenuScene(event);
    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING,
          "Could not create user because: " + e.getMessage());
      alert.showAndWait();
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.WARNING, "Could not create user because: unexpected error"
          + "\n please try again or contact customer service");
      alert.showAndWait();
      e.printStackTrace();
    }
  }


  private void createUser()
      throws IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
    if (userNameTextField.getText().isBlank()) {
      throw new IllegalArgumentException("Username cannot be empty");
    }
    if (passwordTextField.getText().isBlank()) {
      throw new IllegalArgumentException("Password cannot be empty");
    }
    if (!passwordTextField.getText().equals(secondaryPasswordTextField.getText())) {
      throw new IllegalArgumentException("The entered passwords do not match, please try again");
    }

    String userName = userNameTextField.getText();
    String password = passwordTextField.getText();

    User newUser = new User(userName, password);

    RegisterManager.getInstance().getUserRegister().addObject(newUser);
  }

  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.MAIN_MENU);
  }

  public void switchToLoginScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.LOGIN);
  }
}
