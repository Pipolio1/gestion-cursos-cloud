package com.duoc.eft.gestioncursoscloud.service.impl;

import com.duoc.eft.gestioncursoscloud.dto.CalificacionRequest;
import com.duoc.eft.gestioncursoscloud.dto.CalificacionResponse;
import com.duoc.eft.gestioncursoscloud.model.Calificacion;
import com.duoc.eft.gestioncursoscloud.model.Estudiante;
import com.duoc.eft.gestioncursoscloud.model.Examen;
import com.duoc.eft.gestioncursoscloud.repository.CalificacionRepository;
import com.duoc.eft.gestioncursoscloud.repository.EstudianteRepository;
import com.duoc.eft.gestioncursoscloud.repository.ExamenRepository;
import com.duoc.eft.gestioncursoscloud.service.CalificacionService;
import com.duoc.eft.gestioncursoscloud.util.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final ExamenRepository examenRepository;
    private final EstudianteRepository estudianteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CalificacionResponse> listarTodas() {
        return calificacionRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalificacionResponse> listarPorExamen(Long examenId) {
        return calificacionRepository.findByExamenId(examenId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalificacionResponse> listarPorEstudiante(Long estudianteId) {
        return calificacionRepository.findByEstudianteId(estudianteId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CalificacionResponse registrar(CalificacionRequest request) {
        Examen examen = examenRepository.findById(request.getExamenId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Examen no encontrado con id: " + request.getExamenId()));
        Estudiante estudiante = estudianteRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con id: " + request.getEstudianteId()));

        Calificacion calificacion = Calificacion.builder()
                .examen(examen)
                .estudiante(estudiante)
                .nota(request.getNota())
                .build();
        return toResponse(calificacionRepository.save(calificacion));
    }

    @Override
    @Transactional
    public CalificacionResponse actualizar(Long id, CalificacionRequest request) {
        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Calificacion no encontrada con id: " + id));
        Examen examen = examenRepository.findById(request.getExamenId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Examen no encontrado con id: " + request.getExamenId()));
        Estudiante estudiante = estudianteRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con id: " + request.getEstudianteId()));
        calificacion.setExamen(examen);
        calificacion.setEstudiante(estudiante);
        calificacion.setNota(request.getNota());
        return toResponse(calificacionRepository.save(calificacion));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Calificacion no encontrada con id: " + id));
        calificacionRepository.delete(calificacion);
    }

    private CalificacionResponse toResponse(Calificacion calificacion) {
        return CalificacionResponse.builder()
                .id(calificacion.getId())
                .examenId(calificacion.getExamen().getId())
                .tituloExamen(calificacion.getExamen().getTitulo())
                .estudianteId(calificacion.getEstudiante().getId())
                .nombreEstudiante(calificacion.getEstudiante().getNombre())
                .nota(calificacion.getNota())
                .fechaRegistro(calificacion.getFechaRegistro())
                .build();
    }
}
