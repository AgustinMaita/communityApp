package com.community.communityApp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Administrator class representing a resident with administrative privileges.
 * 
 * CORE JAVA CONCEPTS DEMONSTRATED:
 * - Multi-level inheritance (Administrator extends Resident extends Person)
 * - Method overriding with super() calls
 * - Collections Framework with different collection types
 * - Enum with methods and constructors
 * - Generics with bounded wildcards
 * - Java 8+ features (LocalDateTime, Optional, Stream API)
 * - Composition (Administrator HAS-A permissions and actions)
 * 
 * INHERITANCE HIERARCHY:
 * Person (abstract)
 *   ↓
 * Resident (concrete)
 *   ↓
 * Administrator (concrete)
 * 
 * DESIGN RATIONALE:
 * - Administrator IS-A Resident (inherits all resident functionality)
 * - Administrator HAS-A additional permissions and administrative actions
 * - Demonstrates both inheritance and composition patterns
 */
public class Administrator extends Resident {
    
    /**
     * ENUM with rich functionality demonstration
     * 
     * ADVANCED ENUM features:
     * - Enum with constructor, fields, and methods
     * - Each enum constant has specific permissions
     * - Demonstrates enum as first-class objects
     * - Type-safe role definitions
     */
    public enum AdminRole {
        PRESIDENT("President", 5, "Full administrative access"),
        VICE_PRESIDENT("Vice President", 4, "Most administrative functions"),
        TREASURER("Treasurer", 3, "Financial management and reporting"),
        SECRETARY("Secretary", 2, "Communication and record keeping"),
        BOARD_MEMBER("Board Member", 1, "Limited administrative access");
        
        private final String displayName;
        private final int authorityLevel;
        private final String description;
        
        // Enum constructor
        AdminRole(String displayName, int authorityLevel, String description) {
            this.displayName = displayName;
            this.authorityLevel = authorityLevel;
            this.description = description;
        }
        
        // Enum methods
        public String getDisplayName() {
            return displayName;
        }
        
        public int getAuthorityLevel() {
            return authorityLevel;
        }
        
        public String getDescription() {
            return description;
        }
        
        /**
         * CONTROL FLOW demonstration with enhanced switch
         * 
         * JAVA 8+ SWITCH expressions:
         * - More concise than traditional switch
         * - Expression-based (returns value)
         * - Exhaustive (compiler ensures all cases covered)
         */
        public boolean canApproveExpense(double amount) {
            return switch (this) {
                case PRESIDENT -> amount <= 10000.0;
                case VICE_PRESIDENT -> amount <= 5000.0;
                case TREASURER -> amount <= 2000.0;
                case SECRETARY -> amount <= 500.0;
                case BOARD_MEMBER -> amount <= 100.0;
            };
        }
    }
    
    /**
     * Inner class to represent administrative actions
     * 
     * NESTED CLASSES:
     * - Encapsulates related functionality
     * - Access to outer class members
     * - Logical grouping of related classes
     */
    public static class AdminAction {
        private final LocalDateTime timestamp;
        private final String action;
        private final String description;
        
        public AdminAction(String action, String description) {
            this.timestamp = LocalDateTime.now();
            this.action = action;
            this.description = description;
        }
        
        // Getters
        public LocalDateTime getTimestamp() { return timestamp; }
        public String getAction() { return action; }
        public String getDescription() { return description; }
        
        @Override
        public String toString() {
            return String.format("[%s] %s: %s", timestamp, action, description);
        }
    }
    
    /**
     * COLLECTIONS FRAMEWORK with different collection types
     * 
     * Collection choice rationale:
     * - Set<String> for permissions: unique values, fast lookup
     * - List<AdminAction> for actions: ordered, chronological, allows duplicates
     * - Map<String, Object> for metadata: key-value pairs, flexible values
     */
    private AdminRole role;
    private Set<String> permissions;
    private List<AdminAction> adminActions;
    private Map<String, Object> adminMetadata;
    private LocalDateTime appointmentDate;
    
