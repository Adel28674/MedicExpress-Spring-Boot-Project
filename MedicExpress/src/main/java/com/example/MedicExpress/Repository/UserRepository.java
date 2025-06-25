package com.example.MedicExpress.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.MedicExpress.Model.Role;
import com.example.MedicExpress.Model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    // Find user by email and role
    Optional<UserEntity> findByEmailAndRole(String email, Role role);

    // Search patients by name or firstName (case-insensitive)
    @Query("SELECT u FROM UserEntity u WHERE u.role = :role AND " +
           "(LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(CONCAT(u.firstName, ' ', u.name)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(CONCAT(u.name, ' ', u.firstName)) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<UserEntity> findPatientsByNameContaining(@Param("searchTerm") String searchTerm, @Param("role") Role role);

    // Find all users with PATIENT role
    List<UserEntity> findByRole(Role role);

}