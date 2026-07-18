package com.duoc.eft.gestioncursoscloud.controller;

import com.duoc.eft.gestioncursoscloud.dto.ApiResponse;
import com.duoc.eft.gestioncursoscloud.dto.CalificacionRequest;
import com.duoc.eft.gestioncursoscloud.dto.CalificacionResponse;
import com.duoc.eft.gestioncursoscloud.service.CalificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CalificacionController {

    private final CalificacionService calificacionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CalificacionResponse>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(calificacionService.listarTodas()));
    }

    @GetMapping("/examen/{examenId}")
    public ResponseEntity<ApiResponse<List<CalificacionResponse>>> listarPorExamen(@PathVariable Long examenId) {
        return ResponseEntity.ok(ApiResponse.ok(calificacionService.listarPorExamen(examenId)));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<ApiResponse<List<CalificacionResponse>>> listarPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(ApiResponse.ok(calificacionService.listarPorEstudiante(estudianteId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CalificacionResponse>> registrar(@Valid @RequestBody CalificacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Calificacion registrada", calificacionService.registrar(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CalificacionResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody CalificacionRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Calificacion actualizada", calificacionService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        calificacionService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Calificacion eliminada", null));
    }
}
