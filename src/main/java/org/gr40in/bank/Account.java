package org.gr40in.bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private static Long idGenerator = 0L;
    private Long id;
    private String fullName;
    private BigDecimal balance;
    private List<AccountOperation> transactions;

    /**
     * checked parameters before create Account
     *
     * @param fullName full name client
     * @param balance  opening balance
     * @return Account - if all ok
     */
    public static Account create(String fullName, Double balance) throws IllegalArgumentException {
        if (fullName == null || fullName.length() < 3)
            throw new IllegalArgumentException("Uncorrected parameter <full name> :" + fullName);
        if (BigDecimal.valueOf(balance).compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Uncorrected parameter <balance> (must be positive) :" + balance);
        return new Account(fullName, BigDecimal.valueOf(balance));
    }

    public boolean operationAllowed(AccountOperation operation) {
        return operation.getAmountOfFunds().add(balance).compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean add(AccountOperation operation) throws InsufficientFundsException {
        if (operationAllowed(operation)) {
            balance = operation.getAmountOfFunds().add(balance);
            return transactions.add(operation);
        } else throw new InsufficientFundsException(String.format("Operation with this %s amount of funds denied!",
                operation.getAmountOfFunds().toString()));
    }

    private Account(String fullName, BigDecimal balance) {
        this.id = idGenerator++;
        this.fullName = fullName;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    //region Getters / Setters


    @Override
    public String toString() {
        return "acc_id:" + id + " " + fullName + " [" + balance + "$]";
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    private void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    //endregion
}
