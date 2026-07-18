package com.duoc.eft.gestioncursoscloud.service;

import com.duoc.eft.gestioncursoscloud.dto.ExamenRequest;
import com.duoc.eft.gestioncursoscloud.dto.ExamenResponse;

import java.util.List;

public interface ExamenService {
    List<ExamenResponse> listarTodos();
    List<ExamenResponse> listarPorCurso(Long cursoId);
    ExamenResponse buscarPorId(Long id);
    ExamenResponse crear(ExamenRequest request);
    ExamenResponse actualizar(Long id, ExamenRequest request);
    void eliminar(Long id);
}
