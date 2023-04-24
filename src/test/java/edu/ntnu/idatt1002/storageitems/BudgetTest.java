package edu.ntnu.idatt1002.storageitems;

import edu.ntnu.idatt1002.storageitems.Budget;
import edu.ntnu.idatt1002.storageitems.Contact;
import edu.ntnu.idatt1002.storageitems.Expense;
import edu.ntnu.idatt1002.storageitems.Sale;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Budget tests")
public class BudgetTest {
   Budget budget;
   Expense expense;
   Contact contact;
   Sale sale;

  @BeforeEach
  void setUpBudget() {
    budget = new Budget("ProjectTest");
    expense = new Expense(100, new Date(),"product");
    contact = new Contact("name", "email@email.com", "street",
        12, "12345678", "11111111111", "7043", "1234");
    sale = new Sale(contact, new Date(), "product", "22222222222", 100);
  }

  @Nested
  class ConstructorTest {

    @Test
    @DisplayName("Create valid budget")
    void createValidBudget() {
      assertDoesNotThrow(() -> new Budget("Project"));
    }

    @Test
    @DisplayName("Create invalid budget")
    void createInvalidBudget() {
      assertThrows(IllegalArgumentException.class, () -> new Budget(""));
    }
  }

  @Nested
  class Getters {

    @Test
    @DisplayName("Should get project")
    void getProjectTest() {
      String expected = "ProjectTest";
      String actual = budget.getProjectName();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get list of expenses test")
    void getListOfExpensesTest() {
      budget.addExpense(expense);
      boolean listContainsExpense = budget.getListOfExpenses().contains(expense);
      assertTrue(listContainsExpense);
    }

    @Test
    @DisplayName("Get list of sales test")
    void getListOfSalesTest() {
      budget.addSale(sale);
      boolean listContainsSale = budget.getListOfSales().contains(sale);
      assertTrue(listContainsSale);
    }
  }

  @Nested
  class ListHandling {

    @Test
    @DisplayName("Add list of expenses")
    void addListOfExpenses() {
      List<Expense> expenseList = new ArrayList<>();
      expenseList.add(new Expense(100, new Date(), "product 1"));
      expenseList.add(new Expense(100, new Date(), "product 1"));

      budget.addListOfExpenses(expenseList);

      int expectedSize = 2;
      int actualSize = budget.getListOfExpenses().size();

      assertNotNull(budget.getListOfExpenses().get(0));
      assertEquals(expectedSize, actualSize);
    }

    @Test
    @DisplayName("Add list of sales")
    void addListOfSales(){
      List<Sale> listOfSales = new ArrayList<>();
      listOfSales.add(sale);
      budget.addListOfSales(listOfSales);
      Sale expected = sale;
      Sale actual = budget.getListOfSales().get(0);

      assertEquals(expected, actual);
    }
  }
}
