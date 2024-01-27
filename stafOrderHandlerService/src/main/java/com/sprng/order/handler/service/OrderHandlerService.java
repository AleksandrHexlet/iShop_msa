package com.sprng.order.handler.service;

import com.sprng.library.exception.IshopResponseException;
import com.sprng.order.handler.events.OrderEvent;
import com.sprng.order.handler.model.Order;
import com.sprng.order.handler.repository.OrderHandlerRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
@EnableScheduling()
public class OrderHandlerService {

    private OrderHandlerRepository orderHandlerRepository;
    private KafkaTemplate<Integer, OrderEvent> kafkaTemplate;

    @Autowired
    public OrderHandlerService(OrderHandlerRepository orderHandlerRepository, KafkaTemplate<Integer, OrderEvent> kafkaTemplate) {
        this.orderHandlerRepository = orderHandlerRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 3, timeUnit = TimeUnit.MINUTES)
//    @Scheduled(cron = "0 15 10 15 * ?")
//    @Scheduled(fixedRate = 1000)
    public void createOrderWithScheduling() {
        int id = randomNumber();
        Order order = new Order(id, "order_" + id, 4, "employeesNumber_" + id);
         orderHandlerRepository.save(order).doOnNext(savedOrder-> {
             OrderEvent orderEvent = new OrderEvent(order.getNamePosition(), order.getCount(), order.getEmployeesNumber());

        kafkaTemplate.send("createOrder", order.getId(), orderEvent)
                .whenComplete((result, throwable) -> {
                    System.out.println("result.getProducerRecord().key(); = " + result.getProducerRecord().key());
                    System.out.println("result.topic.name = " + result.getRecordMetadata().topic());
                    if (throwable != null) try {
                        orderHandlerRepository.delete(order);
                        throw new IshopResponseException("createOrder not write");
                    } catch (IshopResponseException e) {
                        throw new RuntimeException(e);
                    }
                });
         });
    }

    private int randomNumber() {
        return (int) (Math.random() * 10000);
    }
}


//    private void sendData(PaymentData paymentData) {
//        kafkaTemplate.send("orderServiceTopic", paymentData)
//                .whenComplete((result, throwable) -> {
//                    System.out.println("result.getProducerRecord().key(); = "  + result.getProducerRecord().key());
//                    System.out.println("result.topic.name = "  + result.getRecordMetadata().topic());
//                    if(throwable != null) try {
//                        throw new IshopResponseException("some wrong");
//                    } catch (IshopResponseException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//    }
//
//    @KafkaListener(topicPartitions = @TopicPartition(topic = "paymentServiceTopic", partitions = "0"))
//    public void getResponse(String resultPayment) {
//        System.out.println("resultPayment === " + resultPayment);
//    }

