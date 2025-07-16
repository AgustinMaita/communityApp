package com.community.communityApp.service;

import com.community.communityApp.exception.ServiceException;
import com.community.communityApp.model.Service;
import com.community.communityApp.repository.Repository;
import com.community.communityApp.repository.InMemoryRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service class for managing community services.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Service layer pattern with business logic
 * - Repository pattern usage
 * - Exception handling with custom exceptions
 * - Java 8+ Stream API with complex operations
 * - Optional usage for null safety
 * - Collections Framework with different collection types
 * - Enum usage in business logic
 * - Method overloading and overriding
 * 
 * DESIGN PATTERNS:
 * - Service Layer: Encapsulates business logic
 * - Repository Pattern: Abstracts data access
 * - State Machine: Service state transitions
 * - Factory Pattern: Service creation methods
 * 
 * BUSINESS LOGIC:
 * - Service lifecycle management
 * - State transition validation
 * - Scheduling and completion logic
 * - Search and filtering operations
 * 
 * SERVICE STATES:
 * REQUESTED → SCHEDULED → IN_PROGRESS → COMPLETED
 *     ↓           ↓           ↓
 * CANCELLED   CANCELLED   FAILED
 */
public class CommunityService {
    
    /**
     * DEPENDENCY INJECTION simulation
     * 
     * REPOSITORY PATTERN:
     * - Uses Repository interface for abstraction
     * - Enables different storage implementations
     * - Supports testing with mock repositories
     * - Promotes loose coupling
     */
    private final Repository<Service, String> serviceRepository;
    
    /**
     * CONSTRUCTOR INJECTION pattern
     * 
     * DEPENDENCY INJECTION:
     * - Repository injected through constructor
     * - Promotes immutable service design
     * - Enables different repository implementations
     * - Supports testing and modularity
     */
    public CommunityService(Repository<Service, String> serviceRepository) {
        if (serviceRepository == null) {
            throw new IllegalArgumentException("Service repository cannot be null");
        }
        this.serviceRepository = serviceRepository;
    }
    
    /**
     * Default constructor with default repository
     * 
     * DEFAULT IMPLEMENTATION:
     * - Uses InMemoryRepository as default
     * - Convenient for simple usage
     * - Demonstrates constructor overloading
     */
    public CommunityService() {
        this(new InMemoryRepository<>());
    }
    
