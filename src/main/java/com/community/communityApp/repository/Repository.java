package com.community.communityApp.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic Repository interface defining common data access operations.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Generics with type parameters (T, ID)
 * - Interface definition and contracts
 * - Optional for null safety
 * - Collections Framework with List
 * - Generic method definitions
 * - Bounded type parameters
 * 
 * DESIGN PATTERNS:
 * - Repository Pattern: Abstracts data access logic
 * - Generic Programming: Reusable code for different entity types
 * - Contract-based Design: Interface defines what, not how
 * 
 * GENERICS EXPLANATION:
 * - T: Represents the entity type (Person, Resident, Service, etc.)
 * - ID: Represents the identifier type (String, Integer, Long, etc.)
 * - Provides type safety at compile time
 * - Eliminates need for casting
 * - Enables code reuse across different entity types
 * 
 * INTERFACE BENEFITS:
 * - Defines contract without implementation
 * - Allows multiple implementations (InMemory, Database, File, etc.)
 * - Enables dependency injection and testing
 * - Promotes loose coupling
 * 
 * @param <T> The entity type this repository manages
 * @param <ID> The type of the entity's unique identifier
 */
public interface Repository<T, ID> {
    
    /**
     * Save an entity to the repository.
     * 
     * GENERICS usage:
     * - Parameter T: accepts any entity type
     * - Return T: returns the same entity type
     * - Type safety: compiler ensures consistent types
     * 
     * @param entity The entity to save (cannot be null)
     * @return The saved entity
     * @throws IllegalArgumentException if entity is null
     */
    T save(T entity);
    
    /**
     * Find an entity by its unique identifier.
     * 
     * OPTIONAL usage:
     * - Optional<T> indicates the entity might not exist
     * - Prevents NullPointerException
     * - Forces explicit null handling
     * - More expressive than returning null
     * 
     * @param id The unique identifier to search for
     * @return Optional containing the entity if found, empty otherwise
     */
    Optional<T> findById(ID id);
    
    /**
     * Check if an entity exists by its identifier.
     * 
     * PRIMITIVE RETURN TYPE:
     * - boolean primitive for simple true/false
     * - More efficient than Boolean wrapper
     * - Clear semantic meaning
     * 
     * @param id The unique identifier to check
     * @return true if entity exists, false otherwise
     */
    boolean existsById(ID id);
    
    /**
     * Retrieve all entities from the repository.
     * 
     * COLLECTIONS FRAMEWORK:
     * - List<T> provides ordered collection
     * - Generic List ensures type safety
     * - Allows duplicate entities if needed
     * - Supports indexing and iteration
     * 
     * @return List of all entities (never null, may be empty)
     */
    List<T> findAll();
    
    /**
     * Count the total number of entities.
     * 
     * WRAPPER CLASS usage:
     * - Long wrapper class for large counts
     * - Allows null return if count is unavailable
     * - Better than int for scalability
     * 
     * @return The number of entities in the repository
     */
    long count();
    
    /**
     * Delete an entity by its identifier.
     * 
     * VOID RETURN TYPE:
     * - No return value needed for delete operations
     * - Side effect operation (modifies repository state)
     * - Boolean return could indicate success/failure
     * 
     * @param id The identifier of the entity to delete
     * @return true if entity was deleted, false if not found
     */
    boolean deleteById(ID id);
    
    /**
     * Delete a specific entity.
     * 
     * OVERLOADED METHODS:
     * - Same method name, different parameters
     * - Provides flexibility in API usage
     * - Compile-time method resolution
     * 
     * @param entity The entity to delete
     * @return true if entity was deleted, false if not found
     */
    boolean delete(T entity);
    
    /**
     * Delete all entities from the repository.
     * 
     * DANGEROUS OPERATION:
     * - Clears entire repository
     * - Should be used with caution
     * - Useful for testing and cleanup
     */
    void deleteAll();
    
    /**
     * Update an existing entity.
     * 
     * OPTIONAL RETURN:
     * - Optional<T> indicates update might fail
     * - Returns updated entity if successful
     * - Empty Optional if entity doesn't exist
     * 
     * @param entity The entity with updated values
     * @return Optional containing updated entity if successful
     */
    Optional<T> update(T entity);
    
    /**
     * Save or update an entity (upsert operation).
     * 
     * UPSERT PATTERN:
     * - INSERT if entity doesn't exist
     * - UPDATE if entity exists
     * - Convenient for batch operations
     * 
     * @param entity The entity to save or update
     * @return The saved/updated entity
     */
    T saveOrUpdate(T entity);
}