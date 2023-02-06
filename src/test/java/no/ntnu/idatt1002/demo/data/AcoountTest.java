package no.ntnu.idatt1002.demo.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AcoountTest {

    @Test
    public void testThatAccountDepositSucceed() {

        Account account = new Account("demosinkonto");
        assertTrue(account.deposit(100))
        ;
    }

    @Test
    public void testThatAccountDepositFeil() {

        Account account = new Account("demosinkonto");
        assertFalse(account.deposit(-100))
        ;
    }
}
