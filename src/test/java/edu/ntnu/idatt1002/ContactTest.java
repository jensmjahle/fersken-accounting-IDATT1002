
package edu.ntnu.idatt1002;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {
  private static final Contact contact = new Contact("name", "email", "street", 1, "12345678", "00000000000", "1234");
  @Nested
  class ConstructorTest {
    @Test
    @DisplayName("Invalid street test")
    public void invalidStreetTest() {
      assertThrows(IllegalArgumentException.class, () -> contact.setStreet(""));
    }
    @Test
    @DisplayName("Invalid street number test")
    public void invalidStreetNumberTest() {
      assertThrows(IllegalArgumentException.class, () -> contact.setStreetNumber(-1));
    }
    @Test
    @DisplayName("Invalid account number test")
    public void invalidAccountNumberTest() {
      assertThrows(IllegalArgumentException.class, () -> contact.setAccountNumber(""));
    }
    @Test
    @DisplayName("Invalid post code test")
    public void invalidPostCodeTest() {
      assertThrows(IllegalArgumentException.class, () -> contact.setPostCode(""));
    }
  }
  @Nested
  class GettersAndSetters {
    @Test
    @DisplayName("Name test")
    public void nameTest() {
      assertEquals("name", contact.getName());
    }
    @Test
    @DisplayName("E-mail tests")
    public void emailTest() {
      assertEquals("email", contact.getEmail());
    }
    @Test
    @DisplayName("Street test")
    public void streetTest() {
      contact.setStreet("test");
      assertEquals("test", contact.getStreet());
    }
    @Test
    @DisplayName("Street number test")
    public void streetNumberTest() {
      contact.setStreetNumber(100);
      assertEquals(100, contact.getStreetNumber());
    }
    @Test
    @DisplayName("Phone number test")
    public void phoneNumberTest() {
      assertEquals("12345678", contact.getPhoneNumber());
    }
    @Test
    @DisplayName("Account number test")
    public void accountNumberTest() {
      contact.setAccountNumber("99999999999");
      assertEquals("99999999999", contact.getAccountNumber());
    }
    @Test
    @DisplayName("Post code test")
    public void postCodeTest() {
      contact.setPostCode("9999");
      assertEquals("9999", contact.getPostCode());
    }
  }
}
