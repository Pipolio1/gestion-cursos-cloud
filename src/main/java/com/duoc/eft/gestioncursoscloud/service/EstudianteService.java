package com.duoc.eft.gestioncursoscloud.service;

import com.duoc.eft.gestioncursoscloud.dto.EstudianteRequest;
import com.duoc.eft.gestioncursoscloud.dto.EstudianteResponse;

import java.util.List;

public interface EstudianteService {
    List<EstudianteResponse> listarTodos();
    EstudianteResponse buscarPorId(Long id);
    EstudianteResponse crear(EstudianteRequest request);
    EstudianteResponse actualizar(Long id, EstudianteRequest request);
    void eliminar(Long id);
}
