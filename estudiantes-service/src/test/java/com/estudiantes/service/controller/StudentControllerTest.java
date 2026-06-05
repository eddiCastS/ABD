package com.estudiantes.service.controller;

import com.estudiantes.service.entity.Estudiante;
import com.estudiantes.service.repository.EstudianteRepository;
import com.estudiantes.service.service.EstudianteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private EstudianteService estudianteService;

    @InjectMocks
    private EstudianteController controller;

    // TEST 1 - Listar todos los estudiantes
    @Test
    void testGetStudents() {
        List<Estudiante> students = Arrays.asList(
                new Estudiante(1L, "Carlos Ruiz", 21, "5512345678",
                        "carlos@test.com", null, "A001", "Sistemas", 2, null),
                new Estudiante(2L, "Ana López", 22, "5598765432",
                        "ana@test.com", null, "B002", "Sistemas", 3, null)
        );
        when(estudianteService.listarTodos()).thenReturn(students);

        List<Estudiante> result = controller.listarTodos().getBody();

        assertEquals(2, result.size());
        verify(estudianteService, times(1)).listarTodos();
    }

    // TEST 2 - Lista vacía cuando no hay estudiantes
    @Test
    void testGetStudentsEmptyList() {
        when(estudianteService.listarTodos()).thenReturn(List.of());

        List<Estudiante> result = controller.listarTodos().getBody();

        assertEquals(0, result.size());
        verify(estudianteService, times(1)).listarTodos();
    }
}