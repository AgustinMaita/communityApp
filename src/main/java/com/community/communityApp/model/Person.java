package com.community.communityApp.model;

import com.community.communityApp.repository.Identifiable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Abstract base class representing a person in the community system.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Abstract classes and inheritance hierarchy
 * - Encapsulation with private fields and public getters/setters
 * - Object class methods (equals, hashCode, toString)
 * - Java 8+ LocalDate for proper date handling
 * - Access modifiers (private, protected, public)
 * 
 * DESIGN RATIONALE:
 * - Abstract because we never create a "generic" person - always a specific type
 * - Contains common attributes shared by all person types in the system
 * - Provides template methods that subclasses can override
 * - Implements proper equals/hashCode contract for collection usage
 * 
 * INHERITANCE STRATEGY:
 * This class serves as the root of our person hierarchy:
 * Person (abstract) → Resident → Administrator
 * 
 * This demonstrates the IS-A relationship: Administrator IS-A Resident IS-A Person
 */
public abstract class Person implements Identifiable<String> {
    
    /**
     * VARIABLES & DATA TYPES demonstration:
     * - Using appropriate data types for each field
     * - String for text data (name, email)
     * - LocalDate for modern date handling (better than java.util.Date)
     * - Integer wrapper class instead of int primitive to allow null values
     */
    private String name;
    private String email;
    private LocalDate birthDate;
    private Integer age; // Wrapper class allows null if age is not provided
    
    /**
     * Protected constructor - only subclasses can create Person instances
     * 
     * ACCESS MODIFIERS explanation:
     * - protected: accessible to subclasses but not to external classes
     * - This enforces that Person cannot be instantiated directly
     * - Subclasses must call this constructor using super()
     */
    protected Person(String name, String email, LocalDate birthDate) {
        // Input validation demonstrating defensive programming
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        this.name = name.trim();
        this.email = email.toLowerCase().trim();
        this.birthDate = birthDate;
        this.age = calculateAge(birthDate);
    }
    
    /**
     * Private utility method for email validation
     * 
     * ENCAPSULATION principle:
     * - This method is internal implementation detail
     * - Other classes don't need to know HOW email validation works
     * - They just need to know that it happens
     */
    private boolean isValidEmail(String email) {
        // Simple email validation - in real applications, use more robust validation
        return email.contains("@") && email.contains(".");
    }
    
    /**
     * Private utility method for age calculation
     * 
     * JAVA 8+ FEATURES demonstration:
     * - Using LocalDate for proper date arithmetic
     * - Could also demonstrate Optional if birthDate was Optional<LocalDate>
     */
    private Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return null;
        }
        return LocalDate.now().getYear() - birthDate.getYear();
    }
    
    /**
     * Abstract method that subclasses must implement
     * 
     * ABSTRACTION principle:
     * - Each person type has a different role in the community
     * - Base class defines the contract but not the implementation
     * - Forces subclasses to provide their own role definition
     */
    public abstract String getRole();
    
    /**
     * Template method that can be overridden by subclasses
     * 
     * INHERITANCE and POLYMORPHISM:
     * - Provides default behavior that subclasses can extend
     * - Subclasses can call super.getDisplayInfo() and add their own info
     * - Demonstrates method overriding vs method overloading
     */
    public String getDisplayInfo() {
        return String.format("Name: %s, Email: %s, Age: %s, Role: %s", 
                           name, email, age != null ? age : "Not provided", getRole());
    }
    
    // ENCAPSULATION: Getter and Setter methods
    // These provide controlled access to private fields
    
    /**
     * Getter for name field
     * 
     * ENCAPSULATION benefits:
     * - Controlled access to private data
     * - Can add validation or logging if needed
     * - Protects internal representation
     */
    public String getName() {
        return name;
    }
    
    /**
     * Setter for name field with validation
     * 
     * DEFENSIVE PROGRAMMING:
     * - Validates input before setting
     * - Maintains object invariants
     * - Prevents invalid state
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email.toLowerCase().trim();
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        this.age = calculateAge(birthDate); // Recalculate age when birthDate changes
    }
    
    public Integer getAge() {
        return age;
    }
    
    /**
     * INTERFACE IMPLEMENTATION - Identifiable interface
     * 
     * GENERIC INTERFACE:
     * - Person implements Identifiable<String>
     * - Uses email as unique identifier
     * - Provides type-safe ID operations
     * - Enables repository pattern usage
     */
    @Override
    public String getId() {
        return email;
    }
    
    @Override
    public void setId(String id) {
        setEmail(id); // Delegate to setEmail for validation
    }
    
    /**
     * OBJECT CLASS METHODS override
     * 
     * These methods are essential for proper object behavior in collections
     * and general object comparison
     */
    
    /**
     * Equals method for object comparison
     * 
     * COLLECTIONS FRAMEWORK importance:
     * - Required for HashSet, HashMap key comparison
     * - Defines when two Person objects are considered equal
     * - Must be consistent with hashCode()
     */
    @Override
    public boolean equals(Object obj) {
        // Standard equals method pattern
        if (this == obj) return true; // Same reference
        if (obj == null || getClass() != obj.getClass()) return false; // Null or different class
        
        Person person = (Person) obj;
        return Objects.equals(name, person.name) && 
               Objects.equals(email, person.email);
    }
    
    /**
     * HashCode method for hash-based collections
     * 
     * COLLECTIONS FRAMEWORK requirement:
     * - Must be consistent with equals()
     * - Objects that are equal must have same hash code
     * - Used by HashMap, HashSet for efficient storage/retrieval
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
    
    /**
     * ToString method for string representation
     * 
     * DEBUGGING and LOGGING:
     * - Provides meaningful string representation
     * - Useful for debugging and logging
     * - Called automatically by println() and string concatenation
     */
    @Override
    public String toString() {
        return String.format("Person{name='%s', email='%s', age=%s}", 
                           name, email, age);
    }
}