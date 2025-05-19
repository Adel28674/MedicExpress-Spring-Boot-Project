package com.example.MedicExpress.Repository;

import com.example.MedicExpress.Model.DeliveryDriverEntity;
import com.example.MedicExpress.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<UserEntity, Long> {}
