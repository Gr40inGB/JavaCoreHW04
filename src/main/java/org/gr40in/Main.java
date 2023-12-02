package org.gr40in;

import org.gr40in.bank.Account;
import org.gr40in.bank.AccountOperation;
import org.gr40in.bank.Deposit;
import org.gr40in.bank.Withdrawal;

public class Main {
    public static void main(String[] args) {
        Account test = Account.create("Richard", 12000.0);
        System.out.println(test);
//        Account test2 = Account.create("iii", -10.0);
//        System.out.println(test2);
        AccountOperation operation = Deposit.create(test, 10000.0);
        System.out.println(operation);
        try {
            operation.runOperation();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(operation);
        AccountOperation operation2 = Withdrawal.create(test, 1423540.0);
        try {
            operation2.runOperation();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(operation2);
        System.out.println(test);

    }
}