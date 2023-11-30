package org.gr40in.bank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class AccountOperation {
    private static Long idGenerator = 0L;
    private Long operationID;
    private Account account;
    private LocalDateTime dateTime;
    private BigDecimal amountOfFunds;

    public void runOperation(){
        if (operationAllowed()) {
            account.add(this);
        }
    }

    public boolean operationAllowed() {
        return account.operationAllowed(this);
    }

    public AccountOperation(Account account, Double amountOfFunds) {
        this.operationID = idGenerator++;
        this.account = account;
        this.dateTime = LocalDateTime.now();
        this.amountOfFunds = BigDecimal.valueOf(amountOfFunds);
    }

    //region Getters / Setters

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getAmountOfFunds() {
        return amountOfFunds;
    }

    public void setAmountOfFunds(BigDecimal amountOfFunds) {
        this.amountOfFunds = amountOfFunds;
    }
    //endregion
}
