package bank.database;

import bank.Account;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Specific DAO interface for Account entity with additional methods.
 */
public interface AccountDAO extends DAO<Account, String> {
    
    /**
     * Finds all accounts owned by a specific customer.
     * @param customerId The customer ID
     * @return List of account IDs
     */
    List<String> findByCustomerId(String customerId);
    
    /**
     * Calculates the total balance across all accounts for a customer.
     * @param customerId The customer ID
     * @return The total balance
     */
    BigDecimal getTotalBalanceByCustomerId(String customerId);
    
    /**
     * Updates the balance of an account.
     * @param accountId The account ID
     * @param newBalance The new balance
     * @return true if updated successfully
     */
    boolean updateBalance(String accountId, BigDecimal newBalance);
}