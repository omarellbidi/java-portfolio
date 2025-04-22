package bank.database;

import bank.Customer;
import java.util.Date;
import java.util.List;

/**
 * Specific DAO interface for Customer entity with additional methods.
 */
public interface CustomerDAO extends DAO<Customer, String> {
    
    /**
     * Finds customers by their first name, last name, and birth date.
     * @param firstName Customer's first name
     * @param lastName Customer's last name
     * @param birthDay Customer's birth date
     * @return List of matching customer IDs
     */
    List<String> findByNameAndBirthDay(String firstName, String lastName, Date birthDay);
    
    /**
     * Verifies if a customer exists by ID.
     * @param customerId The customer ID to check
     * @return true if the customer exists
     */
    boolean exists(String customerId);
}