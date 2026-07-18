package com.duoc.eft.gestioncursoscloud.controller;

import com.duoc.eft.gestioncursoscloud.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class HomeController {

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, String>>> home() {
        return ResponseEntity.ok(ApiResponse.ok(Map.of(
                "mensaje", "Gestion de Cursos Cloud Native",
                "version", "1.0.0",
                "status", "UP"
        )));
    }
}
