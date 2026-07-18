package com.duoc.eft.gestioncursoscloud.service;

import com.duoc.eft.gestioncursoscloud.dto.InscripcionRequest;
import com.duoc.eft.gestioncursoscloud.dto.InscripcionResponse;

import java.util.List;

public interface InscripcionService {
    List<InscripcionResponse> listarTodas();
    List<InscripcionResponse> listarPorEstudiante(Long estudianteId);
    List<InscripcionResponse> listarPorCurso(Long cursoId);
    InscripcionResponse inscribir(InscripcionRequest request);
    InscripcionResponse completar(Long id);
    InscripcionResponse cancelar(Long id);
}
