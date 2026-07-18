package com.duoc.eft.gestioncursoscloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private boolean exito;
    private String mensaje;
    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
                .exito(true)
                .mensaje("Operacion exitosa")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> ok(String mensaje, T data) {
        return ApiResponse.<T>builder()
                .exito(true)
                .mensaje(mensaje)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(String mensaje) {
        return ApiResponse.<T>builder()
                .exito(false)
                .mensaje(mensaje)
                .data(null)
                .build();
    }
}
