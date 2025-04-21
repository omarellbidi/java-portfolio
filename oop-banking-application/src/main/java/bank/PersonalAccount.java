package bank;

import java.math.BigDecimal;

/**
 * Represents a personal account in the bank. Personal accounts are intended for
 * individual customers and do not allow a negative balance.
 */
public class PersonalAccount extends Account {
    public PersonalAccount(String id, BigDecimal initialBalance, String customerId) {
        super(id, initialBalance, customerId);
    }

    @Override
    public boolean deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            setBalance(getBalance().add(amount));
            return true;
        }
        return false;
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0 && getBalance().compareTo(amount) >= 0) {
            setBalance(getBalance().subtract(amount));
            return true;
        }
        return false;
    }
}
