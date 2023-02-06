package no.ntnu.idatt1002.demo.data;

public class Account {

    private String name;
    private double balance;

    public Account(final String name) {
        this.name = name;
    }

    public boolean deposit(double amount) {
        if(amount > 0) {
            balance += amount;
            return true;
        } else {
            return false;
        }
    }
}