    /**
     * Constructor demonstrating multi-level inheritance
     * 
     * INHERITANCE chain:
     * - Calls super() to initialize Resident
     * - Resident calls super() to initialize Person
     * - Shows proper constructor chaining
     */
    public Administrator(String name, String email, LocalDate birthDate, 
                        String apartmentNumber, Integer unitCount, AdminRole role) {
        // Call parent constructor (Resident)
        super(name, email, birthDate, apartmentNumber, unitCount);
        
        if (role == null) {
            throw new IllegalArgumentException("Admin role cannot be null");
        }
        
        this.role = role;
        this.appointmentDate = LocalDateTime.now();
        
        // Initialize collections
        this.permissions = new HashSet<>();
        this.adminActions = new ArrayList<>();
        this.adminMetadata = new HashMap<>();
        
        // Set role-based permissions
        initializePermissions();
        
        // Record appointment action
        recordAction("APPOINTMENT", "Appointed as " + role.getDisplayName());
    }
    
    /**
     * CONTROL FLOW with if-else chains
     * 
     * PERMISSION SYSTEM:
     * - Different roles get different permissions
     * - Demonstrates cascading permissions (higher roles include lower permissions)
     * - Shows practical use of if-else for hierarchical logic
     */
    private void initializePermissions() {
        // Base permissions for all administrators
        permissions.add("VIEW_REPORTS");
        permissions.add("SEND_NOTIFICATIONS");
        
        // CONTROL FLOW demonstration with if-else
        if (role.getAuthorityLevel() >= 1) {
            permissions.add("MANAGE_RESERVATIONS");
        }
        
        if (role.getAuthorityLevel() >= 2) {
            permissions.add("MANAGE_COMMUNICATIONS");
            permissions.add("ACCESS_RESIDENT_INFO");
        }
        
        if (role.getAuthorityLevel() >= 3) {
            permissions.add("MANAGE_FINANCES");
            permissions.add("APPROVE_EXPENSES");
        }
        
        if (role.getAuthorityLevel() >= 4) {
            permissions.add("MANAGE_STAFF");
            permissions.add("SYSTEM_CONFIGURATION");
        }
        
        if (role.getAuthorityLevel() >= 5) {
            permissions.add("FULL_ACCESS");
            permissions.add("MANAGE_ADMINISTRATORS");
        }
    }
    
    /**
     * METHOD OVERRIDING demonstration
     * 
     * POLYMORPHISM:
     * - Overrides getRole() from Person
     * - Provides Administrator-specific implementation
     * - Shows how inheritance allows different behaviors for same method call
     */
    @Override
    public String getRole() {
        return "Administrator (" + role.getDisplayName() + ")";
    }
    
    /**
     * METHOD OVERRIDING with super() call
     * 
     * INHERITANCE best practice:
     * - Extends parent behavior rather than replacing it
     * - Calls super.getDisplayInfo() to reuse parent logic
     * - Adds Administrator-specific information
     */
    @Override
    public String getDisplayInfo() {
        String baseInfo = super.getDisplayInfo();
        return String.format("%s, Admin Role: %s, Appointed: %s", 
                           baseInfo, role.getDisplayName(), 
                           appointmentDate.toLocalDate());
    }
    
    /**
     * COLLECTIONS FRAMEWORK - Set operations
     * 
     * SET benefits for permissions:
     * - Automatic duplicate removal
     * - Fast contains() operation
     * - No ordering needed for permissions
     */
    public void addPermission(String permission) {
        if (permission == null || permission.trim().isEmpty()) {
            throw new IllegalArgumentException("Permission cannot be null or empty");
        }
        permissions.add(permission.trim().toUpperCase());
        recordAction("PERMISSION_GRANTED", "Added permission: " + permission);
    }
    
    public boolean hasPermission(String permission) {
        return permissions.contains(permission.toUpperCase());
    }
    
    public Set<String> getPermissions() {
        return new HashSet<>(permissions); // Defensive copy
    }
    
