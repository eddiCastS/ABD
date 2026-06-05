package com.estudiantes.service.exception;

// ============================================================
//  RecursoNoEncontradoException.java
//  Se lanza cuando no se encuentra un estudiante en la BD
//  Ruta: src/main/java/mx/edu/universidad/estudiantesservice/exception/
// ============================================================

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// @ResponseStatus le dice a Spring que responda con código 404 cuando se lance esta excepción
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
