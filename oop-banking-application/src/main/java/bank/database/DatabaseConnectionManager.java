package bank.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Manages database connections following the Singleton pattern.
 * Handles connection creation, pooling and configuration.
 */
public class DatabaseConnectionManager {
    private static DatabaseConnectionManager instance;
    private String url;
    private String username;
    private String password;
    private int maxPoolSize = 10;
    private Connection[] connectionPool;
    private boolean[] connectionStatus;

    private DatabaseConnectionManager() {
        loadDatabaseProperties();
        initializeConnectionPool();
    }

    public static synchronized DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }

    private void loadDatabaseProperties() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config/database.properties")) {
            props.load(fis);
            this.url = props.getProperty("db.url");
            this.username = props.getProperty("db.username");
            this.password = props.getProperty("db.password");
            
            if (props.getProperty("db.maxPoolSize") != null) {
                this.maxPoolSize = Integer.parseInt(props.getProperty("db.maxPoolSize"));
            }
        } catch (IOException e) {
            // Fallback to default values if properties file is not found
            this.url = "jdbc:mysql://localhost:3306/bankdb";
            this.username = "bankuser";
            this.password = "bankpassword";
            System.err.println("Warning: Could not load database properties file. Using default values.");
        }
    }

    private void initializeConnectionPool() {
        try {
            // Load the database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Initialize the connection pool
            connectionPool = new Connection[maxPoolSize];
            connectionStatus = new boolean[maxPoolSize];
            
            // Create connections
            for (int i = 0; i < maxPoolSize; i++) {
                connectionPool[i] = createNewConnection();
                connectionStatus[i] = false; // Not in use
            }
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Database driver not found", e);
        } catch (SQLException e) {
            throw new DatabaseException("Error initializing connection pool", e);
        }
    }

    private Connection createNewConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public synchronized Connection getConnection() {
        for (int i = 0; i < maxPoolSize; i++) {
            if (!connectionStatus[i]) {
                try {
                    // Check if connection is still valid
                    if (connectionPool[i] == null || connectionPool[i].isClosed()) {
                        connectionPool[i] = createNewConnection();
                    }
                    
                    connectionStatus[i] = true;
                    return connectionPool[i];
                } catch (SQLException e) {
                    throw new DatabaseException("Error getting database connection", e);
                }
            }
        }
        throw new DatabaseException("Connection pool exhausted");
    }

    public synchronized void releaseConnection(Connection connection) {
        for (int i = 0; i < maxPoolSize; i++) {
            if (connectionPool[i] == connection) {
                connectionStatus[i] = false;
                break;
            }
        }
    }

    public void closeAllConnections() {
        for (int i = 0; i < maxPoolSize; i++) {
            try {
                if (connectionPool[i] != null && !connectionPool[i].isClosed()) {
                    connectionPool[i].close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}