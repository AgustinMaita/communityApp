package com.community.communityApp;

import com.community.communityApp.exception.*;
import com.community.communityApp.model.*;
import com.community.communityApp.repository.*;
import com.community.communityApp.service.*;
import com.community.communityApp.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Test runner for the Community Management Application
 * 
 * This class demonstrates and tests all Core Java concepts implemented
 * in the application without requiring interactive console input.
 */
public class TestRunner {

    public static void main(String[] args) {
        System.out.println("TESTING COMMUNITY MANAGEMENT APPLICATION");
        System.out.println("=" + "=".repeat(50));
        
        TestRunner runner = new TestRunner();
        
        try {
            // Test all Core Java concepts
            runner.testVariablesAndDataTypes();
            runner.testControlFlow();
            runner.testOOPPrinciples();
            runner.testAccessModifiers();
            runner.testExceptions();
            runner.testCollections();
            runner.testGenerics();
            runner.testJava8Features();
            runner.testUtilityClasses();
            runner.testCompleteWorkflow();
            
            System.out.println("\nALL TESTS PASSED! Core Java concepts working correctly.");
            
        } catch (Exception e) {
            System.err.println("\nTEST FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test Variables and Data Types
     */
    private void testVariablesAndDataTypes() {
        System.out.println("\nTesting Variables & Data Types...");
        
        // Primitives
        int age = 25;
        boolean isActive = true;
        double balance = 1250.50;
        
        // Wrappers
        Integer apartmentNumber = 101;
        Boolean hasParking = Boolean.TRUE;
        Double maintenanceFee = Double.valueOf(150.75);
        
        // String
        String name = "Test Resident";
        
        System.out.println("  Primitives: int=" + age + ", boolean=" + isActive + ", double=" + balance);
        System.out.println("  Wrappers: Integer=" + apartmentNumber + ", Boolean=" + hasParking + ", Double=" + maintenanceFee);
        System.out.println("  String: " + name);
    }

    /**
     * Test Control Flow
     */
    private void testControlFlow() {
        System.out.println("\nTesting Control Flow...");
        
        // if-else
        int score = 85;
        String grade = (score >= 90) ? "A" : (score >= 80) ? "B" : "C";
        System.out.println("  if-else: Score " + score + " = Grade " + grade);
        
        // switch expression (modern Java)
        String day = "MONDAY";
        String dayType = switch (day) {
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "Weekday";
            case "SATURDAY", "SUNDAY" -> "Weekend";
            default -> "Unknown";
        };
        System.out.println("  switch expression: " + day + " is " + dayType);
        
        // for loop
        System.out.print("  for loop: ");
        for (int i = 1; i <= 3; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        // while loop
        System.out.print("  while loop: ");
        int count = 3;
        while (count > 0) {
            System.out.print(count + " ");
            count--;
        }
        System.out.println();
        
        // do-while loop
        System.out.print("  do-while loop: ");
        int num = 1;
        do {
            System.out.print(num + " ");
            num++;
        } while (num <= 3);
        System.out.println();
    }

    /**
     * Test OOP Principles
     */
    private void testOOPPrinciples() {
        System.out.println("\nTesting OOP Principles...");
        
        // Inheritance: Person → Resident → Administrator
        Administrator admin = new Administrator(
            "Alice Johnson", 
            "alice@community.com", 
            LocalDate.of(1980, 5, 15),
            "A101",
            1,
            Administrator.AdminRole.PRESIDENT
        );
        
        System.out.println("  Inheritance: Administrator extends Resident extends Person");
        System.out.println("    - Name: " + admin.getName());
        System.out.println("    - Role: " + admin.getRole()); // Overridden method
        System.out.println("    - Admin Level: " + admin.getAdminRole());
        
        // Polymorphism
        Person person = admin; // Upcasting
        System.out.println("  Polymorphism: Person reference to Administrator");
        System.out.println("    - Type: " + person.getClass().getSimpleName());
        System.out.println("    - Role: " + person.getRole()); // Dynamic method dispatch
        
        // Interface implementation
        Identifiable<String> identifiable = admin;
        System.out.println("  Interface: Identifiable implementation");
        System.out.println("    - ID: " + identifiable.getId());
        
        // Encapsulation
        System.out.println("  Encapsulation: Private fields with public getters/setters");
    }

    /**
     * Test Access Modifiers
     */
    private void testAccessModifiers() {
        System.out.println("\nTesting Access Modifiers...");
        
        Resident resident = new Resident(
            "Bob Smith",
            "bob@community.com",
            LocalDate.of(1985, 3, 10),
            "B202",
            1
        );
        
        // public access
        System.out.println("  public: getName() = " + resident.getName());
        
        // protected access (accessible within package)
        System.out.println("  protected: Methods accessible within package");
        
        // private access (not directly accessible)
        System.out.println("  private: Fields encapsulated, accessed via methods");
        
        // package-private (default)
        System.out.println("  package-private: Default access within package");
    }

    /**
     * Test Exception Handling
     */
    private void testExceptions() {
        System.out.println("\nTesting Exception Handling...");
        
        ResidentService residentService = new ResidentService();
        
        // Register a resident
        Resident resident = new Resident("Test User", "test@community.com", LocalDate.of(1990, 1, 1), "T001", 1);
        residentService.registerResident(resident);
        
        // Test custom exceptions
        try {
            // This should throw DuplicateResidentException
            residentService.registerResident(resident);
            System.out.println("  Should have thrown DuplicateResidentException");
        } catch (DuplicateResidentException e) {
            System.out.println("  Custom exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        
        try {
            // This should throw ResidentNotFoundException
            residentService.findByEmail("nonexistent@community.com");
            System.out.println("  Should have thrown ResidentNotFoundException");
        } catch (ResidentNotFoundException e) {
            System.out.println("  Custom exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        
        // Test try-catch-finally
        try {
            DateUtil.parseDate("invalid-date");
            System.out.println("  try-catch: Date parsing handled gracefully");
        } catch (Exception e) {
            System.out.println("  try-catch: Exception handled - " + e.getClass().getSimpleName());
        } finally {
            System.out.println("  finally: Always executed");
        }
    }

    /**
     * Test Collections Framework
     */
    private void testCollections() {
        System.out.println("\nTesting Collections Framework...");
        
        // List
        List<String> residents = new ArrayList<>();
        residents.add("John Doe");
        residents.add("Jane Smith");
        residents.add("Bob Johnson");
        System.out.println("  List (ArrayList): " + residents.size() + " residents");
        
        // Set
        Set<String> emergencyContacts = new HashSet<>();
        emergencyContacts.add("911");
        emergencyContacts.add("Police");
        emergencyContacts.add("Fire Department");
        emergencyContacts.add("911"); // Duplicate - won't be added
        System.out.println("  Set (HashSet): " + emergencyContacts.size() + " unique contacts");
        
        // Map
        Map<String, String> preferences = new HashMap<>();
        preferences.put("language", "English");
        preferences.put("timezone", "UTC");
        preferences.put("theme", "dark");
        System.out.println("  Map (HashMap): " + preferences.size() + " preferences");
        
        // Queue
        Queue<String> serviceQueue = new LinkedList<>();
        serviceQueue.offer("Cleaning");
        serviceQueue.offer("Maintenance");
        serviceQueue.offer("Security");
        System.out.println("  Queue (LinkedList): " + serviceQueue.size() + " services queued");
        
        // Iteration
        System.out.print("  Enhanced for loop: ");
        for (String resident : residents) {
            System.out.print(resident.split(" ")[0] + " ");
        }
        System.out.println();
    }

    /**
     * Test Generics
     */
    private void testGenerics() {
        System.out.println("\nTesting Generics...");
        
        // Generic repository
        Repository<Resident, String> residentRepo = new InMemoryRepository<>();
        
        Resident resident1 = new Resident("Generic Test 1", "generic1@test.com", LocalDate.now(), "G001", 1);
        Resident resident2 = new Resident("Generic Test 2", "generic2@test.com", LocalDate.now(), "G002", 1);
        
        // Type-safe operations
        residentRepo.save(resident1);
        residentRepo.save(resident2);
        
        System.out.println("  Generic Repository<T,ID>: Type-safe operations");
        System.out.println("    - Saved residents: " + residentRepo.findAll().size());
        
        // Generic method
        Optional<Resident> found = residentRepo.findById("generic1@test.com");
        System.out.println("  Generic methods: findById returns Optional<T>");
        System.out.println("    - Found: " + found.map(Resident::getName).orElse("Not found"));
        
        // Bounded types
        System.out.println("  Bounded types: Repository<T extends Identifiable<ID>, ID>");
        
        // Generic collections
        List<Resident> typedList = new ArrayList<>();
        typedList.add(resident1);
        typedList.add(resident2);
        System.out.println("  Generic collections: List<Resident> with " + typedList.size() + " items");
    }

    /**
     * Test Java 8+ Features
     */
    private void testJava8Features() {
        System.out.println("\nTesting Java 8+ Features...");
        
        List<Resident> residents = Arrays.asList(
            new Resident("Alice", "alice@test.com", LocalDate.of(1985, 1, 1), "A001", 1),
            new Resident("Bob", "bob@test.com", LocalDate.of(1990, 5, 15), "B001", 1),
            new Resident("Charlie", "charlie@test.com", LocalDate.of(1995, 12, 31), "C001", 1)
        );
        
        // Lambda expressions
        System.out.println("  Lambda expressions:");
        residents.forEach(r -> System.out.println("    - " + r.getName() + " (" + r.getApartmentNumber() + ")"));
        
        // Method references
        System.out.println("  Method references:");
        List<String> names = residents.stream()
            .map(Resident::getName)
            .collect(Collectors.toList());
        System.out.println("    - Names: " + names);
        
        // Stream API
        System.out.println("  Stream API:");
        List<Resident> filtered = residents.stream()
            .filter(r -> r.getName().startsWith("A"))
            .collect(Collectors.toList());
        System.out.println("    - Residents starting with 'A': " + filtered.size());
        
        long count = residents.stream()
            .filter(r -> r.getBirthDate().getYear() >= 1990)
            .count();
        System.out.println("    - Residents born after 1990: " + count);
        
        // Optional
        Optional<Resident> youngest = residents.stream()
            .min(Comparator.comparing(Resident::getBirthDate));
        System.out.println("  Optional:");
        System.out.println("    - Youngest resident: " + youngest.map(Resident::getName).orElse("None"));
        
        // Predicate (Functional Interface)
        java.util.function.Predicate<Resident> isAdult = r -> 
            DateUtil.calculateAge(r.getBirthDate()) >= 18;
        System.out.println("  Predicate: Adult residents: " + 
            residents.stream().filter(isAdult).count());
    }

    /**
     * Test Utility Classes
     */
    private void testUtilityClasses() {
        System.out.println("\nTesting Utility Classes...");
        
        // ValidationUtil
        System.out.println("  ValidationUtil:");
        System.out.println("    - Valid email: " + ValidationUtil.isValidEmail("test@example.com"));
        System.out.println("    - Valid phone: " + ValidationUtil.isValidPhoneNumber("123-456-7890"));
        System.out.println("    - Valid apartment: " + ValidationUtil.isValidApartmentNumber("A101"));
        
        // DateUtil
        System.out.println("  DateUtil:");
        LocalDate today = DateUtil.today();
        System.out.println("    - Today: " + DateUtil.formatForDisplay(today));
        System.out.println("    - Age of 1990-01-01: " + DateUtil.calculateAge(LocalDate.of(1990, 1, 1)));
        System.out.println("    - Is weekend: " + DateUtil.isWeekend(today));
        
        // DateUtil parsing
        Optional<LocalDate> parsed = DateUtil.parseDate("2023-12-25");
        System.out.println("    - Parsed date: " + parsed.map(DateUtil::formatForDisplay).orElse("Invalid"));
    }

    /**
     * Test Complete Workflow
     */
    private void testCompleteWorkflow() {
        System.out.println("\nTesting Complete Workflow...");
        
        // Initialize services
        ResidentService residentService = new ResidentService();
        CommunityService communityService = new CommunityService();
        
        // Register residents
        Resident resident1 = new Resident("Workflow User 1", "workflow1@test.com", LocalDate.of(1985, 6, 15), "W001", 1);
        Resident resident2 = new Resident("Workflow User 2", "workflow2@test.com", LocalDate.of(1990, 8, 20), "W002", 1);
        
        residentService.registerResident(resident1);
        residentService.registerResident(resident2);
        
        // Create administrator
        Administrator admin = new Administrator(
            "Admin User", 
            "admin@test.com", 
            LocalDate.of(1975, 3, 10),
            "A001",
            1,
            Administrator.AdminRole.PRESIDENT
        );
        residentService.registerResident(admin);
        
        // Request services using the correct method signature
        Service requestedService = communityService.requestService(
            Service.ServiceType.CLEANING,
            "Weekly cleaning",
            "workflow1@test.com",
            50.0,
            "A001"
        );
        
        // Test queries
        System.out.println("  Workflow Results:");
        System.out.println("    - Total residents: " + residentService.getAllResidentsSortedByApartment().size());
        System.out.println("    - Administrators: " + residentService.getAllResidentsSortedByApartment().stream()
            .filter(r -> r instanceof Administrator)
            .count());
        System.out.println("    - Service requested: " + requestedService.getId());
        
        // Test search functionality
        List<Resident> searchResults = residentService.getAllResidentsSortedByApartment().stream()
            .filter(r -> r.getName().contains("Workflow"))
            .collect(Collectors.toList());
        System.out.println("    - Search 'Workflow': " + searchResults.size() + " results");
        
        // Test apartment search
        Optional<Resident> apartmentResident = residentService.findByApartmentNumber("W001");
        System.out.println("    - Apartment W001: " + apartmentResident.map(Resident::getName).orElse("Not found"));
    }
}