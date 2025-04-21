package bank;

import java.math.BigDecimal;

/**
 * Represents a corporate account in the bank. Corporate accounts can be owned by
 * multiple customers and are allowed to have a negative balance.
 */
public class CorporateAccount extends Account {
    public CorporateAccount(String id, BigDecimal initialBalance, String customerId) {
        super(id, initialBalance, customerId);
    }

    @Override
    public boolean deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        setBalance(getBalance().add(amount));
        return true;
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        setBalance(getBalance().subtract(amount)); // Allows negative balance
        return true;
    }
}
