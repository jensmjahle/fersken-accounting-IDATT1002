package edu.ntnu.idatt1002;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

  Sale sale;
  Contact contact;

  @BeforeEach
  void setUp() {
    contact = new Contact("Name", "abc@email.com","Street", "10", 12345678, "12345678910", 1234);
    sale = new Sale(contact, new Date(), "Product", "98765432111", 100);
  }

  @Test
  void getCustomer() {
    Contact expected = new Contact("Name", "abc@email.com","Street", "10", 12345678, "12345678910", 1234);
    Contact actual = sale.getCustomer();
    assertEquals(expected, actual);
  }

  @Test
  void getDate() {

  }

  @Test
  void getProduct() {
    String expected = "Product";
    String actual = sale.getProduct();
    assertEquals(expected, actual);

  }

  @Test
  void getReceiverAccount() {
    String expected = "98765432111";
    String actual = sale.getReceiverAccount();
    assertEquals(expected, actual);
  }

  @Test
  void getAmount() {
    double expected = 100;
    double actual = sale.getAmount();
    assertEquals(expected, actual);
  }
}