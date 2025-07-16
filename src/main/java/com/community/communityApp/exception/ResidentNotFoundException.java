package com.community.communityApp.exception;

/**
 * Exception thrown when a requested resident cannot be found.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Exception inheritance and specialization
 * - Custom exception for specific business scenarios
 * - Exception constructor patterns
 * - Optional vs Exception for handling missing data
 * - Exception handling in search operations
 * 
 * EXCEPTION DESIGN:
 * - Extends CommunityAppException for consistency
 * - Specific to resident lookup failures
 * - Provides context about what was searched for
 * - Includes search criteria information
 * 
 * WHEN TO THROW:
 * - Looking up resident by email (not found)
 * - Looking up resident by apartment number (not found)
 * - Updating non-existent resident
 * - Deleting non-existent resident
 * - Accessing resident-specific operations
 * 
 * DESIGN ALTERNATIVES:
 * - Could use Optional<Resident> instead of exceptions
 * - Exceptions better for error conditions
 * - Optional better for normal "not found" scenarios
 * - Choice depends on use case and API design
 */
public class ResidentNotFoundException extends CommunityAppException {
    
    /**
     * SERIALIZATION support
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * SEARCH CRITERIA information
     * 
     * SEARCH CONTEXT:
     * - Stores information about what was searched for
     * - Enables specific error handling
     * - Provides debugging information
     * - Supports user-friendly error messages
     */
    private final String searchField;
    private final String searchValue;
    
    /**
     * Default constructor
     * 
     * CONSTRUCTOR CHAINING:
     * - Provides default error message
     * - Sets appropriate error code
     * - Calls parent constructor
     */
    public ResidentNotFoundException() {
        super("The requested resident could not be found", "RESIDENT_NOT_FOUND");
        this.searchField = "unknown";
        this.searchValue = "unknown";
    }
    
    /**
     * Constructor with basic message
     * 
     * BASIC CONSTRUCTOR:
     * - Custom error message
     * - Specific error code
     * - Default search information
     */
    public ResidentNotFoundException(String message) {
        super(message, "RESIDENT_NOT_FOUND");
        this.searchField = "unknown";
        this.searchValue = "unknown";
    }
    
    /**
     * Constructor with message and cause
     * 
     * EXCEPTION CHAINING:
     * - Preserves original exception
     * - Maintains stack trace
     * - Useful for wrapped exceptions
     */
    public ResidentNotFoundException(String message, Throwable cause) {
        super(message, "RESIDENT_NOT_FOUND", cause);
        this.searchField = "unknown";
        this.searchValue = "unknown";
    }
    
    /**
     * Constructor with search criteria
     * 
     * DETAILED CONSTRUCTOR:
     * - Specifies what was searched for
     * - Includes the search value
     * - Generates descriptive error message
     * - Most useful for debugging
     */
    public ResidentNotFoundException(String searchField, String searchValue) {
        super(String.format("No resident found with %s '%s'", 
                          searchField, searchValue), 
              "RESIDENT_NOT_FOUND");
        this.searchField = searchField != null ? searchField : "unknown";
        this.searchValue = searchValue != null ? searchValue : "unknown";
    }
    
    /**
     * Full constructor with all parameters
     * 
     * COMPLETE CONSTRUCTOR:
     * - Maximum flexibility
     * - Custom message with search criteria
     * - Exception chaining support
     */
    public ResidentNotFoundException(String message, String searchField, 
                                   String searchValue, Throwable cause) {
        super(message, "RESIDENT_NOT_FOUND", cause);
        this.searchField = searchField != null ? searchField : "unknown";
        this.searchValue = searchValue != null ? searchValue : "unknown";
    }
    
    /**
     * Get the field that was searched
     * 
     * SEARCH INFORMATION:
     * - Identifies which field was used for search
     * - Useful for programmatic error handling
     * - Enables field-specific error messages
     * 
     * @return The name of the field that was searched
     */
    public String getSearchField() {
        return searchField;
    }
    
    /**
     * Get the value that was searched for
     * 
     * SEARCH VALUE:
     * - The actual value that was searched
     * - Useful for error messages
     * - Helps with debugging
     * 
     * @return The value that was searched for
     */
    public String getSearchValue() {
        return searchValue;
    }
    
