package com.duoc.eft.gestioncursoscloud.service.impl;

import com.duoc.eft.gestioncursoscloud.dto.CursoRequest;
import com.duoc.eft.gestioncursoscloud.dto.CursoResponse;
import com.duoc.eft.gestioncursoscloud.model.Curso;
import com.duoc.eft.gestioncursoscloud.repository.CursoRepository;
import com.duoc.eft.gestioncursoscloud.service.CursoService;
import com.duoc.eft.gestioncursoscloud.util.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listarTodos() {
        return cursoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CursoResponse buscarPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con id: " + id));
        return toResponse(curso);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listarPorInstructor(String instructorEmail) {
        return cursoRepository.findByInstructorEmail(instructorEmail).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CursoResponse crear(CursoRequest request) {
        Curso curso = Curso.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .instructorEmail(request.getInstructorEmail())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .build();
        return toResponse(cursoRepository.save(curso));
    }

    @Override
    @Transactional
    public CursoResponse actualizar(Long id, CursoRequest request) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con id: " + id));
        curso.setTitulo(request.getTitulo());
        curso.setDescripcion(request.getDescripcion());
        curso.setInstructorEmail(request.getInstructorEmail());
        curso.setFechaInicio(request.getFechaInicio());
        curso.setFechaFin(request.getFechaFin());
        return toResponse(cursoRepository.save(curso));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con id: " + id));
        cursoRepository.delete(curso);
    }

    private CursoResponse toResponse(Curso curso) {
        return CursoResponse.builder()
                .id(curso.getId())
                .titulo(curso.getTitulo())
                .descripcion(curso.getDescripcion())
                .instructorEmail(curso.getInstructorEmail())
                .fechaInicio(curso.getFechaInicio())
                .fechaFin(curso.getFechaFin())
                .fechaRegistro(curso.getFechaRegistro())
                .build();
    }
}
