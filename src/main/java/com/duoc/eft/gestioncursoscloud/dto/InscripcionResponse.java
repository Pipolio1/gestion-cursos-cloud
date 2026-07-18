package com.duoc.eft.gestioncursoscloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscripcionResponse {
    private Long id;
    private Long estudianteId;
    private String estudianteNombre;
    private Long cursoId;
    private String cursoTitulo;
    private LocalDateTime fechaInscripcion;
    private String estado;
}
