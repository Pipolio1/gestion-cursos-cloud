package com.duoc.eft.gestioncursoscloud.service;

import com.duoc.eft.gestioncursoscloud.dto.CursoRequest;
import com.duoc.eft.gestioncursoscloud.dto.CursoResponse;

import java.util.List;

public interface CursoService {
    List<CursoResponse> listarTodos();
    CursoResponse buscarPorId(Long id);
    List<CursoResponse> listarPorInstructor(String instructorEmail);
    CursoResponse crear(CursoRequest request);
    CursoResponse actualizar(Long id, CursoRequest request);
    void eliminar(Long id);
}