    /**
     * Check if search was for a specific field
     * 
     * UTILITY METHOD:
     * - Convenient for field-specific handling
     * - Case-insensitive comparison
     * - Null-safe operation
     * 
     * @param field The field name to check
     * @return true if the search was for the specified field
     */
    public boolean isSearchField(String field) {
        return searchField != null && searchField.equalsIgnoreCase(field);
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
        return switch (searchField.toLowerCase()) {
            case "email" -> String.format(
                "No resident found with email '%s'. Please check the email address and try again.",
                searchValue);
            case "apartment", "apartmentnumber" -> String.format(
                "No resident found in apartment '%s'. Please verify the apartment number.",
                searchValue);
            case "name" -> String.format(
                "No resident found with name '%s'. Please check the spelling and try again.",
                searchValue);
            case "id" -> String.format(
                "No resident found with ID '%s'. The resident may have been removed.",
                searchValue);
            default -> String.format(
                "No resident found matching the search criteria (%s: '%s').",
                searchField, searchValue);
        };
    }
    
    /**
     * Get suggestions for resolving the error
     * 
     * HELPFUL SUGGESTIONS:
     * - Provides actionable suggestions
     * - Helps users resolve the issue
     * - Context-specific advice
     * - Improves user experience
     * 
     * @return Suggestions for resolving the error
     */
    public String getSuggestions() {
        return switch (searchField.toLowerCase()) {
            case "email" -> 
                "Suggestions:\n" +
                "• Check for typos in the email address\n" +
                "• Verify the email is registered in the system\n" +
                "• Try searching by apartment number instead";
            case "apartment", "apartmentnumber" -> 
                "Suggestions:\n" +
                "• Verify the apartment number format (e.g., '101', 'A1', 'B-205')\n" +
                "• Check if the apartment is occupied\n" +
                "• Try searching by resident name or email instead";
            case "name" -> 
                "Suggestions:\n" +
                "• Check the spelling of the name\n" +
                "• Try searching by first name only\n" +
                "• Try searching by email or apartment number instead";
            default -> 
                "Suggestions:\n" +
                "• Verify the search criteria\n" +
                "• Check if the resident is registered in the system\n" +
                "• Try a different search method";
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
     * Factory method for email search failures
     * 
     * EMAIL SEARCH:
     * - Most common search scenario
     * - Specific error message
     * - Consistent error code
     * 
     * @param email The email that was not found
     * @return ResidentNotFoundException for email search failure
     */
    public static ResidentNotFoundException forEmail(String email) {
        return new ResidentNotFoundException("email", email);
    }
    
    /**
     * Factory method for apartment number search failures
     * 
     * APARTMENT SEARCH:
     * - Another common search scenario
     * - Specific to apartment lookups
     * - Clear error message
     * 
     * @param apartmentNumber The apartment number that was not found
     * @return ResidentNotFoundException for apartment search failure
     */
    public static ResidentNotFoundException forApartment(String apartmentNumber) {
        return new ResidentNotFoundException("apartment", apartmentNumber);
    }
    
    /**
     * Factory method for name search failures
     * 
     * NAME SEARCH:
     * - Less precise search method
     * - Might have multiple matches
     * - Requires careful handling
     * 
     * @param name The name that was not found
     * @return ResidentNotFoundException for name search failure
     */
    public static ResidentNotFoundException forName(String name) {
        return new ResidentNotFoundException("name", name);
    }
    
    /**
     * Factory method for ID search failures
     * 
     * ID SEARCH:
     * - Most precise search method
     * - Usually indicates deleted resident
     * - Technical search scenario
     * 
     * @param id The ID that was not found
     * @return ResidentNotFoundException for ID search failure
     */
    public static ResidentNotFoundException forId(String id) {
        return new ResidentNotFoundException("id", id);
    }
    
    /**
     * OBJECT CLASS METHODS override
     * 
     * Enhanced toString() with search information
     * 
     * DEBUGGING SUPPORT:
     * - Includes search details in string representation
     * - Useful for logging and debugging
     * - Consistent format
     */
    @Override
    public String toString() {
        return String.format("%s [%s]: %s (Searched %s: '%s')", 
                           getClass().getSimpleName(), 
                           getErrorCode(), 
                           getMessage(),
                           searchField,
                           searchValue);
    }
    
    /**
     * Enhanced detailed message with search information
     * 
     * DETAILED INFORMATION:
     * - Extends parent's detailed message
     * - Adds search-specific information
     * - Comprehensive debugging data
     */
    @Override
    public String getDetailedMessage() {
        return String.format("%s, Search Field: %s, Search Value: %s", 
                           super.getDetailedMessage(), searchField, searchValue);
    }
}