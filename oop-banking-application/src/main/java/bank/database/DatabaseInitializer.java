package bank.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class to initialize the database schema.
 * This should be run once when the application is first installed.
 */
public class DatabaseInitializer {
    
    private DatabaseConnectionManager connectionManager;
    
    public DatabaseInitializer() {
        this.connectionManager = DatabaseConnectionManager.getInstance();
    }
    
    /**
     * Initializes the database schema from the schema.sql file.
     * 
     * @param schemaFilePath Path to the schema.sql file
     * @return true if schema was initialized successfully
     */
    public boolean initializeSchema(String schemaFilePath) {
        Connection connection = connectionManager.getConnection();
        try {
            // Read SQL from file
            StringBuilder sql = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(schemaFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Ignore comments and empty lines
                    if (!line.trim().startsWith("--") && !line.trim().isEmpty()) {
                        sql.append(line);
                        
                        // Execute when semicolon is found (end of statement)
                        if (line.trim().endsWith(";")) {
                            executeStatement(connection, sql.toString());
                            sql.setLength(0); // Reset StringBuilder
                        }
                    }
                }
            }
            
            return true;
        } catch (IOException | SQLException e) {
            throw new DatabaseException("Error initializing database schema: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    /**
     * Executes a single SQL statement.
     * 
     * @param connection Database connection
     * @param sql SQL statement to execute
     * @throws SQLException If execution fails
     */
    private void executeStatement(Connection connection, String sql) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
    
    /**
     * Main method to run the initializer.
     */
    public static void main(String[] args) {
        String schemaPath = "config/schema.sql";
        
        // Override with command line argument if provided
        if (args.length > 0) {
            schemaPath = args[0];
        }
        
        DatabaseInitializer initializer = new DatabaseInitializer();
        boolean success = initializer.initializeSchema(schemaPath);
        
        if (success) {
            System.out.println("Database schema initialized successfully.");
        } else {
            System.err.println("Failed to initialize database schema.");
        }
    }
}