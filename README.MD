### Account

```java
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

    @Override
    public String toString() {
        return "acc_id:" + id + " " + fullName + " [" + balance + "$]";
    }

    public String getFullName() {
        return fullName;
    }

}
```

### AccountOperation
```java
package org.gr40in.bank;

public abstract class AccountOperation {
    private static Long idGenerator = 0L;
    private Long operationID;
    private Account account;
    private LocalDateTime dateTime;
    private BigDecimal amountOfFunds;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss");
    private boolean approved;

    public boolean runOperation() throws InsufficientFundsException {
        if (account != null) {
            return approved = account.add(this);
        }
        return false;
    }

    @Override
    public String toString() {
        return "op_id:" + operationID +
                " " + account.getFullName() +
                " " + dateTime.format(formatter) +
                " " + amountOfFunds +
                " " + (approved ? "approved" : "denied");
    }

    public AccountOperation(Account account, Double amountOfFunds) {
        this.operationID = idGenerator++;
        this.account = account;
        this.dateTime = LocalDateTime.now();
        this.amountOfFunds = BigDecimal.valueOf(amountOfFunds);
    }

    public Account getAccount() {
        return account;
    }

}

```

### Deposit 
```java
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
```

### Transaction - list of operations

```java

public class Transaction {

    private LocalDateTime dateTime;
    private List<AccountOperation> operationList;
    private boolean approved;

    @Override
    public String toString() {
        return "Transaction" + dateTime + " " + approved;
    }

    public Transaction(Account sourceAccount, Account destinationAccount, Double amount) throws IllegalArgumentException {
        this.dateTime = LocalDateTime.now();
        operationList = new ArrayList<>();
        operationList.add(Withdrawal.create(sourceAccount, amount));
        operationList.add(Deposit.create(destinationAccount, amount));
        this.approved = false;
    }

    public boolean run() throws InsufficientFundsException {
        for (AccountOperation operation : operationList) {
            operation.runOperation();
        }
        return approved = true;
    }

    public List<AccountOperation> getOperationList() {
        return operationList;
    }

    public boolean isApproved() {
        return approved;
    }
}
```


### Main
```java

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
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + warning);
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
```