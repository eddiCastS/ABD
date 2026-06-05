package com.estudiantes.service.controller;

import com.estudiantes.service.entity.Estudiante;
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
class CourseControllerTest {

    @Mock
    private EstudianteService estudianteService;

    @InjectMocks
    private EstudianteController controller;

    // TEST 1 - Buscar estudiantes por carrera
    @Test
    void testBuscarPorCarrera() {
        List<Estudiante> lista = Arrays.asList(
                new Estudiante(1L, "Carlos Ruiz", 21, "5512345678",
                        "carlos@test.com", null, "A001", "Sistemas", 2, null),
                new Estudiante(2L, "Ana López", 22, "5598765432",
                        "ana@test.com", null, "B002", "Sistemas", 3, null)
        );
        when(estudianteService.buscarPorCarrera("Sistemas")).thenReturn(lista);

        List<Estudiante> result = controller.buscarPorCarrera("Sistemas").getBody();

        assertEquals(2, result.size());
        verify(estudianteService, times(1)).buscarPorCarrera("Sistemas");
    }

    // TEST 2 - Buscar por carrera sin resultados
    @Test
    void testBuscarPorCarreraVacia() {
        when(estudianteService.buscarPorCarrera("Medicina")).thenReturn(List.of());

        List<Estudiante> result = controller.buscarPorCarrera("Medicina").getBody();

        assertEquals(0, result.size());
        verify(estudianteService, times(1)).buscarPorCarrera("Medicina");
    }
}