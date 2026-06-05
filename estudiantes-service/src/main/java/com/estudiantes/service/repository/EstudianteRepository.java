package com.estudiantes.service.repository;

// ============================================================
//  EstudianteRepository.java - REPOSITORIO (acceso a la BD)
//  Ruta: src/main/java/mx/edu/universidad/estudiantesservice/repository/EstudianteRepository.java
// ============================================================

import com.estudiantes.service.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// @Repository le indica a Spring que esta interfaz es un repositorio de datos
@Repository
// JpaRepository<Estudiante, Long>:
//   - Estudiante = la entidad que maneja
//   - Long = el tipo del ID
// Al extender JpaRepository, ya tenemos gratis:
//   save(), findById(), findAll(), deleteById(), count(), etc.
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    // ----------------------------------------------------------
    // BÚSQUEDA POR CORREO
    // Spring Data JPA genera el SQL automáticamente por el nombre del método
    // Equivale a: SELECT * FROM estudiantes WHERE correo = ?
    // ----------------------------------------------------------
    Optional<Estudiante> findByCorreo(String correo);

    // ----------------------------------------------------------
    // BÚSQUEDA POR MATRÍCULA
    // Equivale a: SELECT * FROM estudiantes WHERE matricula = ?
    // ----------------------------------------------------------
    Optional<Estudiante> findByMatricula(String matricula);

    // ----------------------------------------------------------
    // VERIFICAR SI EXISTE UN CORREO (para validar duplicados)
    // ----------------------------------------------------------
    boolean existsByCorreo(String correo);

    // ----------------------------------------------------------
    // VERIFICAR SI EXISTE UNA MATRÍCULA (para validar duplicados)
    // ----------------------------------------------------------
    boolean existsByMatricula(String matricula);

    // ----------------------------------------------------------
    // BUSCAR POR CARRERA
    // Equivale a: SELECT * FROM estudiantes WHERE carrera = ?
    // ----------------------------------------------------------
    List<Estudiante> findByCarrera(String carrera);

    // ----------------------------------------------------------
    // BUSCAR POR SEMESTRE
    // ----------------------------------------------------------
    List<Estudiante> findBySemestre(Integer semestre);

    // ----------------------------------------------------------
    // BÚSQUEDA POR NOMBRE (contiene el texto, sin importar mayúsculas)
    // @Query permite escribir consultas JPQL personalizadas
    // :nombre es un parámetro que se pasa con @Param
    // ----------------------------------------------------------
    @Query("SELECT e FROM Estudiante e WHERE LOWER(e.nombreCompleto) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Estudiante> buscarPorNombre(@Param("nombre") String nombre);

    // ----------------------------------------------------------
    // VERIFICAR SI EXISTE CORREO PARA OTRO ESTUDIANTE (al actualizar)
    // Útil para que al editar un estudiante, no se duplique el correo con OTRO registro
    // ----------------------------------------------------------
    boolean existsByCorreoAndIdNot(String correo, Long id);

    // ----------------------------------------------------------
    // VERIFICAR SI EXISTE MATRÍCULA PARA OTRO ESTUDIANTE (al actualizar)
    // ----------------------------------------------------------
    boolean existsByMatriculaAndIdNot(String matricula, Long id);
}
