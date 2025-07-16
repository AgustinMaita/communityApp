package com.community.communityApp;

import com.community.communityApp.model.*;
import com.community.communityApp.service.*;
import com.community.communityApp.util.*;

import java.time.LocalDate;
import java.util.*;

/**
 * Interactive test class for manual testing of the Community Management Application
 */
public class InteractiveTest {
    
    public static void main(String[] args) {
        System.out.println("COMMUNITY MANAGEMENT APPLICATION - INTERACTIVE TEST");
        System.out.println("=" + "=".repeat(60));
        
        // Initialize services
        ResidentService residentService = new ResidentService();
        CommunityService communityService = new CommunityService();
        
        // Load sample data
        loadSampleData(residentService, communityService);
        
        // Simple menu-driven test
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        testResidentOperations(residentService, scanner);
                        break;
                    case 2:
                        testServiceOperations(communityService, scanner);
                        break;
                    case 3:
                        testUtilityClasses(scanner);
                        break;
                    case 4:
                        testJavaFeatures();
                        break;
                    case 5:
                        showCurrentData(residentService, communityService);
                        break;
                    case 0:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                
                if (running) {
                    System.out.println("\\nPress Enter to continue...");
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                scanner.nextLine(); // consume invalid input
            }
        }
        
        scanner.close();
    }
    
    private static void displayMainMenu() {
        System.out.println("\\n" + "=".repeat(50));
        System.out.println("MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Test Resident Operations");
        System.out.println("2. Test Service Operations");
        System.out.println("3. Test Utility Classes");
        System.out.println("4. Test Java 8+ Features");
        System.out.println("5. Show Current Data");
        System.out.println("0. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice: ");
    }
    
    private static void testResidentOperations(ResidentService residentService, Scanner scanner) {
        System.out.println("\\nRESIDENT OPERATIONS TEST");
        System.out.println("-".repeat(40));
        
        System.out.println("1. Current residents:");
        residentService.getAllResidentsSortedByApartment()
            .forEach(r -> System.out.println("   - " + r.getName() + " (" + r.getApartmentNumber() + ")"));
        
        System.out.println("\\n2. Search by apartment number:");
        System.out.print("Enter apartment number: ");
        String apartmentNumber = scanner.nextLine();
        
        Optional<Resident> resident = residentService.findByApartmentNumber(apartmentNumber);
        if (resident.isPresent()) {
            System.out.println("Found: " + resident.get().getName());
        } else {
            System.out.println("No resident found for apartment " + apartmentNumber);
        }
        
        System.out.println("\\n3. Search by email:");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        try {
            Optional<Resident> found = residentService.findByEmail(email);
            if (found.isPresent()) {
                System.out.println("Found: " + found.get().getName() + " - " + found.get().getRole());
            } else {
                System.out.println("Not found: No resident with email " + email);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void testServiceOperations(CommunityService communityService, Scanner scanner) {
        System.out.println("\\nSERVICE OPERATIONS TEST");
        System.out.println("-".repeat(40));
        
        System.out.println("1. Request a new service:");
        System.out.print("Enter service description: ");
        String description = scanner.nextLine();
        
        try {
            Service newService = communityService.requestService(
                Service.ServiceType.MAINTENANCE,
                description,
                "test@community.com",
                100.0,
                "A001"
            );
            System.out.println("Service requested: " + newService.getId());
            System.out.println("Status: " + newService.getStatus());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\\n2. Available service types:");
        Arrays.stream(Service.ServiceType.values())
            .forEach(type -> System.out.println("   - " + type + ": " + type.getDescription()));
    }
    
    private static void testUtilityClasses(Scanner scanner) {
        System.out.println("\\nUTILITY CLASSES TEST");
        System.out.println("-".repeat(40));
        
        System.out.println("1. Date Utility:");
        System.out.println("   - Today: " + DateUtil.formatForDisplay(DateUtil.today()));
        System.out.println("   - Is weekend: " + DateUtil.isWeekend(DateUtil.today()));
        
        System.out.println("\\n2. Validation Utility:");
        System.out.print("Enter email to validate: ");
        String email = scanner.nextLine();
        System.out.println("   - Valid email: " + ValidationUtil.isValidEmail(email));
        
        System.out.print("Enter phone number to validate: ");
        String phone = scanner.nextLine();
        System.out.println("   - Valid phone: " + ValidationUtil.isValidPhoneNumber(phone));
        
        System.out.print("Enter apartment number to validate: ");
        String apartment = scanner.nextLine();
        System.out.println("   - Valid apartment: " + ValidationUtil.isValidApartmentNumber(apartment));
    }
    
    private static void testJavaFeatures() {
        System.out.println("\\nJAVA 8+ FEATURES TEST");
        System.out.println("-".repeat(40));
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        
        System.out.println("1. Lambda expressions:");
        names.forEach(name -> System.out.println("   - Hello, " + name));
        
        System.out.println("\\n2. Stream API:");
        List<String> filtered = names.stream()
            .filter(name -> name.startsWith("A") || name.startsWith("B"))
            .map(String::toUpperCase)
            .sorted()
            .collect(java.util.stream.Collectors.toList());
        System.out.println("   - Filtered names: " + filtered);
        
        System.out.println("\\n3. Optional:");
        Optional<String> longest = names.stream()
            .max(Comparator.comparing(String::length));
        System.out.println("   - Longest name: " + longest.orElse("None"));
        
        System.out.println("\\n4. Method references:");
        names.stream()
            .map(String::length)
            .forEach(length -> System.out.println("   - Length: " + length));
    }
    
    private static void showCurrentData(ResidentService residentService, CommunityService communityService) {
        System.out.println("\\nCURRENT DATA SUMMARY");
        System.out.println("-".repeat(40));
        
        List<Resident> residents = residentService.getAllResidentsSortedByApartment();
        System.out.println("Total residents: " + residents.size());
        
        long administrators = residents.stream()
            .filter(r -> r instanceof Administrator)
            .count();
        System.out.println("Administrators: " + administrators);
        
        System.out.println("\\nResident details:");
        residents.forEach(r -> {
            System.out.println("  - " + r.getName() + " (" + r.getApartmentNumber() + ") - " + r.getRole());
            if (r instanceof Administrator) {
                Administrator admin = (Administrator) r;
                System.out.println("    Admin Role: " + admin.getAdminRole());
            }
        });
    }
    
    private static void loadSampleData(ResidentService residentService, CommunityService communityService) {
        System.out.println("Loading sample data...");
        
        try {
            // Create sample residents
            Resident resident1 = new Resident("John Doe", "john@community.com", LocalDate.of(1985, 3, 15), "A101", 1);
            Resident resident2 = new Resident("Jane Smith", "jane@community.com", LocalDate.of(1990, 7, 22), "B202", 2);
            
            // Create administrator
            Administrator admin = new Administrator(
                "Robert Johnson", 
                "robert@community.com", 
                LocalDate.of(1978, 11, 5),
                "C303",
                3,
                Administrator.AdminRole.PRESIDENT
            );
            
            // Register residents
            residentService.registerResident(resident1);
            residentService.registerResident(resident2);
            residentService.registerResident(admin);
            
            // Request sample services
            communityService.requestService(
                Service.ServiceType.CLEANING,
                "Weekly building cleaning",
                "robert@community.com",
                200.0,
                "BUILDING"
            );
            
            communityService.requestService(
                Service.ServiceType.MAINTENANCE,
                "Fix elevator",
                "john@community.com",
                500.0,
                "ELEVATOR"
            );
            
            System.out.println("Sample data loaded successfully!");
            
        } catch (Exception e) {
            System.err.println("Error loading sample data: " + e.getMessage());
        }
    }
}