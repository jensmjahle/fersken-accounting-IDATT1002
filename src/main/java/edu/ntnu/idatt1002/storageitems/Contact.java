package edu.ntnu.idatt1002.storageitems;


import java.io.Serializable;

/**
 * The class represents a contact of the user. Can be either a customer, supplier, or both.
 */
public class Contact implements Serializable {

  private final String name;
  private final String email;
  private final String phoneNumber;
  private final String organizationNumber;
  private String street;
  private int streetNumber;
  private String accountNumber;
  private String postCode;


  /**
   * Creates an instance of the Contact object with organization number.
   *
   * @param name               The name of the contact.
   * @param email              The e-mail of the contact.
   * @param street             The street name of the address of the contact.
   * @param streetNumber       The street number of the address of the contact. Should not be
   *                           negative or equal to zero.
   * @param phoneNumber        The phone number that can be used to contact the contact. Should be 8
   *                           digits long.
   * @param accountNumber      The account number of the contact. Should be 11 digits long.
   * @param postCode           The postCode of the address of the contact. Should be 4 digits long.
   * @param organizationNumber The organization number for the contact.
   * @throws IllegalArgumentException if the name, email, phone number, account number, post code or
   *                                  organization number are in the wrong format.
   */
  public Contact(String name, String email, String street, int streetNumber, String phoneNumber,
                 String accountNumber, String postCode, String organizationNumber)
      throws IllegalArgumentException {

    if (name.isEmpty() || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be empty");
    }
    if (email.isEmpty() || email.isBlank()) {
      throw new IllegalArgumentException("E-mail cannot be empty");
    }
    if (phoneNumber.length() != 8) {
      throw new IllegalArgumentException("Phone number has to be 8 digits");
    }
    if (!phoneNumber.matches("[0-9]+")) {
      throw new IllegalArgumentException("The phone number can only consist of digits");
    }
    if (!organizationNumber.matches("[0-9]+")) {
      throw new IllegalArgumentException("The organization number can only consist of digits");
    }
    if (!accountNumber.matches("[0-9]+")) {
      throw new IllegalArgumentException("The account number can only consist of digits");
    }
    if (!postCode.matches("[0-9]+")) {
      throw new IllegalArgumentException("The post code can only consist of digits");
    }
    if (!email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
      throw new IllegalArgumentException("Email should be on the format: username@domain.com");
    }

    setStreet(street);
    setStreetNumber(streetNumber);
    setAccountNumber(accountNumber);
    setPostCode(postCode);
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.organizationNumber = organizationNumber;
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
   * Sets a new street name for the contact.
   *
   * @param street The new street name for the contact's location.
   */
  public void setStreet(String street) {
    if (street.isEmpty() || street.isBlank()) {
      throw new IllegalArgumentException("Street cannot be blank");
    }
    this.street = street;
  }

  /**
   * Gets the street number of the address of the contact.
   *
   * @return The street number of the address of the contact.
   */
  public int getStreetNumber() {
    return streetNumber;
  }

  /**
   * Sets a new street number for the contact.
   *
   * @param streetNumber The new street number of the contact.
   */
  public void setStreetNumber(int streetNumber) {
    if (streetNumber <= 0) {
      throw new IllegalArgumentException("Street number cannot be negative or zero");
    }
    this.streetNumber = streetNumber;
  }

  /**
   * Gets the phone number of the contact and returns it.
   *
   * @return The phone number of the contact.
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Gets the organization number of the contact.
   *
   * @return The organization number of the contact.
   */
  public String getOrganizationNumber() {
    return organizationNumber;
  }

  /**
   * Gets the account number of the contact.
   *
   * @return The account number of the contact.
   */
  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * Sets a new account number for the contact.
   *
   * @param accountNumber The contact
   */
  public void setAccountNumber(String accountNumber) {
    if (accountNumber.length() != 11) {
      throw new IllegalArgumentException("Account number has to be 11 digits");
    }
    this.accountNumber = accountNumber;
  }

  /**
   * Gets the post code of the contact's address.
   *
   * @return The post contact's post code.
   */
  public String getPostCode() {
    return postCode;
  }

  /**
   * Sets a new post code for the contact.
   *
   * @param postCode The new post code for the contact.
   */
  public void setPostCode(String postCode) {
    if (postCode.length() != 4) {
      throw new IllegalArgumentException("Post code has to be 4 digits");
    }
    this.postCode = postCode;
  }

  /**
   * Checks if this object is equal to a different object.
   *
   * @param o The object to compare this object to.
   * @return true if the objects are the same, false if not.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Contact contact = (Contact) o;

    if (getStreetNumber() != contact.getStreetNumber()) {
      return false;
    }
    if (!getName().equals(contact.getName())) {
      return false;
    }
    if (!getEmail().equals(contact.getEmail())) {
      return false;
    }
    if (!getPhoneNumber().equals(contact.getPhoneNumber())) {
      return false;
    }
    if (!getOrganizationNumber().equals(contact.getOrganizationNumber())) {
      return false;
    }
    if (!getStreet().equals(contact.getStreet())) {
      return false;
    }
    if (!getAccountNumber().equals(contact.getAccountNumber())) {
      return false;
    }
    return getPostCode().equals(contact.getPostCode());
  }

  /**
   * Creates a hashcode for the object.
   *
   * @return The hashcode of the object.
   */
  @Override
  public int hashCode() {
    int result = getName().hashCode();
    result = 31 * result + getEmail().hashCode();
    result = 31 * result + getStreet().hashCode();
    result = 31 * result + getStreetNumber();
    result = 31 * result + getPhoneNumber().hashCode();
    result = 31 * result + getAccountNumber().hashCode();
    result = 31 * result + getPostCode().hashCode();
    result = 31 * result + getOrganizationNumber().hashCode();
    return result;
  }

  /**
   * Creates a String of the contact with all its data.
   *
   * @return A string of information about the contact.
   */
  @Override
  public String toString() {
    return "Contact{" + "name='" + name + '\'' + ", email='" + email + '\'' + ", street='" + street
        + '\'' + ", streetNumber=" + streetNumber + ", phoneNumber='" + phoneNumber + '\''
        + ", accountNumber='" + accountNumber + '\'' + ", postCode='" + postCode + '\''
        + ", organizationNumber='" + organizationNumber + '\'' + '}';
  }
}
