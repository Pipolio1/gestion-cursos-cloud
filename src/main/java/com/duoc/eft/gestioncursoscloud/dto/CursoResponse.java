package com.duoc.eft.gestioncursoscloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private String instructorEmail;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDateTime fechaRegistro;
}
