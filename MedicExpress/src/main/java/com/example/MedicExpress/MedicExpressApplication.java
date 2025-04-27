package com.example.MedicExpress;

import com.example.MedicExpress.Model.PatientEntity;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MedicExpressApplication {

	public static void main(String[] args) {

		SpringApplication.run(MedicExpressApplication.class, args);

	}

}
