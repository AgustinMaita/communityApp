package com.community.communityApp.exception;

/**
 * Exception thrown when service operations fail.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Exception inheritance and specialization
 * - Custom exception for service-related errors
 * - Exception constructor patterns
 * - Business logic validation through exceptions
 * - State-based exception handling
 * 
 * EXCEPTION DESIGN:
 * - Extends CommunityAppException for consistency
 * - Specific to service management operations
 * - Provides context about service and operation
 * - Includes service state information
 * 
 * WHEN TO THROW:
 * - Invalid service state transitions
 * - Service scheduling conflicts
 * - Service provider unavailability
 * - Service completion failures
 * - Service cancellation errors
 * 
 * SERVICE LIFECYCLE:
 * REQUESTED → SCHEDULED → IN_PROGRESS → COMPLETED
 *     ↓           ↓           ↓
 * CANCELLED   CANCELLED   FAILED
 */
public class ServiceException extends CommunityAppException {
    
    /**
     * SERIALIZATION support
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * SERVICE CONTEXT information
     * 
     * SERVICE DETAILS:
     * - Stores information about the service and operation
     * - Enables specific error handling
     * - Provides debugging information
     * - Supports user-friendly error messages
     */
    private final String serviceId;
    private final String operation;
    private final String currentState;
    
    /**
     * Default constructor
     * 
     * CONSTRUCTOR CHAINING:
     * - Provides default error message
     * - Sets appropriate error code
     * - Calls parent constructor
     */
    public ServiceException() {
        super("A service operation failed", "SERVICE_ERROR");
        this.serviceId = "unknown";
        this.operation = "unknown";
        this.currentState = "unknown";
    }
    
    /**
     * Constructor with basic message
     * 
     * BASIC CONSTRUCTOR:
     * - Custom error message
     * - Specific error code
     * - Default service information
     */
    public ServiceException(String message) {
        super(message, "SERVICE_ERROR");
        this.serviceId = "unknown";
        this.operation = "unknown";
        this.currentState = "unknown";
    }
    
    /**
     * Constructor with message and cause
     * 
     * EXCEPTION CHAINING:
     * - Preserves original exception
     * - Maintains stack trace
     * - Useful for wrapped exceptions
     */
    public ServiceException(String message, Throwable cause) {
        super(message, "SERVICE_ERROR", cause);
        this.serviceId = "unknown";
        this.operation = "unknown";
        this.currentState = "unknown";
    }
    
    /**
     * Constructor with service context
     * 
     * DETAILED CONSTRUCTOR:
     * - Specifies service and operation details
     * - Includes current service state
     * - Generates descriptive error message
     * - Most useful for debugging
     */
    public ServiceException(String serviceId, String operation, String currentState) {
        super(String.format("Service operation '%s' failed for service '%s' in state '%s'", 
                          operation, serviceId, currentState), 
              "SERVICE_ERROR");
        this.serviceId = serviceId != null ? serviceId : "unknown";
        this.operation = operation != null ? operation : "unknown";
        this.currentState = currentState != null ? currentState : "unknown";
    }
    
    /**
     * Constructor with custom error code
     * 
     * CUSTOM ERROR CODE:
     * - Allows specific error categorization
     * - Enables fine-grained error handling
     * - Supports different service error types
     */
    public ServiceException(String message, String errorCode, String serviceId, 
                          String operation, String currentState) {
        super(message, errorCode);
        this.serviceId = serviceId != null ? serviceId : "unknown";
        this.operation = operation != null ? operation : "unknown";
        this.currentState = currentState != null ? currentState : "unknown";
    }
    
    /**
     * Full constructor with all parameters
     * 
     * COMPLETE CONSTRUCTOR:
     * - Maximum flexibility
     * - Custom message with service context
     * - Exception chaining support
     * - Custom error code
     */
    public ServiceException(String message, String errorCode, String serviceId, 
                          String operation, String currentState, Throwable cause) {
        super(message, errorCode, cause);
        this.serviceId = serviceId != null ? serviceId : "unknown";
        this.operation = operation != null ? operation : "unknown";
        this.currentState = currentState != null ? currentState : "unknown";
    }
    
    /**
     * Get the service ID that caused the error
     * 
     * SERVICE IDENTIFICATION:
     * - Identifies which service failed
     * - Useful for programmatic error handling
     * - Enables service-specific error messages
     * 
     * @return The ID of the service that caused the error
     */
    public String getServiceId() {
        return serviceId;
    }
    
    /**
     * Get the operation that failed
     * 
     * OPERATION IDENTIFICATION:
     * - Identifies which operation failed
     * - Useful for error categorization
     * - Enables operation-specific handling
     * 
     * @return The operation that failed
     */
    public String getOperation() {
        return operation;
    }
    
    /**
     * Get the current state of the service
     * 
     * STATE INFORMATION:
     * - Current state when error occurred
     * - Useful for state transition errors
     * - Helps with debugging
     * 
     * @return The current state of the service
     */
    public String getCurrentState() {
        return currentState;
    }
    
    /**
     * Check if error is for a specific operation
     * 
     * UTILITY METHOD:
     * - Convenient for operation-specific handling
     * - Case-insensitive comparison
     * - Null-safe operation
     * 
     * @param op The operation to check
     * @return true if the error is for the specified operation
     */
    public boolean isOperation(String op) {
        return operation != null && operation.equalsIgnoreCase(op);
    }
    
