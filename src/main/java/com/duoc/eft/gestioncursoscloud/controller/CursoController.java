package com.duoc.eft.gestioncursoscloud.controller;

import com.duoc.eft.gestioncursoscloud.dto.ApiResponse;
import com.duoc.eft.gestioncursoscloud.dto.CursoRequest;
import com.duoc.eft.gestioncursoscloud.dto.CursoResponse;
import com.duoc.eft.gestioncursoscloud.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CursoResponse>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(cursoService.listarTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CursoResponse>> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(cursoService.buscarPorId(id)));
    }

    @GetMapping("/instructor/{email}")
    public ResponseEntity<ApiResponse<List<CursoResponse>>> listarPorInstructor(@PathVariable String email) {
        return ResponseEntity.ok(ApiResponse.ok(cursoService.listarPorInstructor(email)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CursoResponse>> crear(@Valid @RequestBody CursoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Curso creado", cursoService.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CursoResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody CursoRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Curso actualizado", cursoService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Curso eliminado", null));
    }
}
