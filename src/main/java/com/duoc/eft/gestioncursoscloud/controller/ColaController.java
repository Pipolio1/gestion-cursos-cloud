package com.duoc.eft.gestioncursoscloud.controller;

import com.duoc.eft.gestioncursoscloud.dto.ApiResponse;
import com.duoc.eft.gestioncursoscloud.dto.MensajeColaRequest;
import com.duoc.eft.gestioncursoscloud.rabbitmq.ColaConsumidor;
import com.duoc.eft.gestioncursoscloud.rabbitmq.ColaProductor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cola")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ColaController {

    private final ColaProductor colaProductor;
    private final ColaConsumidor colaConsumidor;

    @PostMapping("/enviar")
    public ResponseEntity<ApiResponse<String>> enviar(@Valid @RequestBody MensajeColaRequest mensaje) {
        colaProductor.enviarMensaje(mensaje);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Mensaje enviado a la cola", mensaje.getContenido()));
    }

    @GetMapping("/ultimos")
    public ResponseEntity<ApiResponse<List<MensajeColaRequest>>> ultimos() {
        return ResponseEntity.ok(ApiResponse.ok(
                "Ultimos mensajes consumidos",
                List.copyOf(colaConsumidor.getUltimosMensajes())
        ));
    }
}
