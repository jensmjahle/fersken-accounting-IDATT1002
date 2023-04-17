package edu.ntnu.idatt1002;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Represents an expense in NOK. For example buying new equipment or buying payable services can be
 * expenses.
 */
public class Expense implements Serializable {

  private Contact contact;
  private double amount;
  private Date date;
  private String product;


  /**
   * Creates an expense with a contact(class Contact). This constructor is relevant for expenses
   * including the need for a contact. Example: buying lager equipment with invoice.
   *
   * @param contact information about the contact. As a Contact
   * @param amount  of money in NOK. As a double
   * @param date    of the expense. As a Date
   */
  public Expense(Contact contact, double amount, Date date, String product)
      throws NullPointerException, IllegalArgumentException {
    if (product.isEmpty()){
      throw new IllegalArgumentException("Product cannot be empty");
    }
    if (amount <= 0) {
      throw new IllegalArgumentException("Expense amount can not be 0 or a negative number");
    }
    this.contact = Objects.requireNonNull(contact, "Contact is missing");
    this.date = Objects.requireNonNull(date, "Date is missing");
    this.product = product;
    this.amount = amount;

  }

  public Expense(double amount, String product) throws IllegalArgumentException, NullPointerException {
    if (amount <= 0) {
      throw new IllegalArgumentException("Expense amount can not be 0 or a negative number");
    }

    this.product = Objects.requireNonNull(product, "Product cannot be null");
    this.amount = amount;
    this.date = null;
  }

  /**
   * Creates an expense without a contact. This constructor is relevant for expenses that does not
   * need a contact. Example: small purchases in convenient store.
   *
   * @param amount of money in NOK. As a double
   * @param date   of the expense. As a Date
   */
  public Expense(double amount, Date date, String product) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Expense amount can not be 0 or a negative number");
    }
    this.date = Objects.requireNonNull(date, "Date is missing");
    this.amount = amount;
    this.product = product;
  }

  /**
   * Gets the contact of the expense.
   *
   * @return The contact. As a Contact
   */
  public Contact getContact() {
    return contact;
  }


  public String getSupplierName() {
    if (contact != null) {
      return contact.getName();
    } else {
      return "";
    }
  }

  /**
   * Gets the amount of money of the expense in NOK.
   *
   * @return The amount of money in NOK. As a double
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Gets the date of the expense.
   *
   * @return The date. As a Date
   */
  public Date getDate() {
    return date;
  }

  public String getProduct() {
    return product;
  }

  @Override
  public String toString() {
    return "Expense{" + "contact=" + contact + ", amount=" + amount + ", date=" + date
        + ", product='" + product + '\'' + '}';
  }
}
