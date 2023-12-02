package org.gr40in.bank;

public class Withdrawal extends AccountOperation {

    public static Withdrawal create(Account account, Double amountOfFunds) {
        if (amountOfFunds <= 0)
            throw new IllegalArgumentException("System doesn't work with zero or negative amount");
        return new Withdrawal(account, amountOfFunds);
    }

    private Withdrawal(Account account, Double amountOfFunds) {
        super(account, -amountOfFunds);
    }
}
