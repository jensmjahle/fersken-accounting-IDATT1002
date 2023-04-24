package edu.ntnu.idatt1002.registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt1002.storageitems.Contact;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContactRegisterTest {

  ContactRegister contactRegister;
  Contact contact;


  @BeforeEach
  void setUp() {

    contactRegister = new ContactRegister("src/test/resources/ContactTest.txt");
    contact = new Contact("name", "email@email.com", "street", 50, "12345678", "12345678910",
        "1740", "1234");
  }

  @Test
  @DisplayName("Should find contact by name")
  void shouldFindContactByName() {
    Contact expectedContact = new Contact("ExpectedContact", "email@email.com", "street", 50,
        "12345678", "12345678910", "1740", "1234");
    contactRegister.addObject(expectedContact);
    Contact actualContact = contactRegister.findContactFromName("ExpectedContact");

    assertEquals(expectedContact, actualContact);
  }

  @Test
  @DisplayName("Should not find contact by name")
  void shouldNotFindContactByName() {
    contactRegister.addObject(contact);
    Contact foundContact = contactRegister.findContactFromName("NonExistingContact");

    assertNull(foundContact);
  }

  @Test
  @DisplayName("removeObject() removes contact from contact register")
  void ShouldRemoveContact() {
    contactRegister.addObject(contact);
    boolean stateOfRemoval = contactRegister.removeObject(contact);
    assertTrue(stateOfRemoval);

    int expectedSize = 0;
    int actualSize = contactRegister.getObjects().size();
    assertEquals(expectedSize, actualSize);
  }

  @Test
  @DisplayName("removeObject() not able to remove an object that is not in the register")
  void ShouldNotRemoveContact() {
    contactRegister.addObject(contact);

    Contact unequalContact = new Contact("name2", "email@email.com", "street2", 60, "87654321",
        "10987654321", "4070", "1234");
    boolean stateOfRemoval = contactRegister.removeObject(unequalContact);
    assertFalse(stateOfRemoval);

    int expectedSize = 1;
    int actualSize = contactRegister.getObjects().size();
    assertEquals(expectedSize, actualSize);
  }

  @Test
  @DisplayName("getObjects() returns a correct list of contacts in contactRegister")
  void shouldGetContacts() {
    contactRegister.addObject(contact);
    List<Contact> expected = new ArrayList<>();
    expected.add(contact);
    List<Contact> actual = contactRegister.getObjects();

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("addObject() adds an contact to the contactRegister")
  void addsContactToContactRegister() {
    contactRegister.addObject(contact);
    Contact expected = contact;
    Contact actual = contactRegister.getObjects().get(0);
    assertEquals(expected, actual);

    int expectedSize = 1;
    int actualSize = contactRegister.getObjects().size();
    assertEquals(expectedSize, actualSize);
  }

  @Test
  @DisplayName("Not able to create contactRegister if filepath is null")
  void shouldThrowNullPointerExceptionIfFilePathIsNull() {
    assertThrows(NullPointerException.class, () -> new ContactRegister(null));
  }

  @AfterEach
  void tearDown() {
    try {
      String location = "src/test/resources/ContactTest.txt";
      Path path = Paths.get(location);
      Files.deleteIfExists(path);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}