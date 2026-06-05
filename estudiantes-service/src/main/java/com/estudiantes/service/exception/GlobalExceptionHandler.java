package com.estudiantes.service.exception;

// ============================================================
//  GlobalExceptionHandler.java - MANEJADOR GLOBAL DE ERRORES
//  Captura todas las excepciones y devuelve respuestas JSON limpias
//  Ruta: src/main/java/mx/edu/universidad/estudiantesservice/exception/GlobalExceptionHandler.java
// ============================================================

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// @RestControllerAdvice intercepta todas las excepciones de los controladores
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ----------------------------------------------------------
    // Captura errores de validación (@NotBlank, @Email, @Pattern, etc.)
    // ----------------------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(MethodArgumentNotValidException ex) {

        // Recolectar todos los errores de validación en un mapa
        Map<String, String> erroresCampos = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            erroresCampos.put(campo, mensaje);
        });

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("estado", 400);
        respuesta.put("error", "Error de validación");
        respuesta.put("campos", erroresCampos);

        return ResponseEntity.badRequest().body(respuesta);
    }

    // ----------------------------------------------------------
    // Captura RecursoNoEncontradoException (404)
    // ----------------------------------------------------------
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarNoEncontrado(RecursoNoEncontradoException ex) {

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("estado", 404);
        respuesta.put("error", "Recurso no encontrado");
        respuesta.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    // ----------------------------------------------------------
    // Captura ValidacionException (400 - datos duplicados)
    // ----------------------------------------------------------
    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacionNegocio(ValidacionException ex) {

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("estado", 400);
        respuesta.put("error", "Error de validación de negocio");
        respuesta.put("mensaje", ex.getMessage());

        return ResponseEntity.badRequest().body(respuesta);
    }

    // ----------------------------------------------------------
    // Captura cualquier otra excepción no controlada (500)
    // ----------------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarGeneral(Exception ex) {

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("estado", 500);
        respuesta.put("error", "Error interno del servidor");
        respuesta.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }
}