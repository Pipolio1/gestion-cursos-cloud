package com.duoc.eft.gestioncursoscloud.service.impl;

import com.duoc.eft.gestioncursoscloud.dto.EstudianteRequest;
import com.duoc.eft.gestioncursoscloud.dto.EstudianteResponse;
import com.duoc.eft.gestioncursoscloud.model.Estudiante;
import com.duoc.eft.gestioncursoscloud.repository.EstudianteRepository;
import com.duoc.eft.gestioncursoscloud.service.EstudianteService;
import com.duoc.eft.gestioncursoscloud.util.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteResponse> listarTodos() {
        return estudianteRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteResponse buscarPorId(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con id: " + id));
        return toResponse(estudiante);
    }

    @Override
    @Transactional
    public EstudianteResponse crear(EstudianteRequest request) {
        Estudiante estudiante = Estudiante.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .build();
        return toResponse(estudianteRepository.save(estudiante));
    }

    @Override
    @Transactional
    public EstudianteResponse actualizar(Long id, EstudianteRequest request) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con id: " + id));
        estudiante.setNombre(request.getNombre());
        estudiante.setEmail(request.getEmail());
        return toResponse(estudianteRepository.save(estudiante));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con id: " + id));
        estudianteRepository.delete(estudiante);
    }

    private EstudianteResponse toResponse(Estudiante estudiante) {
        return EstudianteResponse.builder()
                .id(estudiante.getId())
                .nombre(estudiante.getNombre())
                .email(estudiante.getEmail())
                .fechaRegistro(estudiante.getFechaRegistro())
                .build();
    }
}
