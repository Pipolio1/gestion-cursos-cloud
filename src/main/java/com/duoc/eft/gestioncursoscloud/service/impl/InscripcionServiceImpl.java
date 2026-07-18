package com.duoc.eft.gestioncursoscloud.service.impl;

import com.duoc.eft.gestioncursoscloud.dto.InscripcionRequest;
import com.duoc.eft.gestioncursoscloud.dto.InscripcionResponse;
import com.duoc.eft.gestioncursoscloud.model.Curso;
import com.duoc.eft.gestioncursoscloud.model.Estudiante;
import com.duoc.eft.gestioncursoscloud.model.Inscripcion;
import com.duoc.eft.gestioncursoscloud.repository.CursoRepository;
import com.duoc.eft.gestioncursoscloud.repository.EstudianteRepository;
import com.duoc.eft.gestioncursoscloud.repository.InscripcionRepository;
import com.duoc.eft.gestioncursoscloud.service.InscripcionService;
import com.duoc.eft.gestioncursoscloud.util.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listarTodas() {
        return inscripcionRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listarPorEstudiante(Long estudianteId) {
        return inscripcionRepository.findByEstudianteId(estudianteId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listarPorCurso(Long cursoId) {
        return inscripcionRepository.findByCursoId(cursoId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InscripcionResponse inscribir(InscripcionRequest request) {
        Estudiante estudiante = estudianteRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con id: " + request.getEstudianteId()));
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con id: " + request.getCursoId()));

        Inscripcion inscripcion = Inscripcion.builder()
                .estudiante(estudiante)
                .curso(curso)
                .build();
        return toResponse(inscripcionRepository.save(inscripcion));
    }

    @Override
    @Transactional
    public InscripcionResponse completar(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Inscripcion no encontrada con id: " + id));
        inscripcion.setEstado(Inscripcion.EstadoInscripcion.COMPLETADA);
        return toResponse(inscripcionRepository.save(inscripcion));
    }

    @Override
    @Transactional
    public InscripcionResponse cancelar(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Inscripcion no encontrada con id: " + id));
        inscripcion.setEstado(Inscripcion.EstadoInscripcion.CANCELADA);
        return toResponse(inscripcionRepository.save(inscripcion));
    }

    private InscripcionResponse toResponse(Inscripcion inscripcion) {
        return InscripcionResponse.builder()
                .id(inscripcion.getId())
                .estudianteId(inscripcion.getEstudiante().getId())
                .estudianteNombre(inscripcion.getEstudiante().getNombre())
                .cursoId(inscripcion.getCurso().getId())
                .cursoTitulo(inscripcion.getCurso().getTitulo())
                .fechaInscripcion(inscripcion.getFechaInscripcion())
                .estado(inscripcion.getEstado().name())
                .build();
    }
}
