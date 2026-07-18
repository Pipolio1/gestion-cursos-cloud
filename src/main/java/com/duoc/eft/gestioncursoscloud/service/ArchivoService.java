package com.duoc.eft.gestioncursoscloud.service;

import com.duoc.eft.gestioncursoscloud.dto.ArchivoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArchivoService {
    ArchivoResponse subirArchivo(MultipartFile file, Long cursoId);
    List<ArchivoResponse> listarArchivos(Long cursoId);
    ArchivoResponse buscarPorId(Long id);
    byte[] descargarArchivo(Long id);
    String generarUrlPublica(Long id, int minutos);
    void eliminarArchivo(Long id);
}
