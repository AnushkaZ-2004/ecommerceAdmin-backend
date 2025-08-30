package com.ecommerce.user.repository;

import com.ecommerce.user.entity.User;
import com.ecommerce.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'CUSTOMER'")
    Long countCustomers();
}