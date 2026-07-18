package com.duoc.eft.gestioncursoscloud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoRequest {

    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    private String descripcion;

    @NotBlank(message = "El email del instructor es obligatorio")
    @Email(message = "El email del instructor no es valido")
    private String instructorEmail;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;
}
