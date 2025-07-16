package com.community.communityApp.repository;

/**
 * Interface for entities that have a unique identifier.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Interface definition with generic type parameter
 * - Bounded generics for type safety
 * - Marker interface pattern
 * - Generic method definitions
 * 
 * DESIGN PATTERNS:
 * - Marker Interface: Indicates entities can be identified uniquely
 * - Strategy Pattern: Different identifier types for different entities
 * - Template Method: Defines contract for identifier access
 * 
 * GENERICS EXPLANATION:
 * - ID: Generic type parameter for identifier type
 * - Allows String, Integer, Long, UUID, etc.
 * - Type safety at compile time
 * - Eliminates casting in repository operations
 * 
 * INTERFACE BENEFITS:
 * - Enables generic repository operations
 * - Provides contract for entity identification
 * - Supports polymorphic behavior
 * - Enables type-safe collections
 * 
 * USAGE EXAMPLES:
 * - Person implements Identifiable<String> (email as ID)
 * - Service implements Identifiable<String> (serviceId as ID)
 * - Resident implements Identifiable<String> (apartmentNumber as ID)
 * 
 * @param <ID> The type of the unique identifier
 */
public interface Identifiable<ID> {
    
    /**
     * Get the unique identifier for this entity.
     * 
     * GENERIC RETURN TYPE:
     * - ID type parameter ensures type safety
     * - Compiler enforces consistent identifier types
     * - No casting required in calling code
     * 
     * CONTRACT:
     * - Must return non-null identifier
     * - Identifier should be unique within entity type
     * - Identifier should be immutable
     * - Same identifier should return same entity
     * 
     * @return The unique identifier for this entity
     */
    ID getId();
    
    /**
     * Set the unique identifier for this entity.
     * 
     * MUTABILITY CONSIDERATION:
     * - Some designs prefer immutable identifiers
     * - This method allows for mutable identifiers
     * - Useful for entities created without initial ID
     * - Database-generated IDs need this flexibility
     * 
     * VALIDATION:
     * - Implementations should validate identifier
     * - Should throw exception for null or invalid IDs
     * - Should maintain entity invariants
     * 
     * @param id The unique identifier to set
     * @throws IllegalArgumentException if id is null or invalid
     */
    void setId(ID id);
    
    /**
     * Default method to check if entity has a valid identifier.
     * 
     * JAVA 8+ DEFAULT METHODS:
     * - Provides default implementation in interface
     * - Allows interface evolution without breaking implementations
     * - Enables utility methods in interfaces
     * - Reduces code duplication across implementations
     * 
     * UTILITY METHOD:
     * - Convenient for validation logic
     * - Consistent across all implementations
     * - Can be overridden if needed
     * 
     * @return true if entity has a non-null identifier
     */
    default boolean hasId() {
        return getId() != null;
    }
    
    /**
     * Default method to check if this entity has the same ID as another.
     * 
     * EQUALITY COMPARISON:
     * - Compares entities by their identifiers
     * - Useful for entity identity checking
     * - Handles null safety
     * - Type-safe comparison
     * 
     * BOUNDED GENERICS:
     * - ? extends Identifiable<ID> allows any subtype
     * - Maintains type safety for ID comparison
     * - Supports polymorphic comparisons
     * 
     * @param other The other identifiable entity to compare with
     * @return true if both entities have the same non-null identifier
     */
    default boolean sameId(Identifiable<ID> other) {
        return hasId() && other != null && other.hasId() && 
               getId().equals(other.getId());
    }
}