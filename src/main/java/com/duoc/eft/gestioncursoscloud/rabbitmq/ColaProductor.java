package com.duoc.eft.gestioncursoscloud.rabbitmq;

import com.duoc.eft.gestioncursoscloud.dto.MensajeColaRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColaProductor {

    private final RabbitTemplate rabbitTemplate;

    public void enviarMensaje(MensajeColaRequest mensaje) {
        log.info("Enviando mensaje a cola {}: {}", RabbitMQConfig.QUEUE_NAME, mensaje);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                mensaje
        );
    }
}
