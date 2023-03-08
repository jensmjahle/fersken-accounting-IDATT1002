package edu.ntnu.idatt1002;

/**
 * The class represents a contact of the user. Can be either a customer, supplier, or both.
 */
public class Contact {
  private final String name;
  private final String email;
  private String street;
  private String streetNumber;
  private final int phoneNumber;
  private int accountNumber;
  private int postCode;

  /**
   * Creates an instance of the contact object.
   *
   * @param name The name of the contact.
   * @param email The e-mail of the contact.
   * @param street The street name of the address of the contact.
   * @param streetNumber The street number of the address of the contact.
   * @param phoneNumber The phone number that can be used to contact the contact.
   * @param accountNumber The account number of the contact.
   * @param postCode The postCode of the address of the contact
   */
  public Contact(String name, String email, String country,
                 String street, String streetNumber, int phoneNumber,
                 int accountNumber, int postCode) {
    this.name = name;
    this.email = email;
    this.street = street;
    this.streetNumber = streetNumber;
    this.phoneNumber = phoneNumber;
    this.accountNumber = accountNumber;
    this.postCode = postCode;
  }

  /**
   * Gets the name of the contact.
   *
   * @return The name of the contact.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the e-mail of the contact.
   *
   * @return The name of the contact.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Gets the street name of the address contact.
   *
   * @return The street name of the contact.
   */
  public String getStreet() {
    return street;
  }

  /**
   * Gets the street number of the address of the contact.
   *
   * @return The street number of the address of the contact.
   */
  public String getStreetNumber() {
    return streetNumber;
  }

  /**
   * Gets the phone number of the contact and returns it.
   *
   * @return The phone number of the contact.
   */
  public int getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Gets the account number of the contact.
   *
   * @return The account number of the contact.
   */
  public int getAccountNumber() {
    return accountNumber;
  }

  /**
   * Gets the post code of the contact's address.
   *
   * @return The post contact's post code.
   */
  public int getPostCode() {
    return postCode;
  }

  /**
   * Sets a new street name for the contact.
   *
   * @param street The new street name for the contact's location.
   */
  public void setStreet(String street) {
    this.street = street;
  }

  /**
   * Sets a new street number for the contact.
   *
   * @param streetNumber The new street number of the contact.
   */
  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
  }

  /**
   * Sets a new account number for the contact.
   *
   * @param accountNumber The contact
   */
  public void setAccountNumber(int accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * Sets a new post code for the contact.
   *
   * @param postCode The new post code for the contact.
   */
  public void setPostCode(int postCode) {
    this.postCode = postCode;
  }
}
