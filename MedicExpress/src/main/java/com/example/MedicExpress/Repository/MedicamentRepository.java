package com.example.MedicExpress.Repository;

import com.example.MedicExpress.Model.MedicamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentRepository extends JpaRepository<MedicamentEntity, Long>{}
