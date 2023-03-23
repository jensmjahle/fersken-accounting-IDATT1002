package edu.ntnu.idatt1002.registers;

import edu.ntnu.idatt1002.Contact;

/**
 * This class represents a contact register that stores object of type "Contact".
 * The class extends from class Register.
 * A contact can be either a supplier or a costumer.
 */
public class ContactRegister extends Register<Contact> {

  /**
   * Constructor that creates an instance of the class.
   *
   * @param fileName file name of the file to store data.
   */
  public ContactRegister(String fileName) {
    super(fileName);
  }

}