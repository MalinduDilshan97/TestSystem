package com.spring.starter.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.StaffUser;

public interface StaffUserRepository extends JpaRepository<StaffUser, Integer> {

    @Query("SELECT u FROM StaffUser u WHERE u.username = ?1 AND password = ?2")
    Optional<StaffUser> validateUser(String username, String password);

    @Query("SELECT u FROM StaffUser u WHERE u.username=?1 AND u.active = 1")
    Optional<StaffUser> getUserByUsername(String username);

    @Query("SELECT u FROM StaffUser u WHERE u.username=?1")
    Optional<StaffUser> getUserByUsernameForSignUp(String username);

    @Query("SELECT u FROM StaffUser u WHERE u.email=?1")
    Optional<StaffUser> getUserByEmail(String email);

    @Query("SELECT u FROM StaffUser u WHERE u.active = 1")
    List<StaffUser> getAllActiveUsers();

    @Query("SELECT u FROM StaffUser u WHERE u.active = 0")
    List<StaffUser> getAllDeactivatedUsers();
}
