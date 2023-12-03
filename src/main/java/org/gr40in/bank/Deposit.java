package org.gr40in.bank;

public class Deposit extends AccountOperation {

    public static Deposit create(Account account, Double amountOfFunds) {
        if (amountOfFunds <= 0)
            throw new IllegalArgumentException("Deposit cant be negative");
        return new Deposit(account, amountOfFunds);
    }

    private Deposit(Account account, Double amountOfFunds) {
        super(account, amountOfFunds);
    }
}
