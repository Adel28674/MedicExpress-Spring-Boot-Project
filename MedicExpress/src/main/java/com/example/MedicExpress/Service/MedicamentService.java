package com.example.MedicExpress.Service;

import com.example.MedicExpress.Exception.UserDoesNotExistException;
import com.example.MedicExpress.Model.MedicamentEntity;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.MedicamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class MedicamentService {

    @Autowired
    private MedicamentRepository medicamentRepository;

}

