package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.User;
import edu.ntnu.idatt1002.registers.UserRegister;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class LoginController implements Initializable {

  public TextField userNameTextField;
  public PasswordField passwordTextField;
  private UserRegister userRegister;


  @FXML
  private void onLoginClicked(MouseEvent event) {
    try {
      login(event);
    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, e.getMessage());
      alert.showAndWait();
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.WARNING,
          "Something went wrong, please try again or contact customer support");
      e.printStackTrace();
      alert.showAndWait();
    }
  }

  @FXML
  private void login(MouseEvent event)
      throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

    if (userNameTextField.getText().isEmpty()) {
      throw new IllegalArgumentException("You have to enter your user name");
    }
    if (passwordTextField.getText().isEmpty()) {
      throw new IllegalArgumentException("You have to enter your user password");
    }
    if (!userRegister.userNameExists(userNameTextField.getText())) {
      throw new IllegalArgumentException("User does not exist");
    }

    String userName = userNameTextField.getText();
    User currentUser = userRegister.findUserByName(userName);

    if (Arrays.equals(currentUser.getHash(),
        createHash(passwordTextField.getText(), currentUser.getSalt()))) {
      confirmLoginSuccessful();
      switchToMainMenuScene(event);

    } else {
      throw new IllegalArgumentException("Wrong password or user name");
    }


  }


  private void confirmLoginSuccessful() {
    Alert alert = new Alert(AlertType.CONFIRMATION, "Login successful");
    alert.showAndWait();
  }

  private byte[] createHash(String password, byte[] salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {

    KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    return secretKeyFactory.generateSecret(keySpec).getEncoded();
  }



  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    userRegister = RegisterManager.getInstance().getUserRegister();
  }

  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    RegisterManager.getInstance().setUserName(userNameTextField.getText()); 
    ViewManager.switchToScene(event, View.MAIN_MENU);

  }

  public void switchToCreateUser(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.CREATE_USER);
  }
}
