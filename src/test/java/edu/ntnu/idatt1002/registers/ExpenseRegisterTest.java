package edu.ntnu.idatt1002.registers;

import edu.ntnu.idatt1002.Expense;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import edu.ntnu.idatt1002.Contact;
import java.io.File;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseRegisterTest {
  ExpenseRegister expenseRegister;
  Contact contact;
  Expense expense;
  Date date;
  File file;


  @BeforeEach
  void setUp() {
    file = new File("ExpensesTest");

    expenseRegister = new ExpenseRegister(file.getPath());
    date = new Date();
    contact = new Contact("name", "email@email.com", "street", 12, "12345678", "11111111111", "7043", "1234");
    expense = new Expense(contact, 150, date, "product");
  }

  @Test
  @DisplayName("removeObject() Removes expense from expense register")
  void ShouldRemoveExpense() {
    expenseRegister.addObject(expense);
    boolean stateOfRemoval = expenseRegister.removeObject(expense);


    int expectedSize = 0;
    int actualSize = expenseRegister.getObjects().size();
    assertEquals(expectedSize, actualSize);
    assertTrue(stateOfRemoval);
  }

  @Test
  void ShouldNotRemoveExpense() {
    expenseRegister.addObject(expense);

    Expense unequalExpense = new Expense(contact, 205, new Date(), "22222222222");
    boolean stateOfRemoval = expenseRegister.removeObject(unequalExpense);
    assertFalse(stateOfRemoval);

    int expectedSize = 1;
    int actualSize = expenseRegister.getObjects().size();
    assertEquals(expectedSize, actualSize);
  }

  @Test
  void shouldGetExpenses() {
    expenseRegister.addObject(expense);

    List<Expense> expected = new ArrayList<>();
    expected.add(expense);

    List<Expense> actual = expenseRegister.getObjects();

    assertEquals(expected, actual);
  }
  @Test
  @DisplayName("Add a expense object to the expense register")
  void addsASaleToSaleRegister() {
    expenseRegister.addObject(expense);
    Expense expected = expense;
    Expense actual = expenseRegister.getObjects().get(0);
    int expectedSize = 1;
    int actualSize = expenseRegister.getObjects().size();
    assertEquals(expected, actual);
    assertEquals(expectedSize, actualSize);
  }

  @Test
  @DisplayName("Not able to create expenseRegister if filepath is null")
  void shouldThrowNullPointerExceptionIfFilePathIsNull() {
    assertThrows(NullPointerException.class, () -> {
      ExpenseRegister expenseRegister1 = new ExpenseRegister(null);
    });
  }

  @AfterEach
  void tearDown() {
    try {
      String location = "src/main/resources/registers/" + file.toPath() + ".txt";
      Path path = Paths.get(location);
      Files.deleteIfExists(path);
    } catch (Exception e){
      e.printStackTrace();
    }

  }
}