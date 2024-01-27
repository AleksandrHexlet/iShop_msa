package com.sprng.notification.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaConfig {


//    @Bean
//    public NewTopic orderNotification() {
//        return TopicBuilder.name("orderNotification")
//                .partitions(2) // число разделов внутри топика по количеству получателей, чтобы они не ждали в очередеи
//                .replicas(1) //  количество инстансов топика, делаем если большая нагрузка на топик и тогда брокер сам раскидает задачи между топиками
//                .build();
//    }




}
