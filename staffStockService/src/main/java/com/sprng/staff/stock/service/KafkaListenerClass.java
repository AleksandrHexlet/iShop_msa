package com.sprng.staff.stock.service;

import com.sprng.staff.stock.model.StaffStock;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topicPartitions = @TopicPartition(topic = "createOrder", partitions = "0"), containerFactory="BeanName " +
        "с описанием как фильтровать входящие данные")  // тип данных StaffStock
@KafkaListener(topicPartitions = @TopicPartition(topic = "staffStockSuccess", partitions = "0")) // тип данных StaffStock1
@KafkaListener(topicPartitions = @TopicPartition(topic = "staffStockFail", partitions = "0")) // тип данных StaffStock2
public class KafkaListenerClass {


    @KafkaHandler
    public StaffStock listener(){
        // метод поймёт, что к нему пришли данные по типу возвращаемых данных из метода и сравнит тип данных с
        // типом данных которые пришли в KafkaListener(слушатель)
        return null;
    };
//    @KafkaHandler
//    public StaffStock1 listener(){
//        //
//    }
//    @KafkaHandler
//    public StaffStock2 listener(){
//        //
//    }
}
