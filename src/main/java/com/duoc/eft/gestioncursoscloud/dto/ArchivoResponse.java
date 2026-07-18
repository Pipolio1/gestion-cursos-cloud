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
public class ArchivoResponse {
    private Long id;
    private String nombre;
    private String s3Key;
    private String urlPublica;
    private String tipoContenido;
    private Long cursoId;
    private LocalDateTime fechaSubida;
}
