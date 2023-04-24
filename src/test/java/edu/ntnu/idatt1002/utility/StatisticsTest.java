package edu.ntnu.idatt1002.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt1002.registers.RegisterManager;
import edu.ntnu.idatt1002.storageitems.Contact;
import edu.ntnu.idatt1002.storageitems.Expense;
import edu.ntnu.idatt1002.storageitems.Sale;
import edu.ntnu.idatt1002.storageitems.User;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Year;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


@SuppressWarnings("deprecation")
public class StatisticsTest {

  RegisterManager registerManager;
  Date date;
  Contact contact;
  Sale sale;
  Expense expense;
  Statistics statistics;

  @BeforeEach
  void setUp() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
    registerManager = RegisterManager.getInstance();
    User user = new User("TestUser123456789", "testPassword123");
    if (registerManager.getUserRegister().userNameAlreadyExists(user.getUserName())) {
      RegisterManager.getInstance().getUserRegister().addObject(user);
    }
    registerManager.setUserName("TestUser123456789");

    date = new Date(123, Calendar.JANUARY, 1);
    contact = new Contact("name", "email@email.com", "street", 12, "12345678", "11111111111",
        "7043", "1234");
    sale = new Sale(contact, date, "product", "22222222222", 100);
    expense = new Expense(contact, 200, date, "product");
    registerManager.getSaleRegister().addObject(sale);
    registerManager.getExpenseRegister().addObject(expense);
    statistics = new Statistics(registerManager);
  }

  @AfterEach
  void tearDown() throws NoSuchAlgorithmException, InvalidKeySpecException {

    registerManager.getSaleRegister().removeObject(sale);
    registerManager.getExpenseRegister().removeObject(expense);
    RegisterManager.getInstance().getUserRegister()
        .removeObject(new User("TestUser123456789", "testPassword123"));
  }


  @Nested
  class Sales {

    @Test
    @DisplayName("Should get sale total for period")
    void shouldGetSaleTotalForPeriod() {
      double expected = 100;
      double actual = statistics.getSaleTotalForTimePeriod(date, new Date(123, Calendar.MARCH, 2));
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get sale total for day")
    void shouldGetSaleTotalForDay() {
      double expected = 100;
      double actual = statistics.getSaleTotalForDay(date);
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get sale total for month")
    void shouldGetSaleTotalForMonth() {
      YearMonth yearMonth = YearMonth.of(date.getYear() + 1900, date.getMonth() + 1);
      double expected = 100;
      double actual = statistics.getSaleTotalForMonth(yearMonth);
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get sale total for year")
    void shouldGetSaleTotalForYear() {
      double expected = 100;
      double actual = statistics.getSaleTotalForYear(Year.of(2023));
      assertEquals(expected, actual);
    }
  }

  @Nested
  class Expenses {

    @Test
    @DisplayName("Should get expense total for period")
    void shouldGetExpenseTotalForPeriod() {
      double expected = 200;
      double actual = statistics.getExpenseTotalForTimePeriod(date, new Date(123, Calendar.MARCH, 2));
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get expense total for day")
    void shouldGetExpenseTotalForDay() {
      double expected = 200;
      double actual = statistics.getExpenseTotalForDay(date);
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get expense total for month")
    void shouldGetExpenseTotalForMonth() {
      YearMonth yearMonth = YearMonth.of(date.getYear() + 1900, date.getMonth() + 1);
      double expected = 200;
      double actual = statistics.getExpenseTotalForMonth(yearMonth);
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get expense total for year")
    void shouldGetExpenseTotalForYear() {
      double expected = 200;
      double actual = statistics.getExpenseTotalForYear(Year.of(2023));
      assertEquals(expected, actual);
    }
  }


  @Nested
  class Difference {

    @Test
    @DisplayName("Should get difference total for period")
    void shouldGetDifferenceTotalForPeriod() {
      double expected = -100;
      double actual = statistics.getDifferenceForTimePeriod(date, new Date(123, Calendar.MARCH, 2));
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get difference total for day")
    void shouldGetDifferenceTotalForDay() {
      double expected = -100;
      double actual = statistics.getDifferenceForDay(date);
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get difference total for month")
    void shouldGetDifferenceTotalForMonth() {
      YearMonth yearMonth = YearMonth.of(date.getYear() + 1900, date.getMonth() + 1);
      double expected = -100;
      double actual = statistics.getDifferenceTotalForMonth(yearMonth);
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get difference total for year")
    void shouldGetDifferenceTotalForYear() {
      double expected = -100;
      double actual = statistics.getDifferenceTotalForYear(Year.of(2023));
      assertEquals(expected, actual);
    }
  }
}
