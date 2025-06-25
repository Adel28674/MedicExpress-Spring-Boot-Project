package com.example.MedicExpress.Repository;

import com.example.MedicExpress.Model.Role;
import com.example.MedicExpress.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findByRole(Role role);

}