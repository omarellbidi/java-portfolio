package bank;

import bank.database.DatabaseInitializer;
import java.io.File;
import java.util.Scanner;

/**
 * Main application entry point for the Banking System.
 * Initializes database and provides a simple command line interface.
 */
public class BankApplication {
    
    private static Bank bank;
    
    public static void main(String[] args) {
        System.out.println("Initializing Banking System...");
        
        // Check if database needs to be initialized
        File schemaFile = new File("config/schema.sql");
        if (schemaFile.exists()) {
            System.out.println("Initializing database schema...");
            DatabaseInitializer initializer = new DatabaseInitializer();
            initializer.initializeSchema(schemaFile.getPath());
        }
        
        // Initialize the bank
        bank = new Bank();
        System.out.println("Banking System initialized successfully!");
        
        // Start command line interface
        startCommandLineInterface();
        
        // Shutdown hook to properly close database connections
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Banking System...");
            bank.shutdown();
        }));
    }
    
    /**
     * Simple command line interface for demonstration purposes.
     * In a real application, this would be replaced with a GUI or web interface.
     */
    private static void startCommandLineInterface() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        System.out.println("\nBanking System Command Line Interface");
        System.out.println("====================================");
        
        while (running) {
            System.out.println("\nAvailable commands:");
            System.out.println("1. Register Customer");
            System.out.println("2. Create Personal Account");
            System.out.println("3. Create Corporate Account");
            System.out.println("4. Deposit");
            System.out.println("5. Withdraw");
            System.out.println("6. Transfer");
            System.out.println("7. Check Balance");
            System.out.println("8. View Transaction History");
            System.out.println("9. Exit");
            System.out.print("\nEnter command number: ");
            
            String command = scanner.nextLine().trim();
            
            try {
                switch (command) {
                    case "1":
                        registerCustomer(scanner);
                        break;
                    case "2":
                        createPersonalAccount(scanner);
                        break;
                    case "3":
                        createCorporateAccount(scanner);
                        break;
                    case "4":
                        deposit(scanner);
                        break;
                    case "5":
                        withdraw(scanner);
                        break;
                    case "6":
                        transfer(scanner);
                        break;
                    case "7":
                        checkBalance(scanner);
                        break;
                    case "8":
                        viewTransactionHistory(scanner);
                        break;
                    case "9":
                        running = false;
                        System.out.println("Exiting Banking System...");
                        break;
                    default:
                        System.out.println("Invalid command. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
    
    private static void registerCustomer(Scanner scanner) {
        // Implementation of customer registration logic
        System.out.println("Register Customer");
        System.out.println("=================");
        
        // Implementation would go here
        System.out.println("Not implemented in this demo");
    }
    
    private static void createPersonalAccount(Scanner scanner) {
        // Implementation of personal account creation
        System.out.println("Not implemented in this demo");
    }
    
    private static void createCorporateAccount(Scanner scanner) {
        // Implementation of corporate account creation
        System.out.println("Not implemented in this demo");
    }
    
    private static void deposit(Scanner scanner) {
        // Implementation of deposit logic
        System.out.println("Not implemented in this demo");
    }
    
    private static void withdraw(Scanner scanner) {
        // Implementation of withdraw logic
        System.out.println("Not implemented in this demo");
    }
    
    private static void transfer(Scanner scanner) {
        // Implementation of transfer logic
        System.out.println("Not implemented in this demo");
    }
    
    private static void checkBalance(Scanner scanner) {
        // Implementation of balance check
        System.out.println("Not implemented in this demo");
    }
    
    private static void viewTransactionHistory(Scanner scanner) {
        // Implementation of transaction history view
        System.out.println("Not implemented in this demo");
    }
}