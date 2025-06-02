package com.nexastay.authentification.repository;

import com.nexastay.authentification.model.Role;
import com.nexastay.authentification.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);
}