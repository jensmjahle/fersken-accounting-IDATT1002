package edu.ntnu.idatt1002;

import org.junit.jupiter.api.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Budget tests")
public class BudgetTest {
  private static final Budget budget = new Budget("ProjectTest");
  private static final Expense expense = new Expense(100, new Date(),"product");
  private static final Contact contact = new Contact("name", "email", "street", 12, "12345678", "11111111111", "7043");
  private static final Sale sale = new Sale(contact, new Date(), "product", "22222222222", 100);
  @BeforeAll
  public static void setUpBudget() {
    budget.addExpense(expense);
    budget.addSale(sale);
  }
  @Nested
  class ConstructorTest {
    @Test
    @DisplayName("Valid budget")
    public void validBudgetTest() {
      assertDoesNotThrow(() -> new Budget("Project"));
    }
    @Test
    @DisplayName("Invalid budget")
    public void invalidBudgetTest() {
      assertThrows(IllegalArgumentException.class, () -> new Budget(""));
    }
  }
  @Nested
  class getMethodTest {
    @Test
    @DisplayName("Get project test")
    public void getProjectTest() {
      assertEquals("ProjectTest", budget.getProject());
    }
    @Test
    @DisplayName("Get list of expenses test")
    public void getListOfExpensesTest() {
      assertTrue(budget.getListOfExpenses().contains(expense));
    }
    @Test
    @DisplayName("Get list of sales test")
    public void getListOfSalesTest() {
      assertTrue(budget.getListOfSales().contains(sale));
    }
  }
}
