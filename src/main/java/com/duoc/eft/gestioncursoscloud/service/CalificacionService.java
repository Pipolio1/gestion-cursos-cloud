package com.duoc.eft.gestioncursoscloud.service;

import com.duoc.eft.gestioncursoscloud.dto.CalificacionRequest;
import com.duoc.eft.gestioncursoscloud.dto.CalificacionResponse;

import java.util.List;

public interface CalificacionService {
    List<CalificacionResponse> listarTodas();
    List<CalificacionResponse> listarPorExamen(Long examenId);
    List<CalificacionResponse> listarPorEstudiante(Long estudianteId);
    CalificacionResponse registrar(CalificacionRequest request);
    CalificacionResponse actualizar(Long id, CalificacionRequest request);
    void eliminar(Long id);
}
