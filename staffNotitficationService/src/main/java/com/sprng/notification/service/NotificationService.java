package com.sprng.notification.service;

import com.sprng.notification.model.ExceptionMessageFullFail;
import com.sprng.notification.model.StaffStock;
import com.sprng.notification.repository.NotificationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {
//    @KafkaListener(topics={"orderResult","orderResult1","orderResult"},
//        topicPartitions = { @TopicPartition(topic = "orderResult",partitions = "7" })

    KafkaTemplate <Integer,StaffStock> kafkaTemplate;
    NotificationRepository notificationRepository;
    @KafkaListener(topicPartitions = @TopicPartition(topic = "staffStockSuccess",partitions = "0"))
    public void getMessageFromHandler(StaffStock staffStock){
        try {

        if(staffStock.getNeededOrder() == 0){
            // отправляем внутреннему заказчику сообщение в чат, что заказ готов и он может его получить
        } else {
            // рассылка на склад, что необходимо докупить товар и рассылка заказчику, что заказ будет готов
            // через столько-то дней
        }
        } catch (Exception exception){
            kafkaTemplate.send("notificationFail",0,staffStock.getId(),staffStock)
                    .whenComplete(((staffStockSendResult, throwable) -> {
                        // отправляем заказчику, что резервирование не удалось и просьбу повторите заказ
                        if(throwable !=null){
                            ExceptionMessageFullFail exceptionMessageFullFail = new ExceptionMessageFullFail(throwable.getMessage(), LocalDateTime.now(),staffStock);
                            notificationRepository.save(exceptionMessageFullFail);
                        } else {
                            System.out.println("staffStockSendResult = " + staffStockSendResult.getProducerRecord());
                        }
                    }));
        }

//        createOrder - handler()производитель
//        notificationService -> fail -> createOrder(1)
//        handlerService -> create new order ->  createOrder(0)
//        staffStockService -> fail -> orderResult(0)
//        staffStockService -> success -> orderResult(1)
//        staffStockService слушает    createOrder(1)
     }
}


//