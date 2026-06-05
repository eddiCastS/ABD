package com.estudiantes.service.service;


import com.estudiantes.service.exception.RecursoNoEncontradoException;
import com.estudiantes.service.exception.ValidacionException;
import com.estudiantes.service.entity.Estudiante;
import com.estudiantes.service.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// @Service le dice a Spring que esta clase contiene la lógica de negocio
@Service
public class EstudianteService {

    // @Autowired inyecta automáticamente el repositorio (Spring lo instancia por nosotros)
    @Autowired
    private EstudianteRepository estudianteRepository;

    // ----------------------------------------------------------
    // LISTAR TODOS LOS ESTUDIANTES
    // ----------------------------------------------------------
    public List<Estudiante> listarTodos() {
        return estudianteRepository.findAll();
    }

    // ----------------------------------------------------------
    // BUSCAR ESTUDIANTE POR ID
    // Si no existe, lanza una excepción personalizada
    // ----------------------------------------------------------
    public Estudiante buscarPorId(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró ningún estudiante con el ID: " + id));
    }

    // ----------------------------------------------------------
    // BUSCAR POR NOMBRE (búsqueda parcial)
    // ----------------------------------------------------------
    public List<Estudiante> buscarPorNombre(String nombre) {
        return estudianteRepository.buscarPorNombre(nombre);
    }

    // ----------------------------------------------------------
    // BUSCAR POR CARRERA
    // ----------------------------------------------------------
    public List<Estudiante> buscarPorCarrera(String carrera) {
        return estudianteRepository.findByCarrera(carrera);
    }

    // ----------------------------------------------------------
    // REGISTRAR NUEVO ESTUDIANTE
    // @Transactional = si algo falla, se revierte todo (seguridad)
    // ----------------------------------------------------------
    @Transactional
    public Estudiante registrar(Estudiante estudiante) {

        // Validar que el correo no esté ya registrado
        if (estudianteRepository.existsByCorreo(estudiante.getCorreo())) {
            throw new ValidacionException(
                    "Ya existe un estudiante registrado con el correo: " + estudiante.getCorreo());
        }

        // Validar que la matrícula no esté ya registrada
        if (estudianteRepository.existsByMatricula(estudiante.getMatricula())) {
            throw new ValidacionException(
                    "Ya existe un estudiante registrado con la matrícula: " + estudiante.getMatricula());
        }

        // Guardar en la BD y regresar el estudiante guardado (ya con ID asignado)
        return estudianteRepository.save(estudiante);
    }

    // ----------------------------------------------------------
    // ACTUALIZAR ESTUDIANTE EXISTENTE
    // ----------------------------------------------------------
    @Transactional
    public Estudiante actualizar(Long id, Estudiante datosActualizados) {

        // Verificar que el estudiante exista
        Estudiante estudianteExistente = buscarPorId(id);

        // Validar que el correo no lo use OTRO estudiante diferente
        if (estudianteRepository.existsByCorreoAndIdNot(datosActualizados.getCorreo(), id)) {
            throw new ValidacionException(
                    "El correo " + datosActualizados.getCorreo() + " ya está en uso por otro estudiante");
        }

        // Validar que la matrícula no la use OTRO estudiante diferente
        if (estudianteRepository.existsByMatriculaAndIdNot(datosActualizados.getMatricula(), id)) {
            throw new ValidacionException(
                    "La matrícula " + datosActualizados.getMatricula() + " ya está en uso por otro estudiante");
        }

        // Actualizar los campos con los nuevos datos
        estudianteExistente.setNombreCompleto(datosActualizados.getNombreCompleto());
        estudianteExistente.setEdad(datosActualizados.getEdad());
        estudianteExistente.setTelefono(datosActualizados.getTelefono());
        estudianteExistente.setCorreo(datosActualizados.getCorreo());
        estudianteExistente.setDireccion(datosActualizados.getDireccion());
        estudianteExistente.setMatricula(datosActualizados.getMatricula());
        estudianteExistente.setCarrera(datosActualizados.getCarrera());
        estudianteExistente.setSemestre(datosActualizados.getSemestre());
        // Nota: no actualizamos fechaRegistro para mantener la fecha original

        return estudianteRepository.save(estudianteExistente);
    }

    // ----------------------------------------------------------
    // ELIMINAR ESTUDIANTE
    // ----------------------------------------------------------
    @Transactional
    public void eliminar(Long id) {
        // Verificar que el estudiante exista antes de eliminar
        buscarPorId(id); // Si no existe, lanza excepción
        estudianteRepository.deleteById(id);
    }
}
