package com.community.communityApp.util;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Utility class for console menu operations and user interaction.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Static utility classes and methods
 * - Scanner for user input
 * - Exception handling with try-catch
 * - Collections Framework (List, Map)
 * - Functional interfaces (Consumer, Supplier, Runnable)
 * - Lambda expressions
 * - Method references
 * - Enum usage for menu options
 * - String formatting and manipulation
 * 
 * DESIGN PATTERNS:
 * - Utility Class Pattern: Static methods, private constructor
 * - Command Pattern: Menu actions as functional interfaces
 * - Strategy Pattern: Different input validation strategies
 * - Builder Pattern: Menu builder for complex menus
 * - Singleton Pattern: Shared Scanner instance
 * 
 * USER INTERACTION:
 * - Console input/output
 * - Menu display and navigation
 * - Input validation and error handling
 * - User-friendly error messages
 * - Retry mechanisms
 */
public final class MenuUtil {
    
    /**
     * SINGLETON PATTERN for Scanner
     * 
     * SHARED SCANNER:
     * - Single Scanner instance for all input
     * - Prevents resource conflicts
     * - Thread-safe for console applications
     * - Lazy initialization
     */
    private static Scanner scanner = null;
    
    /**
     * CONSTANTS for menu formatting
     * 
     * FORMATTING CONSTANTS:
     * - Consistent menu appearance
     * - Easy to modify styling
     * - Centralized formatting rules
     * - Reusable across menus
     */
    private static final String MENU_SEPARATOR = "=" + "=".repeat(50);
    private static final String MENU_BORDER = "-" + "-".repeat(50);
    private static final String MENU_PREFIX = "  ";
    private static final String OPTION_FORMAT = "%s%d. %s";
    private static final String HEADER_FORMAT = "%s%s%s";
    
    /**
     * PRIVATE CONSTRUCTOR prevents instantiation
     * 
     * UTILITY CLASS PATTERN:
     * - Private constructor prevents instantiation
     * - Ensures class is used only for static methods
     * - Follows utility class best practices
     */
    private MenuUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Get shared Scanner instance
     * 
     * SINGLETON PATTERN:
     * - Lazy initialization
     * - Thread-safe for console applications
     * - Prevents multiple Scanner instances
     * - Centralized input handling
     * 
     * @return Shared Scanner instance
     */
    private static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
    
    /**
     * Display a formatted menu header
     * 
     * FORMATTING UTILITY:
     * - Consistent header formatting
     * - Visual separation
     * - Professional appearance
     * - Easy to use
     * 
     * @param title The menu title
     */
    public static void displayHeader(String title) {
        System.out.println(MENU_SEPARATOR);
        System.out.println(String.format(HEADER_FORMAT, MENU_PREFIX, title, MENU_PREFIX));
        System.out.println(MENU_SEPARATOR);
    }
    
    /**
     * Display a formatted menu footer
     * 
     * FORMATTING UTILITY:
     * - Consistent footer formatting
     * - Visual separation
     * - Professional appearance
     */
    public static void displayFooter() {
        System.out.println(MENU_BORDER);
    }
    
    /**
     * Display menu options from a list
     * 
     * COLLECTIONS FRAMEWORK:
     * - Works with List<String>
     * - Indexed iteration
     * - Formatted output
     * - Flexible option display
     * 
     * @param options List of menu options
     */
    public static void displayOptions(List<String> options) {
        if (options == null || options.isEmpty()) {
            System.out.println(MENU_PREFIX + "No options available");
            return;
        }
        
        for (int i = 0; i < options.size(); i++) {
            System.out.println(String.format(OPTION_FORMAT, MENU_PREFIX, i + 1, options.get(i)));
        }
    }
    
    /**
     * Display menu options from an array
     * 
     * METHOD OVERLOADING:
     * - Same method name, different parameter types
     * - Convenient for array inputs
     * - Converts array to List
     * - Flexible API
     * 
     * @param options Array of menu options
     */
    public static void displayOptions(String[] options) {
        if (options == null || options.length == 0) {
            System.out.println(MENU_PREFIX + "No options available");
            return;
        }
        
        displayOptions(Arrays.asList(options));
    }
    
