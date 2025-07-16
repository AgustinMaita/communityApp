package com.community.communityApp.model;

import com.community.communityApp.repository.Identifiable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Service class representing various services available in the community.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Value objects and immutability patterns
 * - Enum with rich functionality
 * - Collections Framework with different collection types
 * - Generics with bounded types
 * - Java 8+ features (LocalDateTime, Optional, Stream API)
 * - Composition over inheritance
 * - Builder pattern for complex object creation
 * 
 * DESIGN PATTERNS:
 * - Value Object: Immutable objects representing services
 * - Builder Pattern: For complex Service object creation
 * - Strategy Pattern: Different service types with different behaviors
 * 
 * OBJECT-ORIENTED PRINCIPLES:
 * - Encapsulation: Private fields with controlled access
 * - Abstraction: Service interface hiding implementation details
 * - Composition: Service HAS-A provider, schedule, etc.
 */
public class Service implements Identifiable<String> {
    
    /**
     * ENUM with complex functionality
     * 
     * ADVANCED ENUM features:
     * - Enum with constructor, fields, methods
     * - Each enum constant represents a different service type
     * - Demonstrates enum as first-class objects with behavior
     * - Type-safe service categorization
     */
    public enum ServiceType {
        CLEANING("Cleaning Service", "Regular cleaning and maintenance", 2.0, 4.0),
        MAINTENANCE("Maintenance Service", "Repairs and technical support", 1.0, 8.0),
        SECURITY("Security Service", "Security monitoring and patrol", 24.0, 24.0),
        GARDENING("Gardening Service", "Landscaping and plant care", 1.0, 3.0),
        PEST_CONTROL("Pest Control", "Pest prevention and elimination", 0.5, 2.0),
        POOL_MAINTENANCE("Pool Maintenance", "Pool cleaning and chemical balance", 1.0, 2.0),
        WASTE_MANAGEMENT("Waste Management", "Garbage collection and disposal", 1.0, 1.0);
        
        private final String displayName;
        private final String description;
        private final double minDurationHours;
        private final double maxDurationHours;
        
        // Enum constructor
        ServiceType(String displayName, String description, 
                   double minDurationHours, double maxDurationHours) {
            this.displayName = displayName;
            this.description = description;
            this.minDurationHours = minDurationHours;
            this.maxDurationHours = maxDurationHours;
        }
        
        // Enum methods
        public String getDisplayName() { return displayName; }
        public String getDescription() { return description; }
        public double getMinDurationHours() { return minDurationHours; }
        public double getMaxDurationHours() { return maxDurationHours; }
        
        /**
         * CONTROL FLOW demonstration in enum methods
         * 
         * BUSINESS LOGIC in enums:
         * - Each service type has different availability rules
         * - Demonstrates switch expressions with enums
         * - Shows how enums can encapsulate behavior
         */
        public boolean isAvailableOnWeekends() {
            return switch (this) {
                case CLEANING, MAINTENANCE, GARDENING -> true;
                case SECURITY -> true; // 24/7 service
                case PEST_CONTROL, POOL_MAINTENANCE -> false;
                case WASTE_MANAGEMENT -> false; // Weekdays only
            };
        }
    }
    
    /**
     * ENUM for service status
     * 
     * STATE MANAGEMENT:
     * - Represents different states of a service
     * - Finite state machine concept
     * - Type-safe status tracking
     */
    public enum ServiceStatus {
        REQUESTED("Service has been requested"),
        SCHEDULED("Service is scheduled"),
        IN_PROGRESS("Service is currently being performed"),
        COMPLETED("Service has been completed"),
        CANCELLED("Service has been cancelled"),
        FAILED("Service could not be completed");
        
        private final String description;
        
        ServiceStatus(String description) {
            this.description = description;
        }
        
        public String getDescription() { return description; }
    }
    
    /**
     * VARIABLES & DATA TYPES demonstration
     * 
     * FIELD TYPES:
     * - String for textual data
     * - ServiceType enum for type safety
     * - LocalDateTime for modern date/time handling
     * - Double for pricing (wrapper class for null safety)
     * - Collections for related data
     */
    private final String serviceId;
    private final ServiceType serviceType;
    private final String description;
    private final String providerName;
    private final Double estimatedCost;
    private ServiceStatus status;
    private LocalDateTime requestedAt;
    private LocalDateTime scheduledAt;
    private LocalDateTime completedAt;
    private final String requestedBy; // Apartment number
    
    /**
     * COLLECTIONS FRAMEWORK usage
     * 
     * Different collection types for different purposes:
     * - List for ordered requirements
     * - Set for unique tags
     * - Map for key-value metadata
     */
    private final List<String> requirements;
    private final Set<String> tags;
    private final Map<String, Object> metadata;
    
