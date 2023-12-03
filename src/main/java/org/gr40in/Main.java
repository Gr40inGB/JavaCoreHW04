package org.gr40in;

import org.gr40in.bank.*;

public class Main {
    public static void main(String[] args) {
        final String warning = " Please enter correct data!";

        Account accountRichard = null;
        Account accountGalGadot = null;
        Account accountError = null;

        try {
            accountRichard = Account.create("Richard", 12000.0);
            System.out.println(accountRichard);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + warning);
        }

        try {
            accountGalGadot = Account.create("Wonder Woman", 9999999.0);
            System.out.println(accountGalGadot);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + warning);
        }

        try {
            accountError = Account.create("Dracula", -1.0);
            System.out.println(accountError);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + warning);
        }

        AccountOperation operation1 = null;
        try {
            operation1 = Deposit.create(accountRichard, 10000.0);
            System.out.println(operation1);
            operation1.runOperation();
            System.out.println(operation1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + warning);
        } catch (InsufficientFundsException e) {
            System.out.println(warning);
        }

        AccountOperation operation2 = null;

        try {
            operation2 = Withdrawal.create(accountGalGadot, 540.0);
            System.out.println(operation2);
            operation2.runOperation();
            System.out.println(operation2);
        } catch (InsufficientFundsException e) {
            System.out.println("you don’t have that kind of money, dude");
        }

        System.out.println("---------------------------------------------------------------");
        System.out.println(accountRichard);
        System.out.println(accountGalGadot);

        Transaction transactionFail = null;
        try {
            transactionFail = new Transaction(accountRichard, accountGalGadot, 1234123424.0);
            transactionFail.run();
            System.out.println(transactionFail);
        } catch (InsufficientFundsException e) {
            System.out.println("\t" + e.getMessage());
        }
        System.out.println(accountRichard);
        System.out.println(accountGalGadot);

        System.out.println("---------------------------------------------------------------");

        Transaction transactionOk = null;
        try {
            transactionOk = new Transaction(accountGalGadot, accountRichard, 50_000.0);
            transactionOk.run();
            System.out.println(transactionOk);
        } catch (InsufficientFundsException e) {
            System.out.println("\t" + e.getMessage());
        }
        System.out.println(accountRichard);
        System.out.println(accountGalGadot);
    }
}