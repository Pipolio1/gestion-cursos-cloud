package com.duoc.eft.gestioncursoscloud.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estudiante_seq")
    @SequenceGenerator(name = "estudiante_seq", sequenceName = "estudiante_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }
}
