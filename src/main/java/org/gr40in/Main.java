package org.gr40in;

import org.gr40in.bank.Account;

public class Main {
    public static void main(String[] args) {
        Account test = Account.create("Richard", 12000.0);
        System.out.println(test);
        Account test2 = Account.create("iii", -10.0);
        System.out.println(test2);
    }
}