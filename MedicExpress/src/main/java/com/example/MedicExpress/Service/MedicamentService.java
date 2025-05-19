package com.example.MedicExpress.Service;

import com.example.MedicExpress.Repository.MedicamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicamentService {

    @Autowired
    private MedicamentRepository medicamentRepository;


}

