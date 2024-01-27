package com.sprng.staff.stock.service;

import com.sprng.library.exception.IshopResponseException;
import com.sprng.staff.stock.model.StaffStock;
import com.sprng.staff.stock.repository.StaffStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class StaffStockService {
    private KafkaTemplate<Integer, StaffStock> kafkaTemplate;
    StaffStockRepository staffStockRepository;

    @Autowired
    public StaffStockService(KafkaTemplate<Integer, StaffStock> kafkaTemplate, StaffStockRepository staffStockRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.staffStockRepository = staffStockRepository;
    }

    @Bean
    public void saveStackStock() {
        StaffStock staffStockInit = new StaffStock("pencil", 50);
        staffStockRepository.save(staffStockInit);
    }


    @KafkaListener(topicPartitions = @TopicPartition(topic = "createOrder", partitions = "0"))
    public void getResponse(@Payload StaffStock staffStockProductOrder, @Header(KafkaHeaders.RECEIVED_KEY) int key) {
        StaffStock staffOrder = new StaffStock(staffStockProductOrder.getNamePosition(), 0);
        staffStockRepository.findById(staffStockProductOrder.getId()).doOnNext(staffStockFromDB -> {
            if (staffStockProductOrder.getCount() > staffStockFromDB.getCount()) {
                staffOrder.setCount(staffStockFromDB.getCount());
                staffStockFromDB.setCount(0);
                staffStockRepository.save(staffStockFromDB);
            } else {
                staffOrder.setCount(staffStockProductOrder.getCount());
                staffStockFromDB.setCount(staffStockFromDB.getCount() - staffStockProductOrder.getCount());
                staffStockRepository.save(staffStockFromDB);
            }

            kafkaTemplate.send("createOrder", staffOrder.getId(), staffOrder)
                    .whenComplete((result, throwable) -> {
                        System.out.println("result.getProducerRecord().key(); = " + result.getProducerRecord().key());
                        System.out.println("result.topic.name = " + result.getRecordMetadata().topic());
                        if (throwable != null) try {
//                            orderHandlerRepository.delete(order);
                            throw new IshopResponseException("staffOrder not write");
                        } catch (IshopResponseException e) {
                            throw new RuntimeException(e);
                        }
                    });


        });

        //проверяем, что количество товара на складе достаточное.

        //
        // Если товара на складе не достаточно,
        // тогда списываем сколько есть и // тогда отправляем уведомление о необходимости до заказа
        // (надо 5 штук , а есть 3 штуки. Три штуки отправляем // заказчику и после пополнения отправим
        // еще две штуки)  и отправим уведомление о необходимости   // пополнения склада.

        // сохрани только карандаши 50 штук и потом отнимай из БД , количество корандашей из заказа пришедшего из
        // хендлера  @Payload StaffStock staffStock и отправляй событие "выполнение заказа" в кафку
        // И в нотификейшен слушай событие "выполнение заказа" из кафки, создай модель и записывай в БД само сообщение и время прихода сообщения LocalDate.now


    }
}

