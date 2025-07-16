package com.community.communityApp.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Utility class for date and time operations.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Java 8+ Date/Time API (LocalDate, LocalDateTime, ZonedDateTime)
 * - DateTimeFormatter for parsing and formatting
 * - Exception handling with DateTimeParseException
 * - Static utility methods and final class
 * - Enum usage for time periods
 * - Method overloading
 * - Collections Framework with List and Set
 * - Stream API for date operations
 * 
 * DESIGN PATTERNS:
 * - Utility Class Pattern: Static methods, private constructor
 * - Strategy Pattern: Different formatting strategies
 * - Factory Pattern: Date creation methods
 * - Template Method Pattern: Common date operations
 * 
 * MODERN DATE/TIME API:
 * - LocalDate for dates without time
 * - LocalDateTime for dates with time
 * - ZonedDateTime for timezone-aware dates
 * - Immutable date objects
 * - Thread-safe operations
 * 
 * UTILITY FUNCTIONS:
 * - Date parsing and formatting
 * - Date calculations and comparisons
 * - Business day calculations
 * - Age and period calculations
 * - Holiday and weekend detection
 */
public final class DateUtil {
    
    /**
     * COMMON DATE FORMATTERS
     * 
     * DATE FORMATTING:
     * - Pre-defined formatters for common patterns
     * - Thread-safe formatter objects
     * - Reusable across the application
     * - Consistent date formatting
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DISPLAY_DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
    public static final DateTimeFormatter DISPLAY_DATETIME_FORMAT = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' HH:mm");
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    
    /**
     * ENUM for time periods
     * 
     * TIME PERIOD ENUM:
     * - Type-safe time period representation
     * - Encapsulates period logic
     * - Extensible for new periods
     * - Business-friendly names
     */
    public enum TimePeriod {
        DAY(1, ChronoUnit.DAYS),
        WEEK(7, ChronoUnit.DAYS),
        MONTH(1, ChronoUnit.MONTHS),
        QUARTER(3, ChronoUnit.MONTHS),
        YEAR(1, ChronoUnit.YEARS);
        
        private final int amount;
        private final ChronoUnit unit;
        
        TimePeriod(int amount, ChronoUnit unit) {
            this.amount = amount;
            this.unit = unit;
        }
        
        public int getAmount() { return amount; }
        public ChronoUnit getUnit() { return unit; }
        
        /**
         * Add this period to a date
         * 
         * ENUM METHODS:
         * - Business logic in enum
         * - Encapsulated behavior
         * - Type-safe operations
         * 
         * @param date Base date
         * @return Date with period added
         */
        public LocalDate addTo(LocalDate date) {
            return date.plus(amount, unit);
        }
        
        /**
         * Subtract this period from a date
         * 
         * @param date Base date
         * @return Date with period subtracted
         */
        public LocalDate subtractFrom(LocalDate date) {
            return date.minus(amount, unit);
        }
    }
    
    /**
     * PRIVATE CONSTRUCTOR prevents instantiation
     * 
     * UTILITY CLASS PATTERN:
     * - Private constructor prevents instantiation
     * - Ensures class is used only for static methods
     * - Follows utility class best practices
     */
    private DateUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Get current date
     * 
     * FACTORY METHOD:
     * - Convenient access to current date
     * - Consistent API
     * - Easy to mock for testing
     * 
     * @return Current date
     */
    public static LocalDate today() {
        return LocalDate.now();
    }
    
    /**
     * Get current date and time
     * 
     * FACTORY METHOD:
     * - Convenient access to current datetime
     * - Consistent API
     * - Easy to mock for testing
     * 
     * @return Current date and time
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
    
    /**
     * Parse date from string with default format
     * 
     * DATE PARSING:
     * - Parses string to LocalDate
     * - Uses default format (yyyy-MM-dd)
     * - Handles parsing exceptions
     * - Returns Optional for null safety
     * 
     * OPTIONAL USAGE:
     * - Handles parsing failures gracefully
     * - No null pointer exceptions
     * - Explicit error handling
     * 
     * @param dateString Date string to parse
     * @return Optional containing parsed date
     */
    public static Optional<LocalDate> parseDate(String dateString) {
        return parseDate(dateString, DATE_FORMAT);
    }
    