    /**
     * Constructor demonstrating defensive programming
     * 
     * DEFENSIVE PROGRAMMING:
     * - Validates all required parameters
     * - Throws meaningful exceptions
     * - Initializes collections
     * - Sets default values
     */
    public Service(String serviceId, ServiceType serviceType, String description,
                  String providerName, Double estimatedCost, String requestedBy) {
        // Input validation
        if (serviceId == null || serviceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Service ID cannot be null or empty");
        }
        if (serviceType == null) {
            throw new IllegalArgumentException("Service type cannot be null");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (providerName == null || providerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Provider name cannot be null or empty");
        }
        if (requestedBy == null || requestedBy.trim().isEmpty()) {
            throw new IllegalArgumentException("Requested by cannot be null or empty");
        }
        
        this.serviceId = serviceId.trim();
        this.serviceType = serviceType;
        this.description = description.trim();
        this.providerName = providerName.trim();
        this.estimatedCost = estimatedCost;
        this.requestedBy = requestedBy.trim();
        
        // Initialize with default values
        this.status = ServiceStatus.REQUESTED;
        this.requestedAt = LocalDateTime.now();
        
        // Initialize collections
        this.requirements = new ArrayList<>();
        this.tags = new HashSet<>();
        this.metadata = new HashMap<>();
        
        // Add default tag based on service type
        this.tags.add(serviceType.name().toLowerCase());
    }
    
    /**
     * BUILDER PATTERN implementation
     * 
     * BUILDER PATTERN benefits:
     * - Fluent interface for object creation
     * - Handles complex object construction
     * - Optional parameters without multiple constructors
     * - Immutable objects with flexible creation
     */
    public static class ServiceBuilder {
        private String serviceId;
        private ServiceType serviceType;
        private String description;
        private String providerName;
        private Double estimatedCost;
        private String requestedBy;
        private List<String> requirements = new ArrayList<>();
        private Set<String> tags = new HashSet<>();
        
        public ServiceBuilder serviceId(String serviceId) {
            this.serviceId = serviceId;
            return this;
        }
        
        public ServiceBuilder serviceType(ServiceType serviceType) {
            this.serviceType = serviceType;
            return this;
        }
        
        public ServiceBuilder description(String description) {
            this.description = description;
            return this;
        }
        
        public ServiceBuilder providerName(String providerName) {
            this.providerName = providerName;
            return this;
        }
        
        public ServiceBuilder estimatedCost(Double estimatedCost) {
            this.estimatedCost = estimatedCost;
            return this;
        }
        
        public ServiceBuilder requestedBy(String requestedBy) {
            this.requestedBy = requestedBy;
            return this;
        }
        
        public ServiceBuilder addRequirement(String requirement) {
            this.requirements.add(requirement);
            return this;
        }
        
        public ServiceBuilder addTag(String tag) {
            this.tags.add(tag);
            return this;
        }
        
        public Service build() {
            Service service = new Service(serviceId, serviceType, description, 
                                        providerName, estimatedCost, requestedBy);
            service.requirements.addAll(this.requirements);
            service.tags.addAll(this.tags);
            return service;
        }
    }
    
    /**
     * Factory method for creating ServiceBuilder
     * 
     * FACTORY PATTERN:
     * - Encapsulates object creation logic
     * - Provides clean API for complex object creation
     * - Enables fluent interface
     */
    public static ServiceBuilder builder() {
        return new ServiceBuilder();
    }
    
    /**
     * COLLECTIONS FRAMEWORK - List operations
     * 
     * LIST usage for requirements:
     * - Ordered list of service requirements
     * - Allows duplicates if needed
     * - Indexed access for specific requirements
     */
    public void addRequirement(String requirement) {
        if (requirement != null && !requirement.trim().isEmpty()) {
            requirements.add(requirement.trim());
        }
    }
    
    public List<String> getRequirements() {
        return new ArrayList<>(requirements); // Defensive copy
    }
    
    /**
     * COLLECTIONS FRAMEWORK - Set operations
     * 
     * SET usage for tags:
     * - Unique tags only
     * - Fast lookup and insertion
     * - No ordering needed
     */
    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty()) {
            tags.add(tag.trim().toLowerCase());
        }
    }
    
    public Set<String> getTags() {
        return new HashSet<>(tags); // Defensive copy
    }
    
    /**
     * COLLECTIONS FRAMEWORK - Map operations
     * 
     * MAP usage for metadata:
     * - Key-value pairs for flexible data
     * - String keys for property names
     * - Object values for any data type
     */
    public void setMetadata(String key, Object value) {
        if (key != null && !key.trim().isEmpty()) {
            metadata.put(key.trim(), value);
        }
    }
    
    public Optional<Object> getMetadata(String key) {
        return Optional.ofNullable(metadata.get(key));
    }
    
    /**
     * GENERICS with type safety
     * 
     * TYPE-SAFE METADATA retrieval:
     * - Generic method with type parameter
     * - Runtime type checking
     * - Returns Optional for null safety
     */
    public <T> Optional<T> getTypedMetadata(String key, Class<T> type) {
        Object value = metadata.get(key);
        if (value != null && type.isInstance(value)) {
            return Optional.of(type.cast(value));
        }
        return Optional.empty();
    }
    
    /**
     * CONTROL FLOW with business logic
     * 
     * STATE TRANSITIONS:
     * - Validates state transitions
     * - Demonstrates complex conditional logic
     * - Shows practical use of switch statements
     */
    public boolean canTransitionTo(ServiceStatus newStatus) {
        return switch (status) {
            case REQUESTED -> newStatus == ServiceStatus.SCHEDULED || 
                             newStatus == ServiceStatus.CANCELLED;
            case SCHEDULED -> newStatus == ServiceStatus.IN_PROGRESS || 
                             newStatus == ServiceStatus.CANCELLED;
            case IN_PROGRESS -> newStatus == ServiceStatus.COMPLETED || 
                               newStatus == ServiceStatus.FAILED;
            case COMPLETED, CANCELLED, FAILED -> false; // Terminal states
        };
    }
    
    /**
     * JAVA 8+ FEATURES demonstration
     * 
     * OPTIONAL usage:
     * - Handles nullable timestamps
     * - Provides default values
     * - Demonstrates functional programming concepts
     */
    public void scheduleService(LocalDateTime scheduledTime) {
        if (scheduledTime == null) {
            throw new IllegalArgumentException("Scheduled time cannot be null");
        }
        if (!canTransitionTo(ServiceStatus.SCHEDULED)) {
            throw new IllegalStateException("Cannot schedule service in current state: " + status);
        }
        
        this.scheduledAt = scheduledTime;
        this.status = ServiceStatus.SCHEDULED;
    }
    
    public void completeService() {
        if (!canTransitionTo(ServiceStatus.COMPLETED)) {
            throw new IllegalStateException("Cannot complete service in current state: " + status);
        }
        
        this.completedAt = LocalDateTime.now();
        this.status = ServiceStatus.COMPLETED;
    }
    
    /**
     * JAVA 8+ STREAM API demonstration
     * 
     * STREAM operations:
     * - Filtering collections
     * - Method chaining
     * - Collecting results
     */
    public List<String> getRequirementsContaining(String keyword) {
        return requirements.stream()
                .filter(req -> req.toLowerCase().contains(keyword.toLowerCase()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    // Standard getters (immutable object pattern)
    public String getServiceId() { return serviceId; }
    public ServiceType getServiceType() { return serviceType; }
    public String getDescription() { return description; }
    public String getProviderName() { return providerName; }
    public Double getEstimatedCost() { return estimatedCost; }
    public ServiceStatus getStatus() { return status; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
    public Optional<LocalDateTime> getScheduledAt() { return Optional.ofNullable(scheduledAt); }
    public Optional<LocalDateTime> getCompletedAt() { return Optional.ofNullable(completedAt); }
    public String getRequestedBy() { return requestedBy; }
    
    /**
     * INTERFACE IMPLEMENTATION - Identifiable interface
     * 
     * GENERIC INTERFACE:
     * - Service implements Identifiable<String>
     * - Uses serviceId as unique identifier
     * - Provides type-safe ID operations
     * - Enables repository pattern usage
     */
    @Override
    public String getId() {
        return serviceId;
    }
    
    @Override
    public void setId(String id) {
        throw new UnsupportedOperationException("Service ID cannot be changed after creation");
    }
    
    /**
     * OBJECT CLASS METHODS override
     * 
     * COLLECTIONS FRAMEWORK requirement:
     * - Service objects need proper equals/hashCode
     * - Uses serviceId as unique identifier
     * - Maintains equals/hashCode contract
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Service service = (Service) obj;
        return Objects.equals(serviceId, service.serviceId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(serviceId);
    }
    
    @Override
    public String toString() {
        return String.format("Service{id='%s', type=%s, status=%s, provider='%s', cost=%s}", 
                           serviceId, serviceType, status, providerName, estimatedCost);
    }
}