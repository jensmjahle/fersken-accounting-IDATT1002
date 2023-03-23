package edu.ntnu.idatt1002.registers;

import edu.ntnu.idatt1002.Budget;
import edu.ntnu.idatt1002.Contact;
import edu.ntnu.idatt1002.Sale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SaleRegisterTest {
  SaleRegister saleRegister;
  Contact contact;
  Sale sale;
  Date date;
  File file;


  @BeforeEach
  void setUp() {
    file = new File("SalesTest");

    saleRegister = new SaleRegister("SalesTest");
    date = new Date();
    contact = new Contact("name", "email", "street", 12, "12345678", "11111111111", "7043");
    sale = new Sale(contact, date, "product", "22222222222", 100);
  }



  @Test
  @DisplayName("removeObject() Removes sale from sale register")
  void ShouldRemoveSale() {
    saleRegister.addObject(sale);
    boolean stateOfRemoval = saleRegister.removeObject(sale);


    int expectedSize = 0;
    int actualSize = saleRegister.getObjects().size();
    assertEquals(expectedSize, actualSize);
    assertTrue(stateOfRemoval);
  }

  @Test
  void ShouldNotRemoveSale() {
    saleRegister.addObject(sale);

    Sale unequalSale = new Sale(contact, date, "product", "22222222222", 50);
    boolean stateOfRemoval = saleRegister.removeObject(unequalSale);
    assertFalse(stateOfRemoval);

    int expectedSize = 1;
    int actualSize = saleRegister.getObjects().size();
    assertEquals(expectedSize, actualSize);
  }


  @Test
  void shouldGetSales() {
    saleRegister.addObject(sale);

    List<Sale> expected = new ArrayList<>();
    expected.add(sale);

    List<Sale> actual = saleRegister.getObjects();

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Add a sale object to the sale register")
  void addsASaleToSaleRegister() {
    saleRegister.addObject(sale);
    Sale expected = sale;
    Sale actual = saleRegister.getObjects().get(0);
    int expectedSize = 1;
    int actualSize = saleRegister.getObjects().size();
    assertEquals(expected, actual);
    assertEquals(expectedSize, actualSize);
  }

  @Test
  @DisplayName("Not able to create saleRegister if filepath is null")
  void shouldThrowNullPointerExceptionIfFilePathIsNull() {
    assertThrows(NullPointerException.class, () -> {
      SaleRegister saleRegister1 = new SaleRegister(null);
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