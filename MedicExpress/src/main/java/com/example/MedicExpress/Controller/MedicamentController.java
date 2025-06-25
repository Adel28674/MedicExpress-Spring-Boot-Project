package com.example.MedicExpress.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MedicExpress.Service.MedicamentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/medicament")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MedicamentController {

    @Autowired
    private MedicamentService medicamentService;

}