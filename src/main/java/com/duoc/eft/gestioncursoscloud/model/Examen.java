package com.duoc.eft.gestioncursoscloud.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "examenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examen_seq")
    @SequenceGenerator(name = "examen_seq", sequenceName = "examen_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Column(name = "fecha_examen")
    private LocalDateTime fechaExamen;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }
}
