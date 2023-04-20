package edu.ntnu.idatt1002;

import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) {

    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    try {

      FXMLLoader loader = new FXMLLoader();

//      RegisterManager.getInstance().setUserName(
//          RegisterManager.getInstance().getUserRegister().findUserByName("jesper").getUserName());
      URL url = new File("src/main/resources/pages/Login.fxml").toURI().toURL();
      loader.setLocation(url);

      BorderPane borderPane = loader.load();

      Scene scene = new Scene(borderPane);
      primaryStage.setTitle("Fersken Regnskap");
      primaryStage.setScene(scene);
      primaryStage.show();
      primaryStage.setResizable(false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}