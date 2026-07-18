package com.duoc.eft.gestioncursoscloud.controller;

import com.duoc.eft.gestioncursoscloud.dto.ApiResponse;
import com.duoc.eft.gestioncursoscloud.dto.EstudianteRequest;
import com.duoc.eft.gestioncursoscloud.dto.EstudianteResponse;
import com.duoc.eft.gestioncursoscloud.service.EstudianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EstudianteController {

    private final EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EstudianteResponse>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(estudianteService.listarTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteResponse>> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(estudianteService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EstudianteResponse>> crear(@Valid @RequestBody EstudianteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Estudiante creado", estudianteService.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody EstudianteRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Estudiante actualizado", estudianteService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Estudiante eliminado", null));
    }
}
