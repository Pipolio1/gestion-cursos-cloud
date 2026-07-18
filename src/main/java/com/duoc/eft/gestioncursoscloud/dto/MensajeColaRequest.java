package com.duoc.eft.gestioncursoscloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeColaRequest {
    private String tipo;
    private String contenido;
    private String origen;
}
