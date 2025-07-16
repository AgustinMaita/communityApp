package com.community.communityApp.model;

import java.time.LocalDate;
import java.util.*;

/**
 * Resident class representing a community member who lives in a specific apartment.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Inheritance (extends Person)
 * - Collections Framework (List, Set, Map)
 * - Generics with collections
 * - Enum for type safety
 * - Wrapper classes vs primitives
 * - Java 8+ Optional for null safety
 * - Method overriding
 * 
 * INHERITANCE RELATIONSHIP:
 * Resident extends Person, inheriting all Person attributes and methods
 * while adding resident-specific functionality like apartment management
 * and service requests.
 * 
 * DESIGN PATTERNS:
 * - Composition: Resident HAS-A list of service requests (composition)
 * - Inheritance: Resident IS-A Person (inheritance)
 * - Enum: ResidentStatus provides type-safe constants
 */
public class Resident extends Person {
    
    /**
     * ENUM demonstration for type safety
     * 
     * BENEFITS of ENUM:
     * - Type safety: can't use invalid status values
     * - Compile-time checking
     * - Built-in methods like values(), valueOf()
     * - Can have methods and fields
     */
    public enum ResidentStatus {
        ACTIVE("Active resident with all privileges"),
        INACTIVE("Temporarily inactive resident"),
        PENDING("New resident pending approval"),
        SUSPENDED("Resident with suspended privileges");
        
        private final String description;
        
        // Enum constructor and methods
        ResidentStatus(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * VARIABLES & DATA TYPES demonstration:
     * - String for apartment number (not int because apartments can be "A1", "B2", etc.)
     * - Integer wrapper class for flexible unit count
     * - Collections with generics for type safety
     * - Enum for status
     * - Optional for nullable phone number
     */
    private String apartmentNumber;
    private Integer unitCount; // Wrapper allows null for unknown unit count
    private ResidentStatus status;
    private Optional<String> phoneNumber; // Optional demonstrates null safety
    
    /**
     * COLLECTIONS FRAMEWORK demonstration:
     * Different collection types chosen for specific purposes
     */
    private List<String> serviceRequests;     // Ordered list, allows duplicates
    private Set<String> emergencyContacts;    // Unique contacts only
    private Map<String, String> preferences;  // Key-value pairs for settings
    
    /**
     * Constructor demonstrating inheritance and super() call
     * 
     * INHERITANCE mechanics:
     * - Must call super() to initialize parent class
     * - Additional parameters for Resident-specific fields
     * - Initialization of collections in constructor
     */
    public Resident(String name, String email, LocalDate birthDate, 
                   String apartmentNumber, Integer unitCount) {
        // Call parent constructor - this MUST be first statement
        super(name, email, birthDate);
        
        // Validate resident-specific parameters
        if (apartmentNumber == null || apartmentNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Apartment number cannot be null or empty");
        }
        
        this.apartmentNumber = apartmentNumber.trim().toUpperCase();
        this.unitCount = unitCount;
        this.status = ResidentStatus.ACTIVE; // Default status
        this.phoneNumber = Optional.empty(); // Initially no phone number
        
        // Initialize collections - important to avoid NullPointerException
        this.serviceRequests = new ArrayList<>();
        this.emergencyContacts = new HashSet<>();
        this.preferences = new HashMap<>();
        
        // Set default preferences
        initializeDefaultPreferences();
    }
    
    /**
     * Private method to set up default preferences
     * 
     * COLLECTIONS FRAMEWORK - MAP usage:
     * - Key-value pairs for configuration
     * - String keys for setting names
     * - String values for setting values
     */
    private void initializeDefaultPreferences() {
        preferences.put("email_notifications", "true");
        preferences.put("sms_notifications", "false");
        preferences.put("newsletter_subscription", "true");
        preferences.put("maintenance_alerts", "true");
    }
    
    /**
     * Method overriding demonstration
     * 
     * POLYMORPHISM:
     * - Overrides abstract method from Person
     * - Provides Resident-specific implementation
     * - Can be called polymorphically through Person reference
     */
    @Override
    public String getRole() {
        return "Resident";
    }
    
    /**
     * Method overriding with extension
     * 
     * INHERITANCE best practice:
     * - Calls super.getDisplayInfo() to reuse parent logic
     * - Adds resident-specific information
     * - Demonstrates extending vs replacing parent behavior
     */
    @Override
    public String getDisplayInfo() {
        String baseInfo = super.getDisplayInfo();
        return String.format("%s, Apartment: %s, Units: %s, Status: %s", 
                           baseInfo, apartmentNumber, 
                           unitCount != null ? unitCount : "Unknown",
                           status.getDescription());
    }
    
    /**
     * COLLECTIONS FRAMEWORK - List operations
     * 
     * LIST characteristics:
     * - Ordered collection
     * - Allows duplicates
     * - Indexed access
     * - Dynamic size
     */
    public void addServiceRequest(String request) {
        if (request == null || request.trim().isEmpty()) {
            throw new IllegalArgumentException("Service request cannot be null or empty");
        }
        serviceRequests.add(request.trim());
    }
    
    /**
     * COLLECTIONS FRAMEWORK - List retrieval with defensive copying
     * 
     * DEFENSIVE PROGRAMMING:
     * - Returns new ArrayList to prevent external modification
     * - Protects internal state
     * - Demonstrates encapsulation principle
     */
    public List<String> getServiceRequests() {
        return new ArrayList<>(serviceRequests);
    }
    
    /**
     * COLLECTIONS FRAMEWORK - Set operations
     * 
     * SET characteristics:
     * - No duplicates allowed
     * - Unordered (for HashSet)
     * - Fast lookup/insertion
     * - Perfect for unique emergency contacts
     */
    public void addEmergencyContact(String contact) {
        if (contact == null || contact.trim().isEmpty()) {
            throw new IllegalArgumentException("Emergency contact cannot be null or empty");
        }
        emergencyContacts.add(contact.trim());
    }
    
    public Set<String> getEmergencyContacts() {
        return new HashSet<>(emergencyContacts); // Defensive copy
    }
    
    /**
     * COLLECTIONS FRAMEWORK - Map operations
     * 
     * MAP characteristics:
     * - Key-value pairs
     * - Fast lookup by key
     * - No duplicate keys
     * - Perfect for configuration/preferences
     */
    public void setPreference(String key, String value) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Preference key cannot be null or empty");
        }
        preferences.put(key.trim(), value != null ? value.trim() : "");
    }
    
