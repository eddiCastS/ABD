package com.estudiantes.service.controller;


// ============================================================
//  EstudianteController.java - CONTROLADOR REST
//  Define todos los endpoints (URLs) que expone el microservicio
//  Ruta: src/main/java/mx/edu/universidad/estudiantesservice/controller/EstudianteController.java
// ============================================================

import jakarta.validation.Valid;
import com.estudiantes.service.entity.Estudiante;
import com.estudiantes.service.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController = esta clase maneja peticiones HTTP y devuelve JSON automáticamente
@RestController

// @RequestMapping = ruta base para todos los endpoints de este controlador
// Todos los endpoints empezarán con: /api/estudiantes
@RequestMapping("/estudiantes")

// @CrossOrigin = permite peticiones desde la página web (navegador)
// origins = "*" significa que acepta peticiones de cualquier origen (para desarrollo)
@CrossOrigin(origins = "*")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    // ==========================================================
    // ENDPOINT 1: LISTAR TODOS LOS ESTUDIANTES
    // Método: GET
    // URL: http://localhost:8080/api/estudiantes
    // ==========================================================
    @GetMapping
    public ResponseEntity<List<Estudiante>> listarTodos() {
        List<Estudiante> estudiantes = estudianteService.listarTodos();
        return ResponseEntity.ok(estudiantes); // 200 OK + lista de estudiantes
    }

    // ==========================================================
    // ENDPOINT 2: BUSCAR ESTUDIANTE POR ID
    // Método: GET
    // URL: http://localhost:8080/api/estudiantes/1
    // {id} es una variable de ruta (path variable)
    // ==========================================================
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> buscarPorId(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.buscarPorId(id);
        return ResponseEntity.ok(estudiante); // 200 OK + datos del estudiante
    }

    // ==========================================================
    // ENDPOINT 3: BUSCAR ESTUDIANTES POR NOMBRE
    // Método: GET
    // URL: http://localhost:8080/api/estudiantes/buscar?nombre=Ana
    // ?nombre=Ana es un parámetro de consulta (query parameter)
    // ==========================================================
    @GetMapping("/buscar")
    public ResponseEntity<List<Estudiante>> buscarPorNombre(@RequestParam String nombre) {
        List<Estudiante> estudiantes = estudianteService.buscarPorNombre(nombre);
        return ResponseEntity.ok(estudiantes);
    }

    // ==========================================================
    // ENDPOINT 4: BUSCAR ESTUDIANTES POR CARRERA
    // Método: GET
    // URL: http://localhost:8080/api/estudiantes/carrera/Ingeniería en Sistemas
    // ==========================================================
    @GetMapping("/carrera/{carrera}")
    public ResponseEntity<List<Estudiante>> buscarPorCarrera(@PathVariable String carrera) {
        List<Estudiante> estudiantes = estudianteService.buscarPorCarrera(carrera);
        return ResponseEntity.ok(estudiantes);
    }

    // ==========================================================
    // ENDPOINT 5: REGISTRAR NUEVO ESTUDIANTE
    // Método: POST
    // URL: http://localhost:8080/api/estudiantes
    // Body: JSON con los datos del estudiante
    // @Valid activa las validaciones definidas en la entidad
    // @RequestBody convierte el JSON del body a un objeto Estudiante
    // ==========================================================
    @PostMapping
    public ResponseEntity<Estudiante> registrar(@Valid @RequestBody Estudiante estudiante) {
        Estudiante nuevo = estudianteService.registrar(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); // 201 CREATED + estudiante creado
    }

    // ==========================================================
    // ENDPOINT 6: ACTUALIZAR ESTUDIANTE EXISTENTE
    // Método: PUT
    // URL: http://localhost:8080/api/estudiantes/1
    // Body: JSON con los nuevos datos
    // ==========================================================
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Estudiante estudiante) {
        Estudiante actualizado = estudianteService.actualizar(id, estudiante);
        return ResponseEntity.ok(actualizado); // 200 OK + estudiante actualizado
    }

    // ==========================================================
    // ENDPOINT 7: ELIMINAR ESTUDIANTE
    // Método: DELETE
    // URL: http://localhost:8080/api/estudiantes/1
    // ==========================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content (eliminado con éxito)
    }
}
