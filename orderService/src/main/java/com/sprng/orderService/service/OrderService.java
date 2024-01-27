package com.sprng.orderService.service;

import com.sprng.library.exception.IshopResponseException;
import com.sprng.orderService.model.CustomerOrder;
import com.sprng.orderService.model.OrderDataDTO;
import com.sprng.orderService.model.PaymentData;
import com.sprng.orderService.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ClientProductService clientProductService;
    private KafkaTemplate<String,PaymentData> kafkaTemplate;
    private final ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    public OrderService(OrderRepository orderRepository, ClientProductService clientProductService, KafkaTemplate<String, PaymentData> kafkaTemplate, ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.orderRepository = orderRepository;
        this.clientProductService = clientProductService;
        this.kafkaTemplate = kafkaTemplate;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    private void saveCustomerOrderInDB(List<OrderDataDTO> orderDataListFromClient, JwtAuthenticationToken token) {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderDataDTO(orderDataListFromClient);
        customerOrder.setCreateOrder(LocalDateTime.now());
        customerOrder.setStatusOrder(CustomerOrder.StatusOrder.NEW);
        customerOrder.setUserNameCustomer(token.getToken().getSubject());
        orderRepository.save(customerOrder);
    }
    @CircuitBreaker(name="getClientProductServiceGroup", fallbackMethod = "getDefaultData") // указываем потенциально опасный участок кода, в рамках которого мы анализируем количество не успешных запросов к сервису  clientProductService.getCountProduct
    // и если количество не успешных запросов больше значения указанного в com.sprng.orderService.config;KafkaConfig.defaultCustomizer()
    // метод  .failureRateThreshold(15.9F).build()  тогда мы перестаём отправлять запросы к этому сервису.
    // Запросу к этому сервису из разных частей кода(разных методов) мы можем объединить в группу и анализировать все
    // запросы в рамках группы. В аннтоации @CircuitBreaker параметр fallbackMethod = "getDefaultData" говорит, что возвращать по умолчанию если сервис createNewOrder считается не активным и в ответ обращение к нему мы возвращаем дефолтные значение


    //вместо аннотации можем указать вызов метода circuitBreakerFactory


    public List<OrderDataDTO> createNewOrder(List<OrderDataDTO> orderDataListFromClient, JwtAuthenticationToken token) {
        circuitBreakerFactory
                .create("getClientProductServiceGroup")
                .run(
        clientProductService.getCountProduct(orderDataListFromClient)
                .doOnNext(orderDataFromDB -> {
                    orderDataListFromClient.stream()
                            .peek(orderDataFromClient -> {
                                if (orderDataFromClient.getIdProduct() == orderDataFromDB.getIdProduct()) {
                                    orderDataFromClient.setIdProduct(orderDataFromClient.getIdProduct());
                                    if (orderDataFromClient.getCountProduct() > orderDataFromDB.getCountProduct()) {
                                        orderDataFromClient.setCountProduct(orderDataFromDB.getCountProduct());
                                    }
                                    orderDataFromClient.setCountProduct(orderDataFromClient.getCountProduct());
                                }

                            })
                            .toList();

                })
        ,throwable -> getDefaultData());
        saveCustomerOrderInDB(orderDataListFromClient, token);
        return orderDataListFromClient;
    }

    public Flux<OrderDataDTO> getDefaultData(){
        return Flux.empty();
//        return new ArrayList<OrderDataDTO>();
    }

    public void payOrder(PaymentData paymentData) {

    }

    private void sendData(PaymentData paymentData) {
        kafkaTemplate.send("orderServiceTopic", paymentData)
                .whenComplete((result, throwable) -> {
                    System.out.println("result.getProducerRecord().key(); = "  + result.getProducerRecord().key());
                    System.out.println("result.topic.name = "  + result.getRecordMetadata().topic());
                    if(throwable != null) try {
                        throw new IshopResponseException("some wrong");
                    } catch (IshopResponseException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "paymentServiceTopic", partitions = "0"))
    public void getResponse(String resultPayment) {
        System.out.println("resultPayment === " + resultPayment);
    }



}

