package com.community.communityApp;

import com.community.communityApp.exception.*;
import com.community.communityApp.model.*;
import com.community.communityApp.repository.*;
import com.community.communityApp.service.*;
import com.community.communityApp.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Main application class demonstrating Core Java concepts through a Community Management System.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Main method and application entry point
 * - Static methods and variables
 * - Exception handling with try-catch-finally
 * - Collections Framework (List, Set, Map)
 * - Generics and type safety
 * - Java 8+ features (Streams, Lambdas, Optional)
 * - Object-Oriented Programming (inheritance, polymorphism, encapsulation)
 * - Enum usage and switch statements
 * - Control flow (if-else, loops, switch)
 * - Variables and data types
 * - Method overloading and overriding
 * 
 * APPLICATION ARCHITECTURE:
 * - Console-based user interface
 * - Service layer for business logic
 * - Repository layer for data access
 * - Model layer for domain objects
 * - Utility classes for common operations
 * - Exception handling for error management
 * 
 * DESIGN PATTERNS USED:
 * - MVC Pattern: Model-View-Controller separation
 * - Repository Pattern: Data access abstraction
 * - Service Layer Pattern: Business logic encapsulation
 * - Singleton Pattern: Single application instance
 * - Factory Pattern: Object creation methods
 * - Command Pattern: Menu action handling
 * 
 * This application serves as a comprehensive demonstration of Core Java concepts
 * in a realistic, runnable project that can be used for learning and job preparation.
 */
public class CommunityAppApplication {
    
    /**
     * STATIC VARIABLES for application state
     * 
     * APPLICATION STATE:
     * - Static variables maintain application-wide state
     * - Shared across all methods
     * - Initialized once per application run
     * - Used for dependency injection simulation
     */
    private static ResidentService residentService;
    private static CommunityService communityService;
    private static boolean isRunning = true;
    
    /**
     * CONSTANTS for application configuration
     * 
     * APPLICATION CONSTANTS:
     * - Final static variables for configuration
     * - Immutable application settings
     * - Centralized configuration management
     */
    private static final String APP_NAME = "Community Management System";
    private static final String APP_VERSION = "1.0.0";
    private static final String WELCOME_MESSAGE = "Welcome to the " + APP_NAME + " v" + APP_VERSION;
    
    /**
     * Main method - application entry point
     * 
     * MAIN METHOD:
     * - Entry point for Java applications
     * - Static method called by JVM
     * - String[] args for command line arguments
     * - Exception handling for application errors
     * 
     * EXCEPTION HANDLING:
     * - Try-catch for application-level errors
     * - Finally block for cleanup
     * - Graceful error handling and recovery
     * 
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        try {
            // APPLICATION INITIALIZATION
            initializeApplication();
            
            // WELCOME MESSAGE
            displayWelcomeMessage();
            
            // MAIN APPLICATION LOOP
            runApplication();
            
        } catch (Exception e) {
            // APPLICATION-LEVEL EXCEPTION HANDLING
            MenuUtil.displayError("Application error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // CLEANUP OPERATIONS
            cleanup();
        }
    }
    
    /**
     * Initialize application services and data
     * 
     * INITIALIZATION:
     * - Sets up service dependencies
     * - Initializes repositories
     * - Loads sample data
     * - Demonstrates dependency injection pattern
     */
    private static void initializeApplication() {
        MenuUtil.displayInfo("Initializing Community Management System...");
        
        // REPOSITORY PATTERN: Create repositories
        Repository<Resident, String> residentRepository = new InMemoryRepository<>();
        Repository<Service, String> serviceRepository = new InMemoryRepository<>();
        
        // SERVICE LAYER: Initialize services with repositories
        residentService = new ResidentService(residentRepository);
        communityService = new CommunityService(serviceRepository);
        
        // SAMPLE DATA: Load initial data for demonstration
        loadSampleData();
        
        MenuUtil.displaySuccess("Application initialized successfully!");
    }
    
    /**
     * Load sample data for demonstration
     * 
     * SAMPLE DATA:
     * - Creates sample residents and services
     * - Demonstrates object creation and usage
     * - Shows different data types and collections
     * - Provides realistic test data
     */
    private static void loadSampleData() {
        try {
            // OBJECT CREATION: Create sample residents
            Resident resident1 = new Resident(
                "John Doe", 
                "john.doe@email.com", 
                LocalDate.of(1985, 5, 15),
                "101", 
                2
            );
            
            Resident resident2 = new Resident(
                "Jane Smith", 
                "jane.smith@email.com", 
                LocalDate.of(1990, 8, 22),
                "102", 
                1
            );
            
            // ADMINISTRATOR CREATION: Demonstrates inheritance
            Administrator admin = new Administrator(
                "Robert Johnson",
                "robert.admin@email.com",
                LocalDate.of(1975, 3, 10),
                "201",
                3,
                Administrator.AdminRole.PRESIDENT
            );
            
            // SERVICE REGISTRATION: Register residents
            residentService.registerResident(resident1);
            residentService.registerResident(resident2);
            residentService.registerResident(admin);
            
            // SERVICE REQUESTS: Create sample services
            Service cleaningService = communityService.requestService(
                Service.ServiceType.CLEANING,
                "Weekly apartment cleaning",
                "CleanCorp Services",
                150.0,
                "101"
            );
            
            Service maintenanceService = communityService.requestService(
                Service.ServiceType.MAINTENANCE,
                "Fix leaky faucet in bathroom",
                "Fix-It-Fast",
                75.0,
                "102"
            );
            
            // COLLECTIONS: Add service requests to residents
            resident1.addServiceRequest("Weekly cleaning requested");
            resident2.addServiceRequest("Maintenance request submitted");
            
            // OPTIONAL USAGE: Set phone numbers
            resident1.setPhoneNumber("+1-555-0123");
            resident2.setPhoneNumber("+1-555-0124");
            
            MenuUtil.displayInfo("Sample data loaded successfully!");
            
        } catch (Exception e) {
            MenuUtil.displayError("Failed to load sample data: " + e.getMessage());
        }
    }
    
