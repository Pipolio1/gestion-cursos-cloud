package com.duoc.eft.gestioncursoscloud.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "cola.gestion.cursos";
    public static final String EXCHANGE_NAME = "exchange.gestion.cursos";
    public static final String ROUTING_KEY = "routing.gestion.cursos";

    @Bean
    public Queue colaGestionCursos() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange exchangeGestionCursos() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingCola() {
        return BindingBuilder
                .bind(colaGestionCursos())
                .to(exchangeGestionCursos())
                .with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