    /**
     * Parse date from string with custom format
     * 
     * METHOD OVERLOADING:
     * - Same method name, different parameters
     * - Flexible parsing options
     * - Custom format support
     * 
     * EXCEPTION HANDLING:
     * - Catches DateTimeParseException
     * - Returns Optional.empty() for failures
     * - Graceful error handling
     * 
     * @param dateString Date string to parse
     * @param formatter Custom date formatter
     * @return Optional containing parsed date
     */
    public static Optional<LocalDate> parseDate(String dateString, DateTimeFormatter formatter) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return Optional.empty();
        }
        
        try {
            LocalDate date = LocalDate.parse(dateString.trim(), formatter);
            return Optional.of(date);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }
    
    /**
     * Parse datetime from string with default format
     * 
     * DATETIME PARSING:
     * - Parses string to LocalDateTime
     * - Uses default format (yyyy-MM-dd HH:mm:ss)
     * - Handles parsing exceptions
     * - Returns Optional for null safety
     * 
     * @param datetimeString DateTime string to parse
     * @return Optional containing parsed datetime
     */
    public static Optional<LocalDateTime> parseDateTime(String datetimeString) {
        return parseDateTime(datetimeString, DATETIME_FORMAT);
    }
    
    /**
     * Parse datetime from string with custom format
     * 
     * METHOD OVERLOADING:
     * - Same method name, different parameters
     * - Flexible parsing options
     * - Custom format support
     * 
     * @param datetimeString DateTime string to parse
     * @param formatter Custom datetime formatter
     * @return Optional containing parsed datetime
     */
    public static Optional<LocalDateTime> parseDateTime(String datetimeString, DateTimeFormatter formatter) {
        if (datetimeString == null || datetimeString.trim().isEmpty()) {
            return Optional.empty();
        }
        
        try {
            LocalDateTime dateTime = LocalDateTime.parse(datetimeString.trim(), formatter);
            return Optional.of(dateTime);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }
    
    /**
     * Format date to string with default format
     * 
     * DATE FORMATTING:
     * - Formats LocalDate to string
     * - Uses default format (yyyy-MM-dd)
     * - Null-safe operation
     * - Consistent formatting
     * 
     * @param date Date to format
     * @return Formatted date string
     */
    public static String formatDate(LocalDate date) {
        return formatDate(date, DATE_FORMAT);
    }
    
    /**
     * Format date to string with custom format
     * 
     * METHOD OVERLOADING:
     * - Same method name, different parameters
     * - Flexible formatting options
     * - Custom format support
     * 
     * @param date Date to format
     * @param formatter Custom date formatter
     * @return Formatted date string
     */
    public static String formatDate(LocalDate date, DateTimeFormatter formatter) {
        if (date == null) {
            return "";
        }
        
        return date.format(formatter);
    }
    
    /**
     * Format datetime to string with default format
     * 
     * DATETIME FORMATTING:
     * - Formats LocalDateTime to string
     * - Uses default format (yyyy-MM-dd HH:mm:ss)
     * - Null-safe operation
     * - Consistent formatting
     * 
     * @param dateTime DateTime to format
     * @return Formatted datetime string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return formatDateTime(dateTime, DATETIME_FORMAT);
    }
    
    /**
     * Format datetime to string with custom format
     * 
     * METHOD OVERLOADING:
     * - Same method name, different parameters
     * - Flexible formatting options
     * - Custom format support
     * 
     * @param dateTime DateTime to format
     * @param formatter Custom datetime formatter
     * @return Formatted datetime string
     */
    public static String formatDateTime(LocalDateTime dateTime, DateTimeFormatter formatter) {
        if (dateTime == null) {
            return "";
        }
        
        return dateTime.format(formatter);
    }
    
    /**
     * Calculate age from birth date
     * 
     * AGE CALCULATION:
     * - Calculates age in years
     * - Handles leap years correctly
     * - Uses Period for accurate calculation
     * - Business logic for age determination
     * 
     * @param birthDate Birth date
     * @return Age in years
     */
    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    /**
     * Calculate days between two dates
     * 
     * DATE ARITHMETIC:
     * - Calculates difference in days
     * - Uses ChronoUnit for precision
     * - Handles negative differences
     * - Accurate day counting
     * 
     * @param startDate Start date
     * @param endDate End date
     * @return Number of days between dates
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
    
    /**
     * Check if date is a weekend
     * 
     * WEEKEND DETECTION:
     * - Checks if date falls on weekend
     * - Saturday and Sunday are weekends
     * - Business logic for scheduling
     * - DayOfWeek enum usage
     * 
     * @param date Date to check
     * @return true if date is weekend
     */
    public static boolean isWeekend(LocalDate date) {
        if (date == null) {
            return false;
        }
        
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
    
    /**
     * Check if date is a weekday
     * 
     * WEEKDAY DETECTION:
     * - Checks if date falls on weekday
     * - Monday through Friday are weekdays
     * - Business logic for scheduling
     * - Opposite of weekend check
     * 
     * @param date Date to check
     * @return true if date is weekday
     */
    public static boolean isWeekday(LocalDate date) {
        return !isWeekend(date);
    }
    
    /**
     * Check if date is not in the future
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
     * Check if date is in the past
     * 
     * DATE COMPARISON:
     * - Compares with current date
     * - Useful for validation
     * - Business logic helper
     * 
     * @param date Date to check
     * @return true if date is in the past
     */
    public static boolean isPast(LocalDate date) {
        if (date == null) {
            return false;
        }
        
        return date.isBefore(LocalDate.now());
    }
    
    /**
     * Check if date is in the future
     * 
     * DATE COMPARISON:
     * - Compares with current date
     * - Useful for validation
     * - Business logic helper
     * 
     * @param date Date to check
     * @return true if date is in the future
     */
    public static boolean isFuture(LocalDate date) {
        if (date == null) {
            return false;
        }
        
        return date.isAfter(LocalDate.now());
    }
    
    /**
     * Check if date is today
     * 
     * DATE COMPARISON:
     * - Compares with current date
     * - Exact date matching
     * - Business logic helper
     * 
     * @param date Date to check
     * @return true if date is today
     */
    public static boolean isToday(LocalDate date) {
        if (date == null) {
            return false;
        }
        
        return date.equals(LocalDate.now());
    }
    
    /**
     * Format date for display to users
     * 
     * USER-FRIENDLY FORMATTING:
     * - Human-readable format
     * - Consistent display format
     * - Localized appearance
     * 
     * @param date Date to format
     * @return User-friendly date string
     */
    public static String formatForDisplay(LocalDate date) {
        return formatDate(date, DISPLAY_DATE_FORMAT);
    }
    
    /**
     * Format datetime for display to users
     * 
     * USER-FRIENDLY FORMATTING:
     * - Human-readable format
     * - Consistent display format
     * - Localized appearance
     * 
     * @param dateTime DateTime to format
     * @return User-friendly datetime string
     */
    public static String formatForDisplay(LocalDateTime dateTime) {
        return formatDateTime(dateTime, DISPLAY_DATETIME_FORMAT);
    }
    
    /**
     * Get relative time description
     * 
     * RELATIVE TIME:
     * - Human-readable time differences
     * - "2 days ago", "in 3 hours", etc.
     * - User-friendly time representation
     * 
     * @param dateTime DateTime to describe
     * @return Relative time description
     */
    public static String getRelativeTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "unknown";
        }
        
        LocalDateTime now = LocalDateTime.now();
        long hours = ChronoUnit.HOURS.between(dateTime, now);
        long days = ChronoUnit.DAYS.between(dateTime, now);
        
        if (hours == 0) {
            long minutes = ChronoUnit.MINUTES.between(dateTime, now);
            if (minutes == 0) {
                return "just now";
            } else if (minutes > 0) {
                return minutes + (minutes == 1 ? " minute ago" : " minutes ago");
            } else {
                return "in " + Math.abs(minutes) + (minutes == -1 ? " minute" : " minutes");
            }
        } else if (Math.abs(hours) < 24) {
            if (hours > 0) {
                return hours + (hours == 1 ? " hour ago" : " hours ago");
            } else {
                return "in " + Math.abs(hours) + (hours == -1 ? " hour" : " hours");
            }
        } else {
            if (days > 0) {
                return days + (days == 1 ? " day ago" : " days ago");
            } else {
                return "in " + Math.abs(days) + (days == -1 ? " day" : " days");
            }
        }
    }
}