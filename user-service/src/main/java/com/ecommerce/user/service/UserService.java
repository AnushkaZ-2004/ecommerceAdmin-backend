package com.ecommerce.user.service;

import com.ecommerce.user.dto.LoginRequest;
import com.ecommerce.user.dto.LoginResponse;
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

        if (user.getRole() != Role.ADMIN) {
            return new LoginResponse(null, null, null, null, null, "Access denied. Admin role required.", false);
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