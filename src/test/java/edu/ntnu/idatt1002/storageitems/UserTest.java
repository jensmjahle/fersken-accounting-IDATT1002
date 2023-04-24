package edu.ntnu.idatt1002.storageitems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt1002.storageitems.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

  private static User user;

  @BeforeEach
  void setUp() throws NoSuchAlgorithmException, InvalidKeySpecException {
    user = new User("TestUser", "TestPassword1");
  }

  @Test
  void invalidPasswordLengthThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> new User("TestUser2", "1234567"));
  }

  @Test
  void noDigitsInPasswordThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> new User("TestUser2", "testPassword"));
  }

  @Test
  void usernameSameAsPasswordThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> new User("TestUser2", "TestUser2"));
  }

  @Test
  void shouldGetUsername() {
    String expected = "TestUser";
    String actual = user.getUserName();
    assertEquals(expected, actual);
  }

  @Test
  void hasCreatedHash(){
    assertNotNull(user.getHash());
  }

  @Test
  void shouldSetNewPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
    User user = new User("User", "password1");
    byte[] password1 = user.getHash();
    user.setNewPassword("password2");
    byte[] password2 = user.getHash();
    assertFalse(Arrays.equals(password1, password2));
  }

  @Test
  void shouldNotSetNewPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
    User user = new User("User", "password1");
    assertThrows(IllegalArgumentException.class, () ->  user.setNewPassword("password1"));

  }

  @Test
  void shouldGetToString(){
    String expected = "Username: " + user.getUserName() +
        "\nsalt:" + Arrays.toString(user.getSalt()) +
        "\nhash: " + Arrays.toString(user.getHash());
    String actual = user.toString();
    assertEquals(expected, actual);
  }


}
