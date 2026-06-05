package com.estudiantes.service.exception;

// ============================================================
//  ValidacionException.java
//  Se lanza cuando un dato ya está duplicado (correo, matrícula)
// ============================================================

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidacionException extends RuntimeException {

    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}
