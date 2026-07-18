package com.duoc.eft.gestioncursoscloud.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "archivos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "archivo_seq")
    @SequenceGenerator(name = "archivo_seq", sequenceName = "archivo_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "s3_key", nullable = false)
    private String s3Key;

    @Column(name = "url_publica")
    private String urlPublica;

    @Column(name = "tipo_contenido")
    private String tipoContenido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(name = "fecha_subida")
    private LocalDateTime fechaSubida;

    @PrePersist
    public void prePersist() {
        this.fechaSubida = LocalDateTime.now();
    }
}
