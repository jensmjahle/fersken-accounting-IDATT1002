package edu.ntnu.idatt1002.utility;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PathUtilityTest {

  @Test
  @DisplayName("Should get the resource path of a test file location")
  void shouldGetResourcePath() throws MalformedURLException {

    String expectedEnd = "/src/main/resources/pages/TestLocation.fxml";
    String actual = PathUtility.getResourcePath("TestLocation").getPath();
    boolean stringContainsExpectedEnd = actual.endsWith(expectedEnd);
    assertTrue(stringContainsExpectedEnd);
  }

}
