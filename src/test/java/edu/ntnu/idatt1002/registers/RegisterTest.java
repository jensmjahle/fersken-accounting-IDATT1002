package edu.ntnu.idatt1002.registers;

import edu.ntnu.idatt1002.Sale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegisterTest {
  File file;
  Register<String> register;
  String testString;

  @BeforeEach
  @DisplayName("Set up")
  void setUp(){
    file = new File("RegisterTest");
    register = new Register<>(file.getPath());
    testString = "Test String";
  }



  @Test
  @DisplayName("Should remove object")
  void shouldRemoveObject() {
    register.addObject(testString);
    boolean stateOfRemoval = register.removeObject(testString);
    int expectedSize = 0;
    int actualSize = register.getObjects().size();

    assertEquals(expectedSize, actualSize);
    assertTrue(stateOfRemoval);
  }

  @Test
  @DisplayName("Should not remove object")
  void shouldNotRemoveObject() {
    register.addObject(testString);
    String unequalString = "Unequal String";

    boolean stateOfRemoval = register.removeObject(unequalString);
    int expectedSize = 1;
    int actualSize = register.getObjects().size();

    assertFalse(stateOfRemoval);
    assertEquals(expectedSize, actualSize);
  }




  @Test
  @DisplayName("Should get objects")
  void shoulGetObjects() {
    register.addObject(testString);
    List<String> expected = new ArrayList<>();
    expected.add(testString);
    List<String> actual = register.getObjects();

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should add objects")
  void shouldAddObject() {
    register.addObject(testString);
    String expected = testString;
    String actual = register.getObjects().get(0);
    int expectedSize = 1;
    int actualSize = register.getObjects().size();

    assertEquals(expected, actual);
    assertEquals(expectedSize, actualSize);
  }

  @Test
  void shouldThrowNullPointerExceptionIfFilePathIsNull() {
    assertThrows(NullPointerException.class, () -> {
      Register registerTestException = new SaleRegister(null);
    });
  }

  @AfterEach
  void tearDown() {
    try {
      String location = "src/main/resources/registers/" + file.toPath() + ".txt";
      Path path = Paths.get(location);
      Files.deleteIfExists(path);
    } catch (Exception e){
      e.printStackTrace();
    }

  }
}