    /**
     * Get user-friendly error message
     * 
     * USER-FRIENDLY MESSAGES:
     * - Provides clear explanation for end users
     * - Hides technical implementation details
     * - Suggests possible solutions
     * - Formatted for display in UI
     * 
     * @return User-friendly error message
     */
    public String getUserFriendlyMessage() {
        return switch (operation.toLowerCase()) {
            case "schedule" -> String.format(
                "Unable to schedule service '%s'. %s", 
                serviceId, getScheduleErrorMessage());
            case "start" -> String.format(
                "Unable to start service '%s'. The service must be scheduled first.",
                serviceId);
            case "complete" -> String.format(
                "Unable to complete service '%s'. The service must be in progress.",
                serviceId);
            case "cancel" -> String.format(
                "Unable to cancel service '%s'. %s", 
                serviceId, getCancelErrorMessage());
            default -> String.format(
                "Service operation failed for service '%s'. Please try again later.",
                serviceId);
        };
    }
    
    /**
     * Get specific error message for schedule operations
     * 
     * SCHEDULE ERROR HANDLING:
     * - State-specific error messages
     * - Helpful suggestions
     * - Clear explanations
     */
    private String getScheduleErrorMessage() {
        return switch (currentState.toLowerCase()) {
            case "scheduled" -> "The service is already scheduled.";
            case "in_progress" -> "The service is currently in progress.";
            case "completed" -> "The service has already been completed.";
            case "cancelled" -> "The service has been cancelled.";
            default -> "The service is not in a valid state for scheduling.";
        };
    }
    
    /**
     * Get specific error message for cancel operations
     * 
     * CANCEL ERROR HANDLING:
     * - State-specific error messages
     * - Clear explanations
     * - Terminal state handling
     */
    private String getCancelErrorMessage() {
        return switch (currentState.toLowerCase()) {
            case "completed" -> "The service has already been completed and cannot be cancelled.";
            case "cancelled" -> "The service is already cancelled.";
            case "failed" -> "The service has failed and cannot be cancelled.";
            default -> "The service cannot be cancelled in its current state.";
        };
    }
    
    /**
     * STATIC FACTORY METHODS for common scenarios
     * 
     * FACTORY PATTERN:
     * - Convenient creation methods
     * - Descriptive method names
     * - Common use case shortcuts
     * - Consistent error messages
     */
    
    /**
     * Factory method for state transition errors
     * 
     * STATE TRANSITION ERROR:
     * - Most common service error
     * - Invalid state transitions
     * - Clear error message
     * 
     * @param serviceId The service ID
     * @param fromState The current state
     * @param toState The attempted state
     * @return ServiceException for state transition error
     */
    public static ServiceException stateTransitionError(String serviceId, 
                                                      String fromState, String toState) {
        String message = String.format(
            "Invalid state transition for service '%s': cannot go from '%s' to '%s'",
            serviceId, fromState, toState);
        return new ServiceException(message, "INVALID_STATE_TRANSITION", 
                                  serviceId, "state_transition", fromState);
    }
    
    /**
     * Factory method for scheduling conflicts
     * 
     * SCHEDULING CONFLICT:
     * - Time slot conflicts
     * - Resource unavailability
     * - Provider conflicts
     * 
     * @param serviceId The service ID
     * @param conflictReason The reason for the conflict
     * @return ServiceException for scheduling conflict
     */
    public static ServiceException schedulingConflict(String serviceId, String conflictReason) {
        String message = String.format(
            "Scheduling conflict for service '%s': %s",
            serviceId, conflictReason);
        return new ServiceException(message, "SCHEDULING_CONFLICT", 
                                  serviceId, "schedule", "REQUESTED");
    }
    
    /**
     * Factory method for service completion failures
     * 
     * COMPLETION FAILURE:
     * - Service could not be completed
     * - Technical or business failures
     * - Requires manual intervention
     * 
     * @param serviceId The service ID
     * @param failureReason The reason for the failure
     * @return ServiceException for completion failure
     */
    public static ServiceException completionFailure(String serviceId, String failureReason) {
        String message = String.format(
            "Service '%s' could not be completed: %s",
            serviceId, failureReason);
        return new ServiceException(message, "COMPLETION_FAILURE", 
                                  serviceId, "complete", "IN_PROGRESS");
    }
    
    /**
     * Factory method for provider unavailability
     * 
     * PROVIDER UNAVAILABLE:
     * - Service provider not available
     * - Scheduling conflicts
     * - Resource constraints
     * 
     * @param serviceId The service ID
     * @param providerName The unavailable provider
     * @return ServiceException for provider unavailability
     */
    public static ServiceException providerUnavailable(String serviceId, String providerName) {
        String message = String.format(
            "Provider '%s' is not available for service '%s'",
            providerName, serviceId);
        return new ServiceException(message, "PROVIDER_UNAVAILABLE", 
                                  serviceId, "schedule", "REQUESTED");
    }
    
    /**
     * OBJECT CLASS METHODS override
     * 
     * Enhanced toString() with service information
     * 
     * DEBUGGING SUPPORT:
     * - Includes service details in string representation
     * - Useful for logging and debugging
     * - Consistent format
     */
    @Override
    public String toString() {
        return String.format("%s [%s]: %s (Service: %s, Operation: %s, State: %s)", 
                           getClass().getSimpleName(), 
                           getErrorCode(), 
                           getMessage(),
                           serviceId,
                           operation,
                           currentState);
    }
    
    /**
     * Enhanced detailed message with service information
     * 
     * DETAILED INFORMATION:
     * - Extends parent's detailed message
     * - Adds service-specific information
     * - Comprehensive debugging data
     */
    @Override
    public String getDetailedMessage() {
        return String.format("%s, Service ID: %s, Operation: %s, Current State: %s", 
                           super.getDetailedMessage(), serviceId, operation, currentState);
    }
}