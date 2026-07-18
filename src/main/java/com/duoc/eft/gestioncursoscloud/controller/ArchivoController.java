package com.duoc.eft.gestioncursoscloud.controller;

import com.duoc.eft.gestioncursoscloud.dto.ApiResponse;
import com.duoc.eft.gestioncursoscloud.dto.ArchivoResponse;
import com.duoc.eft.gestioncursoscloud.service.ArchivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/archivos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ArchivoController {

    private final ArchivoService archivoService;

    @PostMapping("/subir")
    public ResponseEntity<ApiResponse<ArchivoResponse>> subir(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("cursoId") Long cursoId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Archivo subido exitosamente", archivoService.subirArchivo(archivo, cursoId)));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<ApiResponse<List<ArchivoResponse>>> listarPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(ApiResponse.ok(archivoService.listarArchivos(cursoId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArchivoResponse>> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(archivoService.buscarPorId(id)));
    }

    @GetMapping("/{id}/descargar")
    public ResponseEntity<byte[]> descargar(@PathVariable Long id) {
        ArchivoResponse archivo = archivoService.buscarPorId(id);
        byte[] contenido = archivoService.descargarArchivo(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(archivo.getTipoContenido() != null ? archivo.getTipoContenido() : "application/octet-stream"));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(archivo.getNombre()).build());
        return new ResponseEntity<>(contenido, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}/url")
    public ResponseEntity<ApiResponse<String>> urlPublica(@PathVariable Long id, @RequestParam(defaultValue = "15") int minutos) {
        return ResponseEntity.ok(ApiResponse.ok("URL generada", archivoService.generarUrlPublica(id, minutos)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        archivoService.eliminarArchivo(id);
        return ResponseEntity.ok(ApiResponse.ok("Archivo eliminado", null));
    }
}
