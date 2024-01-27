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
import org.springframework.messaging.handler.annotation.Headers;
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
//        StaffStock staffOrder = new StaffStock(staffStockProductOrder.getNamePosition(), 0);
        try {
            staffStockRepository.findById(staffStockProductOrder.getId()).doOnNext(staffStockFromDB -> {
                staffStockFromDB.setCount(staffStockFromDB.getCount() - staffStockProductOrder.getCount());
                staffStockFromDB.setSuccess(true);
                if (staffStockProductOrder.getCount() > staffStockFromDB.getCount()) {
                    staffStockProductOrder.setNeededOrder(staffStockProductOrder.getCount() - staffStockFromDB.getCount()); // сколько необходимо дозаказать для выполнения заказа
                } else {
                    staffStockProductOrder.setNeededOrder(0);
                }
                staffStockRepository.save(staffStockFromDB);
            });
            kafkaSend("orderSuccess", 0, staffStockProductOrder.getId(), staffStockProductOrder);
        } catch (Exception exception) {
            staffStockProductOrder.setSuccess(false);
            kafkaSend("orderFail", 0, staffStockProductOrder.getId(), staffStockProductOrder);
        }
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "orderFail", partitions = "1")) // для снятия резерва
    public void rollBackDB(@Payload StaffStock staffStock, @Header(KafkaHeaders.RECEIVED_KEY) int id) {
        staffStockRepository.findByNamePosition(staffStock.getNamePosition())
                .doOnNext(staffStockFromDB -> {
                    staffStockFromDB.setCount(staffStockFromDB.getCount() + staffStock.getCount());
                    staffStockRepository.save(staffStockFromDB); // rollBack DB
                });
    }

    private void kafkaSend(String topicName, int partitionNumber, int id, StaffStock staffOrder) {
        kafkaTemplate.send(topicName, partitionNumber, id, staffOrder)
                .whenComplete((result, throwable) -> {
                    System.out.println("result.getProducerRecord().key(); = " + result.getProducerRecord().key());
                    System.out.println("result.topic.name = " + result.getRecordMetadata().topic());
                    if (throwable != null) try {
                        throw new IshopResponseException("staffOrder not write");
                    } catch (IshopResponseException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

}


//проверяем, что количество товара на складе достаточное.

//
// Если товара на складе не достаточно,
// тогда списываем сколько есть и // тогда отправляем уведомление о необходимости до заказа
// (надо 5 штук , а есть 3 штуки. Три штуки отправляем // заказчику и после пополнения отправим
// еще две штуки)  и отправим уведомление о необходимости   // пополнения склада.

// сохрани только карандаши 50 штук и потом отнимай из БД , количество корандашей из заказа пришедшего из
// хендлера  @Payload StaffStock staffStock и отправляй событие "выполнение заказа" в кафку
// И в нотификейшен слушай событие "выполнение заказа" из кафки, создай модель и записывай в БД само сообщение и время прихода сообщения LocalDate.now
