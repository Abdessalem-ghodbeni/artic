package com.artic.artic_doctors.Repository;

import com.artic.artic_doctors.Entities.Role;
import com.artic.artic_doctors.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    User findByRole(Role role);
}
