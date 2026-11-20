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

    // Dead Letter Queue configuration
    @Value("${rabbitmq.dlq.name:activity.dlq}")
    private String dlqName;

    @Value("${rabbitmq.dlx.exchange:activity.dlx}")
    private String dlxName;

    @Value("${rabbitmq.dlx.routing-key:activity.failed}")
    private String dlqRoutingKey;


    // Main activity queue
    @Bean
    public Queue activitiesQueue() {
        return QueueBuilder.durable(queue)
                .deadLetterExchange(dlxName)
                .deadLetterRoutingKey(dlqRoutingKey)
                .build();
    }

    // Main exchange
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    // Main queue binding
    @Bean
    public Binding activitiesBinding() {
        return BindingBuilder.bind(activitiesQueue())
                .to(directExchange())
                .with(routingKey);
    }

    // Dead Letter Exchange
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(dlxName);
    }

    // Dead Letter Queue
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(dlqName).build();
    }

    // Bind DLQ to DLX
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(dlqRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}