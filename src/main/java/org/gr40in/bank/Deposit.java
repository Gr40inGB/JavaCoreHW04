package org.gr40in.bank;

import java.time.LocalDateTime;

public class Deposit extends AccountOperation {
    @Override
    public boolean runOperation() {
        return false;
    }


    public Deposit(Account account, Double amountOfFunds) {
        super(account, amountOfFunds);
    }
}