    /**
     * Get user input as integer with validation
     * 
     * INPUT VALIDATION:
     * - Validates integer input
     * - Handles NumberFormatException
     * - Range validation
     * - Retry mechanism
     * 
     * EXCEPTION HANDLING:
     * - Try-catch for input errors
     * - Meaningful error messages
     * - Graceful error recovery
     * 
     * @param prompt The input prompt
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return Validated integer input
     */
    public static int getIntInput(String prompt, int min, int max) {
        Scanner scanner = getScanner();
        int value;
        
        while (true) {
            try {
                System.out.print(prompt);
                value = scanner.nextInt();
                
                // VALIDATION: Check range
                if (value < min || value > max) {
                    System.out.println(String.format(
                        "Please enter a number between %d and %d", min, max));
                    continue;
                }
                
                return value;
                
            } catch (InputMismatchException e) {
                // EXCEPTION HANDLING: Invalid integer input
                System.out.println("Please enter a valid number");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    
    /**
     * Get user input as string with validation
     * 
     * STRING INPUT:
     * - Trims whitespace
     * - Validates non-empty input
     * - Optional validation predicate
     * - Retry mechanism
     * 
     * @param prompt The input prompt
     * @param allowEmpty Whether to allow empty input
     * @return Validated string input
     */
    public static String getStringInput(String prompt, boolean allowEmpty) {
        Scanner scanner = getScanner();
        String value;
        
        while (true) {
            System.out.print(prompt);
            value = scanner.nextLine().trim();
            
            if (!allowEmpty && value.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }
            
            return value;
        }
    }
    
    /**
     * Get user input as string with custom validation
     * 
     * FUNCTIONAL PROGRAMMING:
     * - Uses Predicate functional interface
     * - Lambda expressions for validation
     * - Flexible validation logic
     * - Composable validation rules
     * 
     * @param prompt The input prompt
     * @param validator Predicate for input validation
     * @param errorMessage Error message for invalid input
     * @return Validated string input
     */
    public static String getStringInput(String prompt, java.util.function.Predicate<String> validator, 
                                       String errorMessage) {
        Scanner scanner = getScanner();
        String value;
        
        while (true) {
            System.out.print(prompt);
            value = scanner.nextLine().trim();
            
            if (validator.test(value)) {
                return value;
            }
            
            System.out.println(errorMessage);
        }
    }
    
    /**
     * Get yes/no confirmation from user
     * 
     * BOOLEAN INPUT:
     * - Converts string to boolean
     * - Accepts various formats (y/n, yes/no, true/false)
     * - Case-insensitive
     * - Retry mechanism
     * 
     * @param prompt The confirmation prompt
     * @return true for yes, false for no
     */
    public static boolean getConfirmation(String prompt) {
        String input = getStringInput(prompt + " (y/n): ", false);
        
        return switch (input.toLowerCase()) {
            case "y", "yes", "true", "1" -> true;
            case "n", "no", "false", "0" -> false;
            default -> {
                System.out.println("Please enter 'y' for yes or 'n' for no");
                yield getConfirmation(prompt);
            }
        };
    }
    
    /**
     * Display a simple menu and get user choice
     * 
     * MENU HANDLING:
     * - Displays formatted menu
     * - Gets user selection
     * - Validates choice
     * - Returns selected option index
     * 
     * @param title Menu title
     * @param options Menu options
     * @return Selected option index (0-based)
     */
    public static int displayMenu(String title, String[] options) {
        displayHeader(title);
        displayOptions(options);
        displayFooter();
        
        int choice = getIntInput("Enter your choice: ", 1, options.length);
        return choice - 1; // Convert to 0-based index
    }
    
    /**
     * Display a menu and execute action based on choice
     * 
     * COMMAND PATTERN:
     * - Map of choices to actions
     * - Functional interfaces for actions
     * - Flexible action execution
     * - Separation of menu and logic
     * 
     * @param title Menu title
     * @param actions Map of option names to actions
     */
    public static void displayMenuWithActions(String title, Map<String, Runnable> actions) {
        if (actions == null || actions.isEmpty()) {
            System.out.println("No menu actions available");
            return;
        }
        
        String[] options = actions.keySet().toArray(new String[0]);
        List<Runnable> actionList = new ArrayList<>(actions.values());
        
        int choice = displayMenu(title, options);
        
        // Execute selected action
        actionList.get(choice).run();
    }
    
    /**
     * MENU BUILDER for complex menus
     * 
     * BUILDER PATTERN:
     * - Fluent interface for menu creation
     * - Flexible menu construction
     * - Method chaining
     * - Immutable menu objects
     * 
     * NESTED CLASS:
     * - Encapsulates menu building logic
     * - Clean separation of concerns
     * - Builder pattern implementation
     */
    public static class MenuBuilder {
        private String title;
        private final Map<String, Runnable> actions;
        private boolean showExitOption;
        
        public MenuBuilder() {
            this.actions = new LinkedHashMap<>();
            this.showExitOption = true;
        }
        
        /**
         * Set menu title
         * 
         * BUILDER PATTERN:
         * - Fluent interface
         * - Method chaining
         * - Immutable building
         * 
         * @param title Menu title
         * @return Builder instance for chaining
         */
        public MenuBuilder title(String title) {
            this.title = title;
            return this;
        }
        
        /**
         * Add menu option with action
         * 
         * BUILDER PATTERN:
         * - Adds option-action pair
         * - Flexible action types
         * - Method chaining
         * 
         * @param option Option name
         * @param action Action to execute
         * @return Builder instance for chaining
         */
        public MenuBuilder addOption(String option, Runnable action) {
            this.actions.put(option, action);
            return this;
        }
        
        /**
         * Add menu option with consumer action
         * 
         * FUNCTIONAL PROGRAMMING:
         * - Consumer functional interface
         * - No return value
         * - Side effect operations
         * 
         * @param option Option name
         * @param action Consumer action
         * @return Builder instance for chaining
         */
        public MenuBuilder addOption(String option, Consumer<String> action) {
            this.actions.put(option, () -> action.accept(option));
            return this;
        }
        
        /**
         * Configure exit option visibility
         * 
         * BUILDER CONFIGURATION:
         * - Optional exit option
         * - Flexible menu behavior
         * - Configuration chaining
         * 
         * @param showExitOption Whether to show exit option
         * @return Builder instance for chaining
         */
        public MenuBuilder showExitOption(boolean showExitOption) {
            this.showExitOption = showExitOption;
            return this;
        }
        
        /**
         * Build and display the menu
         * 
         * BUILDER PATTERN:
         * - Constructs final menu
         * - Executes menu logic
         * - Handles user interaction
         * 
         * @return Selected option index
         */
        public int build() {
            if (title == null) {
                title = "Menu";
            }
            
            Map<String, Runnable> finalActions = new LinkedHashMap<>(actions);
            
            if (showExitOption) {
                finalActions.put("Exit", () -> System.out.println("Exiting..."));
            }
            
            displayMenuWithActions(title, finalActions);
            
            return finalActions.size() - 1; // Return exit option index
        }
    }
    
    /**
     * Create a new menu builder
     * 
     * FACTORY METHOD:
     * - Creates MenuBuilder instance
     * - Fluent interface starting point
     * - Convenient API
     * 
     * @return New MenuBuilder instance
     */
    public static MenuBuilder menuBuilder() {
        return new MenuBuilder();
    }
    
    /**
     * Display a loading animation
     * 
     * ANIMATION UTILITY:
     * - Simple console animation
     * - Provides user feedback
     * - Handles InterruptedException
     * 
     * @param message Loading message
     * @param durationMs Animation duration in milliseconds
     */
    public static void showLoading(String message, int durationMs) {
        System.out.print(message);
        
        String[] spinner = {"|", "/", "-", "\\"};
        int index = 0;
        long startTime = System.currentTimeMillis();
        
        try {
            while (System.currentTimeMillis() - startTime < durationMs) {
                System.out.print("\r" + message + " " + spinner[index]);
                Thread.sleep(100);
                index = (index + 1) % spinner.length;
            }
            System.out.println("\r" + message + " Done!");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("\r" + message + " Interrupted!");
        }
    }
    
    /**
     * Clear console screen (works on most terminals)
     * 
     * CONSOLE UTILITY:
     * - Clears screen for better UX
     * - Cross-platform compatibility
     * - Simple implementation
     */
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[2J\033[H");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback: print empty lines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    /**
     * Pause execution and wait for user input
     * 
     * UTILITY METHOD:
     * - Pauses program execution
     * - Waits for user acknowledgment
     * - Useful for menu flow control
     */
    public static void pauseForUser() {
        System.out.print("Press Enter to continue...");
        getScanner().nextLine();
    }
    
    /**
     * Display error message with formatting
     * 
     * ERROR DISPLAY:
     * - Consistent error formatting
     * - Visual distinction from normal output
     * - User-friendly error presentation
     * 
     * @param message Error message
     */
    public static void displayError(String message) {
        System.out.println("\n" + "ERROR: " + message + "\n");
    }
    
    /**
     * Display success message with formatting
     * 
     * SUCCESS DISPLAY:
     * - Consistent success formatting
     * - Visual distinction from normal output
     * - User-friendly success presentation
     * 
     * @param message Success message
     */
    public static void displaySuccess(String message) {
        System.out.println("\n" + "SUCCESS: " + message + "\n");
    }
    
    /**
     * Display warning message with formatting
     * 
     * WARNING DISPLAY:
     * - Consistent warning formatting
     * - Visual distinction from normal output
     * - User-friendly warning presentation
     * 
     * @param message Warning message
     */
    public static void displayWarning(String message) {
        System.out.println("\n" + "WARNING: " + message + "\n");
    }
    
    /**
     * Display info message with formatting
     * 
     * INFO DISPLAY:
     * - Consistent info formatting
     * - Visual distinction from normal output
     * - User-friendly info presentation
     * 
     * @param message Info message
     */
    public static void displayInfo(String message) {
        System.out.println("\n" + "INFO: " + message + "\n");
    }
}