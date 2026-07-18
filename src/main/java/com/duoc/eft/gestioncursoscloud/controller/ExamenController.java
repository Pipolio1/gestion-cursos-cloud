package com.duoc.eft.gestioncursoscloud.controller;

import com.duoc.eft.gestioncursoscloud.dto.ApiResponse;
import com.duoc.eft.gestioncursoscloud.dto.ExamenRequest;
import com.duoc.eft.gestioncursoscloud.dto.ExamenResponse;
import com.duoc.eft.gestioncursoscloud.service.ExamenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examenes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExamenController {

    private final ExamenService examenService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExamenResponse>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(examenService.listarTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExamenResponse>> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(examenService.buscarPorId(id)));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<ApiResponse<List<ExamenResponse>>> listarPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(ApiResponse.ok(examenService.listarPorCurso(cursoId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ExamenResponse>> crear(@Valid @RequestBody ExamenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Examen creado", examenService.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExamenResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody ExamenRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Examen actualizado", examenService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        examenService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Examen eliminado", null));
    }
}
