package com.community.communityApp.service;

import com.community.communityApp.exception.DuplicateResidentException;
import com.community.communityApp.exception.ResidentNotFoundException;
import com.community.communityApp.model.Resident;
import com.community.communityApp.repository.Repository;
import com.community.communityApp.repository.InMemoryRepository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service class for managing resident operations.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Service layer pattern with business logic
 * - Repository pattern usage
 * - Exception handling with try-catch-finally
 * - Java 8+ Stream API with lambdas
 * - Optional usage for null safety
 * - Collections Framework operations
 * - Generic programming with bounded types
 * - Method overloading
 * 
 * DESIGN PATTERNS:
 * - Service Layer: Encapsulates business logic
 * - Repository Pattern: Abstracts data access
 * - Dependency Injection: Uses repository interface
 * - Exception Translation: Converts low-level to high-level exceptions
 * 
 * BUSINESS LOGIC:
 * - Resident registration and management
 * - Duplicate detection and prevention
 * - Search and filtering operations
 * - Validation and business rules
 * 
 * THREAD SAFETY:
 * - Uses thread-safe repository implementation
 * - Stateless service design
 * - Safe for concurrent access
 */
public class ResidentService {
    
    /**
     * DEPENDENCY INJECTION simulation
     * 
     * REPOSITORY PATTERN:
     * - Uses Repository interface for abstraction
     * - Enables different storage implementations
     * - Supports testing with mock repositories
     * - Promotes loose coupling
     */
    private final Repository<Resident, String> residentRepository;
    
    /**
     * CONSTRUCTOR INJECTION pattern
     * 
     * DEPENDENCY INJECTION:
     * - Repository injected through constructor
     * - Promotes immutable service design
     * - Enables different repository implementations
     * - Supports testing and modularity
     */
    public ResidentService(Repository<Resident, String> residentRepository) {
        if (residentRepository == null) {
            throw new IllegalArgumentException("Resident repository cannot be null");
        }
        this.residentRepository = residentRepository;
    }
    
    /**
     * Default constructor with default repository
     * 
     * DEFAULT IMPLEMENTATION:
     * - Uses InMemoryRepository as default
     * - Convenient for simple usage
     * - Demonstrates constructor overloading
     */
    public ResidentService() {
        this(new InMemoryRepository<>());
    }
    
