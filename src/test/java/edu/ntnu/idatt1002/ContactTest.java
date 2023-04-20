package edu.ntnu.idatt1002;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

  Contact contact;
  Contact contactWithOrganizationNumber;

  @BeforeEach
  void setUp(){
    contact = new Contact("name", "email@email.com", "street", 1,
        "12345678", "00000000000", "1234", "1234");
    contactWithOrganizationNumber = new Contact("name", "email@email.com", "street", 1,
        "12345678", "00000000000", "1234", "12345");
  }

  @Nested
  class ConstructorTest {

    @Test
    @DisplayName("Create contact without organization number")
    void createContactWithoutOrganizationNumber(){
      assertDoesNotThrow(() -> {Contact contact = new Contact("name", "email@email.com", "street", 1,
          "12345678", "00000000000", "1234", "1234");});
    }

    @Test
    @DisplayName("Create contact with organization number")
    void createContactWithOrganizationNumber(){
      assertDoesNotThrow(() -> {Contact contact = new Contact("name", "email@email.com", "street", 1,
          "12345678", "00000000000", "1234", "123");});
    }


    @Test
    @DisplayName("Empty name throws IllegalArgumentException")
    void emptyNameThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> new Contact("", "email@email.com",
          "street", 1, "12345678",
          "00000000000", "1234", "1234"));
    }

    @Test
    @DisplayName("Empty eMail throws IllegalArgumentException")
    void emptyEMailThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> new Contact("name", "",
          "street", 1, "12345678",
          "00000000000", "1234", "1234"));
    }

    @Test
    @DisplayName("Empty phone number throws IllegalArgumentException")
    void emptyPhoneNumberThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> new Contact("name", "email@email.com",
          "street", 1, "",
          "00000000000", "1234", "1234"));
    }

    @Test
    @DisplayName("Invalid street throws IllegalArgumentException")
    void invalidStreetTest() {
      assertThrows(IllegalArgumentException.class, () -> contact.setStreet(""));
    }

    @Test
    @DisplayName("Invalid street number throws IllegalArgumentException")
    void invalidStreetNumberTest() {
      assertThrows(IllegalArgumentException.class, () -> contact.setStreetNumber(-1));
    }

    @Test
    @DisplayName("Invalid account number Throws IllegalArgumentException")
    void invalidAccountNumberTest() {
      assertThrows(IllegalArgumentException.class, () -> contact.setAccountNumber(""));
    }

    @Test
    @DisplayName("Invalid post code Throws IllegalArgumentException")
    void invalidPostCodeTest() {
      assertThrows(IllegalArgumentException.class, () -> contact.setPostCode(""));
    }
  }

  @Nested
  class Getters {

    @Test
    @DisplayName("Should get name")
    void shouldGetName() {
      String expected = "name";
      String actual = contact.getName();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get e-mail")
    void shouldGetEmail() {
      String expected = "email@email.com";
      String actual = contact.getEmail();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get street")
    void shouldGetStreet() {
      String expected = "street";
      String actual = contact.getStreet();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get street number")
    void shouldGetStreetNumber() {
      int expected = 1;
      int actual = contact.getStreetNumber();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get phone number")
    void shouldGetPhoneNumber() {
      String expected = "12345678";
      String actual = contact.getPhoneNumber();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get account number")
    void shouldGetAccountNumber() {
      String expected = "00000000000";
      String actual = contact.getAccountNumber();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get post code")
    void shouldGetPostCode() {
      String expected = "1234";
      String actual = contact.getPostCode();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get organization number")
    void shouldGetOrganizationNumber(){
      String expected = "12345";
      String actual = contactWithOrganizationNumber.getOrganizationNumber();
      assertEquals(expected, actual);
    }
  }

  @Nested
  class Equals {

    @Test
    @DisplayName("Contacts should be equal")
    void contactsShouldBeEqual() {
      Contact contact1 = new Contact("name", "email@email.com", "street", 1,
          "12345678", "00000000000", "1234", "1234");
      Contact contact2 = new Contact("name", "email@email.com", "street", 1,
          "12345678", "00000000000", "1234", "1234");

      assertEquals(contact1, contact2);
    }

    @Test
    @DisplayName("Contacts should not be equal")
    void contactsShouldNotBeEqual(){
      Contact contact1 = new Contact("name", "email@email.com", "street", 1,
          "12345678", "00000000000", "1234", "1234");
      Contact contact2 = new Contact("unequal name", "email@otheremail.com", "a", 2,
          "12345678", "00000000000", "1234", "1234");

      assertNotEquals(contact1, contact2);
    }
  }
}
