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
public class CalificacionResponse {
    private Long id;
    private Long examenId;
    private String tituloExamen;
    private Long estudianteId;
    private String nombreEstudiante;
    private Double nota;
    private LocalDateTime fechaRegistro;
}
