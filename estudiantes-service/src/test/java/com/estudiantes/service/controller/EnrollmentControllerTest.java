package com.estudiantes.service.controller;

import com.estudiantes.service.entity.Estudiante;
import com.estudiantes.service.service.EstudianteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentControllerTest {

    @Mock
    private EstudianteService estudianteService;

    @InjectMocks
    private EstudianteController controller;

    // TEST 1 - Registrar estudiante nuevo
    @Test
    void testRegistrarEstudiante() {
        Estudiante e = new Estudiante(null, "Pedro Gómez", 24,
                "5566778899", "pedro@test.com", null,
                "E005", "Sistemas", 5, null);

        when(estudianteService.registrar(e)).thenReturn(e);

        Estudiante result = controller.registrar(e).getBody();

        assertEquals("Pedro Gómez", result.getNombreCompleto());
        assertEquals("Sistemas", result.getCarrera());
        verify(estudianteService, times(1)).registrar(e);
    }

    // TEST 2 - Eliminar estudiante existente
    @Test
    void testEliminarEstudiante() {
        doNothing().when(estudianteService).eliminar(1L);

        controller.eliminar(1L);

        verify(estudianteService, times(1)).eliminar(1L);
    }
}