    /**
     * Display welcome message and application information
     * 
     * USER INTERFACE:
     * - Welcome message display
     * - Application information
     * - User guidance
     */
    private static void displayWelcomeMessage() {
        MenuUtil.clearScreen();
        MenuUtil.displayHeader(WELCOME_MESSAGE);
        
        System.out.println("This application demonstrates Core Java concepts through a");
        System.out.println("realistic Community Management System.");
        System.out.println();
        System.out.println("Features demonstrated:");
        System.out.println("• Object-Oriented Programming (Inheritance, Polymorphism, Encapsulation)");
        System.out.println("• Collections Framework (List, Set, Map)");
        System.out.println("• Exception Handling (try-catch-finally, custom exceptions)");
        System.out.println("• Java 8+ Features (Streams, Lambdas, Optional)");
        System.out.println("• Generics and Type Safety");
        System.out.println("• Design Patterns (Repository, Service Layer, Builder)");
        System.out.println();
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Main application loop
     * 
     * APPLICATION LOOP:
     * - Displays main menu
     * - Handles user input
     * - Processes menu selections
     * - Continues until user exits
     * 
     * CONTROL FLOW:
     * - While loop for continuous operation
     * - Switch statement for menu handling
     * - Exception handling for user errors
     */
    private static void runApplication() {
        while (isRunning) {
            try {
                displayMainMenu();
            } catch (Exception e) {
                MenuUtil.displayError("Menu error: " + e.getMessage());
                MenuUtil.pauseForUser();
            }
        }
    }
    
    /**
     * Display main menu and handle user selection
     * 
     * MENU SYSTEM:
     * - Uses MenuUtil for consistent display
     * - Switch statement for option handling
     * - Method delegation for functionality
     * - Exception handling for invalid input
     */
    private static void displayMainMenu() {
        String[] menuOptions = {
            "Manage Residents",
            "Manage Services", 
            "View Statistics",
            "Search and Filter Demo",
            "Java 8+ Features Demo",
            "Exception Handling Demo",
            "Collections Framework Demo",
            "Exit Application"
        };
        
        int choice = MenuUtil.displayMenu("Main Menu", menuOptions);
        
        // SWITCH STATEMENT: Handle user choice
        switch (choice) {
            case 0 -> manageResidents();
            case 1 -> manageServices();
            case 2 -> viewStatistics();
            case 3 -> searchAndFilterDemo();
            case 4 -> java8FeaturesDemo();
            case 5 -> exceptionHandlingDemo();
            case 6 -> collectionsFrameworkDemo();
            case 7 -> exitApplication();
            default -> MenuUtil.displayError("Invalid menu choice");
        }
    }
    
    /**
     * Resident management menu
     * 
     * RESIDENT MANAGEMENT:
     * - CRUD operations for residents
     * - Input validation
     * - Exception handling
     * - Service integration
     */
    private static void manageResidents() {
        String[] residentOptions = {
            "Add New Resident",
            "View All Residents",
            "Search Resident",
            "Update Resident",
            "Delete Resident",
            "Back to Main Menu"
        };
        
        int choice = MenuUtil.displayMenu("Resident Management", residentOptions);
        
        switch (choice) {
            case 0 -> addNewResident();
            case 1 -> viewAllResidents();
            case 2 -> searchResident();
            case 3 -> updateResident();
            case 4 -> deleteResident();
            case 5 -> { /* Return to main menu */ }
            default -> MenuUtil.displayError("Invalid choice");
        }
    }
    
    /**
     * Add new resident with input validation
     * 
     * INPUT VALIDATION:
     * - Uses ValidationUtil for validation
     * - Exception handling for invalid data
     * - User-friendly error messages
     * - Retry mechanism for errors
     */
    private static void addNewResident() {
        try {
            MenuUtil.displayHeader("Add New Resident");
            
            // INPUT COLLECTION: Get resident information
            String name = MenuUtil.getStringInput("Enter resident name: ", false);
            
            // VALIDATION: Email validation with retry
            String email;
            do {
                email = MenuUtil.getStringInput("Enter email address: ", false);
                if (!ValidationUtil.isValidEmail(email)) {
                    MenuUtil.displayError("Invalid email format. Please try again.");
                    email = null;
                }
            } while (email == null);
            
            // DATE INPUT: Birth date with validation
            LocalDate birthDate = null;
            do {
                String birthDateStr = MenuUtil.getStringInput("Enter birth date (yyyy-MM-dd): ", false);
                Optional<LocalDate> parsedDate = DateUtil.parseDate(birthDateStr);
                if (parsedDate.isPresent() && DateUtil.isNotInFuture(parsedDate.get())) {
                    birthDate = parsedDate.get();
                } else {
                    MenuUtil.displayError("Invalid date format or date is in the future. Please try again.");
                }
            } while (birthDate == null);
            
            // APARTMENT VALIDATION
            String apartmentNumber;
            do {
                apartmentNumber = MenuUtil.getStringInput("Enter apartment number: ", false);
                if (!ValidationUtil.isValidApartmentNumber(apartmentNumber)) {
                    MenuUtil.displayError("Invalid apartment number format. Please try again.");
                    apartmentNumber = null;
                }
            } while (apartmentNumber == null);
            
            // OPTIONAL INPUT: Unit count
            int unitCount = MenuUtil.getIntInput("Enter unit count (1-10): ", 1, 10);
            
            // OBJECT CREATION: Create new resident
            Resident newResident = new Resident(name, email, birthDate, apartmentNumber, unitCount);
            
            // OPTIONAL: Phone number
            if (MenuUtil.getConfirmation("Add phone number?")) {
                String phone = MenuUtil.getStringInput("Enter phone number: ", false);
                newResident.setPhoneNumber(phone);
            }
            
            // SERVICE CALL: Register resident
            Resident savedResident = residentService.registerResident(newResident);
            
            MenuUtil.displaySuccess("Resident added successfully!");
            System.out.println("Resident Details:");
            System.out.println(savedResident.getDisplayInfo());
            
        } catch (DuplicateResidentException e) {
            MenuUtil.displayError("Duplicate resident: " + e.getUserFriendlyMessage());
        } catch (Exception e) {
            MenuUtil.displayError("Failed to add resident: " + e.getMessage());
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * View all residents with formatted display
     * 
     * DATA DISPLAY:
     * - Retrieves all residents from service
     * - Formats data for display
     * - Handles empty collections
     * - Demonstrates iteration
     */
    private static void viewAllResidents() {
        try {
            MenuUtil.displayHeader("All Residents");
            
            // SERVICE CALL: Get all residents
            List<Resident> residents = residentService.getAllResidentsSortedByApartment();
            
            if (residents.isEmpty()) {
                MenuUtil.displayInfo("No residents found.");
            } else {
                System.out.println(String.format("Found %d resident(s):", residents.size()));
                System.out.println();
                
                // ENHANCED FOR LOOP: Iterate through residents
                for (Resident resident : residents) {
                    System.out.println("• " + resident.getDisplayInfo());
                    
                    // OPTIONAL: Show additional details
                    if (resident.getPhoneNumber().isPresent()) {
                        System.out.println("  Phone: " + resident.getPhoneNumber().get());
                    }
                    
                    // COLLECTIONS: Show service requests
                    List<String> serviceRequests = resident.getServiceRequests();
                    if (!serviceRequests.isEmpty()) {
                        System.out.println("  Service Requests: " + serviceRequests.size());
                    }
                    
                    System.out.println();
                }
            }
            
        } catch (Exception e) {
            MenuUtil.displayError("Failed to retrieve residents: " + e.getMessage());
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Search resident functionality
     * 
     * SEARCH FUNCTIONALITY:
     * - Multiple search criteria
     * - Exception handling for not found
     * - User-friendly results display
     * - Demonstrates Optional usage
     */
    private static void searchResident() {
        try {
            MenuUtil.displayHeader("Search Resident");
            
            String[] searchOptions = {
                "Search by Email",
                "Search by Apartment Number",
                "Search by Name",
                "Back"
            };
            
            int choice = MenuUtil.displayMenu("Search Options", searchOptions);
            
            switch (choice) {
                case 0 -> searchByEmail();
                case 1 -> searchByApartment();
                case 2 -> searchByName();
                case 3 -> { /* Return */ }
                default -> MenuUtil.displayError("Invalid choice");
            }
            
        } catch (Exception e) {
            MenuUtil.displayError("Search failed: " + e.getMessage());
            MenuUtil.pauseForUser();
        }
    }
    
    /**
     * Search resident by email
     * 
     * OPTIONAL USAGE:
     * - Demonstrates Optional handling
     * - Null-safe operations
     * - Exception handling for not found
     */
    private static void searchByEmail() {
        String email = MenuUtil.getStringInput("Enter email to search: ", false);
        
        // OPTIONAL: Handle search result
        Optional<Resident> resident = residentService.findByEmail(email);
        
        if (resident.isPresent()) {
            MenuUtil.displaySuccess("Resident found!");
            System.out.println(resident.get().getDisplayInfo());
        } else {
            MenuUtil.displayError("No resident found with email: " + email);
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Search resident by apartment number
     * 
     * OPTIONAL USAGE:
     * - Demonstrates Optional handling
     * - Business logic integration
     * - Clean error handling
     */
    private static void searchByApartment() {
        String apartmentNumber = MenuUtil.getStringInput("Enter apartment number: ", false);
        
        Optional<Resident> resident = residentService.findByApartmentNumber(apartmentNumber);
        
        if (resident.isPresent()) {
            MenuUtil.displaySuccess("Resident found!");
            System.out.println(resident.get().getDisplayInfo());
        } else {
            MenuUtil.displayError("No resident found in apartment: " + apartmentNumber);
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Search residents by name pattern
     * 
     * COLLECTIONS:
     * - List processing
     * - Search results handling
     * - Iteration and display
     */
    private static void searchByName() {
        String namePattern = MenuUtil.getStringInput("Enter name pattern: ", false);
        
        List<Resident> residents = residentService.searchByName(namePattern);
        
        if (residents.isEmpty()) {
            MenuUtil.displayError("No residents found matching: " + namePattern);
        } else {
            MenuUtil.displaySuccess("Found " + residents.size() + " resident(s):");
            residents.forEach(resident -> System.out.println("• " + resident.getDisplayInfo()));
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Update resident information
     * 
     * UPDATE OPERATIONS:
     * - Find existing resident
     * - Update fields
     * - Validation and error handling
     * - Service integration
     */
    private static void updateResident() {
        try {
            String email = MenuUtil.getStringInput("Enter email of resident to update: ", false);
            
            Optional<Resident> existingResident = residentService.findByEmail(email);
            
            if (existingResident.isEmpty()) {
                MenuUtil.displayError("Resident not found with email: " + email);
                MenuUtil.pauseForUser();
                return;
            }
            
            Resident resident = existingResident.get();
            MenuUtil.displayInfo("Current resident information:");
            System.out.println(resident.getDisplayInfo());
            
            // UPDATE FIELDS: Allow user to update specific fields
            if (MenuUtil.getConfirmation("Update name?")) {
                String newName = MenuUtil.getStringInput("Enter new name: ", false);
                resident.setName(newName);
            }
            
            if (MenuUtil.getConfirmation("Update apartment number?")) {
                String newApartment = MenuUtil.getStringInput("Enter new apartment number: ", false);
                resident.setApartmentNumber(newApartment);
            }
            
            if (MenuUtil.getConfirmation("Update phone number?")) {
                String newPhone = MenuUtil.getStringInput("Enter new phone number (or press Enter to remove): ", true);
                if (newPhone.isEmpty()) {
                    resident.setPhoneNumber(null);
                } else {
                    resident.setPhoneNumber(newPhone);
                }
            }
            
            // SERVICE CALL: Update resident
            Resident updatedResident = residentService.updateResident(resident);
            
            MenuUtil.displaySuccess("Resident updated successfully!");
            System.out.println("Updated information:");
            System.out.println(updatedResident.getDisplayInfo());
            
        } catch (Exception e) {
            MenuUtil.displayError("Failed to update resident: " + e.getMessage());
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Delete resident
     * 
     * DELETE OPERATIONS:
     * - Confirmation dialog
     * - Service integration
     * - Exception handling
     * - User feedback
     */
    private static void deleteResident() {
        try {
            String email = MenuUtil.getStringInput("Enter email of resident to delete: ", false);
            
            // CONFIRMATION: Double-check before deletion
            if (!MenuUtil.getConfirmation("Are you sure you want to delete this resident?")) {
                MenuUtil.displayInfo("Deletion cancelled.");
                MenuUtil.pauseForUser();
                return;
            }
            
            // SERVICE CALL: Delete resident
            boolean deleted = residentService.deleteResident(email);
            
            if (deleted) {
                MenuUtil.displaySuccess("Resident deleted successfully!");
            } else {
                MenuUtil.displayError("Failed to delete resident");
            }
            
        } catch (ResidentNotFoundException e) {
            MenuUtil.displayError("Resident not found: " + e.getUserFriendlyMessage());
        } catch (Exception e) {
            MenuUtil.displayError("Failed to delete resident: " + e.getMessage());
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Service management menu
     * 
     * SERVICE MANAGEMENT:
     * - Service lifecycle operations
     * - State management
     * - Business logic demonstration
     */
    private static void manageServices() {
        String[] serviceOptions = {
            "Request New Service",
            "View All Services",
            "Schedule Service",
            "Complete Service",
            "Cancel Service",
            "Back to Main Menu"
        };
        
        int choice = MenuUtil.displayMenu("Service Management", serviceOptions);
        
        switch (choice) {
            case 0 -> requestNewService();
            case 1 -> viewAllServices();
            case 2 -> scheduleService();
            case 3 -> completeService();
            case 4 -> cancelService();
            case 5 -> { /* Return to main menu */ }
            default -> MenuUtil.displayError("Invalid choice");
        }
    }
    
    /**
     * Request new service
     * 
     * SERVICE REQUEST:
     * - Enum usage for service types
     * - Input validation
     * - Service creation
     * - Error handling
     */
    private static void requestNewService() {
        try {
            MenuUtil.displayHeader("Request New Service");
            
            // ENUM USAGE: Display service types
            Service.ServiceType[] serviceTypes = Service.ServiceType.values();
            String[] typeOptions = new String[serviceTypes.length];
            
            for (int i = 0; i < serviceTypes.length; i++) {
                typeOptions[i] = serviceTypes[i].getDisplayName() + " - " + serviceTypes[i].getDescription();
            }
            
            int typeChoice = MenuUtil.displayMenu("Select Service Type", typeOptions);
            Service.ServiceType selectedType = serviceTypes[typeChoice];
            
            // INPUT COLLECTION
            String description = MenuUtil.getStringInput("Enter service description: ", false);
            String providerName = MenuUtil.getStringInput("Enter provider name: ", false);
            
            Double estimatedCost = null;
            try {
                String costInput = MenuUtil.getStringInput("Enter estimated cost (or press Enter to skip): ", true);
                if (!costInput.isEmpty()) {
                    estimatedCost = Double.parseDouble(costInput);
                }
            } catch (NumberFormatException e) {
                MenuUtil.displayWarning("Invalid cost format. Service will be created without cost estimate.");
            }
            
            String requestedBy = MenuUtil.getStringInput("Enter apartment number: ", false);
            
            // SERVICE CREATION
            Service newService = communityService.requestService(
                selectedType, description, providerName, estimatedCost, requestedBy
            );
            
            MenuUtil.displaySuccess("Service requested successfully!");
            System.out.println("Service ID: " + newService.getServiceId());
            System.out.println("Status: " + newService.getStatus());
            
        } catch (Exception e) {
            MenuUtil.displayError("Failed to request service: " + e.getMessage());
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * View all services
     * 
     * SERVICE DISPLAY:
     * - Service listing
     * - Status information
     * - Formatted output
     * - Collection iteration
     */
    private static void viewAllServices() {
        try {
            MenuUtil.displayHeader("All Services");
            
            // Get services by status for better organization
            Service.ServiceStatus[] statuses = Service.ServiceStatus.values();
            
            for (Service.ServiceStatus status : statuses) {
                List<Service> services = communityService.getServicesByStatus(status);
                
                if (!services.isEmpty()) {
                    System.out.println("\n" + status.getDescription() + " (" + services.size() + "):");
                    System.out.println("-".repeat(50));
                    
                    for (Service service : services) {
                        System.out.println("• " + service.getServiceId() + " - " + service.getServiceType().getDisplayName());
                        System.out.println("  Provider: " + service.getProviderName());
                        System.out.println("  Requested by: " + service.getRequestedBy());
                        System.out.println("  Cost: " + (service.getEstimatedCost() != null ? "$" + service.getEstimatedCost() : "Not specified"));
                        System.out.println("  Requested: " + DateUtil.formatForDisplay(service.getRequestedAt()));
                        System.out.println();
                    }
                }
            }
            
        } catch (Exception e) {
            MenuUtil.displayError("Failed to retrieve services: " + e.getMessage());
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Schedule a service
     * 
     * SERVICE SCHEDULING:
     * - Service state management
     * - DateTime input and validation
     * - Business logic integration
     * - Error handling
     */
    private static void scheduleService() {
        try {
            String serviceId = MenuUtil.getStringInput("Enter service ID to schedule: ", false);
            
            Optional<Service> service = communityService.findServiceById(serviceId);
            if (service.isEmpty()) {
                MenuUtil.displayError("Service not found: " + serviceId);
                MenuUtil.pauseForUser();
                return;
            }
            
            System.out.println("Service Details:");
            System.out.println("Type: " + service.get().getServiceType().getDisplayName());
            System.out.println("Status: " + service.get().getStatus());
            System.out.println("Provider: " + service.get().getProviderName());
            
            // DATE INPUT: Schedule datetime
            String scheduleDateInput = MenuUtil.getStringInput("Enter schedule date and time (yyyy-MM-dd HH:mm): ", false);
            
            Optional<LocalDateTime> scheduleTime = DateUtil.parseDateTime(scheduleDateInput, 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            
            if (scheduleTime.isEmpty()) {
                MenuUtil.displayError("Invalid date format. Please use yyyy-MM-dd HH:mm");
                MenuUtil.pauseForUser();
                return;
            }
            
            // SERVICE CALL: Schedule service
            Service scheduledService = communityService.scheduleService(serviceId, scheduleTime.get());
            
            MenuUtil.displaySuccess("Service scheduled successfully!");
            System.out.println("Scheduled for: " + DateUtil.formatForDisplay(scheduleTime.get()));
            
        } catch (ServiceException e) {
            MenuUtil.displayError("Scheduling failed: " + e.getUserFriendlyMessage());
        } catch (Exception e) {
            MenuUtil.displayError("Failed to schedule service: " + e.getMessage());
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Complete a service
     * 
     * SERVICE COMPLETION:
     * - Service state transitions
     * - Business logic validation
     * - User feedback
     */
    private static void completeService() {
        try {
            String serviceId = MenuUtil.getStringInput("Enter service ID to complete: ", false);
            
            Service completedService = communityService.completeService(serviceId);
            
            MenuUtil.displaySuccess("Service completed successfully!");
            System.out.println("Service ID: " + completedService.getServiceId());
            System.out.println("Completed at: " + DateUtil.formatForDisplay(LocalDateTime.now()));
            
        } catch (ServiceException e) {
            MenuUtil.displayError("Completion failed: " + e.getUserFriendlyMessage());
        } catch (Exception e) {
            MenuUtil.displayError("Failed to complete service: " + e.getMessage());
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Cancel a service
     * 
     * SERVICE CANCELLATION:
     * - Confirmation dialog
     * - State management
     * - Error handling
     */
    private static void cancelService() {
        try {
            String serviceId = MenuUtil.getStringInput("Enter service ID to cancel: ", false);
            
            if (!MenuUtil.getConfirmation("Are you sure you want to cancel this service?")) {
                MenuUtil.displayInfo("Cancellation aborted.");
                MenuUtil.pauseForUser();
                return;
            }
            
            Service cancelledService = communityService.cancelService(serviceId);
            
            MenuUtil.displaySuccess("Service cancelled successfully!");
            System.out.println("Service ID: " + cancelledService.getServiceId());
            
        } catch (ServiceException e) {
            MenuUtil.displayError("Cancellation failed: " + e.getUserFriendlyMessage());
        } catch (Exception e) {
            MenuUtil.displayError("Failed to cancel service: " + e.getMessage());
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * View application statistics
     * 
     * STATISTICS DISPLAY:
     * - Data aggregation
     * - Business metrics
     * - Formatted output
     * - Service integration
     */
    private static void viewStatistics() {
        try {
            MenuUtil.displayHeader("Application Statistics");
            
            // RESIDENT STATISTICS
            ResidentService.ResidentStatistics residentStats = residentService.getStatistics();
            System.out.println("📊 RESIDENT STATISTICS");
            System.out.println("Total Residents: " + residentStats.getTotalResidents());
            System.out.println("Active Residents: " + residentStats.getActiveResidents());
            System.out.println("Inactive Residents: " + residentStats.getInactiveResidents());
            System.out.println("Occupied Apartments: " + residentStats.getOccupiedApartments());
            System.out.println();
            
            // SERVICE STATISTICS
            CommunityService.ServiceStatistics serviceStats = communityService.getStatistics();
            System.out.println("🔧 SERVICE STATISTICS");
            System.out.println("Total Services: " + serviceStats.getTotalServices());
            System.out.println();
            
            System.out.println("Services by Status:");
            serviceStats.getStatusCounts().forEach((status, count) -> 
                System.out.println("  " + status + ": " + count));
            System.out.println();
            
            System.out.println("Services by Type:");
            serviceStats.getTypeCounts().forEach((type, count) -> 
                System.out.println("  " + type.getDisplayName() + ": " + count));
            System.out.println();
            
            // APARTMENT AVAILABILITY
            Set<String> occupiedApartments = residentService.getOccupiedApartments();
            System.out.println("APARTMENT INFORMATION");
            System.out.println("Occupied Apartments: " + String.join(", ", occupiedApartments));
            
        } catch (Exception e) {
            MenuUtil.displayError("Failed to retrieve statistics: " + e.getMessage());
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Search and filter demonstration
     * 
     * SEARCH FUNCTIONALITY:
     * - Advanced search options
     * - Filter demonstrations
     * - Predicate usage
     * - Stream API examples
     */
    private static void searchAndFilterDemo() {
        try {
            MenuUtil.displayHeader("Search and Filter Demo");
            
            String[] searchOptions = {
                "Filter by Resident Status",
                "Filter by Service Type",
                "Advanced Resident Search",
                "Advanced Service Search",
                "Back"
            };
            
            int choice = MenuUtil.displayMenu("Search Options", searchOptions);
            
            switch (choice) {
                case 0 -> filterByResidentStatus();
                case 1 -> filterByServiceType();
                case 2 -> advancedResidentSearch();
                case 3 -> advancedServiceSearch();
                case 4 -> { /* Return */ }
                default -> MenuUtil.displayError("Invalid choice");
            }
            
        } catch (Exception e) {
            MenuUtil.displayError("Search demo failed: " + e.getMessage());
            MenuUtil.pauseForUser();
        }
    }
    
    /**
     * Filter residents by status
     * 
     * ENUM FILTERING:
     * - Enum value selection
     * - Status-based filtering
     * - Result display
     */
    private static void filterByResidentStatus() {
        Resident.ResidentStatus[] statuses = Resident.ResidentStatus.values();
        String[] statusOptions = new String[statuses.length];
        
        for (int i = 0; i < statuses.length; i++) {
            statusOptions[i] = statuses[i].getDescription();
        }
        
        int choice = MenuUtil.displayMenu("Select Status", statusOptions);
        Resident.ResidentStatus selectedStatus = statuses[choice];
        
        List<Resident> filteredResidents = residentService.getResidentsByStatus(selectedStatus);
        
        MenuUtil.displayInfo("Residents with status: " + selectedStatus.getDescription());
        if (filteredResidents.isEmpty()) {
            System.out.println("No residents found with this status.");
        } else {
            filteredResidents.forEach(resident -> 
                System.out.println("• " + resident.getDisplayInfo()));
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Filter services by type
     * 
     * SERVICE TYPE FILTERING:
     * - Service type selection
     * - Type-based filtering
     * - Result display
     */
    private static void filterByServiceType() {
        Service.ServiceType[] types = Service.ServiceType.values();
        String[] typeOptions = new String[types.length];
        
        for (int i = 0; i < types.length; i++) {
            typeOptions[i] = types[i].getDisplayName();
        }
        
        int choice = MenuUtil.displayMenu("Select Service Type", typeOptions);
        Service.ServiceType selectedType = types[choice];
        
        List<Service> filteredServices = communityService.getServicesByType(selectedType);
        
        MenuUtil.displayInfo("Services of type: " + selectedType.getDisplayName());
        if (filteredServices.isEmpty()) {
            System.out.println("No services found of this type.");
        } else {
            filteredServices.forEach(service -> 
                System.out.println("• " + service.getServiceId() + " - " + service.getProviderName()));
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Advanced resident search using predicates
     * 
     * PREDICATE USAGE:
     * - Functional programming
     * - Lambda expressions
     * - Complex filtering
     * - Stream API operations
     */
    private static void advancedResidentSearch() {
        MenuUtil.displayHeader("Advanced Resident Search");
        
        // PREDICATE EXAMPLES: Various search predicates
        Map<String, Predicate<Resident>> searchOptions = new LinkedHashMap<>();
        
        searchOptions.put("Residents with phone numbers", 
            resident -> resident.getPhoneNumber().isPresent());
        
        searchOptions.put("Residents without phone numbers", 
            resident -> resident.getPhoneNumber().isEmpty());
        
        searchOptions.put("Residents with service requests", 
            resident -> !resident.getServiceRequests().isEmpty());
        
        searchOptions.put("Residents aged 30 or older", 
            resident -> resident.getAge() != null && resident.getAge() >= 30);
        
        searchOptions.put("Residents in apartments 100-199", 
            resident -> {
                try {
                    int aptNum = Integer.parseInt(resident.getApartmentNumber());
                    return aptNum >= 100 && aptNum <= 199;
                } catch (NumberFormatException e) {
                    return false;
                }
            });
        
        // MENU DISPLAY
        String[] optionNames = searchOptions.keySet().toArray(new String[0]);
        int choice = MenuUtil.displayMenu("Select Search Criteria", optionNames);
        
        String selectedOption = optionNames[choice];
        Predicate<Resident> selectedPredicate = searchOptions.get(selectedOption);
        
        // SEARCH EXECUTION
        List<Resident> results = residentService.findResidents(selectedPredicate);
        
        MenuUtil.displayInfo("Search Results for: " + selectedOption);
        if (results.isEmpty()) {
            System.out.println("No residents found matching the criteria.");
        } else {
            System.out.println("Found " + results.size() + " resident(s):");
            results.forEach(resident -> 
                System.out.println("• " + resident.getDisplayInfo()));
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Advanced service search using predicates
     * 
     * SERVICE FILTERING:
     * - Complex service filters
     * - Predicate combinations
     * - Business logic filters
     */
    private static void advancedServiceSearch() {
        MenuUtil.displayHeader("Advanced Service Search");
        
        // SERVICE PREDICATES
        Map<String, Predicate<Service>> searchOptions = new LinkedHashMap<>();
        
        searchOptions.put("Services with cost estimates", 
            service -> service.getEstimatedCost() != null);
        
        searchOptions.put("Services without cost estimates", 
            service -> service.getEstimatedCost() == null);
        
        searchOptions.put("Expensive services (> $100)", 
            service -> service.getEstimatedCost() != null && service.getEstimatedCost() > 100.0);
        
        searchOptions.put("Services requested today", 
            service -> DateUtil.isToday(service.getRequestedAt().toLocalDate()));
        
        searchOptions.put("Services available on weekends", 
            service -> service.getServiceType().isAvailableOnWeekends());
        
        // MENU AND EXECUTION
        String[] optionNames = searchOptions.keySet().toArray(new String[0]);
        int choice = MenuUtil.displayMenu("Select Search Criteria", optionNames);
        
        String selectedOption = optionNames[choice];
        Predicate<Service> selectedPredicate = searchOptions.get(selectedOption);
        
        List<Service> results = communityService.findServices(selectedPredicate);
        
        MenuUtil.displayInfo("Search Results for: " + selectedOption);
        if (results.isEmpty()) {
            System.out.println("No services found matching the criteria.");
        } else {
            System.out.println("Found " + results.size() + " service(s):");
            results.forEach(service -> 
                System.out.println("• " + service.getServiceId() + " - " + service.getServiceType().getDisplayName()));
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Java 8+ features demonstration
     * 
     * JAVA 8+ FEATURES:
     * - Stream API operations
     * - Lambda expressions
     * - Method references
     * - Optional usage
     * - Functional interfaces
     */
    private static void java8FeaturesDemo() {
        try {
            MenuUtil.displayHeader("Java 8+ Features Demo");
            
            String[] demoOptions = {
                "Stream API Demo",
                "Lambda Expressions Demo",
                "Optional Usage Demo",
                "Method References Demo",
                "Functional Interfaces Demo",
                "Back"
            };
            
            int choice = MenuUtil.displayMenu("Java 8+ Features", demoOptions);
            
            switch (choice) {
                case 0 -> streamApiDemo();
                case 1 -> lambdaExpressionsDemo();
                case 2 -> optionalUsageDemo();
                case 3 -> methodReferencesDemo();
                case 4 -> functionalInterfacesDemo();
                case 5 -> { /* Return */ }
                default -> MenuUtil.displayError("Invalid choice");
            }
            
        } catch (Exception e) {
            MenuUtil.displayError("Java 8+ demo failed: " + e.getMessage());
            MenuUtil.pauseForUser();
        }
    }
    
    /**
     * Stream API demonstration
     * 
     * STREAM API:
     * - Filtering operations
     * - Mapping operations
     * - Collecting results
     * - Parallel processing
     * - Complex operations
     */
    private static void streamApiDemo() {
        MenuUtil.displayHeader("Stream API Demo");
        
        List<Resident> allResidents = residentService.getAllResidentsSortedByApartment();
        
        System.out.println("📊 Stream API Operations Demo:");
        System.out.println();
        
        // FILTER OPERATION
        System.out.println("1. Filter - Residents with phone numbers:");
        List<Resident> residentsWithPhones = allResidents.stream()
            .filter(resident -> resident.getPhoneNumber().isPresent())
            .collect(Collectors.toList());
        
        residentsWithPhones.forEach(resident -> 
            System.out.println("   • " + resident.getName() + " - " + resident.getPhoneNumber().get()));
        System.out.println();
        
        // MAP OPERATION
        System.out.println("2. Map - Extract all apartment numbers:");
        List<String> apartmentNumbers = allResidents.stream()
            .map(Resident::getApartmentNumber)
            .collect(Collectors.toList());
        
        System.out.println("   Apartments: " + String.join(", ", apartmentNumbers));
        System.out.println();
        
        // REDUCE OPERATION
        System.out.println("3. Reduce - Total unit count:");
        int totalUnits = allResidents.stream()
            .mapToInt(resident -> resident.getUnitCount() != null ? resident.getUnitCount() : 0)
            .sum();
        
        System.out.println("   Total units: " + totalUnits);
        System.out.println();
        
        // GROUPING OPERATION
        System.out.println("4. Group by - Residents by status:");
        Map<Resident.ResidentStatus, List<Resident>> residentsByStatus = allResidents.stream()
            .collect(Collectors.groupingBy(Resident::getStatus));
        
        residentsByStatus.forEach((status, residents) -> 
            System.out.println("   " + status + ": " + residents.size() + " residents"));
        System.out.println();
        
        // PARALLEL STREAM
        System.out.println("5. Parallel Stream - Count with parallel processing:");
        long count = allResidents.parallelStream()
            .filter(resident -> resident.getAge() != null)
            .count();
        
        System.out.println("   Residents with age information: " + count);
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Lambda expressions demonstration
     * 
     * LAMBDA EXPRESSIONS:
     * - Various lambda syntaxes
     * - Functional interfaces
     * - Method implementations
     * - Expression vs statement lambdas
     */
    private static void lambdaExpressionsDemo() {
        MenuUtil.displayHeader("Lambda Expressions Demo");
        
        System.out.println("🔧 Lambda Expression Examples:");
        System.out.println();
        
        List<Resident> residents = residentService.getAllResidentsSortedByApartment();
        
        // SIMPLE LAMBDA
        System.out.println("1. Simple Lambda - Filter active residents:");
        residents.stream()
            .filter(r -> r.getStatus() == Resident.ResidentStatus.ACTIVE)
            .forEach(r -> System.out.println("   • " + r.getName()));
        System.out.println();
        
        // MULTI-LINE LAMBDA
        System.out.println("2. Multi-line Lambda - Complex filtering:");
        residents.stream()
            .filter(resident -> {
                boolean hasPhone = resident.getPhoneNumber().isPresent();
                boolean isActive = resident.getStatus() == Resident.ResidentStatus.ACTIVE;
                return hasPhone && isActive;
            })
            .forEach(resident -> System.out.println("   • " + resident.getName() + " (Active with phone)"));
        System.out.println();
        
        // LAMBDA WITH CUSTOM FUNCTIONAL INTERFACE
        System.out.println("3. Custom Functional Interface:");
        
        // Custom functional interface
        @FunctionalInterface
        interface ResidentProcessor {
            void process(Resident resident);
        }
        
        ResidentProcessor processor = resident -> {
            System.out.println("   Processing: " + resident.getName());
            System.out.println("   Apartment: " + resident.getApartmentNumber());
            System.out.println("   Status: " + resident.getStatus());
            System.out.println();
        };
        
        residents.stream()
            .limit(2)
            .forEach(processor::process);
        
        // LAMBDA COMPOSITION
        System.out.println("4. Lambda Composition:");
        Predicate<Resident> isActive = r -> r.getStatus() == Resident.ResidentStatus.ACTIVE;
        Predicate<Resident> hasPhone = r -> r.getPhoneNumber().isPresent();
        Predicate<Resident> activeWithPhone = isActive.and(hasPhone);
        
        long count = residents.stream()
            .filter(activeWithPhone)
            .count();
        
        System.out.println("   Active residents with phone: " + count);
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Optional usage demonstration
     * 
     * OPTIONAL FEATURES:
     * - Optional creation
     * - Optional operations
     * - Null safety
     * - Functional style
     */
    private static void optionalUsageDemo() {
        MenuUtil.displayHeader("Optional Usage Demo");
        
        System.out.println("🔒 Optional Safety Examples:");
        System.out.println();
        
        // OPTIONAL CREATION
        Optional<String> presentValue = Optional.of("Hello World");
        Optional<String> emptyValue = Optional.empty();
        Optional<String> nullableValue = Optional.ofNullable(null);
        
        System.out.println("1. Optional Creation:");
        System.out.println("   Present value: " + presentValue.isPresent());
        System.out.println("   Empty value: " + emptyValue.isPresent());
        System.out.println("   Nullable value: " + nullableValue.isPresent());
        System.out.println();
        
        // OPTIONAL OPERATIONS
        System.out.println("2. Optional Operations:");
        
        // Get resident and safely access phone
        Optional<Resident> resident = residentService.findByEmail("john.doe@email.com");
        
        // Traditional way (verbose)
        if (resident.isPresent()) {
            Resident r = resident.get();
            if (r.getPhoneNumber().isPresent()) {
                System.out.println("   Phone (traditional): " + r.getPhoneNumber().get());
            } else {
                System.out.println("   No phone number available");
            }
        }
        
        // Functional way (elegant)
        String phone = resident
            .flatMap(Resident::getPhoneNumber)
            .orElse("No phone available");
        System.out.println("   Phone (functional): " + phone);
        System.out.println();
        
        // OPTIONAL CHAINING
        System.out.println("3. Optional Chaining:");
        
        String result = resident
            .filter(r -> r.getStatus() == Resident.ResidentStatus.ACTIVE)
            .map(Resident::getName)
            .map(String::toUpperCase)
            .orElse("NO ACTIVE RESIDENT");
        
        System.out.println("   Result: " + result);
        System.out.println();
        
        // OPTIONAL WITH EXCEPTIONS
        System.out.println("4. Optional with Exceptions:");
        
        try {
            String apartmentNumber = resident
                .map(Resident::getApartmentNumber)
                .orElseThrow(() -> new IllegalStateException("Resident not found"));
            System.out.println("   Apartment: " + apartmentNumber);
        } catch (Exception e) {
            System.out.println("   Error: " + e.getMessage());
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Method references demonstration
     * 
     * METHOD REFERENCES:
     * - Static method references
     * - Instance method references
     * - Constructor references
     * - Bound method references
     */
    private static void methodReferencesDemo() {
        MenuUtil.displayHeader("Method References Demo");
        
        System.out.println("🔗 Method Reference Examples:");
        System.out.println();
        
        List<Resident> residents = residentService.getAllResidentsSortedByApartment();
        
        // STATIC METHOD REFERENCE
        System.out.println("1. Static Method Reference:");
        List<String> names = residents.stream()
            .map(Resident::getName)
            .collect(Collectors.toList());
        
        names.forEach(System.out::println);
        System.out.println();
        
        // INSTANCE METHOD REFERENCE
        System.out.println("2. Instance Method Reference:");
        residents.stream()
            .map(Resident::getDisplayInfo)
            .forEach(System.out::println);
        System.out.println();
        
        // CONSTRUCTOR REFERENCE
        System.out.println("3. Constructor Reference:");
        List<String> apartmentNumbers = residents.stream()
            .map(Resident::getApartmentNumber)
            .collect(Collectors.toCollection(ArrayList::new));
        
        System.out.println("   Apartments collected: " + apartmentNumbers);
        System.out.println();
        
        // BOUND METHOD REFERENCE
        System.out.println("4. Bound Method Reference:");
        String prefix = "Resident: ";
        residents.stream()
            .map(Resident::getName)
            .map(prefix::concat)
            .forEach(System.out::println);
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Functional interfaces demonstration
     * 
     * FUNCTIONAL INTERFACES:
     * - Predicate interface
     * - Function interface
     * - Consumer interface
     * - Supplier interface
     * - Custom functional interfaces
     */
    private static void functionalInterfacesDemo() {
        MenuUtil.displayHeader("Functional Interfaces Demo");
        
        System.out.println("⚡ Functional Interface Examples:");
        System.out.println();
        
        List<Resident> residents = residentService.getAllResidentsSortedByApartment();
        
        // PREDICATE INTERFACE
        System.out.println("1. Predicate Interface:");
        Predicate<Resident> isActive = r -> r.getStatus() == Resident.ResidentStatus.ACTIVE;
        Predicate<Resident> hasPhone = r -> r.getPhoneNumber().isPresent();
        
        long activeCount = residents.stream().filter(isActive).count();
        long phoneCount = residents.stream().filter(hasPhone).count();
        long bothCount = residents.stream().filter(isActive.and(hasPhone)).count();
        
        System.out.println("   Active residents: " + activeCount);
        System.out.println("   Residents with phone: " + phoneCount);
        System.out.println("   Active with phone: " + bothCount);
        System.out.println();
        
        // FUNCTION INTERFACE
        System.out.println("2. Function Interface:");
        java.util.function.Function<Resident, String> toDisplayString = 
            r -> r.getName() + " (" + r.getApartmentNumber() + ")";
        
        residents.stream()
            .map(toDisplayString)
            .forEach(display -> System.out.println("   " + display));
        System.out.println();
        
        // CONSUMER INTERFACE
        System.out.println("3. Consumer Interface:");
        Consumer<Resident> printDetails = resident -> {
            System.out.println("   Name: " + resident.getName());
            System.out.println("   Apartment: " + resident.getApartmentNumber());
            System.out.println("   Status: " + resident.getStatus());
            System.out.println();
        };
        
        residents.stream()
            .limit(1)
            .forEach(printDetails);
        
        // SUPPLIER INTERFACE
        System.out.println("4. Supplier Interface:");
        java.util.function.Supplier<String> currentTime = () -> DateUtil.formatForDisplay(LocalDateTime.now());
        java.util.function.Supplier<Integer> randomNumber = () -> (int) (Math.random() * 100);
        
        System.out.println("   Current time: " + currentTime.get());
        System.out.println("   Random number: " + randomNumber.get());
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Exception handling demonstration
     * 
     * EXCEPTION HANDLING:
     * - Try-catch-finally blocks
     * - Custom exceptions
     * - Exception chaining
     * - Exception best practices
     */
    private static void exceptionHandlingDemo() {
        MenuUtil.displayHeader("Exception Handling Demo");
        
        System.out.println("Exception Handling Examples:");
        System.out.println();
        
        // BASIC EXCEPTION HANDLING
        System.out.println("1. Basic Try-Catch:");
        try {
            String invalidEmail = "invalid-email";
            Resident resident = new Resident("Test", invalidEmail, LocalDate.now().minusYears(25), "999", 1);
            System.out.println("   This should not print");
        } catch (IllegalArgumentException e) {
            System.out.println("   Caught exception: " + e.getMessage());
        }
        System.out.println();
        
        // CUSTOM EXCEPTION HANDLING
        System.out.println("2. Custom Exception Handling:");
        try {
            // Try to add duplicate resident
            Resident duplicate = new Resident("John Doe", "john.doe@email.com", LocalDate.now().minusYears(30), "101", 1);
            residentService.registerResident(duplicate);
        } catch (DuplicateResidentException e) {
            System.out.println("   Duplicate resident error: " + e.getUserFriendlyMessage());
            System.out.println("   Conflicting field: " + e.getConflictingField());
            System.out.println("   Conflicting value: " + e.getConflictingValue());
        }
        System.out.println();
        
        // EXCEPTION CHAINING
        System.out.println("3. Exception Chaining:");
        try {
            // This will demonstrate exception chaining
            residentService.deleteResident("nonexistent@email.com");
        } catch (ResidentNotFoundException e) {
            System.out.println("   Not found error: " + e.getUserFriendlyMessage());
            System.out.println("   Search field: " + e.getSearchField());
            System.out.println("   Search value: " + e.getSearchValue());
            System.out.println("   Suggestions: " + e.getSuggestions());
        }
        System.out.println();
        
        // TRY-WITH-RESOURCES SIMULATION
        System.out.println("4. Resource Management:");
        try {
            // Simulate resource usage
            System.out.println("   Opening resource...");
            // Simulate some work
            System.out.println("   Working with resource...");
            System.out.println("   Resource used successfully");
        } catch (Exception e) {
            System.out.println("   Resource error: " + e.getMessage());
        } finally {
            System.out.println("   Cleaning up resource...");
        }
        System.out.println();
        
        // MULTIPLE CATCH BLOCKS
        System.out.println("5. Multiple Catch Blocks:");
        try {
            // Simulate different types of exceptions
            String operation = MenuUtil.getStringInput("Enter operation (parse/divide/access): ", false);
            
            switch (operation.toLowerCase()) {
                case "parse" -> {
                    int number = Integer.parseInt("not-a-number");
                    System.out.println("Parsed: " + number);
                }
                case "divide" -> {
                    int result = 10 / 0;
                    System.out.println("Result: " + result);
                }
                case "access" -> {
                    String[] array = {"a", "b", "c"};
                    System.out.println("Element: " + array[5]);
                }
                default -> System.out.println("   Valid operation entered");
            }
        } catch (NumberFormatException e) {
            System.out.println("   Number format error: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("   Arithmetic error: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("   Array access error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("   General error: " + e.getMessage());
        }
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Collections Framework demonstration
     * 
     * COLLECTIONS FRAMEWORK:
     * - List operations
     * - Set operations
     * - Map operations
     * - Queue operations
     * - Collection algorithms
     */
    private static void collectionsFrameworkDemo() {
        MenuUtil.displayHeader("Collections Framework Demo");
        
        System.out.println("📦 Collections Framework Examples:");
        System.out.println();
        
        // LIST OPERATIONS
        System.out.println("1. List Operations:");
        List<String> apartmentList = new ArrayList<>();
        apartmentList.add("101");
        apartmentList.add("102");
        apartmentList.add("103");
        apartmentList.add("102"); // Duplicate allowed
        
        System.out.println("   List contents: " + apartmentList);
        System.out.println("   Size: " + apartmentList.size());
        System.out.println("   Contains 102: " + apartmentList.contains("102"));
        System.out.println("   Index of 102: " + apartmentList.indexOf("102"));
        System.out.println("   Last index of 102: " + apartmentList.lastIndexOf("102"));
        System.out.println();
        
        // SET OPERATIONS
        System.out.println("2. Set Operations:");
        Set<String> apartmentSet = new HashSet<>(apartmentList);
        Set<String> treeSet = new TreeSet<>(apartmentList);
        
        System.out.println("   HashSet contents: " + apartmentSet);
        System.out.println("   TreeSet contents: " + treeSet);
        System.out.println("   Set size (duplicates removed): " + apartmentSet.size());
        System.out.println();
        
        // MAP OPERATIONS
        System.out.println("3. Map Operations:");
        Map<String, Resident> residentMap = new HashMap<>();
        
        residentService.getAllResidentsSortedByApartment()
            .forEach(resident -> residentMap.put(resident.getApartmentNumber(), resident));
        
        System.out.println("   Map size: " + residentMap.size());
        System.out.println("   Keys: " + residentMap.keySet());
        System.out.println("   Contains apartment 101: " + residentMap.containsKey("101"));
        
        residentMap.forEach((apartment, resident) -> 
            System.out.println("   " + apartment + " -> " + resident.getName()));
        System.out.println();
        
        // QUEUE OPERATIONS
        System.out.println("4. Queue Operations:");
        Queue<String> serviceQueue = new LinkedList<>();
        
        serviceQueue.offer("Cleaning Request");
        serviceQueue.offer("Maintenance Request");
        serviceQueue.offer("Repair Request");
        
        System.out.println("   Queue contents: " + serviceQueue);
        System.out.println("   Peek: " + serviceQueue.peek());
        System.out.println("   Poll: " + serviceQueue.poll());
        System.out.println("   After poll: " + serviceQueue);
        System.out.println();
        
        // COLLECTION ALGORITHMS
        System.out.println("5. Collection Algorithms:");
        List<String> names = residentService.getAllResidentsSortedByApartment()
            .stream()
            .map(Resident::getName)
            .collect(Collectors.toList());
        
        System.out.println("   Original names: " + names);
        
        Collections.sort(names);
        System.out.println("   Sorted names: " + names);
        
        Collections.reverse(names);
        System.out.println("   Reversed names: " + names);
        
        Collections.shuffle(names);
        System.out.println("   Shuffled names: " + names);
        
        String maxName = Collections.max(names);
        String minName = Collections.min(names);
        System.out.println("   Max name: " + maxName);
        System.out.println("   Min name: " + minName);
        
        MenuUtil.pauseForUser();
    }
    
    /**
     * Exit application
     * 
     * APPLICATION SHUTDOWN:
     * - User confirmation
     * - Cleanup operations
     * - Graceful shutdown
     */
    private static void exitApplication() {
        if (MenuUtil.getConfirmation("Are you sure you want to exit?")) {
            isRunning = false;
            MenuUtil.displayInfo("Thank you for using the Community Management System!");
            MenuUtil.displayInfo("Goodbye!");
        }
    }
    
    /**
     * Cleanup operations
     * 
     * CLEANUP:
     * - Resource cleanup
     * - Final operations
     * - Shutdown procedures
     */
    private static void cleanup() {
        // In a real application, this would close database connections,
        // save state, release resources, etc.
        System.out.println("Performing cleanup operations...");
        
        // Reset static variables
        residentService = null;
        communityService = null;
        
        System.out.println("Cleanup completed.");
    }
}