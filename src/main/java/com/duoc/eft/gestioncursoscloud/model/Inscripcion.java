package com.duoc.eft.gestioncursoscloud.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inscripcion_seq")
    @SequenceGenerator(name = "inscripcion_seq", sequenceName = "inscripcion_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoInscripcion estado;

    @PrePersist
    public void prePersist() {
        this.fechaInscripcion = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoInscripcion.ACTIVA;
        }
    }

    public enum EstadoInscripcion {
        ACTIVA, COMPLETADA, CANCELADA
    }
}
