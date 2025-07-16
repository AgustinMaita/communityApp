package com.community.communityApp.exception;

/**
 * Exception thrown when attempting to add a resident that already exists.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Exception inheritance and specialization
 * - Custom exception with specific use case
 * - Exception constructor patterns
 * - Business logic validation through exceptions
 * - Exception handling in collections
 * 
 * EXCEPTION DESIGN:
 * - Extends CommunityAppException for consistency
 * - Specific to resident duplication scenarios
 * - Provides meaningful error messages
 * - Includes relevant data (apartment number, email)
 * 
 * WHEN TO THROW:
 * - Adding resident with existing email
 * - Adding resident with existing apartment number
 * - Bulk operations that detect duplicates
 * - Import operations with conflicting data
 * 
 * USAGE EXAMPLES:
 * - ResidentService.addResident() validation
 * - Batch import operations
 * - Data integrity checks
 * - User registration validation
 */
public class DuplicateResidentException extends CommunityAppException {
    
    /**
     * SERIALIZATION support
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * DUPLICATE CONFLICT information
     * 
     * CONFLICT DETAILS:
     * - Stores information about the duplicate conflict
     * - Enables specific error handling
     * - Provides debugging information
     * - Supports user-friendly error messages
     */
    private final String conflictingField;
    private final String conflictingValue;
    
    /**
     * Default constructor
     * 
     * CONSTRUCTOR CHAINING:
     * - Provides default error message
     * - Sets appropriate error code
     * - Calls parent constructor
     */
    public DuplicateResidentException() {
        super("A resident with the same information already exists", "DUPLICATE_RESIDENT");
        this.conflictingField = "unknown";
        this.conflictingValue = "unknown";
    }
    
    /**
     * Constructor with basic message
     * 
     * BASIC CONSTRUCTOR:
     * - Custom error message
     * - Specific error code
     * - Default conflict information
     */
    public DuplicateResidentException(String message) {
        super(message, "DUPLICATE_RESIDENT");
        this.conflictingField = "unknown";
        this.conflictingValue = "unknown";
    }
    
    /**
     * Constructor with message and cause
     * 
     * EXCEPTION CHAINING:
     * - Preserves original exception
     * - Maintains stack trace
     * - Useful for wrapped exceptions
     */
    public DuplicateResidentException(String message, Throwable cause) {
        super(message, "DUPLICATE_RESIDENT", cause);
        this.conflictingField = "unknown";
        this.conflictingValue = "unknown";
    }
    
    /**
     * Constructor with conflict details
     * 
     * DETAILED CONSTRUCTOR:
     * - Specifies which field caused the conflict
     * - Includes the conflicting value
     * - Generates descriptive error message
     * - Most useful for debugging
     */
    public DuplicateResidentException(String conflictingField, String conflictingValue) {
        super(String.format("A resident with %s '%s' already exists", 
                          conflictingField, conflictingValue), 
              "DUPLICATE_RESIDENT");
        this.conflictingField = conflictingField != null ? conflictingField : "unknown";
        this.conflictingValue = conflictingValue != null ? conflictingValue : "unknown";
    }
    
    /**
     * Full constructor with all parameters
     * 
     * COMPLETE CONSTRUCTOR:
     * - Maximum flexibility
     * - Custom message with conflict details
     * - Exception chaining support
     */
    public DuplicateResidentException(String message, String conflictingField, 
                                    String conflictingValue, Throwable cause) {
        super(message, "DUPLICATE_RESIDENT", cause);
        this.conflictingField = conflictingField != null ? conflictingField : "unknown";
        this.conflictingValue = conflictingValue != null ? conflictingValue : "unknown";
    }
    
    /**
     * Get the field that caused the conflict
     * 
     * CONFLICT INFORMATION:
     * - Identifies which field was duplicate
     * - Useful for programmatic error handling
     * - Enables field-specific error messages
     * 
     * @return The name of the conflicting field
     */
    public String getConflictingField() {
        return conflictingField;
    }
    
    /**
     * Get the value that caused the conflict
     * 
     * CONFLICT VALUE:
     * - The actual value that was duplicate
     * - Useful for error messages
     * - Helps with debugging
     * 
     * @return The conflicting value
     */
    public String getConflictingValue() {
        return conflictingValue;
    }
    
    /**
     * Check if conflict is for a specific field
     * 
     * UTILITY METHOD:
     * - Convenient for field-specific handling
     * - Case-insensitive comparison
     * - Null-safe operation
     * 
     * @param field The field name to check
     * @return true if the conflict is for the specified field
     */
    public boolean isConflictingField(String field) {
        return conflictingField != null && conflictingField.equalsIgnoreCase(field);
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
        return switch (conflictingField.toLowerCase()) {
            case "email" -> String.format(
                "A resident with email '%s' is already registered. Please use a different email address.",
                conflictingValue);
            case "apartment", "apartmentnumber" -> String.format(
                "Apartment '%s' is already occupied. Please check the apartment number.",
                conflictingValue);
            case "name" -> String.format(
                "A resident named '%s' already exists. Please verify if this is the same person.",
                conflictingValue);
            default -> String.format(
                "A resident with the same %s already exists. Please use different information.",
                conflictingField);
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
     * Factory method for email conflicts
     * 
     * EMAIL CONFLICT:
     * - Most common duplication scenario
     * - Specific error message
     * - Consistent error code
     * 
     * @param email The conflicting email address
     * @return DuplicateResidentException for email conflict
     */
    public static DuplicateResidentException forEmail(String email) {
        return new DuplicateResidentException("email", email);
    }
    
    /**
     * Factory method for apartment number conflicts
     * 
     * APARTMENT CONFLICT:
     * - Another common duplication scenario
     * - Specific to apartment assignments
     * - Clear error message
     * 
     * @param apartmentNumber The conflicting apartment number
     * @return DuplicateResidentException for apartment conflict
     */
    public static DuplicateResidentException forApartment(String apartmentNumber) {
        return new DuplicateResidentException("apartment", apartmentNumber);
    }
    
    /**
     * Factory method for name conflicts
     * 
     * NAME CONFLICT:
     * - Less common but possible
     * - Might indicate same person registered twice
     * - Requires manual verification
     * 
     * @param name The conflicting name
     * @return DuplicateResidentException for name conflict
     */
    public static DuplicateResidentException forName(String name) {
        return new DuplicateResidentException("name", name);
    }
    
    /**
     * OBJECT CLASS METHODS override
     * 
     * Enhanced toString() with conflict information
     * 
     * DEBUGGING SUPPORT:
     * - Includes conflict details in string representation
     * - Useful for logging and debugging
     * - Consistent format
     */
    @Override
    public String toString() {
        return String.format("%s [%s]: %s (Conflicting %s: '%s')", 
                           getClass().getSimpleName(), 
                           getErrorCode(), 
                           getMessage(),
                           conflictingField,
                           conflictingValue);
    }
    
    /**
     * Enhanced detailed message with conflict information
     * 
     * DETAILED INFORMATION:
     * - Extends parent's detailed message
     * - Adds conflict-specific information
     * - Comprehensive debugging data
     */
    @Override
    public String getDetailedMessage() {
        return String.format("%s, Conflicting Field: %s, Conflicting Value: %s", 
                           super.getDetailedMessage(), conflictingField, conflictingValue);
    }
}