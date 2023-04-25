package edu.ntnu.idatt1002.viewmanagement;

import edu.ntnu.idatt1002.utility.PathUtility;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

/**
 * Class that represents a ViewManager. Has methods for switching the application to a new scene.
 */
public class ViewManager {

  private static Scene scene;
  private static final Logger LOGGER = Logger.getLogger(ViewManager.class.getName());

  /**
   * Sets the scene to switch to.
   *
   * @param scene The scene that will be switched to.
   */
  public static void setScene(Scene scene) {
    ViewManager.scene = scene;
  }

  /**
   * Switches to the specified scene.
   *
   * @param event Event that triggers the switch of scenes.
   * @param view  The scene that will be switched to
   */
  public static void switchToScene(InputEvent event, View view) {
    try {
      Parent root = FXMLLoader.load(PathUtility.getResourcePath(view.getFileName()));
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      LOGGER.warning("Error while switching scene because: " + e.getMessage());
      Alert alert = new Alert(AlertType.ERROR,
          "Cannot switch to selected page\nPlease contact customer service");
      alert.showAndWait();
    }
  }
}
