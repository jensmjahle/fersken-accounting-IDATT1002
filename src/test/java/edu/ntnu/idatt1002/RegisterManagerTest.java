package edu.ntnu.idatt1002;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt1002.registers.BudgetRegister;
import edu.ntnu.idatt1002.registers.ContactRegister;
import edu.ntnu.idatt1002.registers.ExpenseRegister;
import edu.ntnu.idatt1002.registers.SaleRegister;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisterManagerTest {

  RegisterManager registerManager;

  @BeforeEach
  void setUp() {
    registerManager = RegisterManager.getInstance();
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  @DisplayName("getInstance() returns same instance")
  void getInstance() {
    RegisterManager instance1 = RegisterManager.getInstance();
    RegisterManager instance2 = registerManager;
    assertSame(instance1, instance2);

    assertNotNull(instance1);
    assertTrue(instance1 instanceof RegisterManager);

  }

  @Test
  @DisplayName("BudgetRegister singleton instance is returned by getBudgetRegister()")
  void getBudgetRegister() {
    BudgetRegister instance1 = registerManager.getBudgetRegister();
    BudgetRegister instance2 = registerManager.getBudgetRegister();
    assertSame(instance1, instance2);

    assertNotNull(instance1);
    assertTrue(registerManager.getBudgetRegister() instanceof BudgetRegister);
  }


  @Test
  @DisplayName("SaleRegister singleton instance is returned by getSaleRegister()")
  void shouldGetSaleRegister() {
    SaleRegister instance1 = registerManager.getSaleRegister();
    SaleRegister instance2 = registerManager.getSaleRegister();
    assertSame(instance1, instance2);

    assertNotNull(instance1);
    assertTrue(registerManager.getSaleRegister() instanceof SaleRegister);
  }

  @Test
  @DisplayName("ExpenseRegister singleton instance is returned by getExpenseRegister()")
  void shouldGetExpenseRegister() {
    ExpenseRegister instance1 = registerManager.getExpenseRegister();
    ExpenseRegister instance2 = registerManager.getExpenseRegister();

    assertNotNull(instance1);
    assertTrue(registerManager.getExpenseRegister() instanceof ExpenseRegister);
  }

  @Test
  @DisplayName("CustomerRegister singleton instance is returned by getCostumerRegister()")
  void shouldGetCustomerRegister() {
    ContactRegister instance1 = registerManager.getCustomerRegister();
    ContactRegister instance2 = registerManager.getCustomerRegister();

    assertNotNull(instance1);
    assertTrue(registerManager.getCustomerRegister() instanceof ContactRegister);
  }

  @Test
  @DisplayName("SupplierRegister singleton instance is returned by getSupplierRegister()")
  void shouldGetSupplierRegister() {
    ContactRegister instance1 = registerManager.getSupplierRegister();
    ContactRegister instance2 = registerManager.getSupplierRegister();
    assertSame(instance1, instance2);

    assertNotNull(instance1);
    assertTrue(registerManager.getSupplierRegister() instanceof ContactRegister);
  }

  @Test
  @DisplayName("Supplier register is not the same instance as customer register")
  void supplierNotSameInstanceAsCustomer() {
    ContactRegister supplierInstance = registerManager.getSupplierRegister();
    ContactRegister customerInstance = registerManager.getCustomerRegister();
    assertNotSame(supplierInstance, customerInstance);
  }
}