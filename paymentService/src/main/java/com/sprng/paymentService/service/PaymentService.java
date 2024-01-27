package com.sprng.paymentService.service;


import com.sprng.library.exception.IshopResponseException;
import com.sprng.paymentService.model.PaymentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    public PaymentService(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private void sendData(String paymentResult) {
        kafkaTemplate.send("paymentServiceTopic", paymentResult)
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

    @KafkaListener(topicPartitions = @TopicPartition(topic = "orderServiceTopic", partitions = "0"))
    public void getResponse(PaymentData paymentData) {
        String response = "";
        if(Math.random() > 0.5){
            response = "success";
        } else {
            response="fail";
        }
        sendData(response);
//        System.out.println("resultPayment === " + resultPayment);
    }
}
