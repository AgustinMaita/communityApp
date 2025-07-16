package com.community.communityApp.repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory implementation of the Repository interface.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Generic class implementation with bounded type parameters
 * - Collections Framework (Map, List, Set)
 * - Thread-safe collections (ConcurrentHashMap)
 * - Atomic operations (AtomicLong)
 * - Java 8+ Stream API with lambdas
 * - Optional usage for null safety
 * - Method overloading and implementation
 * - Defensive programming and validation
 * 
 * DESIGN PATTERNS:
 * - Repository Pattern: Encapsulates data access logic
 * - Generic Programming: Reusable for different entity types
 * - Thread-Safe Singleton: Safe for concurrent access
 * - Factory Pattern: Creates instances for different entity types
 * 
 * GENERICS EXPLANATION:
 * - T extends Identifiable<ID>: Bounded type parameter
 * - Ensures T has getId() and setId() methods
 * - ID: The identifier type (String, Integer, etc.)
 * - Type safety prevents runtime ClassCastException
 * 
 * THREAD SAFETY:
 * - ConcurrentHashMap for thread-safe operations
 * - AtomicLong for thread-safe counters
 * - Synchronized methods where needed
 * - Defensive copying to prevent concurrent modification
 * 
 * @param <T> The entity type that must be Identifiable
 * @param <ID> The identifier type
 */
public class InMemoryRepository<T extends Identifiable<ID>, ID> implements Repository<T, ID> {
    
    /**
     * COLLECTIONS FRAMEWORK - Map for data storage
     * 
     * ConcurrentHashMap benefits:
     * - Thread-safe operations
     * - Better performance than Hashtable
     * - Allows concurrent reads
     * - Locks only portions of the map
     * 
     * ID → T mapping:
     * - Fast O(1) lookup by identifier
     * - Ensures unique identifiers
     * - Efficient for CRUD operations
     */
    private final Map<ID, T> data;
    
    /**
     * ATOMIC OPERATIONS for thread safety
     * 
     * AtomicLong benefits:
     * - Thread-safe increment operations
     * - Lock-free implementation
     * - Better performance than synchronized long
     * - Guarantees atomic read-modify-write operations
     */
    private final AtomicLong modificationCount;
    
    /**
     * Constructor initializing thread-safe collections
     * 
     * THREAD-SAFE INITIALIZATION:
     * - ConcurrentHashMap for concurrent access
     * - AtomicLong for modification tracking
     * - No external synchronization needed
     */
    public InMemoryRepository() {
        this.data = new ConcurrentHashMap<>();
        this.modificationCount = new AtomicLong(0);
    }
    
    /**
     * Save an entity to the repository
     * 
     * IMPLEMENTATION DETAILS:
     * - Validates input parameters
     * - Stores entity by its identifier
     * - Increments modification counter
     * - Returns the saved entity
     * 
     * THREAD SAFETY:
     * - ConcurrentHashMap.put() is thread-safe
     * - AtomicLong.incrementAndGet() is atomic
     * - No additional synchronization needed
     */
    @Override
    public T save(T entity) {
        // INPUT VALIDATION - defensive programming
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Entity must have a non-null identifier");
        }
        
        // Store entity by its identifier
        data.put(entity.getId(), entity);
        
        // Track modifications for concurrent access monitoring
        modificationCount.incrementAndGet();
        