    /**
     * Register a new resident in the system
     * 
     * BUSINESS LOGIC:
     * - Validates resident data
     * - Checks for duplicates
     * - Saves to repository
     * - Handles exceptions
     * 
     * EXCEPTION HANDLING:
     * - Custom exceptions for business errors
     * - Validation before saving
     * - Meaningful error messages
     * 
     * @param resident The resident to register
     * @return The registered resident
     * @throws DuplicateResidentException if resident already exists
     * @throws IllegalArgumentException if resident data is invalid
     */
    public Resident registerResident(Resident resident) {
        // INPUT VALIDATION
        if (resident == null) {
            throw new IllegalArgumentException("Resident cannot be null");
        }
        
        try {
            // BUSINESS LOGIC: Check for duplicates
            validateNoDuplicateResident(resident);
            
            // REPOSITORY PATTERN: Save to repository
            Resident savedResident = residentRepository.save(resident);
            
            // LOGGING simulation (in real app, would use logging framework)
            System.out.println("Resident registered successfully: " + savedResident.getName());
            
            return savedResident;
            
        } catch (Exception e) {
            // EXCEPTION HANDLING: Log and re-throw
            System.err.println("Failed to register resident: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * PRIVATE HELPER METHOD for duplicate validation
     * 
     * BUSINESS LOGIC VALIDATION:
     * - Checks email uniqueness
     * - Checks apartment number uniqueness
     * - Throws specific exceptions
     * 
     * EXCEPTION HANDLING:
     * - Different exceptions for different duplicates
     * - Meaningful error messages
     * - Business rule enforcement
     */
    private void validateNoDuplicateResident(Resident resident) {
        // Check email uniqueness
        if (residentRepository.existsById(resident.getId())) {
            throw DuplicateResidentException.forEmail(resident.getEmail());
        }
        
        // Check apartment number uniqueness
        Optional<Resident> existingResident = findByApartmentNumber(resident.getApartmentNumber());
        if (existingResident.isPresent()) {
            throw DuplicateResidentException.forApartment(resident.getApartmentNumber());
        }
    }
    
    /**
     * Find resident by email (unique identifier)
     * 
     * REPOSITORY PATTERN:
     * - Uses repository's findById method
     * - Email is the primary key
     * - Returns Optional for null safety
     * 
     * OPTIONAL USAGE:
     * - Optional<Resident> prevents null pointer exceptions
     * - Forces explicit null handling
     * - More expressive than null returns
     * 
     * @param email The resident's email
     * @return Optional containing resident if found
     */
    public Optional<Resident> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return residentRepository.findById(email.trim().toLowerCase());
    }
    
    /**
     * Find resident by apartment number
     * 
     * JAVA 8+ STREAM API:
     * - Uses stream operations for filtering
     * - Lambda expressions for predicates
     * - Method chaining for readability
     * - Functional programming style
     * 
     * COLLECTIONS FRAMEWORK:
     * - Works with List from repository
     * - Efficient filtering operations
     * - Type-safe operations
     * 
     * @param apartmentNumber The apartment number to search for
     * @return Optional containing resident if found
     */
    public Optional<Resident> findByApartmentNumber(String apartmentNumber) {
        if (apartmentNumber == null || apartmentNumber.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return residentRepository.findAll().stream()
                .filter(resident -> apartmentNumber.trim().equalsIgnoreCase(resident.getApartmentNumber()))
                .findFirst();
    }
    
    /**
     * Search residents by name (partial match)
     * 
     * JAVA 8+ STREAM API demonstration:
     * - Stream operations with filtering
     * - Lambda expressions for string matching
     * - Case-insensitive search
     * - Collecting results to List
     * 
     * FUNCTIONAL PROGRAMMING:
     * - Declarative style
     * - Immutable operations
     * - Method chaining
     * - Expressive predicates
     * 
     * @param namePattern The name pattern to search for
     * @return List of residents matching the pattern
     */
    public List<Resident> searchByName(String namePattern) {
        if (namePattern == null || namePattern.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchPattern = namePattern.trim().toLowerCase();
        
        return residentRepository.findAll().stream()
                .filter(resident -> resident.getName().toLowerCase().contains(searchPattern))
                .sorted(Comparator.comparing(Resident::getName))
                .collect(Collectors.toList());
    }
    
    /**
     * Get all residents sorted by apartment number
     * 
     * JAVA 8+ STREAM API:
     * - Stream operations for sorting
     * - Method reference for comparator
     * - Collecting to List
     * 
     * COLLECTIONS FRAMEWORK:
     * - Sorting with Comparator
     * - Type-safe operations
     * - Immutable result
     * 
     * @return List of all residents sorted by apartment number
     */
    public List<Resident> getAllResidentsSortedByApartment() {
        return residentRepository.findAll().stream()
                .sorted(Comparator.comparing(Resident::getApartmentNumber))
                .collect(Collectors.toList());
    }
    
    /**
     * Get residents by status
     * 
     * ENUM USAGE:
     * - Type-safe status filtering
     * - Enum comparison
     * - Business logic with enums
     * 
     * STREAM API:
     * - Filtering by enum value
     * - Lambda expressions
     * - Collecting results
     * 
     * @param status The resident status to filter by
     * @return List of residents with the specified status
     */
    public List<Resident> getResidentsByStatus(Resident.ResidentStatus status) {
        if (status == null) {
            return new ArrayList<>();
        }
        
        return residentRepository.findAll().stream()
                .filter(resident -> resident.getStatus() == status)
                .sorted(Comparator.comparing(Resident::getName))
                .collect(Collectors.toList());
    }
    
    /**
     * Update resident information
     * 
     * BUSINESS LOGIC:
     * - Validates resident exists
     * - Updates repository
     * - Handles exceptions
     * 
     * EXCEPTION HANDLING:
     * - Throws ResidentNotFoundException if not found
     * - Validates input parameters
     * - Meaningful error messages
     * 
     * @param resident The resident with updated information
     * @return The updated resident
     * @throws ResidentNotFoundException if resident doesn't exist
     */
    public Resident updateResident(Resident resident) {
        if (resident == null) {
            throw new IllegalArgumentException("Resident cannot be null");
        }
        
        // Check if resident exists
        if (!residentRepository.existsById(resident.getId())) {
            throw ResidentNotFoundException.forEmail(resident.getEmail());
        }
        
        // Check for apartment number conflicts (if apartment changed)
        Optional<Resident> existingResident = findByApartmentNumber(resident.getApartmentNumber());
        if (existingResident.isPresent() && !existingResident.get().getId().equals(resident.getId())) {
            throw DuplicateResidentException.forApartment(resident.getApartmentNumber());
        }
        
        return residentRepository.save(resident);
    }
    
    /**
     * Delete resident by email
     * 
     * BUSINESS LOGIC:
     * - Validates resident exists
     * - Deletes from repository
     * - Returns success status
     * 
     * EXCEPTION HANDLING:
     * - Throws ResidentNotFoundException if not found
     * - Validates input parameters
     * 
     * @param email The email of the resident to delete
     * @return true if resident was deleted
     * @throws ResidentNotFoundException if resident doesn't exist
     */
    public boolean deleteResident(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        
        // Check if resident exists
        if (!residentRepository.existsById(email.trim().toLowerCase())) {
            throw ResidentNotFoundException.forEmail(email);
        }
        
        return residentRepository.deleteById(email.trim().toLowerCase());
    }
    
    /**
     * Get total number of residents
     * 
     * REPOSITORY PATTERN:
     * - Delegates to repository
     * - Simple count operation
     * - No business logic needed
     * 
     * @return Total number of residents
     */
    public long getResidentCount() {
        return residentRepository.count();
    }
    
    /**
     * Check if apartment is available
     * 
     * BUSINESS LOGIC:
     * - Checks apartment availability
     * - Useful for apartment assignment
     * - Boolean return for simple check
     * 
     * @param apartmentNumber The apartment number to check
     * @return true if apartment is available
     */
    public boolean isApartmentAvailable(String apartmentNumber) {
        if (apartmentNumber == null || apartmentNumber.trim().isEmpty()) {
            return false;
        }
        
        return findByApartmentNumber(apartmentNumber).isEmpty();
    }
    
    /**
     * Get occupied apartments
     * 
     * JAVA 8+ STREAM API:
     * - Stream operations for mapping
     * - Method reference for property extraction
     * - Collecting to Set for uniqueness
     * 
     * COLLECTIONS FRAMEWORK:
     * - Set for unique apartment numbers
     * - Sorted set for ordered results
     * 
     * @return Set of occupied apartment numbers
     */
    public Set<String> getOccupiedApartments() {
        return residentRepository.findAll().stream()
                .map(Resident::getApartmentNumber)
                .collect(Collectors.toCollection(TreeSet::new));
    }
    
    /**
     * ADVANCED SEARCH with custom predicate
     * 
     * FUNCTIONAL PROGRAMMING:
     * - Predicate functional interface
     * - Lambda expressions
     * - Higher-order functions
     * 
     * GENERICS:
     * - Bounded wildcard types
     * - Type safety with predicates
     * - Flexible search criteria
     * 
     * @param predicate The search predicate
     * @return List of residents matching the predicate
     */
    public List<Resident> findResidents(Predicate<Resident> predicate) {
        if (predicate == null) {
            return getAllResidentsSortedByApartment();
        }
        
        return residentRepository.findAll().stream()
                .filter(predicate)
                .sorted(Comparator.comparing(Resident::getName))
                .collect(Collectors.toList());
    }
    
    /**
     * Get resident statistics
     * 
     * INNER CLASS for statistics
     * 
     * BUSINESS LOGIC:
     * - Calculates various statistics
     * - Groups data by different criteria
     * - Provides summary information
     * 
     * @return ResidentStatistics object
     */
    public ResidentStatistics getStatistics() {
        List<Resident> allResidents = residentRepository.findAll();
        
        return new ResidentStatistics(
            allResidents.size(),
            (int) allResidents.stream().filter(r -> r.getStatus() == Resident.ResidentStatus.ACTIVE).count(),
            (int) allResidents.stream().filter(r -> r.getStatus() == Resident.ResidentStatus.INACTIVE).count(),
            getOccupiedApartments().size()
        );
    }
    
    /**
     * INNER CLASS for resident statistics
     * 
     * NESTED CLASS:
     * - Encapsulates related data
     * - Immutable data transfer object
     * - Clean API for statistics
     * 
     * IMMUTABLE OBJECT:
     * - Final fields
     * - No setters
     * - Thread-safe
     */
    public static class ResidentStatistics {
        private final int totalResidents;
        private final int activeResidents;
        private final int inactiveResidents;
        private final int occupiedApartments;
        
        public ResidentStatistics(int totalResidents, int activeResidents, 
                                int inactiveResidents, int occupiedApartments) {
            this.totalResidents = totalResidents;
            this.activeResidents = activeResidents;
            this.inactiveResidents = inactiveResidents;
            this.occupiedApartments = occupiedApartments;
        }
        
        public int getTotalResidents() { return totalResidents; }
        public int getActiveResidents() { return activeResidents; }
        public int getInactiveResidents() { return inactiveResidents; }
        public int getOccupiedApartments() { return occupiedApartments; }
        
        @Override
        public String toString() {
            return String.format(
                "ResidentStatistics{total=%d, active=%d, inactive=%d, apartments=%d}",
                totalResidents, activeResidents, inactiveResidents, occupiedApartments);
        }
    }
}