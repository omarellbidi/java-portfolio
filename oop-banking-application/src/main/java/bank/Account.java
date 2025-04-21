package bank;

import java.math.BigDecimal;

public abstract class Account {
    private String id;
    private BigDecimal balance;
    private String customerId;

    // Constructs a new Account instance.
    public Account(String id, BigDecimal balance, String customerId) {
        this.id = id;
        this.balance = balance;
        this.customerId = customerId;
    }

    // Retrieves the account ID.
    public String getId() {
        return id;
    }

    /**
     * Retrieves the current balance of the account.
     * @return the current balance as a BigDecimal
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Retrieves the customer ID associated with this account.
     * @return the unique id of the customer
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets the account's balance. This method is protected to ensure it is only
     * called within the bank package.
     */
    protected void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    // Abstract method to deposit a specified amount into the account.
    public abstract boolean deposit(BigDecimal amount);

    // Abstract method to withdraw a specified amount from the account.
    public abstract boolean withdraw(BigDecimal amount);
}
