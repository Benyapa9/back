package ac.th.fearfreeanimals.controller;

import ac.th.fearfreeanimals.entity.User;
import ac.th.fearfreeanimals.entity.Role;
import ac.th.fearfreeanimals.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins ="*",allowedHeaders = "*")
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Create a general user
    @PostMapping("/general")
    public ResponseEntity<User> createGeneralUser(@RequestBody User user) {
        // Find the "GENERAL" role
        Role role = roleRepository.findByName("GENERAL")
                .orElseThrow(() -> new RuntimeException("Role GENERAL not found"));
        
        user.setRole(role);
        User createdUser = userRepository.save(user);
        return ResponseEntity.ok(createdUser);
    }

    // Create a patient by doctor
    @PostMapping("/patient")
    public ResponseEntity<User> createPatientByDoctor(@RequestBody User user) {
        // Find the "PATIENT" role
        Role role = roleRepository.findByName("PATIENT")
                .orElseThrow(() -> new RuntimeException("Role PATIENT not found"));

        user.setRole(role);
        // Generate Access Code
        String accessCode = "FFANM" + String.format("%03d", (userRepository.countByRoleName("PATIENT") + 1));
        user.setAccessCode(accessCode);

        User createdUser = userRepository.save(user);
        return ResponseEntity.ok(createdUser);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        return ResponseEntity.ok(user);
    }

    // Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        Role role = roleRepository.findByName(userDetails.getRole().getName())
                .orElseThrow(() -> new RuntimeException("Role not found: " + userDetails.getRole().getName()));

        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setRole(role);
        user.setAccessCode(userDetails.getAccessCode());
        user.setFearLevel(userDetails.getFearLevel());
        user.setCoins(userDetails.getCoins());

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/validate-access-code/{accessCode}")
    public ResponseEntity<Boolean> validateAccessCode(@PathVariable String accessCode) {
        boolean exists = userRepository.existsByAccessCode(accessCode);
        if (exists) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/user-id-by-access-code/{accessCode}")
    public ResponseEntity<Long> getUserIdByAccessCode(@PathVariable String accessCode) {
        User user = userRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new RuntimeException("User not found with AccessCode: " + accessCode));
        return ResponseEntity.ok(user.getId());
    }

    @GetMapping("/username-by-user-id/{userId}")
    public ResponseEntity<String> getUsernameByUserId(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return ResponseEntity.ok(user.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/{id}/fear-percentage")
    public ResponseEntity<?> updateFearPercentage(
            @PathVariable Long id,
            @RequestBody Map<String, Double> request) {
        Double fearPercentage = request.get("fearPercentage");
        if (fearPercentage == null) {
            return ResponseEntity.badRequest().body("Missing fearPercentage in request body");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        user.setFearPercentage(fearPercentage);
        userRepository.save(user);

        return ResponseEntity.ok("Fear percentage updated successfully");
    }

}
