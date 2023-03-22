package edu.ntnu.idatt1002;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

  Sale sale;
  Contact contact;
  Date date;

  @BeforeEach
  @DisplayName("Set up")
  void setUp() {
    date = new Date();
    contact = new Contact("Name", "abc@email.com","Street", 10, "12345678", "12345678910", "1234");
    sale = new Sale(contact, date, "Product", "98765432111", 100);
  }

  @Test
  @DisplayName("Invalid sales amount throws IllegalArgumentException")
  void invalidSalesAmountThrowsIllegalArgumentException(){
    assertThrows(IllegalArgumentException.class, () ->
    {Sale invalidSale = new Sale(contact, date, "Product", "98765432111", -1);
    });

  }

  @Test
  @DisplayName("Invalid account number throws IllegalArgumentException")
  void invalidAccountNumberThrowsIllegalArgumentException(){
    assertThrows(IllegalArgumentException.class, () ->
    {Sale invalidSale = new Sale(contact, date, "Product", "12345", 100);
    });

  }


  @Test
  @DisplayName("Should get customer")
  void shouldGetCustomer() {
    Contact expected = new Contact("Name", "abc@email.com","Street", 10, "12345678", "12345678910", "1234");
    Contact actual = sale.getCustomer();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should get product")
  void shouldGetProduct() {
    String expected = "Product";
    String actual = sale.getProduct();
    assertEquals(expected, actual);

  }

  @Test
  @DisplayName("Should get receiver account")
  void getReceiverAccount() {
    String expected = "98765432111";
    String actual = sale.getReceiverAccount();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should get amount")
  void shouldGetAmount() {
    double expected = 100;
    double actual = sale.getAmount();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should get date")
  void shouldGetDate(){
    Date expected = date;
    Date actual = sale.getDate();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should get ToString")
  void shouldGetToString(){
    String expected = "Sale{customer=Contact{name='Name'," +
        " email='abc@email.com', street='Street', streetNumber=10," +
        " phoneNumber='12345678', accountNumber='12345678910'," +
        " postCode='1234', organizationNumber=''}," +
        " date=" + date.toString() + "," +
        " product='Product', receiverAccount='98765432111', amount=100.0}";
    String actual = sale.toString();
    assertEquals(expected, actual);
  }
}