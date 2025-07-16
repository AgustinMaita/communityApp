package com.community.communityApp.exception;

/**
 * Base exception class for all Community Application exceptions.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Exception hierarchy and inheritance
 * - Custom exception classes
 * - Exception constructors and chaining
 * - Exception handling best practices
 * - Checked vs unchecked exceptions
 * 
 * EXCEPTION DESIGN PATTERNS:
 * - Exception hierarchy: All custom exceptions extend this base
 * - Consistent error handling across the application
 * - Meaningful error messages and causes
 * - Proper exception chaining for debugging
 * 
 * INHERITANCE HIERARCHY:
 * RuntimeException (unchecked)
 *   ↓
 * CommunityAppException (unchecked)
 *   ↓
 * Specific exceptions (DuplicateResidentException, etc.)
 * 
 * DESIGN RATIONALE:
 * - Extends RuntimeException for unchecked exceptions
 * - Provides common functionality for all app exceptions
 * - Enables polymorphic exception handling
 * - Includes cause chaining for debugging
 * 
 * WHEN TO USE:
 * - Base class for all application-specific exceptions
 * - When you need common exception behavior
 * - For polymorphic catch blocks
 * - When building exception hierarchies
 */
public class CommunityAppException extends RuntimeException {
    
    /**
     * SERIALIZATION support
     * 
     * SERIALIZATION:
     * - serialVersionUID for version control
     * - Ensures compatibility across versions
     * - Required for Serializable classes
     * - Exception classes are Serializable by default
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * ERROR CODE support for categorizing exceptions
     * 
     * ERROR CODES:
     * - Helps with internationalization
     * - Enables programmatic error handling
     * - Useful for logging and monitoring
     * - Allows client-side error categorization
     */
    private final String errorCode;
    
    /**
     * Default constructor
     * 
     * CONSTRUCTOR CHAINING:
     * - Calls this() constructor with default message
     * - Demonstrates constructor overloading
     * - Provides default behavior
     */
    public CommunityAppException() {
        this("An error occurred in the Community Application");
    }
    
    /**
     * Constructor with message
     * 
     * EXCEPTION MESSAGES:
     * - Descriptive error messages for debugging
     * - Human-readable error information
     * - Calls super() to initialize parent class
     * - Sets default error code
     */
    public CommunityAppException(String message) {
        super(message);
        this.errorCode = "GENERAL_ERROR";
    }
    
    /**
     * Constructor with message and cause
     * 
     * EXCEPTION CHAINING:
     * - Preserves original exception information
     * - Maintains stack trace from root cause
     * - Enables debugging of nested exceptions
     * - Calls super() with both message and cause
     */
    public CommunityAppException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "GENERAL_ERROR";
    }
    
    /**
     * Constructor with cause only
     * 
     * CAUSE-ONLY CONSTRUCTOR:
     * - Uses cause's message as default
     * - Preserves original exception
     * - Useful for wrapping checked exceptions
     */
    public CommunityAppException(Throwable cause) {
        super(cause);
        this.errorCode = "GENERAL_ERROR";
    }
    
    /**
     * Constructor with message and error code
     * 
     * CUSTOM ERROR CODES:
     * - Categorizes exceptions by type
     * - Enables programmatic error handling
     * - Useful for client applications
     * - Supports internationalization
     */
    public CommunityAppException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode != null ? errorCode : "GENERAL_ERROR";
    }
    
    /**
     * Full constructor with all parameters
     * 
     * COMPLETE CONSTRUCTOR:
     * - Maximum flexibility for exception creation
     * - Supports all exception features
     * - Used by subclasses for initialization
     */
    public CommunityAppException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode != null ? errorCode : "GENERAL_ERROR";
    }
    
    /**
     * Get the error code for this exception
     * 
     * GETTER METHOD:
     * - Provides access to error code
     * - Enables programmatic error handling
     * - Used by logging and monitoring systems
     * 
     * @return The error code for this exception
     */
    public String getErrorCode() {
        return errorCode;
    }
    
    /**
     * Check if this exception has a specific error code
     * 
     * UTILITY METHOD:
     * - Convenient for error code checking
     * - Handles null safety
     * - Case-insensitive comparison
     * - Returns boolean for conditional logic
     * 
     * @param code The error code to check
     * @return true if this exception has the specified error code
     */
    public boolean hasErrorCode(String code) {
        return errorCode != null && errorCode.equalsIgnoreCase(code);
    }
    
    /**
     * Get detailed error information
     * 
     * DEBUGGING SUPPORT:
     * - Provides comprehensive error details
     * - Includes message, error code, and cause
     * - Useful for logging and debugging
     * - Formatted for readability
     * 
     * @return Detailed error information string
     */
    public String getDetailedMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Error Code: ").append(errorCode);
        sb.append(", Message: ").append(getMessage());
        
        if (getCause() != null) {
            sb.append(", Cause: ").append(getCause().getClass().getSimpleName());
            sb.append(" - ").append(getCause().getMessage());
        }
        
        return sb.toString();
    }
    
    /**
     * OBJECT CLASS METHODS override
     * 
     * toString() for debugging and logging
     * 
     * DEBUGGING SUPPORT:
     * - Provides meaningful string representation
     * - Includes error code and message
     * - Used by logging frameworks
     * - Consistent format across exceptions
     */
    @Override
    public String toString() {
        return String.format("%s [%s]: %s", 
                           getClass().getSimpleName(), errorCode, getMessage());
    }
    
    /**
     * Static factory method for creating exceptions
     * 
     * FACTORY PATTERN:
     * - Alternative to constructors
     * - More descriptive method names
     * - Can return subclasses
     * - Enables fluent exception creation
     * 
     * @param message The exception message
     * @param errorCode The error code
     * @return New CommunityAppException instance
     */
    public static CommunityAppException create(String message, String errorCode) {
        return new CommunityAppException(message, errorCode);
    }
    
    /**
     * Static factory method for wrapping other exceptions
     * 
     * EXCEPTION WRAPPING:
     * - Converts checked exceptions to unchecked
     * - Preserves original exception information
     * - Maintains stack trace
     * - Enables consistent error handling
     * 
     * @param cause The original exception
     * @return New CommunityAppException wrapping the cause
     */
    public static CommunityAppException wrap(Throwable cause) {
        if (cause instanceof CommunityAppException) {
            return (CommunityAppException) cause;
        }
        return new CommunityAppException(cause.getMessage(), "WRAPPED_EXCEPTION", cause);
    }
}