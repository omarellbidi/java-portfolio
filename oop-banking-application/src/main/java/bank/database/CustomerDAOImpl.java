package bank.database;

import bank.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the CustomerDAO interface for database operations.
 */
public class CustomerDAOImpl implements CustomerDAO {
    
    private DatabaseConnectionManager connectionManager;
    
    public CustomerDAOImpl() {
        this.connectionManager = DatabaseConnectionManager.getInstance();
    }
    
    @Override
    public Customer save(Customer customer) {
        String sql = "INSERT INTO customers (id, first_name, last_name, birth_day) VALUES (?, ?, ?, ?)";
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getId());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getLastName());
            stmt.setTimestamp(4, new Timestamp(customer.getBirthDay().getTime()));
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("Creating customer failed, no rows affected.");
            }
            
            return customer;
        } catch (SQLException e) {
            throw new DatabaseException("Error saving customer: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    @Override
    public Optional<Customer> findById(String id) {
        String sql = "SELECT id, first_name, last_name, birth_day FROM customers WHERE id = ?";
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer(
                    rs.getString("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    new Date(rs.getTimestamp("birth_day").getTime())
                );
                return Optional.of(customer);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseException("Error finding customer: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    @Override
    public List<Customer> findAll() {
        String sql = "SELECT id, first_name, last_name, birth_day FROM customers";
        List<Customer> customers = new ArrayList<>();
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getString("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    new Date(rs.getTimestamp("birth_day").getTime())
                );
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving customers: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    @Override
    public Customer update(Customer customer) {
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, birth_day = ? WHERE id = ?";
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setTimestamp(3, new Timestamp(customer.getBirthDay().getTime()));
            stmt.setString(4, customer.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("Updating customer failed, no such customer.");
            }
            
            return customer;
        } catch (SQLException e) {
            throw new DatabaseException("Error updating customer: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    @Override
    public boolean deleteById(String id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting customer: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    @Override
    public List<String> findByNameAndBirthDay(String firstName, String lastName, Date birthDay) {
        String sql = "SELECT id FROM customers WHERE first_name = ? AND last_name = ? AND birth_day = ?";
        List<String> customerIds = new ArrayList<>();
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setTimestamp(3, new Timestamp(birthDay.getTime()));
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                customerIds.add(rs.getString("id"));
            }
            return customerIds;
        } catch (SQLException e) {
            throw new DatabaseException("Error finding customers by name and birth date: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
    
    @Override
    public boolean exists(String customerId) {
        String sql = "SELECT COUNT(*) FROM customers WHERE id = ?";
        
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new DatabaseException("Error checking customer existence: " + e.getMessage(), e);
        } finally {
            connectionManager.releaseConnection(connection);
        }
    }
}