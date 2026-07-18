package com.duoc.eft.gestioncursoscloud.service.impl;

import com.duoc.eft.gestioncursoscloud.dto.ArchivoResponse;
import com.duoc.eft.gestioncursoscloud.model.Archivo;
import com.duoc.eft.gestioncursoscloud.model.Curso;
import com.duoc.eft.gestioncursoscloud.repository.ArchivoRepository;
import com.duoc.eft.gestioncursoscloud.repository.CursoRepository;
import com.duoc.eft.gestioncursoscloud.s3.S3StorageService;
import com.duoc.eft.gestioncursoscloud.service.ArchivoService;
import com.duoc.eft.gestioncursoscloud.util.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArchivoServiceImpl implements ArchivoService {

    private final ArchivoRepository archivoRepository;
    private final CursoRepository cursoRepository;
    private final S3StorageService s3StorageService;

    @Override
    @Transactional
    public ArchivoResponse subirArchivo(MultipartFile file, Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con id: " + cursoId));

        String key = cursoId + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        try {
            s3StorageService.subirArchivo(file, key);
        } catch (IOException e) {
            throw new RuntimeException("Error al subir archivo a S3", e);
        }

        Archivo archivo = Archivo.builder()
                .nombre(file.getOriginalFilename())
                .s3Key(key)
                .tipoContenido(file.getContentType())
                .curso(curso)
                .build();
        return toResponse(archivoRepository.save(archivo));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArchivoResponse> listarArchivos(Long cursoId) {
        return archivoRepository.findByCursoId(cursoId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ArchivoResponse buscarPorId(Long id) {
        Archivo archivo = archivoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Archivo no encontrado con id: " + id));
        return toResponse(archivo);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] descargarArchivo(Long id) {
        Archivo archivo = archivoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Archivo no encontrado con id: " + id));
        try (var stream = s3StorageService.descargarArchivo(archivo.getS3Key())) {
            return stream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error al descargar archivo de S3", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String generarUrlPublica(Long id, int minutos) {
        Archivo archivo = archivoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Archivo no encontrado con id: " + id));
        return s3StorageService.generarUrlPresignada(archivo.getS3Key(), Duration.ofMinutes(minutos));
    }

    @Override
    @Transactional
    public void eliminarArchivo(Long id) {
        Archivo archivo = archivoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Archivo no encontrado con id: " + id));
        s3StorageService.eliminarArchivo(archivo.getS3Key());
        archivoRepository.delete(archivo);
    }

    private ArchivoResponse toResponse(Archivo archivo) {
        return ArchivoResponse.builder()
                .id(archivo.getId())
                .nombre(archivo.getNombre())
                .s3Key(archivo.getS3Key())
                .urlPublica(archivo.getUrlPublica())
                .tipoContenido(archivo.getTipoContenido())
                .cursoId(archivo.getCurso() != null ? archivo.getCurso().getId() : null)
                .fechaSubida(archivo.getFechaSubida())
                .build();
    }
}
