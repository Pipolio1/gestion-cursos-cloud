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
public class ExamenResponse {
    private Long id;
    private String titulo;
    private Long cursoId;
    private String cursoTitulo;
    private LocalDateTime fechaExamen;
    private LocalDateTime fechaRegistro;
}
