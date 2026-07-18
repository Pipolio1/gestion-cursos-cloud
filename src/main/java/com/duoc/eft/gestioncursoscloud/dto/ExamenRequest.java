package com.duoc.eft.gestioncursoscloud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamenRequest {

    @NotBlank(message = "El titulo del examen es obligatorio")
    private String titulo;

    @NotNull(message = "El ID del curso es obligatorio")
    private Long cursoId;

    @NotNull(message = "La fecha del examen es obligatoria")
    private LocalDateTime fechaExamen;
}
