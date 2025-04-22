package bank.database;

import bank.Account;
import bank.PersonalAccount;
import bank.CorporateAccount;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the AccountDAO interface for database operations.
 */
public class AccountDAOImpl implements AccountDAO {
    
    private DatabaseConnectionManager connectionManager;
    
    public AccountDAOImpl() {
        this.connectionManager = DatabaseConnectionManager.getInstance();
    }
    
    @Override
    public Account save(Account account) {
        String sql = "INSERT INTO accounts (id, balance, customer_id, account_type) VALUES (?, ?, ?, ?)";
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getId());
            stmt.setBigDecimal(2, account.getBalance());
            stmt.setString(3, account.getCustomerId());
            
            // Determine account type
            String accountType = account instanceof PersonalAccount ? "PERSONAL" : "CORPORATE";
            stmt.setString(4, accountType);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("Creating account failed, no rows affected.");
            }
            
            return account;
        } catch (SQLException e) {
            throw new DatabaseException("Error saving account: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    @Override
    public Optional<Account> findById(String id) {
        String sql = "SELECT id, balance, customer_id, account_type FROM accounts WHERE id = ?";
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String accountId = rs.getString("id");
                BigDecimal balance = rs.getBigDecimal("balance");
                String customerId = rs.getString("customer_id");
                String accountType = rs.getString("account_type");
                
                Account account;
                if ("PERSONAL".equals(accountType)) {
                    account = new PersonalAccount(accountId, balance, customerId);
                } else {
                    account = new CorporateAccount(accountId, balance, customerId);
                }
                return Optional.of(account);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseException("Error finding account: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    @Override
    public List<Account> findAll() {
        String sql = "SELECT id, balance, customer_id, account_type FROM accounts";
        List<Account> accounts = new ArrayList<>();
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String accountId = rs.getString("id");
                BigDecimal balance = rs.getBigDecimal("balance");
                String customerId = rs.getString("customer_id");
                String accountType = rs.getString("account_type");
                
                Account account;
                if ("PERSONAL".equals(accountType)) {
                    account = new PersonalAccount(accountId, balance, customerId);
                } else {
                    account = new CorporateAccount(accountId, balance, customerId);
                }
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving accounts: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    @Override
    public Account update(Account account) {
        String sql = "UPDATE accounts SET balance = ?, customer_id = ? WHERE id = ?";
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBigDecimal(1, account.getBalance());
            stmt.setString(2, account.getCustomerId());
            stmt.setString(3, account.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("Updating account failed, no such account.");
            }
            
            return account;
        } catch (SQLException e) {
            throw new DatabaseException("Error updating account: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    @Override
    public boolean deleteById(String id) {
        String sql = "DELETE FROM accounts WHERE id = ?";
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting account: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    @Override
    public List<String> findByCustomerId(String customerId) {
        String sql = "SELECT id FROM accounts WHERE customer_id = ?";
        List<String> accountIds = new ArrayList<>();
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                accountIds.add(rs.getString("id"));
            }
            return accountIds;
        } catch (SQLException e) {
            throw new DatabaseException("Error finding accounts by customer ID: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    @Override
    public BigDecimal getTotalBalanceByCustomerId(String customerId) {
        String sql = "SELECT SUM(balance) as total FROM accounts WHERE customer_id = ?";
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
            return BigDecimal.ZERO;
        } catch (SQLException e) {
            throw new DatabaseException("Error calculating total balance: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    @Override
    public boolean updateBalance(String accountId, BigDecimal newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBigDecimal(1, newBalance);
            stmt.setString(2, accountId);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error updating account balance: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
}