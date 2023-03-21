package edu.ntnu.idatt1002;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("fxml/MainMenu.fxml"));

    Scene scene = new Scene(root, 900, 600);

    stage.setTitle("FXML Welcome");
    stage.setScene(scene);
    stage.show();
  }
}
