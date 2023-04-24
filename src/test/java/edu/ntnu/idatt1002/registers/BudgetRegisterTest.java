package edu.ntnu.idatt1002.registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt1002.storageitems.Budget;
import edu.ntnu.idatt1002.storageitems.Contact;
import edu.ntnu.idatt1002.storageitems.Expense;
import edu.ntnu.idatt1002.storageitems.Sale;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BudgetRegisterTest {

  Budget budget;
  List<Expense> expenses;
  List<Sale> sales;
  Contact contact;
  Date date;
  BudgetRegister budgetRegister;

  @BeforeEach
  void setUp() {
    expenses = new ArrayList<>();
    sales = new ArrayList<>();
    budget = new Budget("Project");
    date = new Date();
    contact = new Contact("name", "email@email.com", "street", 12, "12345678", "11111111111",
        "7043", "1234");
    budgetRegister = new BudgetRegister("src/test/resources/BudgetTest.txt");
  }


  @Test
  @DisplayName("Should remove budget from the file that stores budgets")
  void shouldRemoveBudget() {
    budgetRegister.addObject(budget);
    boolean stateOfRemoval = budgetRegister.removeObject(budget);
    int expectedSize = 0;
    int actualSize = budgetRegister.getObjects().size();
    assertEquals(expectedSize, actualSize);
    assertTrue(stateOfRemoval);
  }

  @Test
  void shouldNotRemoveObject() {
    budgetRegister.addObject(budget);

    Budget unequalBudget = new Budget("Project 2");
    boolean stateOfRemoval = budgetRegister.removeObject(unequalBudget);

    int expectedSize = 1;
    int actualSize = budgetRegister.getObjects().size();
    assertEquals(expectedSize, actualSize);
    assertFalse(stateOfRemoval);

  }

  @Test
  void shouldGetBudgets() {
    budgetRegister.addObject(budget);

    List<Budget> expected = new ArrayList<>();
    expected.add(budget);

    List<Budget> actual = budgetRegister.getObjects();

    assertEquals(expected, actual);
  }

  @Test
  void shouldAddBudget() {
    budgetRegister.addObject(budget);
    Budget expected = budget;
    Budget actual = budgetRegister.getObjects().get(0);
    int expectedSize = 1;
    int actualSize = budgetRegister.getObjects().size();

    assertEquals(expected, actual);
    assertEquals(expectedSize, actualSize);
  }

  @Test
  @DisplayName("Not able to create saleRegister if filepath is null")
  void shouldThrowNullPointerExceptionIfFilePathIsNull() {
    assertThrows(NullPointerException.class, () -> new SaleRegister(null));
  }

  @Test
  @DisplayName("Throws NullPointerException if trying to add a null object")
  void cannotAddInvalidObject() {
    assertThrows(NullPointerException.class, () -> budgetRegister.addObject(null));
  }


  @AfterEach
  void tearDown() {
    try {
      String location = "src/test/resources/BudgetTest.txt";
      Path path = Paths.get(location);
      Files.deleteIfExists(path);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}