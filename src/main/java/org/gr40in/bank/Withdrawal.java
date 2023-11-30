package org.gr40in.bank;

import java.time.LocalDateTime;

public class Withdrawal extends AccountOperation{
    @Override
    public boolean runOperation() {
        return false;
    }


    public Withdrawal(Account account, LocalDateTime dateTime) {
        super(account, dateTime);
    }
}