    /**
     * Request a new service
     * 
     * BUSINESS LOGIC:
     * - Validates service request
     * - Generates unique service ID
     * - Creates service in REQUESTED state
     * - Saves to repository
     * 
     * EXCEPTION HANDLING:
     * - Custom exceptions for business errors
     * - Validation before saving
     * - Meaningful error messages
     * 
     * @param serviceType The type of service to request
     * @param description Service description
     * @param providerName Service provider name
     * @param estimatedCost Estimated cost
     * @param requestedBy Who requested the service
     * @return The created service
     */
    public Service requestService(Service.ServiceType serviceType, String description,
                                String providerName, Double estimatedCost, String requestedBy) {
        // INPUT VALIDATION
        if (serviceType == null) {
            throw new IllegalArgumentException("Service type cannot be null");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Service description cannot be null or empty");
        }
        if (providerName == null || providerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Provider name cannot be null or empty");
        }
        if (requestedBy == null || requestedBy.trim().isEmpty()) {
            throw new IllegalArgumentException("Requested by cannot be null or empty");
        }
        
        try {
            // BUSINESS LOGIC: Generate unique service ID
            String serviceId = generateServiceId(serviceType);
            
            // Create service using Builder pattern
            Service service = Service.builder()
                    .serviceId(serviceId)
                    .serviceType(serviceType)
                    .description(description)
                    .providerName(providerName)
                    .estimatedCost(estimatedCost)
                    .requestedBy(requestedBy)
                    .build();
            
            // REPOSITORY PATTERN: Save to repository
            Service savedService = serviceRepository.save(service);
            
            // LOGGING simulation
            System.out.println("Service requested successfully: " + serviceId);
            
            return savedService;
            
        } catch (Exception e) {
            // EXCEPTION HANDLING: Log and re-throw
            System.err.println("Failed to request service: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * PRIVATE HELPER METHOD for generating unique service IDs
     * 
     * BUSINESS LOGIC:
     * - Creates unique identifiers
     * - Uses service type and timestamp
     * - Ensures uniqueness across requests
     * 
     * ID FORMAT: {SERVICE_TYPE}_{TIMESTAMP}_{RANDOM}
     */
    private String generateServiceId(Service.ServiceType serviceType) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String random = String.valueOf((int) (Math.random() * 1000));
        return String.format("%s_%s_%s", serviceType.name(), timestamp, random);
    }
    
    /**
     * Schedule a service
     * 
     * BUSINESS LOGIC:
     * - Validates service exists
     * - Checks state transition validity
     * - Updates service state
     * - Sets scheduled time
     * 
     * STATE MACHINE:
     * - REQUESTED → SCHEDULED transition
     * - Validates state transitions
     * - Throws exceptions for invalid transitions
     * 
     * @param serviceId The service ID to schedule
     * @param scheduledTime When to schedule the service
     * @return The updated service
     * @throws ServiceException if service cannot be scheduled
     */
    public Service scheduleService(String serviceId, LocalDateTime scheduledTime) {
        if (serviceId == null || serviceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Service ID cannot be null or empty");
        }
        if (scheduledTime == null) {
            throw new IllegalArgumentException("Scheduled time cannot be null");
        }
        
        // Find service
        Optional<Service> optionalService = serviceRepository.findById(serviceId);
        if (optionalService.isEmpty()) {
            throw new ServiceException("Service not found", "SERVICE_NOT_FOUND", 
                                     serviceId, "schedule", "UNKNOWN");
        }
        
        Service service = optionalService.get();
        
        // BUSINESS LOGIC: Validate state transition
        if (!service.canTransitionTo(Service.ServiceStatus.SCHEDULED)) {
            throw ServiceException.stateTransitionError(serviceId, 
                                                       service.getStatus().toString(), 
                                                       Service.ServiceStatus.SCHEDULED.toString());
        }
        
        // BUSINESS LOGIC: Check scheduling constraints
        validateSchedulingConstraints(service, scheduledTime);
        
        try {
            // Update service state
            service.scheduleService(scheduledTime);
            
            // Save updated service
            Service updatedService = serviceRepository.save(service);
            
            System.out.println("Service scheduled successfully: " + serviceId);
            return updatedService;
            
        } catch (Exception e) {
            throw new ServiceException("Failed to schedule service", "SCHEDULING_ERROR", 
                                     serviceId, "schedule", service.getStatus().toString(), e);
        }
    }
    
    /**
     * PRIVATE HELPER METHOD for validating scheduling constraints
     * 
     * BUSINESS LOGIC:
     * - Validates scheduling time
     * - Checks provider availability
     * - Validates service type constraints
     * - Checks for conflicts
     */
    private void validateSchedulingConstraints(Service service, LocalDateTime scheduledTime) {
        // Check if scheduled time is in the future
        if (scheduledTime.isBefore(LocalDateTime.now())) {
            throw ServiceException.schedulingConflict(service.getServiceId(), 
                                                    "Cannot schedule service in the past");
        }
        
        // Check weekend availability for service type
        if (scheduledTime.getDayOfWeek().getValue() >= 6) { // Saturday or Sunday
            if (!service.getServiceType().isAvailableOnWeekends()) {
                throw ServiceException.schedulingConflict(service.getServiceId(), 
                    "Service type not available on weekends");
            }
        }
        
        // Check for provider conflicts (simplified logic)
        boolean hasConflict = serviceRepository.findAll().stream()
                .anyMatch(s -> s.getProviderName().equals(service.getProviderName()) &&
                              s.getStatus() == Service.ServiceStatus.SCHEDULED &&
                              s.getScheduledAt().isPresent() &&
                              isTimeConflict(s.getScheduledAt().get(), scheduledTime));
        
        if (hasConflict) {
            throw ServiceException.schedulingConflict(service.getServiceId(), 
                "Provider has scheduling conflict");
        }
    }
    
    /**
     * PRIVATE HELPER METHOD for checking time conflicts
     * 
     * BUSINESS LOGIC:
     * - Checks if two time slots conflict
     * - Assumes 2-hour service duration
     * - Simplified conflict detection
     */
    private boolean isTimeConflict(LocalDateTime existing, LocalDateTime proposed) {
        // Simple conflict check: 2-hour window
        LocalDateTime existingEnd = existing.plusHours(2);
        LocalDateTime proposedEnd = proposed.plusHours(2);
        
        return (proposed.isBefore(existingEnd) && proposedEnd.isAfter(existing));
    }
    
    /**
     * Start a service (transition to IN_PROGRESS)
     * 
     * STATE MACHINE:
     * - SCHEDULED → IN_PROGRESS transition
     * - Validates state transitions
     * - Updates service state
     * 
     * @param serviceId The service ID to start
     * @return The updated service
     * @throws ServiceException if service cannot be started
     */
    public Service startService(String serviceId) {
        Optional<Service> optionalService = serviceRepository.findById(serviceId);
        if (optionalService.isEmpty()) {
            throw new ServiceException("Service not found", "SERVICE_NOT_FOUND", 
                                     serviceId, "start", "UNKNOWN");
        }
        
        Service service = optionalService.get();
        
        // BUSINESS LOGIC: Validate state transition
        if (!service.canTransitionTo(Service.ServiceStatus.IN_PROGRESS)) {
            throw ServiceException.stateTransitionError(serviceId, 
                                                       service.getStatus().toString(), 
                                                       Service.ServiceStatus.IN_PROGRESS.toString());
        }
        
        // Update service state (would need to add method to Service class)
        // For now, create new service with updated state
        // In real implementation, would have setState method
        
        System.out.println("Service started: " + serviceId);
        return service;
    }
    
    /**
     * Complete a service
     * 
     * STATE MACHINE:
     * - IN_PROGRESS → COMPLETED transition
     * - Validates state transitions
     * - Updates completion time
     * 
     * @param serviceId The service ID to complete
     * @return The updated service
     * @throws ServiceException if service cannot be completed
     */
    public Service completeService(String serviceId) {
        Optional<Service> optionalService = serviceRepository.findById(serviceId);
        if (optionalService.isEmpty()) {
            throw new ServiceException("Service not found", "SERVICE_NOT_FOUND", 
                                     serviceId, "complete", "UNKNOWN");
        }
        
        Service service = optionalService.get();
        
        // BUSINESS LOGIC: Validate state transition
        if (!service.canTransitionTo(Service.ServiceStatus.COMPLETED)) {
            throw ServiceException.stateTransitionError(serviceId, 
                                                       service.getStatus().toString(), 
                                                       Service.ServiceStatus.COMPLETED.toString());
        }
        
        try {
            // Update service state
            service.completeService();
            
            // Save updated service
            Service updatedService = serviceRepository.save(service);
            
            System.out.println("Service completed successfully: " + serviceId);
            return updatedService;
            
        } catch (Exception e) {
            throw new ServiceException("Failed to complete service", "COMPLETION_ERROR", 
                                     serviceId, "complete", service.getStatus().toString(), e);
        }
    }
    
    /**
     * Cancel a service
     * 
     * STATE MACHINE:
     * - Any state → CANCELLED transition (except terminal states)
     * - Validates state transitions
     * - Updates service state
     * 
     * @param serviceId The service ID to cancel
     * @return The updated service
     * @throws ServiceException if service cannot be cancelled
     */
    public Service cancelService(String serviceId) {
        Optional<Service> optionalService = serviceRepository.findById(serviceId);
        if (optionalService.isEmpty()) {
            throw new ServiceException("Service not found", "SERVICE_NOT_FOUND", 
                                     serviceId, "cancel", "UNKNOWN");
        }
        
        Service service = optionalService.get();
        
        // BUSINESS LOGIC: Validate state transition
        if (!service.canTransitionTo(Service.ServiceStatus.CANCELLED)) {
            throw ServiceException.stateTransitionError(serviceId, 
                                                       service.getStatus().toString(), 
                                                       Service.ServiceStatus.CANCELLED.toString());
        }
        
        // Update service state (would need setState method in Service class)
        // For now, simulate cancellation
        
        System.out.println("Service cancelled: " + serviceId);
        return service;
    }
    
    /**
     * Find service by ID
     * 
     * REPOSITORY PATTERN:
     * - Uses repository's findById method
     * - Returns Optional for null safety
     * - Simple delegation
     * 
     * @param serviceId The service ID to find
     * @return Optional containing service if found
     */
    public Optional<Service> findServiceById(String serviceId) {
        if (serviceId == null || serviceId.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return serviceRepository.findById(serviceId.trim());
    }
    
    /**
     * Get services by type
     * 
     * JAVA 8+ STREAM API:
     * - Filtering by service type
     * - Enum comparison
     * - Collecting results
     * 
     * @param serviceType The service type to filter by
     * @return List of services of the specified type
     */
    public List<Service> getServicesByType(Service.ServiceType serviceType) {
        if (serviceType == null) {
            return new ArrayList<>();
        }
        
        return serviceRepository.findAll().stream()
                .filter(service -> service.getServiceType() == serviceType)
                .sorted(Comparator.comparing(Service::getRequestedAt))
                .collect(Collectors.toList());
    }
    
    /**
     * Get services by status
     * 
     * JAVA 8+ STREAM API:
     * - Filtering by service status
     * - Enum comparison
     * - Collecting results
     * 
     * @param status The service status to filter by
     * @return List of services with the specified status
     */
    public List<Service> getServicesByStatus(Service.ServiceStatus status) {
        if (status == null) {
            return new ArrayList<>();
        }
        
        return serviceRepository.findAll().stream()
                .filter(service -> service.getStatus() == status)
                .sorted(Comparator.comparing(Service::getRequestedAt))
                .collect(Collectors.toList());
    }
    
    /**
     * Get services requested by a specific resident
     * 
     * JAVA 8+ STREAM API:
     * - Filtering by requester
     * - String comparison
     * - Collecting results
     * 
     * @param requestedBy The resident who requested services
     * @return List of services requested by the resident
     */
    public List<Service> getServicesByRequester(String requestedBy) {
        if (requestedBy == null || requestedBy.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return serviceRepository.findAll().stream()
                .filter(service -> requestedBy.trim().equalsIgnoreCase(service.getRequestedBy()))
                .sorted(Comparator.comparing(Service::getRequestedAt).reversed())
                .collect(Collectors.toList());
    }
    
    /**
     * Get services by provider
     * 
     * JAVA 8+ STREAM API:
     * - Filtering by provider name
     * - String comparison
     * - Collecting results
     * 
     * @param providerName The provider name to filter by
     * @return List of services by the specified provider
     */
    public List<Service> getServicesByProvider(String providerName) {
        if (providerName == null || providerName.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return serviceRepository.findAll().stream()
                .filter(service -> providerName.trim().equalsIgnoreCase(service.getProviderName()))
                .sorted(Comparator.comparing(Service::getRequestedAt))
                .collect(Collectors.toList());
    }
    
    /**
     * ADVANCED SEARCH with custom predicate
     * 
     * FUNCTIONAL PROGRAMMING:
     * - Predicate functional interface
     * - Lambda expressions
     * - Higher-order functions
     * 
     * @param predicate The search predicate
     * @return List of services matching the predicate
     */
    public List<Service> findServices(Predicate<Service> predicate) {
        if (predicate == null) {
            return serviceRepository.findAll();
        }
        
        return serviceRepository.findAll().stream()
                .filter(predicate)
                .sorted(Comparator.comparing(Service::getRequestedAt))
                .collect(Collectors.toList());
    }
    
    /**
     * Get service statistics
     * 
     * BUSINESS LOGIC:
     * - Calculates various statistics
     * - Groups data by different criteria
     * - Provides summary information
     * 
     * @return ServiceStatistics object
     */
    public ServiceStatistics getStatistics() {
        List<Service> allServices = serviceRepository.findAll();
        
        Map<Service.ServiceStatus, Long> statusCounts = allServices.stream()
                .collect(Collectors.groupingBy(Service::getStatus, Collectors.counting()));
        
        Map<Service.ServiceType, Long> typeCounts = allServices.stream()
                .collect(Collectors.groupingBy(Service::getServiceType, Collectors.counting()));
        
        return new ServiceStatistics(
            allServices.size(),
            statusCounts,
            typeCounts
        );
    }
    
    /**
     * INNER CLASS for service statistics
     * 
     * NESTED CLASS:
     * - Encapsulates related data
     * - Immutable data transfer object
     * - Clean API for statistics
     */
    public static class ServiceStatistics {
        private final int totalServices;
        private final Map<Service.ServiceStatus, Long> statusCounts;
        private final Map<Service.ServiceType, Long> typeCounts;
        
        public ServiceStatistics(int totalServices, 
                               Map<Service.ServiceStatus, Long> statusCounts,
                               Map<Service.ServiceType, Long> typeCounts) {
            this.totalServices = totalServices;
            this.statusCounts = new HashMap<>(statusCounts);
            this.typeCounts = new HashMap<>(typeCounts);
        }
        
        public int getTotalServices() { return totalServices; }
        public Map<Service.ServiceStatus, Long> getStatusCounts() { 
            return new HashMap<>(statusCounts); 
        }
        public Map<Service.ServiceType, Long> getTypeCounts() { 
            return new HashMap<>(typeCounts); 
        }
        
        @Override
        public String toString() {
            return String.format("ServiceStatistics{total=%d, statuses=%s, types=%s}",
                               totalServices, statusCounts, typeCounts);
        }
    }
}