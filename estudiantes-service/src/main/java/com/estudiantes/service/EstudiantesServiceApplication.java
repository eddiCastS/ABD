package com.estudiantes.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EstudiantesServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(EstudiantesServiceApplication.class, args);
		System.out.println("==============================================");
		System.out.println("  Microservicio de Estudiantes iniciado!     ");
		System.out.println("  URL: http://localhost:8080/api/estudiantes  ");
		System.out.println("==============================================");
	}
}
