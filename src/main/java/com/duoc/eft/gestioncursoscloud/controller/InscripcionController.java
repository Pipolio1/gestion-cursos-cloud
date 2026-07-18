package com.duoc.eft.gestioncursoscloud.controller;

import com.duoc.eft.gestioncursoscloud.dto.ApiResponse;
import com.duoc.eft.gestioncursoscloud.dto.InscripcionRequest;
import com.duoc.eft.gestioncursoscloud.dto.InscripcionResponse;
import com.duoc.eft.gestioncursoscloud.service.InscripcionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<InscripcionResponse>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(inscripcionService.listarTodas()));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<ApiResponse<List<InscripcionResponse>>> listarPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(ApiResponse.ok(inscripcionService.listarPorEstudiante(estudianteId)));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<ApiResponse<List<InscripcionResponse>>> listarPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(ApiResponse.ok(inscripcionService.listarPorCurso(cursoId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InscripcionResponse>> inscribir(@Valid @RequestBody InscripcionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Inscripcion realizada", inscripcionService.inscribir(request)));
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<ApiResponse<InscripcionResponse>> completar(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Inscripcion completada", inscripcionService.completar(id)));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ApiResponse<InscripcionResponse>> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Inscripcion cancelada", inscripcionService.cancelar(id)));
    }
}
