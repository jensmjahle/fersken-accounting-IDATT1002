package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.registers.RegisterManager;
import edu.ntnu.idatt1002.storageitems.User;
import edu.ntnu.idatt1002.registers.UserRegister;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Class that represents a controller used for logging in the user.
 */
public class LoginController implements Initializable {

  public TextField userNameTextField;
  public PasswordField passwordTextField;
  private UserRegister userRegister;
  private int loginAttempts;
  private int loginDelays;
  private boolean activeTimer;
  private static final int maxLoginAttempts = 4;

  /**
   * Tries to log in, if there is anything wrong with the input values, the user will be notified,
   * and the user will not be logged in.
   *
   * @param event The event that triggers the method.
   */
  @FXML
  private void onLoginClicked(InputEvent event) {
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

  /**
   * Tries to log in with input values, if something is wrong, an exception will be thrown.
   *
   * @param event The event that triggers the login attempt.
   * @throws NoSuchAlgorithmException If the hashing algorithm is not found.
   * @throws InvalidKeySpecException  If the
   */
  @FXML
  private void login(InputEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException {

    if (loginAttempts > maxLoginAttempts) {
      handleTooManyLogins();
    }

    if (userNameTextField.getText().isEmpty()) {
      throw new IllegalArgumentException("You have to enter your user name");
    }
    if (passwordTextField.getText().isEmpty()) {
      throw new IllegalArgumentException("You have to enter your user password");
    }
    if (userRegister.userNameAlreadyExists(userNameTextField.getText())) {
      throw new IllegalArgumentException("User does not exist");
    }

    String userName = userNameTextField.getText();
    User currentUser = userRegister.findUserByName(userName);

    checkPassword(event, currentUser);
  }

  /**
   * Adds a delay of 2 to the power of amount of login delays experienced.
   */
  private void handleTooManyLogins() {
    int delayMinutes = (int) Math.pow(2, loginDelays);
    if (!activeTimer) {
      loginDelays++;
      createEnteringDelay(delayMinutes);
      throw new IllegalArgumentException(
          "You have tried to log in to many times\nPlease try again in: " + delayMinutes
              + " minutes");
    }
    throw new IllegalArgumentException("The " + delayMinutes + " minute timer is not finished");
  }

  /**
   * Checks if the password is correct, if it is, the user will be logged in. If not, the amount of
   * login attempts will increase.
   *
   * @param event       Event to trigger the login.
   * @param currentUser The user retrieved from the user register based on the username.
   * @throws NoSuchAlgorithmException If a hash of the input password cannot be created,
   * @throws InvalidKeySpecException  If a hash of the input password cannot be created,
   */
  private void checkPassword(InputEvent event, User currentUser)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    if (Arrays.equals(currentUser.getHash(),
        createHash(passwordTextField.getText(), currentUser.getSalt()))) {
      try {
        RegisterManager.getInstance().setUserName(userNameTextField.getText());
        confirmLoginSuccessful();
        switchToMainMenuScene(event);
      } catch (Exception e) {
        Alert alert = new Alert(AlertType.ERROR,
            "Critical error while trying to log in, please contact customer service");
        alert.showAndWait();
      }

    } else {
      loginAttempts++;
      int attemptsLeft = 6 - loginAttempts;
      throw new IllegalArgumentException(
          "Wrong password or user name\n" + "You have " + attemptsLeft + " attempts left");
    }
  }

  /**
   * Creates an entering delay timer. When the timer is finished, activeTimer will be set to false,
   *        and the amount of login attempts is reset to 0.
   *
   * @param delayMinutes The amount of minutes that the delay will last.
   */
  private void createEnteringDelay(int delayMinutes) {

    Timer timer = new Timer();
    activeTimer = true;

    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        Platform.runLater(() -> {
          Alert alert = new Alert(AlertType.CONFIRMATION, "You can now try to log in again");
          alert.showAndWait();
          activeTimer = false;
          loginAttempts = 0;
        });
      }
    };

    timer.schedule(timerTask, delayMinutes * 60L * 1000);

  }

  /**
   * Alerts the user that the login is successful
   */
  private void confirmLoginSuccessful() {
    Alert alert = new Alert(AlertType.CONFIRMATION, "Login successful");
    alert.showAndWait();
  }

  /**
   * Creates a hash from an input password and salt.
   *
   * @param password The input password.
   * @param salt     The input salt.
   * @return A hash created from the input password and salt
   * @throws NoSuchAlgorithmException If the algorithm for the creating the secret key is not
   *                                  found.
   * @throws InvalidKeySpecException  If the secret key cannot be created.
   */
  private byte[] createHash(String password, byte[] salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {

    KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    return secretKeyFactory.generateSecret(keySpec).getEncoded();
  }

  /**
   * Initialises the class with the user register and sets the active timer status to false.
   *
   * @param url            The location used to resolve relative paths for the root object, or null
   *                       if the location is not known.
   * @param resourceBundle The resources used to localize the root object, or null if the root
   *                       object was not localized.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    userRegister = RegisterManager.getInstance().getUserRegister();
    activeTimer = false;
  }

  /**
   * Switches the scene to the main menu scene.
   *
   * @param event The event that triggers the method.
   */
  private void switchToMainMenuScene(InputEvent event) {
    ViewManager.switchToScene(event, View.MAIN_MENU);
  }

  /**
   * Switches the scene to the "create user" scene.
   *
   * @param event The event that triggers the method.
   */
  @FXML
  private void switchToCreateUser(InputEvent event) {
    ViewManager.switchToScene(event, View.CREATE_USER);
  }

  /**
   * Handles any input keypress, and activates their respective shortcuts.
   *
   * @param keyEvent The event that triggers the shortcut
   */
  @FXML
  private void handleKeyPressed(KeyEvent keyEvent) {
    switch (keyEvent.getCode()) {
      case ENTER -> onLoginClicked(keyEvent);
      case ESCAPE -> switchToCreateUser(keyEvent);
      default -> {//No default action on purpose
      }
    }
  }
}
