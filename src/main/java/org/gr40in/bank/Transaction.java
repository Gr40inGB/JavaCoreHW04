package org.gr40in.bank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
