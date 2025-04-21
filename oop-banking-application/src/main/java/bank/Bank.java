// El lbidi Omar , s1081842 

package bank;

import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


 //  Manages banking operations including customer and account management.

 
public class Bank {
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, Account> accounts = new HashMap<>();

    /**
     * Registers a customer and returns a unique customer ID.
     */
    public String registerCustomer(String firstName, String lastName, Date birthDay) {
        String id = firstName.substring(0, 1) + lastName + System.nanoTime();  // Generate a unique ID
        Customer newCustomer = new Customer(id, firstName, lastName, birthDay);
        customers.put(id, newCustomer);  // Add to customers map
        return id;
    }

    
      //Retrieves customer IDs based on name and birthday.
     
    public Collection<String> getCustomers(String firstName, String lastName, Date birthDay) {
        return customers.values().stream()
            .filter(c -> c.getFirstName().equals(firstName) && 
                         c.getLastName().equals(lastName) && 
                         c.getBirthDay().equals(birthDay))
            .map(Customer::getId)
            .collect(Collectors.toList());
    }

    
     // Removes an account by ID.
     
    public boolean removeAccount(String accountId) {
        return accounts.remove(accountId) != null;  // True if account existed and was removed
    }

    
     // Registers a corporate account for multiple customers.
     
    public Optional<String> registerCorporateAccount(String[] customerData) {
        for (String customerId : customerData) {
            if (!customers.containsKey(customerId)) {
                return Optional.empty();  // Return empty if any customer ID is invalid
            }
        }
        String accountId = "C-" + System.nanoTime();  // Generate a unique ID
        CorporateAccount newAccount = new CorporateAccount(accountId, BigDecimal.ZERO, customerData[0]);
        accounts.put(accountId, newAccount);
        return Optional.of(accountId);
    }

    /**
     * Registers a personal account for a single customer.
     */
    public Optional<String> registerPersonalAccount(String customerId) {
        if (customers.containsKey(customerId)) {
            String accountId = "P-" + System.nanoTime();  // Generate a unique ID
            PersonalAccount newAccount = new PersonalAccount(accountId, BigDecimal.ZERO, customerId);
            accounts.put(accountId, newAccount);
            return Optional.of(accountId);
        }
        return Optional.empty();
    }

    /**
     * Retrieves the balance for a specified account.
     */
    public Optional<BigDecimal> getBalance(String accountId) {
        if (accounts.containsKey(accountId)) {
            return Optional.of(accounts.get(accountId).getBalance());
        }
        return Optional.empty();
    }

    /**
     * Deposits an amount to a specific account.
     */
    public boolean deposit(String accountId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 || !accounts.containsKey(accountId)) {
            return false;  // Validation failed
        }
        return accounts.get(accountId).deposit(amount);
    }

    /**
     * Withdraws an amount from a specific account.
     */
    public boolean withdraw(String accountId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 || !accounts.containsKey(accountId)) {
            return false;  // Validation failed
        }
        return accounts.get(accountId).withdraw(amount);
    }

    /**
     * Transfers an amount from one account to another.
     */
    public boolean transfer(String fromAccountId, String toAccountId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 || !accounts.containsKey(fromAccountId) || !accounts.containsKey(toAccountId)) {
            return false;  // Validation failed
        }
        if (withdraw(fromAccountId, amount) && deposit(toAccountId, amount)) {
            return true;
        }
        return false;
    }

    /**
     * Retrieves a list of all account IDs for a given customer.
     */
    public Optional<Collection<String>> getAccounts(String customerId) {
        if (customers.containsKey(customerId)) {
            return Optional.of(accounts.values().stream()
                    .filter(acc -> acc.getCustomerId().equals(customerId))
                    .map(Account::getId)
                    .collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    /**
     * Calculates the total balance of all accounts owned by a customer.
     */
    public Optional<BigDecimal> getTotalBalance(String customerId) {
        if (customers.containsKey(customerId)) {
            BigDecimal total = accounts.values().stream()
                .filter(acc -> acc.getCustomerId().equals(customerId))
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            return Optional.of(total);  // Sum the balances
        }
        return Optional.empty();
    }
}


