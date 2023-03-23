package edu.ntnu.idatt1002;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class PathUtility {

  private PathUtility(){
    throw new UnsupportedOperationException("Cannot create path utility object");
  }
  public static URL getResourcePath(String location) throws MalformedURLException {
    String partOfPath = "src/main/resources/pages/" + location + ".fxml";
    return new File(partOfPath).toURI().toURL();
  }

}
