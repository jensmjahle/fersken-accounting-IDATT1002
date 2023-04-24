package edu.ntnu.idatt1002.registers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import edu.ntnu.idatt1002.storageitems.User;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisterManagerTest {

  RegisterManager registerManager;

  @BeforeEach
  void setUp() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
    registerManager = RegisterManager.getInstance();
    User user = new User("TestUser123456789", "testPassword123");
    if (registerManager.getUserRegister().userNameAlreadyExists(user.getUserName())) {
      RegisterManager.getInstance().getUserRegister().addObject(user);
    }
    registerManager.setUserName("TestUser123456789");
  }

  @AfterEach
  void tearDown() throws NoSuchAlgorithmException, InvalidKeySpecException {
    RegisterManager.getInstance().getUserRegister()
        .removeObject(new User("TestUser123456789", "testPassword123"));
  }

  @Test
  @DisplayName("getInstance() returns same instance")
  void shouldGetInstance() {
    RegisterManager instance1 = RegisterManager.getInstance();
    RegisterManager instance2 = registerManager;

    assertSame(instance1, instance2);

    assertNotNull(instance1);

  }

  @Test
  @DisplayName("BudgetRegister singleton instance is returned by getBudgetRegister()")
  void getBudgetRegister() {
    BudgetRegister instance1 = registerManager.getBudgetRegister();
    BudgetRegister instance2 = registerManager.getBudgetRegister();
    assertSame(instance1, instance2);

    assertNotNull(instance1);
    assertNotNull(registerManager.getBudgetRegister());
  }


  @Test
  @DisplayName("SaleRegister singleton instance is returned by getSaleRegister()")
  void shouldGetSaleRegister() {
    SaleRegister instance1 = registerManager.getSaleRegister();
    SaleRegister instance2 = registerManager.getSaleRegister();
    assertSame(instance1, instance2);

    assertNotNull(instance1);
    assertNotNull(registerManager.getSaleRegister());
  }

  @Test
  @DisplayName("ExpenseRegister singleton instance is returned by getExpenseRegister()")
  void shouldGetExpenseRegister() {
    ExpenseRegister instance1 = registerManager.getExpenseRegister();

    assertNotNull(instance1);
    assertNotNull(registerManager.getExpenseRegister());
  }

  @Test
  @DisplayName("CustomerRegister singleton instance is returned by getCostumerRegister()")
  void shouldGetCustomerRegister() {
    ContactRegister instance1 = registerManager.getCustomerRegister();

    assertNotNull(instance1);
    assertNotNull(registerManager.getCustomerRegister());
  }

  @Test
  @DisplayName("SupplierRegister singleton instance is returned by getSupplierRegister()")
  void shouldGetSupplierRegister() {
    ContactRegister instance1 = registerManager.getSupplierRegister();
    ContactRegister instance2 = registerManager.getSupplierRegister();
    assertSame(instance1, instance2);

    assertNotNull(instance1);
    assertNotNull(registerManager.getSupplierRegister());
  }

  @Test
  @DisplayName("Supplier register is not the same instance as customer register")
  void supplierNotSameInstanceAsCustomer() {
    ContactRegister supplierInstance = registerManager.getSupplierRegister();
    ContactRegister customerInstance = registerManager.getCustomerRegister();
    assertNotSame(supplierInstance, customerInstance);
  }
}