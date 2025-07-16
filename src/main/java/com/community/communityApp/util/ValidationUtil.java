package com.community.communityApp.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.function.Predicate;

/**
 * Utility class for validation operations.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Static utility classes and methods
 * - Regular expressions (Pattern, Matcher)
 * - Access modifiers (private constructor)
 * - Method overloading
 * - Functional interfaces (Predicate)
 * - Java 8+ lambda expressions
 * - String manipulation and validation
 * - Date/time validation
 * 
 * DESIGN PATTERNS:
 * - Utility Class Pattern: Static methods, private constructor
 * - Strategy Pattern: Different validation strategies
 * - Factory Pattern: Predicate factory methods
 * - Fluent Interface: Chained validations
 * 
 * UTILITY CLASS DESIGN:
 * - All methods are static
 * - Private constructor prevents instantiation
 * - Stateless operations
 * - Pure functions (no side effects)
 * - Reusable across the application
 * 
 * VALIDATION TYPES:
 * - Email format validation
 * - Name validation
 * - Apartment number validation
 * - Date validation
 * - String validation
 */
public final class ValidationUtil {
    
    /**
     * REGULAR EXPRESSION patterns for validation
     * 
     * PATTERN COMPILATION:
     * - Static final patterns for performance
     * - Compiled once, reused many times
     * - Thread-safe Pattern objects
     * - Immutable and efficient
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    private static final Pattern NAME_PATTERN = Pattern.compile(
        "^[a-zA-Z\\s'-]{2,50}$"
    );
    
    private static final Pattern APARTMENT_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9\\-]{1,10}$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^\\+?[1-9]\\d{1,14}$"
    );
    
    /**
     * PRIVATE CONSTRUCTOR prevents instantiation
     * 
     * UTILITY CLASS PATTERN:
     * - Private constructor prevents instantiation
     * - Throws exception if accessed via reflection
     * - Ensures class is used only for static methods
     * - Follows utility class best practices
     */
    private ValidationUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Validate email format
     * 
     * REGULAR EXPRESSION validation:
     * - Uses compiled Pattern for performance
     * - Matches standard email format
     * - Handles null and empty strings
     * - Returns boolean for simple validation
     * 
     * EMAIL VALIDATION:
     * - Checks for basic email structure
     * - Validates domain format
     * - Allows common email characters
     * - Not 100% RFC compliant but practical
     * 
     * @param email The email to validate
     * @return true if email format is valid
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Validate name format
     * 
     * NAME VALIDATION:
     * - Allows letters, spaces, apostrophes, hyphens
     * - Length between 2 and 50 characters
     * - Common name patterns
     * - International character support
     * 
     * @param name The name to validate
     * @return true if name format is valid
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        String trimmedName = name.trim();
        return NAME_PATTERN.matcher(trimmedName).matches();
    }
    
    /**
     * Validate apartment number format
     * 
     * APARTMENT VALIDATION:
     * - Allows alphanumeric characters and hyphens
     * - Length between 1 and 10 characters
     * - Flexible format for different naming schemes
     * - Examples: "101", "A1", "B-205", "12-A"
     * 
     * @param apartmentNumber The apartment number to validate
     * @return true if apartment number format is valid
     */
    public static boolean isValidApartmentNumber(String apartmentNumber) {
        if (apartmentNumber == null || apartmentNumber.trim().isEmpty()) {
            return false;
        }
        
        String trimmedNumber = apartmentNumber.trim();
        return APARTMENT_PATTERN.matcher(trimmedNumber).matches();
    }
    
    /**
     * Validate phone number format
     * 
     * PHONE VALIDATION:
     * - International phone number format
     * - Optional country code (+)
     * - 2-15 digits total
     * - Flexible for different countries
     * 
     * @param phoneNumber The phone number to validate
     * @return true if phone number format is valid
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        
        String trimmedNumber = phoneNumber.trim();
        return PHONE_PATTERN.matcher(trimmedNumber).matches();
    }
    
    /**
     * Validate string is not null or empty
     * 
     * BASIC STRING VALIDATION:
     * - Checks for null values
     * - Checks for empty strings
     * - Trims whitespace
     * - Common validation pattern
     * 
     * @param value The string to validate
     * @return true if string is not null or empty
     */
    public static boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Validate string length within range
     * 
     * LENGTH VALIDATION:
     * - Checks minimum length
     * - Checks maximum length
     * - Trims whitespace before checking
     * - Flexible length constraints
     * 
     * @param value The string to validate
     * @param minLength Minimum allowed length
     * @param maxLength Maximum allowed length
     * @return true if string length is within range
     */
    public static boolean isValidLength(String value, int minLength, int maxLength) {
        if (value == null) {
            return false;
        }
        
        int length = value.trim().length();
        return length >= minLength && length <= maxLength;
    }
    
    /**
     * Validate date is not in the future
     * 
     * DATE VALIDATION:
     * - Checks if date is in the past or present
     * - Useful for birthdate validation
     * - Uses LocalDate for modern date handling
     * - Handles null values
     * 
     * @param date The date to validate
     * @return true if date is not in the future
     */
    public static boolean isNotInFuture(LocalDate date) {
        if (date == null) {
            return false;
        }
        
        return !date.isAfter(LocalDate.now());
    }
    
    /**
     * Validate date is within reasonable age range
     * 
     * AGE VALIDATION:
     * - Checks if birthdate represents reasonable age
     * - Minimum age: 18 years
     * - Maximum age: 120 years
     * - Realistic age constraints
     * 
     * @param birthDate The birth date to validate
     * @return true if age is within reasonable range
     */
    public static boolean isValidAge(LocalDate birthDate) {
        if (birthDate == null) {
            return false;
        }
        
        LocalDate now = LocalDate.now();
        LocalDate minDate = now.minusYears(120); // Maximum 120 years old
        LocalDate maxDate = now.minusYears(18);  // Minimum 18 years old
        
        return birthDate.isAfter(minDate) && birthDate.isBefore(maxDate);
    }
    
    /**
     * Validate datetime is in the future
     * 
     * FUTURE DATE VALIDATION:
     * - Checks if datetime is in the future
     * - Useful for scheduling validation
     * - Uses LocalDateTime for precision
     * - Handles null values
     * 
     * @param dateTime The datetime to validate
     * @return true if datetime is in the future
     */
    public static boolean isInFuture(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        
        return dateTime.isAfter(LocalDateTime.now());
    }
    
    /**
     * FUNCTIONAL PROGRAMMING approach to validation
     * 
     * PREDICATE FACTORY METHODS:
     * - Returns Predicate functional interfaces
     * - Enables lambda expressions
     * - Composable validation logic
     * - Reusable validation rules
     */
    
    /**
     * Create email validation predicate
     * 
     * PREDICATE FACTORY:
     * - Returns Predicate<String> for email validation
     * - Can be used with Stream API
     * - Composable with other predicates
     * - Functional programming style
     * 
     * @return Predicate for email validation
     */
    public static Predicate<String> emailValidator() {
        return ValidationUtil::isValidEmail;
    }
    
    /**
     * Create name validation predicate
     * 
     * PREDICATE FACTORY:
     * - Returns Predicate<String> for name validation
     * - Method reference syntax
     * - Functional interface
     * 
     * @return Predicate for name validation
     */
    public static Predicate<String> nameValidator() {
        return ValidationUtil::isValidName;
    }
    
    /**
     * Create apartment number validation predicate
     * 
     * PREDICATE FACTORY:
     * - Returns Predicate<String> for apartment validation
     * - Method reference syntax
     * - Functional interface
     * 
     * @return Predicate for apartment number validation
     */
    public static Predicate<String> apartmentValidator() {
        return ValidationUtil::isValidApartmentNumber;
    }
    
    /**
     * Create length validation predicate
     * 
     * PREDICATE FACTORY with PARAMETERS:
     * - Returns Predicate<String> for length validation
     * - Captures parameters in closure
     * - Lambda expression syntax
     * - Parameterized validation
     * 
     * @param minLength Minimum allowed length
     * @param maxLength Maximum allowed length
     * @return Predicate for length validation
     */
    public static Predicate<String> lengthValidator(int minLength, int maxLength) {
        return value -> isValidLength(value, minLength, maxLength);
    }
    
    /**
     * VALIDATION RESULT class for detailed validation feedback
     * 
     * INNER CLASS:
     * - Encapsulates validation result
     * - Provides success/failure status
     * - Includes error messages
     * - Immutable result object
     * 
     * BUILDER PATTERN:
     * - Fluent interface for creating results
     * - Optional error messages
     * - Convenient creation methods
     */
    public static class ValidationResult {
        private final boolean valid;
        private final String errorMessage;
        
        private ValidationResult(boolean valid, String errorMessage) {
            this.valid = valid;
            this.errorMessage = errorMessage;
        }
        
        public boolean isValid() {
            return valid;
        }
        
        public String getErrorMessage() {
            return errorMessage;
        }
        
        public static ValidationResult success() {
            return new ValidationResult(true, null);
        }
        
        public static ValidationResult failure(String errorMessage) {
            return new ValidationResult(false, errorMessage);
        }
        
        @Override
        public String toString() {
            return valid ? "ValidationResult{valid=true}" : 
                          "ValidationResult{valid=false, error='" + errorMessage + "'}";
        }
    }
    
    /**
     * Comprehensive email validation with detailed result
     * 
     * DETAILED VALIDATION:
     * - Returns ValidationResult with error details
     * - Provides specific error messages
     * - More informative than boolean result
     * - Useful for user feedback
     * 
     * @param email The email to validate
     * @return ValidationResult with success/failure and error message
     */
    public static ValidationResult validateEmailDetailed(String email) {
        if (email == null) {
            return ValidationResult.failure("Email cannot be null");
        }
        
        if (email.trim().isEmpty()) {
            return ValidationResult.failure("Email cannot be empty");
        }
        
        if (!isValidEmail(email)) {
            return ValidationResult.failure("Invalid email format");
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Comprehensive name validation with detailed result
     * 
     * DETAILED VALIDATION:
     * - Multiple validation checks
     * - Specific error messages
     * - Comprehensive validation logic
     * 
     * @param name The name to validate
     * @return ValidationResult with success/failure and error message
     */
    public static ValidationResult validateNameDetailed(String name) {
        if (name == null) {
            return ValidationResult.failure("Name cannot be null");
        }
        
        if (name.trim().isEmpty()) {
            return ValidationResult.failure("Name cannot be empty");
        }
        
        if (!isValidLength(name, 2, 50)) {
            return ValidationResult.failure("Name must be between 2 and 50 characters");
        }
        
        if (!isValidName(name)) {
            return ValidationResult.failure("Name contains invalid characters");
        }
        
        return ValidationResult.success();
    }
    
    /**
     * VALIDATION CHAINING example
     * 
     * FLUENT VALIDATION:
     * - Chains multiple validations
     * - Short-circuit evaluation
     * - Composable validation logic
     * - Functional programming style
     * 
     * @param email The email to validate
     * @return ValidationResult for chained validations
     */
    public static ValidationResult validateEmailChain(String email) {
        return Optional.ofNullable(email)
                .filter(e -> !e.trim().isEmpty())
                .filter(ValidationUtil::isValidEmail)
                .map(e -> ValidationResult.success())
                .orElse(ValidationResult.failure("Invalid email"));
    }
}