    /**
     * COLLECTIONS FRAMEWORK - List operations
     * 
     * LIST benefits for actions:
     * - Maintains chronological order
     * - Allows duplicate actions
     * - Indexed access for pagination
     */
    public void recordAction(String action, String description) {
        AdminAction adminAction = new AdminAction(action, description);
        adminActions.add(adminAction);
    }
    
    public List<AdminAction> getAdminActions() {
        return new ArrayList<>(adminActions); // Defensive copy
    }
    
    /**
     * JAVA 8+ FEATURES demonstration
     * 
     * STREAM API usage:
     * - Filtering actions by criteria
     * - Method chaining for readable code
     * - Collecting results into new collections
     */
    public List<AdminAction> getActionsAfterDate(LocalDateTime date) {
        return adminActions.stream()
                .filter(action -> action.getTimestamp().isAfter(date))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * COLLECTIONS FRAMEWORK - Map operations
     * 
     * MAP benefits for metadata:
     * - Key-value pairs for flexible data
     * - String keys for property names
     * - Object values for any data type
     */
    public void setMetadata(String key, Object value) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Metadata key cannot be null or empty");
        }
        adminMetadata.put(key.trim(), value);
    }
    
    public Optional<Object> getMetadata(String key) {
        return Optional.ofNullable(adminMetadata.get(key));
    }
    
    /**
     * GENERICS with bounded wildcards demonstration
     * 
     * BOUNDED WILDCARDS:
     * - ? extends T: upper bound (read-only)
     * - ? super T: lower bound (write-only)
     * - Provides type safety while allowing flexibility
     */
    public <T> Optional<T> getTypedMetadata(String key, Class<T> type) {
        Object value = adminMetadata.get(key);
        if (value != null && type.isInstance(value)) {
            return Optional.of(type.cast(value));
        }
        return Optional.empty();
    }
    
    /**
     * CONTROL FLOW with method logic
     * 
     * BUSINESS LOGIC:
     * - Combines role authority with specific permission checks
     * - Demonstrates complex conditional logic
     * - Shows practical use of boolean operations
     */
    public boolean canPerformAction(String action, double amount) {
        // Check if user has the required permission
        if (!hasPermission(action)) {
            return false;
        }
        
        // Special case for expense approvals
        if (action.equals("APPROVE_EXPENSES")) {
            return role.canApproveExpense(amount);
        }
        
        // For other actions, check authority level
        return switch (action) {
            case "MANAGE_ADMINISTRATORS" -> role.getAuthorityLevel() >= 5;
            case "SYSTEM_CONFIGURATION" -> role.getAuthorityLevel() >= 4;
            case "MANAGE_FINANCES" -> role.getAuthorityLevel() >= 3;
            case "MANAGE_COMMUNICATIONS" -> role.getAuthorityLevel() >= 2;
            default -> role.getAuthorityLevel() >= 1;
        };
    }
    
    // Standard getters and setters
    public AdminRole getAdminRole() {
        return role;
    }
    
    public void setAdminRole(AdminRole role) {
        if (role == null) {
            throw new IllegalArgumentException("Admin role cannot be null");
        }
        AdminRole oldRole = this.role;
        this.role = role;
        
        // Re-initialize permissions for new role
        permissions.clear();
        initializePermissions();
        
        // Record the role change
        recordAction("ROLE_CHANGE", 
                    String.format("Role changed from %s to %s", 
                                oldRole.getDisplayName(), role.getDisplayName()));
    }
    
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }
    
    /**
     * OBJECT CLASS METHODS override
     * 
     * COLLECTIONS FRAMEWORK requirement:
     * - Administrator objects need proper equals/hashCode
     * - Uses parent equals logic plus role comparison
     * - Maintains equals/hashCode contract
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        
        Administrator admin = (Administrator) obj;
        return Objects.equals(role, admin.role);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), role);
    }
    
    @Override
    public String toString() {
        return String.format("Administrator{%s, role=%s, permissions=%d}", 
                           super.toString(), role.getDisplayName(), permissions.size());
    }
}