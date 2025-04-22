package bank.database;

/**
 * Custom exception class for database operations.
 * Encapsulates SQL exceptions and provides clearer error messages.
 */
public class DatabaseException extends RuntimeException {
    
    public DatabaseException(String message) {
        super(message);
    }
    
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}