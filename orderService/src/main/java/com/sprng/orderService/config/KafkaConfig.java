package com.sprng.orderService.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaConfig {


    @Bean
    public NewTopic orderService() {
        return TopicBuilder.name("orderServiceTopic")
                .partitions(2) // число разделов внутри топика по количеству получателей, чтобы они не ждали в очередеи
                .replicas(1) //  количество инстансов топика
                .build();
    }

    @Bean
    public NewTopic paymentService() {
        return TopicBuilder.name("paymentServiceTopic")
                .partitions(1) // число разделов внутри топика по количеству получателей, чтобы они не ждали в очередеи
                .replicas(1) //  количество инстансов топика
                .build();
    }


    @Bean
    Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id).circuitBreakerConfig(
                CircuitBreakerConfig
                        .from(CircuitBreakerConfig.ofDefaults()).slidingWindowSize(50) // оценка последних 50 запросов
                        .failureRateThreshold(15.9F).build() // если 15.9% из этих 50 запросов неактивны(не пришел ответ), тогда сервис считается не исправным и мы перестаём посылать запросы на него. Вместо обычных запросов мы отправляем тестовые запросы и ждём пока процент не удачных тестовых запросов будет меньше указанного значение в методе failureRateThreshold(15.9F). Функционал повторных запросов предоставляет библиотека org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j:3.1.0
        ).build());
    }


}
