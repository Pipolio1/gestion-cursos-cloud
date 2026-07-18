package com.duoc.eft.gestioncursoscloud.rabbitmq;

import com.duoc.eft.gestioncursoscloud.dto.MensajeColaRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Component
public class ColaConsumidor {

    private final ConcurrentLinkedQueue<MensajeColaRequest> ultimosMensajes = new ConcurrentLinkedQueue<>();

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void recibirMensaje(MensajeColaRequest mensaje) {
        log.info("Mensaje recibido: {}", mensaje);
        ultimosMensajes.add(mensaje);
        if (ultimosMensajes.size() > 50) {
            ultimosMensajes.poll();
        }
    }

    public ConcurrentLinkedQueue<MensajeColaRequest> getUltimosMensajes() {
        return ultimosMensajes;
    }
}