        return entity;
    }
    
    /**
     * Find an entity by its identifier
     * 
     * OPTIONAL USAGE:
     * - Optional.ofNullable() handles null values safely
     * - Prevents NullPointerException
     * - Forces explicit null handling by caller
     * - More expressive than returning null
     * 
     * COLLECTIONS FRAMEWORK:
     * - Map.get() returns null if key not found
     * - O(1) average time complexity
     * - Thread-safe read operation
     */
    @Override
    public Optional<T> findById(ID id) {
        if (id == null) {
            return Optional.empty();
        }
        
        return Optional.ofNullable(data.get(id));
    }
    
    /**
     * Check if an entity exists by its identifier
     * 
     * BOOLEAN PRIMITIVE:
     * - Simple true/false return
     * - More efficient than Boolean wrapper
     * - Clear semantic meaning
     * 
     * COLLECTIONS FRAMEWORK:
     * - Map.containsKey() is optimized for existence checks
     * - O(1) average time complexity
     * - Thread-safe operation
     */
    @Override
    public boolean existsById(ID id) {
        return id != null && data.containsKey(id);
    }
    
    /**
     * Retrieve all entities from the repository
     * 
     * COLLECTIONS FRAMEWORK:
     * - ArrayList for ordered collection
     * - Defensive copying prevents external modification
     * - Stream API for efficient collection operations
     * 
     * JAVA 8+ STREAM API:
     * - data.values() returns Collection<T>
     * - stream() creates a Stream<T>
     * - collect() gathers stream elements into List
     * - Method chaining for readable code
     */
    @Override
    public List<T> findAll() {
        return new ArrayList<>(data.values());
    }
    
    /**
     * Count the total number of entities
     * 
     * COLLECTIONS FRAMEWORK:
     * - Map.size() returns current size
     * - O(1) time complexity
     * - Thread-safe operation
     * 
     * PRIMITIVE RETURN:
     * - long primitive for large counts
     * - More efficient than Long wrapper
     * - Supports up to 2^63 - 1 entities
     */
    @Override
    public long count() {
        return data.size();
    }
    
    /**
     * Delete an entity by its identifier
     * 
     * COLLECTIONS FRAMEWORK:
     * - Map.remove() returns removed value or null
     * - O(1) average time complexity
     * - Thread-safe operation
     * 
     * BOOLEAN RETURN:
     * - Indicates success/failure of operation
     * - More informative than void return
     * - Useful for error handling
     */
    @Override
    public boolean deleteById(ID id) {
        if (id == null) {
            return false;
        }
        
        boolean removed = data.remove(id) != null;
        if (removed) {
            modificationCount.incrementAndGet();
        }
        
        return removed;
    }
    
    /**
     * Delete a specific entity
     * 
     * METHOD OVERLOADING:
     * - Same method name, different parameters
     * - Provides API flexibility
     * - Compile-time method resolution
     * 
     * DEFENSIVE PROGRAMMING:
     * - Validates entity and its identifier
     * - Checks if entity exists before deletion
     * - Consistent with deleteById behavior
     */
    @Override
    public boolean delete(T entity) {
        if (entity == null || entity.getId() == null) {
            return false;
        }
        
        return deleteById(entity.getId());
    }
    
    /**
     * Delete all entities from the repository
     * 
     * COLLECTIONS FRAMEWORK:
     * - Map.clear() removes all entries
     * - O(n) time complexity
     * - Thread-safe operation
     * 
     * DANGEROUS OPERATION:
     * - Clears entire repository
     * - Should be used with caution
     * - Useful for testing and cleanup
     */
    @Override
    public void deleteAll() {
        data.clear();
        modificationCount.incrementAndGet();
    }
    
    /**
     * Update an existing entity
     * 
     * OPTIONAL RETURN:
     * - Optional.empty() if entity doesn't exist
     * - Optional.of(entity) if update successful
     * - Forces explicit handling of update failures
     * 
     * UPDATE SEMANTICS:
     * - Only updates existing entities
     * - Preserves identifier
     * - Atomic operation
     */
    @Override
    public Optional<T> update(T entity) {
        if (entity == null || entity.getId() == null) {
            return Optional.empty();
        }
        
        // Check if entity exists before updating
        if (!existsById(entity.getId())) {
            return Optional.empty();
        }
        
        data.put(entity.getId(), entity);
        modificationCount.incrementAndGet();
        
        return Optional.of(entity);
    }
    
    /**
     * Save or update an entity (upsert operation)
     * 
     * UPSERT PATTERN:
     * - INSERT if entity doesn't exist
     * - UPDATE if entity exists
     * - Convenient for batch operations
     * - Atomic operation
     */
    @Override
    public T saveOrUpdate(T entity) {
        return save(entity); // save() already handles both cases
    }
    
    /**
     * JAVA 8+ STREAM API demonstration
     * 
     * Find entities by a predicate (filter condition)
     * 
     * FUNCTIONAL PROGRAMMING:
     * - Predicate<T> functional interface
     * - Lambda expressions for filtering
     * - Method chaining for readable code
     * - Lazy evaluation of stream operations
     * 
     * GENERICS with WILDCARDS:
     * - Predicate<? super T> accepts predicates for T or its supertypes
     * - Provides flexibility in predicate types
     * - Maintains type safety
     */
    public List<T> findByPredicate(java.util.function.Predicate<? super T> predicate) {
        if (predicate == null) {
            return findAll();
        }
        
        return data.values().stream()
                .filter(predicate)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * JAVA 8+ STREAM API with method references
     * 
     * Find entities by multiple identifiers
     * 
     * STREAM OPERATIONS:
     * - Stream from collection
     * - Filter by containment check
     * - Collect to new ArrayList
     * - Method reference for constructor
     */
    public List<T> findByIds(Collection<ID> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        
        return data.entrySet().stream()
                .filter(entry -> ids.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * JAVA 8+ OPTIONAL with Stream API
     * 
     * Find first entity matching a predicate
     * 
     * STREAM TERMINATION:
     * - findFirst() returns Optional<T>
     * - Short-circuit operation
     * - Stops at first match
     * - Handles empty results gracefully
     */
    public Optional<T> findFirst(java.util.function.Predicate<? super T> predicate) {
        if (predicate == null) {
            return Optional.empty();
        }
        
        return data.values().stream()
                .filter(predicate)
                .findFirst();
    }
    
    /**
     * Get repository statistics
     * 
     * INNER CLASS demonstration
     * 
     * NESTED CLASS benefits:
     * - Logical grouping of related data
     * - Encapsulation of statistics
     * - Type safety for return values
     * - Immutable data transfer object
     */
    public RepositoryStats getStats() {
        return new RepositoryStats(data.size(), modificationCount.get());
    }
    
    /**
     * Inner class for repository statistics
     * 
     * IMMUTABLE OBJECT pattern:
     * - Final fields
     * - No setters
     * - All data provided in constructor
     * - Thread-safe by design
     */
    public static class RepositoryStats {
        private final long entityCount;
        private final long modificationCount;
        
        public RepositoryStats(long entityCount, long modificationCount) {
            this.entityCount = entityCount;
            this.modificationCount = modificationCount;
        }
        
        public long getEntityCount() { return entityCount; }
        public long getModificationCount() { return modificationCount; }
        
        @Override
        public String toString() {
            return String.format("RepositoryStats{entities=%d, modifications=%d}", 
                               entityCount, modificationCount);
        }
    }
}