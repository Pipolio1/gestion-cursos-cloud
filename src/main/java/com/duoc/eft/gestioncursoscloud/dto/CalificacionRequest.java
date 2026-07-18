package com.duoc.eft.gestioncursoscloud.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalificacionRequest {

    @NotNull(message = "El ID del examen es obligatorio")
    private Long examenId;

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long estudianteId;

    @NotNull(message = "La nota es obligatoria")
    @Min(value = 1, message = "La nota minima es 1")
    @Max(value = 7, message = "La nota maxima es 7")
    private Double nota;
}
