package edu.ntnu.idatt1002;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {
  private static Contact contactTest;
  private static Date dateTest;
  Expense expenseTest;

  @BeforeEach
  void setUp() {
    contactTest = new Contact("name", "email", "street", 10,
        "12345678", "11111111111", "1234");
    dateTest = new Date();
    expenseTest = new Expense(contactTest, 500, dateTest);
  }
  @Test
  @DisplayName("Check if getContact() returns correctly")
  void getContact() {
    Contact actual = expenseTest.getContact();
    assertEquals(contactTest, actual);
  }

  @Test
  @DisplayName("Check if getAmount() returns correctly")
  void getAmount() {
    double expected = 500;
    double actual = expenseTest.getAmount();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Check if getDate() returns correctly")
  void getDate() {
    Date actual = expenseTest.getDate();
    assertEquals(dateTest, actual);
  }

  @Test
  @DisplayName("Not able to create an instance without contact if amount is 0")
  void throwsIllegalArgumentExceptionIfAmountIsZeroWithoutContact() {
    assertThrows(IllegalArgumentException.class, () -> {
      Expense expense = new Expense(0, dateTest);
    });
  }

  @Test
  @DisplayName("Not able to create an instance with contact if amount is 0")
  void throwsIllegalArgumentExceptionIfAmountIsZeroWithContact() {
    assertThrows(IllegalArgumentException.class, () -> {
      Expense expense = new Expense(contactTest,0, dateTest);
    });
  }

  @Test
  @DisplayName("Not able to create an instance with contact if amount is negative")
  void throwsIllegalArgumentExceptionIfAmountIsNegativeWithContact() {
    assertThrows(IllegalArgumentException.class, () -> {
      Expense expense = new Expense(contactTest,-5, dateTest);
    });
  }

  @Test
  @DisplayName("Not able to create an instance without contact if amount is negative")
  void throwsIllegalArgumentExceptionIfAmountIsNegativeWithoutContact() {
    assertThrows(IllegalArgumentException.class, () -> {
      Expense expense = new Expense(-5, dateTest);
    });
  }

  @Test
  @DisplayName("Not able to create an instance if contact is null")
  void throwsNullPointerExceptionIfContactIsNull() {
    assertThrows(NullPointerException.class, () -> {
      Expense expense = new Expense(null, 500, dateTest);
    });
  }

  @Test
  @DisplayName("Not able to create an instance with contact if date is null")
  void throwsNullPointerExceptionIfDateIsNullWithContact() {
    assertThrows(NullPointerException.class, () -> {
      Expense expense = new Expense(contactTest, 500, null);
    });
  }

  @Test
  @DisplayName("Not able to create an instance without contact if date is null")
  void throwsNullPointerExceptionIfDateIsNullWithoutContact() {
    assertThrows(NullPointerException.class, () -> {
      Expense expense = new Expense(500, null);
    });
  }
}