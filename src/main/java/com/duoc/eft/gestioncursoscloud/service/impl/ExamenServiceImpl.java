package com.duoc.eft.gestioncursoscloud.service.impl;

import com.duoc.eft.gestioncursoscloud.dto.ExamenRequest;
import com.duoc.eft.gestioncursoscloud.dto.ExamenResponse;
import com.duoc.eft.gestioncursoscloud.model.Curso;
import com.duoc.eft.gestioncursoscloud.model.Examen;
import com.duoc.eft.gestioncursoscloud.repository.CursoRepository;
import com.duoc.eft.gestioncursoscloud.repository.ExamenRepository;
import com.duoc.eft.gestioncursoscloud.service.ExamenService;
import com.duoc.eft.gestioncursoscloud.util.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamenServiceImpl implements ExamenService {

    private final ExamenRepository examenRepository;
    private final CursoRepository cursoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ExamenResponse> listarTodos() {
        return examenRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamenResponse> listarPorCurso(Long cursoId) {
        return examenRepository.findByCursoId(cursoId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ExamenResponse buscarPorId(Long id) {
        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Examen no encontrado con id: " + id));
        return toResponse(examen);
    }

    @Override
    @Transactional
    public ExamenResponse crear(ExamenRequest request) {
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con id: " + request.getCursoId()));
        Examen examen = Examen.builder()
                .titulo(request.getTitulo())
                .curso(curso)
                .fechaExamen(request.getFechaExamen())
                .build();
        return toResponse(examenRepository.save(examen));
    }

    @Override
    @Transactional
    public ExamenResponse actualizar(Long id, ExamenRequest request) {
        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Examen no encontrado con id: " + id));
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con id: " + request.getCursoId()));
        examen.setTitulo(request.getTitulo());
        examen.setCurso(curso);
        examen.setFechaExamen(request.getFechaExamen());
        return toResponse(examenRepository.save(examen));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Examen no encontrado con id: " + id));
        examenRepository.delete(examen);
    }

    private ExamenResponse toResponse(Examen examen) {
        return ExamenResponse.builder()
                .id(examen.getId())
                .titulo(examen.getTitulo())
                .cursoId(examen.getCurso().getId())
                .cursoTitulo(examen.getCurso().getTitulo())
                .fechaExamen(examen.getFechaExamen())
                .fechaRegistro(examen.getFechaRegistro())
                .build();
    }
}
