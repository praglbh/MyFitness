package com.fitness.aiservice.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;



    // Main activity queue
    @Bean
    public Queue activityQueue() {
        return new Queue(queue, true);
    }

    // Main exchange
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    // Main queue binding
    @Bean
    public Binding activityBinding(Queue activityQueue, DirectExchange activityExchange) {
        return BindingBuilder.bind(activityQueue).to(activityExchange).with(routingKey);
    }
    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}