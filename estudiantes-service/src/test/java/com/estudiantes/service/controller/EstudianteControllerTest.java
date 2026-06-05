package com.estudiantes.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estudiantes.service.entity.Estudiante;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EstudianteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    // ─────────────────────────────────────────────
    // TEST 1 - Registrar estudiante válido (POST)
    // ─────────────────────────────────────────────
    @Test
    void POST_estudianteValido_retorna201() throws Exception {
        Estudiante e = new Estudiante(null,
                "Carlos Ruiz", 21, "5512345678",
                "carlos@test.com", null,
                "A001", "Sistemas", 2, null);

        mockMvc.perform(post("/estudiantes")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(e)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreCompleto")
                        .value("Carlos Ruiz"));
    }

    // ─────────────────────────────────────────────
    // TEST 2 - Listar todos los estudiantes (GET)
    // ─────────────────────────────────────────────
    @Test
    void GET_listarTodos_retorna200() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/estudiantes")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void GET_buscarPorId_existente_retorna200() throws Exception {
        // Primero registramos un estudiante
        Estudiante e = new Estudiante(null,
                "Ana López", 22, "5598765432",
                "ana@test.com", null,
                "B002", "Sistemas", 3, null);

        String respuesta = mockMvc.perform(post("/estudiantes")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(e)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extraemos el ID del estudiante creado
        Long id = mapper.readTree(respuesta).get("id").asLong();

        // Buscamos por ese ID
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/estudiantes/" + id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCompleto").value("Ana López"));
    }

    // ─────────────────────────────────────────────
    // TEST 4 - Buscar estudiante por ID inexistente
    // ─────────────────────────────────────────────
    @Test
    void GET_buscarPorId_noExistente_retorna404() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/estudiantes/99999")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // ─────────────────────────────────────────────
    // TEST 5 - Buscar estudiante por nombre
    // ─────────────────────────────────────────────
    @Test
    void GET_buscarPorNombre_retorna200() throws Exception {
        // Registramos un estudiante con nombre conocido
        Estudiante e = new Estudiante(null,
                "Luis Pérez", 20, "5511223344",
                "luis@test.com", null,
                "C003", "Sistemas", 1, null);

        mockMvc.perform(post("/estudiantes")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(e)))
                .andExpect(status().isCreated());

        // Buscamos por nombre
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/estudiantes/buscar?nombre=Luis")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // ─────────────────────────────────────────────
    // TEST 6 - Actualizar estudiante existente (PUT)
    // ─────────────────────────────────────────────
    @Test
    void PUT_actualizarEstudiante_retorna200() throws Exception {
        // Creamos estudiante
        Estudiante e = new Estudiante(null,
                "María Torres", 23, "5544332211",
                "maria@test.com", null,
                "D004", "Sistemas", 4, null);

        String respuesta = mockMvc.perform(post("/estudiantes")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(e)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = mapper.readTree(respuesta).get("id").asLong();

        // Actualizamos con nuevo nombre
        Estudiante actualizado = new Estudiante(null,
                "María Torres Actualizada", 23, "5544332211",
                "maria@test.com", null,
                "D004", "Sistemas", 4, null);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/estudiantes/" + id)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCompleto")
                        .value("María Torres Actualizada"));
    }

    // ─────────────────────────────────────────────
    // TEST 7 - Eliminar estudiante existente (DELETE)
    // ─────────────────────────────────────────────
    @Test
    void DELETE_eliminarEstudiante_retorna204() throws Exception {
        // Creamos estudiante para eliminar
        Estudiante e = new Estudiante(null,
                "Pedro Gómez", 24, "5566778899",
                "pedro@test.com", null,
                "E005", "Sistemas", 5, null);

        String respuesta = mockMvc.perform(post("/estudiantes")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(e)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = mapper.readTree(respuesta).get("id").asLong();

        // Eliminamos
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .delete("/estudiantes/" + id))
                .andExpect(status().isNoContent());
    }
}