    public Optional<String> getPreference(String key) {
        return Optional.ofNullable(preferences.get(key));
    }
    
    public Map<String, String> getAllPreferences() {
        return new HashMap<>(preferences); // Defensive copy
    }
    
    /**
     * JAVA 8+ OPTIONAL demonstration
     * 
     * OPTIONAL benefits:
     * - Explicit handling of nullable values
     * - Prevents NullPointerException
     * - Encourages defensive programming
     * - More expressive than null checks
     */
    public Optional<String> getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            this.phoneNumber = Optional.empty();
        } else {
            this.phoneNumber = Optional.of(phone.trim());
        }
    }
    
    /**
     * WRAPPER CLASSES vs PRIMITIVES demonstration
     * 
     * Why Integer instead of int:
     * - Can be null (unknown unit count)
     * - More flexible for optional values
     * - Better integration with generics
     * - Allows for "not set" vs "zero" distinction
     */
    public Integer getUnitCount() {
        return unitCount;
    }
    
    public void setUnitCount(Integer unitCount) {
        this.unitCount = unitCount;
    }
    
    // Standard getters and setters
    public String getApartmentNumber() {
        return apartmentNumber;
    }
    
    public void setApartmentNumber(String apartmentNumber) {
        if (apartmentNumber == null || apartmentNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Apartment number cannot be null or empty");
        }
        this.apartmentNumber = apartmentNumber.trim().toUpperCase();
    }
    
    public ResidentStatus getStatus() {
        return status;
    }
    
    public void setStatus(ResidentStatus status) {
        this.status = status != null ? status : ResidentStatus.ACTIVE;
    }
    
    /**
     * OBJECT CLASS METHODS override
     * 
     * COLLECTIONS FRAMEWORK requirement:
     * - Resident objects need proper equals/hashCode for collections
     * - Uses apartmentNumber as unique identifier
     * - Extends parent equals logic
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false; // Check parent equality first
        if (getClass() != obj.getClass()) return false;
        
        Resident resident = (Resident) obj;
        return Objects.equals(apartmentNumber, resident.apartmentNumber);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), apartmentNumber);
    }
    
    @Override
    public String toString() {
        return String.format("Resident{%s, apartment='%s', units=%s, status=%s}", 
                           super.toString(), apartmentNumber, unitCount, status);
    }
}