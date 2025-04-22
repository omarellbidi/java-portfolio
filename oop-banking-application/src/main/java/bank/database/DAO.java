package bank.database;

import java.util.List;
import java.util.Optional;

/**
 * Generic Data Access Object interface defining standard CRUD operations.
 * @param <T> The entity type this DAO manages
 * @param <ID> The type of the entity's primary key
 */
public interface DAO<T, ID> {
    
    /**
     * Saves an entity to the database.
     * @param entity The entity to save
     * @return The saved entity, possibly with generated ID
     */
    T save(T entity);
    
    /**
     * Finds an entity by its ID.
     * @param id The entity's ID
     * @return An Optional containing the entity if found
     */
    Optional<T> findById(ID id);
    
    /**
     * Retrieves all entities.
     * @return A list of all entities
     */
    List<T> findAll();
    
    /**
     * Updates an existing entity.
     * @param entity The entity to update
     * @return The updated entity
     */
    T update(T entity);
    
    /**
     * Deletes an entity by its ID.
     * @param id The ID of the entity to delete
     * @return true if deleted successfully
     */
    boolean deleteById(ID id);
}