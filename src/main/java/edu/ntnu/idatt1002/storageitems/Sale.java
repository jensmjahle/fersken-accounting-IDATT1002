package edu.ntnu.idatt1002.storageitems;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * The class represents a sale for the user.
 */
public class Sale implements Serializable {

  private final Contact contact;
  private final String customer;
  private final Date date;
  private final String product;
  private final String receiverAccount;
  private final double amount;

  /**
   * Allocates a Sale object and initialises it.
   *
   * @param contact         The contact that bought a service from the user.
   * @param date            The date of purchase.
   * @param product         The product that has been sold to the customer.
   * @param receiverAccount The account that should receive the money from the sale.
   * @param amount          The amount of money the customer is set to pay from the sale.
   * @throws NullPointerException     If any of the input values are null.
   * @throws IllegalArgumentException If any of the input variables are invalid.
   */
  public Sale(Contact contact, Date date, String product, String receiverAccount, double amount)
      throws NullPointerException, IllegalArgumentException {
    if (amount <= 0) {
      throw new IllegalArgumentException("A sale's amount cannot be 0 or less");
    }
    if (receiverAccount.length() != 11) {
      throw new IllegalArgumentException("The account number has to be 11 numbers long");
    }
    if (!receiverAccount.matches("[0-9]+")) {
      throw new IllegalArgumentException("The account number can only consist of digits");
    }

    this.contact = Objects.requireNonNull(contact, "Contact cannot be null");
    this.date = Objects.requireNonNull(date, "Date cannot be null");
    this.product = Objects.requireNonNull(product, "Product cannot be null");
    this.receiverAccount = Objects.requireNonNull(receiverAccount, "Account number cannot be null");
    this.amount = amount;
    this.customer = null;

  }

  /**
   * Allocates a Sale object and initialises it.
   *
   * @param customer        The customer that bought a service from the user.
   * @param date            The date of purchase.
   * @param product         The product that has been sold to the customer.
   * @param receiverAccount The account that should receive the money from the sale.
   * @param amount          The amount of money the customer is set to pay from the sale.
   * @throws NullPointerException     If any of the input values are null.
   * @throws IllegalArgumentException If any of the input variables are invalid.
   */
  public Sale(String customer, Date date, String product, String receiverAccount, double amount)
      throws NullPointerException, IllegalArgumentException {
    if (amount <= 0) {
      throw new IllegalArgumentException("A sale's amount cannot be 0 or less");
    }
    if (receiverAccount.length() != 11) {
      throw new IllegalArgumentException("The account number has to be 11 numbers long");
    }
    if (!receiverAccount.matches("[0-9]+")) {
      throw new IllegalArgumentException("The account number can only consist of digits");
    }

    this.customer = Objects.requireNonNull(customer, "Customer cannot be null");
    this.date = Objects.requireNonNull(date, "Date cannot be null");
    this.product = Objects.requireNonNull(product, "Product cannot be null");
    this.receiverAccount = Objects.requireNonNull(receiverAccount, "Account number cannot be null");
    this.amount = amount;
    this.contact = null;
  }

  /**
   * Allocates a Sale object and initialises it.
   *
   * @param amount The amount of money the customer is set to pay from the sale.
   * @throws NullPointerException     If any of the input values are null.
   * @throws IllegalArgumentException If any of the input variables are invalid.
   */
  public Sale(String product, double amount) throws NullPointerException, IllegalArgumentException {
    if (amount <= 0) {
      throw new IllegalArgumentException("A sale's amount cannot be 0 or less");
    }
    this.product = Objects.requireNonNull(product, "Product cannot be null");
    this.amount = amount;
    this.contact = null;
    this.receiverAccount = null;
    this.customer = null;
    this.date = null;
  }

  /**
   * Gets the customer of the sale.
   *
   * @return The customer of the sale.
   */
  public Contact getContact() {
    return contact;
  }

  /**
   * Gets the customer of the sale.
   *
   * @return The customer of the sale as a String.
   */
  public String getCustomer() {
    return contact.getName();
  }

  /**
   * Finds out the name of the contact if the sale has a contact, if not, it will return the
   * customer.
   *
   * @return the name of the contact if the sale has a contact, if not, it will return the customer.
   */
  public String getCustomerName() {
    if (contact != null) {
      return contact.getName();
    } else {
      return customer;
    }
  }

  /**
   * Gets the sales date.
   *
   * @return The sales date.
   */
  public Date getDate() {
    return date;
  }

  /**
   * Gets the description of the product that has been sold.
   *
   * @return The name of the product that has been sold.
   */
  public String getProduct() {
    return product;
  }

  /**
   * Gets the account number of the account that will receive the money.
   *
   * @return The account number that receives the money.
   */
  public String getReceiverAccount() {
    return receiverAccount;
  }

  /**
   * Gets the amount of money the customer has to pay for the product.
   *
   * @return The amount of money the customer has to pay for the product.
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Returns a string containing information about the sale.
   *
   * @return a string containing information about the sale.
   */
  @Override
  public String toString() {
    return "Sale{" + "customer=" + contact + ", date=" + date + ", product='" + product + '\''
        + ", receiverAccount='" + receiverAccount + '\'' + ", amount=" + amount + '}';
  }
}
