// src/main/java/com/ecommerce/user/service/UserService.java
package com.ecommerce.user.service;

import com.ecommerce.user.dto.LoginRequest;
import com.ecommerce.user.dto.LoginResponse;
import com.ecommerce.user.dto.RegisterRequest;
import com.ecommerce.user.dto.RegisterResponse;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.entity.Role;
import com.ecommerce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public LoginResponse authenticate(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return new LoginResponse(null, null, null, null, null, "User not found", false);
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(request.getPassword())) {
            return new LoginResponse(null, null, null, null, null, "Invalid password", false);
        }

        // Allow both ADMIN and CUSTOMER to login, but check the context
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.CUSTOMER) {
            return new LoginResponse(null, null, null, null, null, "Access denied", false);
        }

        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                "Login successful",
                true
        );
    }

    public RegisterResponse registerCustomer(RegisterRequest request) {
        try {
            // Check if user already exists
            Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
            if (existingUser.isPresent()) {
                return new RegisterResponse(null, null, null, null, null,
                        "User with this email already exists", false);
            }

            // Validate input
            if (request.getFirstName() == null || request.getFirstName().trim().isEmpty()) {
                return new RegisterResponse(null, null, null, null, null,
                        "First name is required", false);
            }

            if (request.getLastName() == null || request.getLastName().trim().isEmpty()) {
                return new RegisterResponse(null, null, null, null, null,
                        "Last name is required", false);
            }

            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return new RegisterResponse(null, null, null, null, null,
                        "Email is required", false);
            }

            if (request.getPassword() == null || request.getPassword().length() < 6) {
                return new RegisterResponse(null, null, null, null, null,
                        "Password must be at least 6 characters", false);
            }

            // Create new user
            User newUser = new User();
            newUser.setFirstName(request.getFirstName().trim());
            newUser.setLastName(request.getLastName().trim());
            newUser.setEmail(request.getEmail().trim().toLowerCase());
            newUser.setPassword(request.getPassword()); // In production, hash this password

            // Set role - default to CUSTOMER for registration
            if (request.getRole() != null && request.getRole().equalsIgnoreCase("ADMIN")) {
                newUser.setRole(Role.ADMIN);
            } else {
                newUser.setRole(Role.CUSTOMER);
            }

            // Save user
            User savedUser = userRepository.save(newUser);

            return new RegisterResponse(
                    savedUser.getId(),
                    savedUser.getEmail(),
                    savedUser.getFirstName(),
                    savedUser.getLastName(),
                    savedUser.getRole(),
                    "Registration successful",
                    true
            );

        } catch (Exception e) {
            return new RegisterResponse(null, null, null, null, null,
                    "Registration failed: " + e.getMessage(), false);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getCustomers() {
        return userRepository.findByRole(Role.CUSTOMER);
    }

    public Long getCustomerCount() {
        return userRepository.countCustomers();
    }
}