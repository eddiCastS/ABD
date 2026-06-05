package com.estudiantes.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 1, message = "La edad mínima es 1")
    @Max(value = 120, message = "La edad máxima es 120")
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener exactamente 10 dígitos numéricos")
    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @NotBlank(message = "La matrícula es obligatoria")
    @Column(name = "matricula", nullable = false, unique = true, length = 20)
    private String matricula;

    @NotBlank(message = "La carrera es obligatoria")
    @Column(name = "carrera", nullable = false, length = 100)
    private String carrera;

    @NotNull(message = "El semestre es obligatorio")
    @Min(value = 1, message = "El semestre mínimo es 1")
    @Max(value = 12, message = "El semestre máximo es 12")
    @Column(name = "semestre", nullable = false)
    private Integer semestre;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void asignarFechaRegistro() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDateTime.now();
        }
    }
}